# github地址

project

https://github.com/LordSl/price-unit

https://gitee.com/lconq/price-unit

clone

https://github.com/LordSl/price-unit.git

https://gitee.com/lconq/price-unit.git

# 示意图

![price-unit](https://gitee.com/lconq/my-img-oss/raw/master/img/price-unit.png)

# 使用说明

## Handler

需实现HandlerModel接口

```java
package

...

import ...

@Component
@Unit(order = {"1.3", "2.5"}, flow = {FlowAlpha.class, FlowBeta.class})
public class HandlerX implements HandlerModel {

    @Produce(name = "user_order")
    OrderDTO orderDTO;

    @Consume
    Integer ReqTime;

    @Through
    String userName;

    @Refer
    @Autowired
    Hello hello;

    public HandlerX() {
        init();//调用init()完成注册
    }

  	/*
  	public HandlerX(Integer mark) {
    }

    @Override
    public HandlerModel getTemplate() {
        return new HandlerX(1);
    }
    */

    @Override
    public void handle() {
        hello.hello();
        userName = String.format("尊敬的%s先生/女士", userName);
        orderDTO = new orderDTO()
                .setUserName(userName)
                .setReqTime(reqTime)
                .build();
    }
}
```

实现handle()方法，完成计算

可选择实现getTemplate()方法，该方法用于提供一个“干净”的handler对象（所有字段为null），不实现该方法时，会默认以无参构造方法代替

### @Unit

表明该类为一个Handler单元

有两个必填字段，order和flow，order表示优先级，flow表示注册到哪个FlowModel

*order越小越优先执行，如果两个Handler的order相差小于0.1，则较大的会覆盖较小的*

### @Consume

**从flow中消费一个KV对**

在handle()方法执行前，从上下文容器Container获取一个KV对的V值，注入到成员变量中，K值由name决定（默认为成员变量名）

*获取后会从Container中删除该K-V对*

### @Produce

**生产一个KV对到flow中**

在handle()方法执行后，将成员变量的值作为K-V对的V值，注入回上下文容器Container中，K值由name决定（默认为成员变量名）

### @Through

**使一个KV对经过该handler**

在handle()方法执行前，从上下文容器Container获取值，注入到成员变量中，**不删除**

若该成员变量是**引用类型**，则不会再进行其他操作

若该成员变量是**基本类型**及其**装箱类**，则会在handle()方法执行后，将成员变量注入回上下文容器Container中

### @Refer

**引用一个外部bean**

与@Autowired @Resource等配合使用，使lamda函数在执行时能访问到spring注入的bean

*引用的bean不在上下文容器Container中，而是在一个全局静态容器中*

## Flow

需实现FlowModel接口

```java
package

...

import ...

@Component
public class FlowAlpha implements FlowModel {
    public void exec(...) {
        Container container = new Container();
        //todo 填入初始值
        container = execAsChain(container);
        //todo 获取结果
        //do sth
    }
}
```

不需要实现任何方法

提供了execAsChain()方法，表示将注册到该flow类中的所有lamda函数链式执行

# 开发中的功能

## schema

将各lamda表达式的输入输出、flow的结构信息，输出为一个json文件

## debug

python实现，根据schema.json，查找flow错误，如KV同名不同类，未@Produce就@Through和@Consume等

## 可视化

python实现，根据schema.json，生成flow数据流图

## 影响评估

python实现，输入key，返回该key若改变会影响到下游的哪些handler

## 非链式执行

lamda函数不再只以链式执行，而是提供多种数据结构，包括

链式

决策树

网格

...

## 自动并行优化

**该功能可选，且有一定风险**

@Through和@Refer增加一个可选字段op

op="r"表示对该KV进行读操作

op="w"表示写操作

读操作R

@Consume @Through(op="r") @Refer(op="r")

写操作W

@Produce @Through(op="w") @Refer(op="w")

优化器会自动分析各handler的依赖关系，如果两个handler不存在W->R或W->W的关系，则会让它们并行执行

**优化器不会判断“r“和“w”的正确性，java的语言特性导致，只要传递了引用，对象就有可能被改变，请自行确保其正确性**

## 池化处理

**该功能与自动并行优化配合使用**

并行优化后，per request会产生多个thread，在高并发的情况下可能会导致资源耗尽，建议做池化处理

一个flow一个线程池，各个handler初始时均分，根据执行情况再动态分配，避免某个handler成为瓶颈

每个handler伴有一个任务队列，其中放置上下文容器Container，handler担当消费者

