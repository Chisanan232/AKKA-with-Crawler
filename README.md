# AKKA-with-Crawler

### Description
This is a sample about how to let framework AKKA integrate with Crawler. <br>
<br>

### Motivation
Study for framework AKKA and integrate it with crawler to implement a sample. <br>
<br>

### Skills
This project classifies program to 2 parts. One is crawler, another one is other prcesses logic-implements (like Multiple Actors relationship, Send message mechanism and build Kafka producer, consumer, etc). <br>

#### Environment
OS: MacOS (Current Version: 10.14.5)

#### Crawler
Language: Python <br>
Version: 3.7 up <br>
Framework: Requests <br>


#### All other logic-Implements
Language: Scala <br>
Version: 2.12 <br>
Framework: Spark (version: 2.4.5), AKKA (version: 2.4.20) <br>
<br>


It's easy and convenience to develop crawler code by Python. For architecturing program with functional and high perfermence, must be AKKA. <br>
This project architecture like a boss-employee relationship. Please refer to the below: <br>
<br>

![](https://github.com/Chisanan232/Akka-with-Crawler/raw/master/docs/imgs/GoogleMap_Cafe_Decentralized_Crawler_Diagram-Akka_Actors_Tree.png)

<br>
The sofrware architecture like: <br>
    Master ---> Worker Leader(s) ---> Worker(s)

* Master <br>
The boss of all actors. It builds and manages worker leaders. <br>

* Worker Leaders <br>
Build workers and distribute job to them to work. <br>

* Workers <br>
Receive task and essentailly work the content. <br>

By the way, developers could customize methods be needed in every actors. 


AKKA CookBook
===
It's just a simple start for every developer to quickly understand how to implement AKKA actor with API.

Build AKKA Actor System
---
It's necessary to create a system and build AKKA actors tree in it. <br>

```scala
import akka.actor.{ActorSystem, Props}

val system = ActorSystem(AkkaConfig.SystemName)
```

Build AKKA Actor 
---

First of all, it creates a master actor which is the top of the tree after builds a system, in other words, all actors be created in it later are its children. <br>

```scala
val king = system.actorOf(Props[CrawlerKing], "Master-Actor")
```

> Here like build a mailbox and other actor will send message to it. <br>

However, it should announce the AKKA actor object before create it. <br>
For example, it could do that like below: <br>

```scala
// Define message and announce content
trait Msg {content: String}
case class TestMsg (content: String) extends Msg

// Define a AKKA actor
class CrawlerKing extends Actor{
    override def receive: Receive = {
        case TestMsg =>
            println("I got the message!")
    }
}
```

Method *receive* is a abstract method in trait *Actor* and it should be overrided. It decides that what message it will receive. <br>

> Here like define what mail it accepts. <br>

Let's send message to it. <br>

Send Message
---
For AKKA, the send message is very easy and simple. Just use *!*.

```scala
king ! TestMsg
```

### Project Program Running-Result 

Below are the log message of running-result: <br>

    (Some Spark Log Message)
    ......
    2020-07-14 15:14:30 INFO  MemoryStore:54 - MemoryStore cleared
    2020-07-14 15:14:30 INFO  BlockManager:54 - BlockManager stopped
    2020-07-14 15:14:30 INFO  BlockManagerMaster:54 - BlockManagerMaster stopped
    2020-07-14 15:14:30 INFO  OutputCommitCoordinator$OutputCommitCoordinatorEndpoint:54 - OutputCommitCoordinator stopped!
    2020-07-14 15:14:30 INFO  SparkContext:54 - Successfully stopped SparkContext
    2020-07-14 15:14:30 INFO  SparkContext:54 - SparkContext already stopped.
    [INFO] [07/14/2020 15:14:30.812] [run-main-0] [akka.remote.Remoting] Starting remoting
    [INFO] [07/14/2020 15:14:30.941] [run-main-0] [akka.remote.Remoting] Remoting started; listening on addresses :[akka.tcp://CauchySystem@127.0.0.1:56318]
    [INFO] [07/14/2020 15:14:30.942] [run-main-0] [akka.remote.Remoting] Remoting now listens on addresses: [akka.tcp://CauchySystem@127.0.0.1:56318]
    [INFO] [07/14/2020 15:14:31.058] [run-main-0] [king.log.stream] The crawler King actor (a.k.a Master Actor) name is King.
    [INFO] [07/14/2020 15:14:31.059] [run-main-0] [king.log.stream] Program Finish!
    [INFO] [07/14/2020 15:14:31.061] [CauchySystem-akka.actor.default-dispatcher-2] [akka.tcp://CauchySystem@127.0.0.1:56318/user/King] The task amount is: 2.0
    [INFO] [07/14/2020 15:14:31.061] [CauchySystem-akka.actor.default-dispatcher-2] [akka.tcp://CauchySystem@127.0.0.1:56318/user/King] The task conditions we need to use is List(1101, 1102)
    [INFO] [07/14/2020 15:14:31.073] [CauchySystem-akka.actor.default-dispatcher-4] [akka.tcp://CauchySystem@127.0.0.1:56318/user/King/crawler_Paladin_0] Here is Paladin !
    [INFO] [07/14/2020 15:14:31.074] [CauchySystem-akka.actor.default-dispatcher-2] [akka.tcp://CauchySystem@127.0.0.1:56318/user/King] Response from Paladin: I'm akka://CauchySystem/user/King/crawler_Paladin_0 and I'm ready !
    [INFO] [07/14/2020 15:14:31.074] [CauchySystem-akka.actor.default-dispatcher-2] [akka.tcp://CauchySystem@127.0.0.1:56318/user/King] [King] Thank you Paladin crawler_Paladin_0
    [INFO] [07/14/2020 15:14:31.074] [CauchySystem-akka.actor.default-dispatcher-2] [akka.tcp://CauchySystem@127.0.0.1:56318/user/King] Send task to worker from managers.
    [INFO] [07/14/2020 15:14:31.075] [CauchySystem-akka.actor.default-dispatcher-3] [akka.tcp://CauchySystem@127.0.0.1:56318/user/King/crawler_Paladin_0] [P] content: This is your task parameters.
    [INFO] [07/14/2020 15:14:31.075] [CauchySystem-akka.actor.default-dispatcher-3] [akka.tcp://CauchySystem@127.0.0.1:56318/user/King/crawler_Paladin_0] [P] taskAmount: 1.0
    [INFO] [07/14/2020 15:14:31.075] [CauchySystem-akka.actor.default-dispatcher-3] [akka.tcp://CauchySystem@127.0.0.1:56318/user/King/crawler_Paladin_0] [P] taskCondition: List(1101)
    [INFO] [07/14/2020 15:14:31.075] [CauchySystem-akka.actor.default-dispatcher-4] [akka.tcp://CauchySystem@127.0.0.1:56318/user/King/crawler_Paladin_1] Here is Paladin !
    [INFO] [07/14/2020 15:14:31.075] [CauchySystem-akka.actor.default-dispatcher-3] [akka.tcp://CauchySystem@127.0.0.1:56318/user/King/crawler_Paladin_0] context.self.path.name: crawler_Paladin_0
    [INFO] [07/14/2020 15:14:31.075] [CauchySystem-akka.actor.default-dispatcher-3] [akka.tcp://CauchySystem@127.0.0.1:56318/user/King/crawler_Paladin_0] context.self.path.name: akka://CauchySystem
    [INFO] [07/14/2020 15:14:31.075] [CauchySystem-akka.actor.default-dispatcher-3] [akka.tcp://CauchySystem@127.0.0.1:56318/user/King/crawler_Paladin_0] context.parent: Actor[akka://CauchySystem/user/King#-574344648]
    [INFO] [07/14/2020 15:14:31.075] [CauchySystem-akka.actor.default-dispatcher-3] [akka.tcp://CauchySystem@127.0.0.1:56318/user/King/crawler_Paladin_0] context.parent.path.name: King
    [INFO] [07/14/2020 15:14:31.075] [CauchySystem-akka.actor.default-dispatcher-2] [akka.tcp://CauchySystem@127.0.0.1:56318/user/King] Response from Paladin: I'm akka://CauchySystem/user/King/crawler_Paladin_1 and I'm ready !
    [INFO] [07/14/2020 15:14:31.075] [CauchySystem-akka.actor.default-dispatcher-2] [akka.tcp://CauchySystem@127.0.0.1:56318/user/King] [King] Thank you Paladin crawler_Paladin_1
    [P] All soldier: soldier_0
    [P] All soldier: soldier_0
    [INFO] [07/14/2020 15:14:31.075] [CauchySystem-akka.actor.default-dispatcher-2] [akka.tcp://CauchySystem@127.0.0.1:56318/user/King] Send task to worker from managers.
    [INFO] [07/14/2020 15:14:31.075] [CauchySystem-akka.actor.default-dispatcher-17] [akka.tcp://CauchySystem@127.0.0.1:56318/user/King/crawler_Paladin_1] [P] content: This is your task parameters.
    [INFO] [07/14/2020 15:14:31.075] [CauchySystem-akka.actor.default-dispatcher-17] [akka.tcp://CauchySystem@127.0.0.1:56318/user/King/crawler_Paladin_1] [P] taskAmount: 1.0
    [INFO] [07/14/2020 15:14:31.075] [CauchySystem-akka.actor.default-dispatcher-17] [akka.tcp://CauchySystem@127.0.0.1:56318/user/King/crawler_Paladin_1] [P] taskCondition: List(1102)
    [INFO] [07/14/2020 15:14:31.075] [CauchySystem-akka.actor.default-dispatcher-17] [akka.tcp://CauchySystem@127.0.0.1:56318/user/King/crawler_Paladin_1] context.self.path.name: crawler_Paladin_1
    [INFO] [07/14/2020 15:14:31.075] [CauchySystem-akka.actor.default-dispatcher-17] [akka.tcp://CauchySystem@127.0.0.1:56318/user/King/crawler_Paladin_1] context.self.path.name: akka://CauchySystem
    [INFO] [07/14/2020 15:14:31.075] [CauchySystem-akka.actor.default-dispatcher-17] [akka.tcp://CauchySystem@127.0.0.1:56318/user/King/crawler_Paladin_1] context.parent: Actor[akka://CauchySystem/user/King#-574344648]
    [INFO] [07/14/2020 15:14:31.075] [CauchySystem-akka.actor.default-dispatcher-17] [akka.tcp://CauchySystem@127.0.0.1:56318/user/King/crawler_Paladin_1] context.parent.path.name: King
    This is worker, Start work !
    This is worker, Start work !
    [S] content: Hi, every crawler soldiers, here are your guys tasks.
    [S] content: Hi, every crawler soldiers, here are your guys tasks.
    [S] taskList: List(1102)
    [S] taskList: List(1101)
    [INFO] Running Python Code Command Line:
    python src/main/scala/Dockerize_SBT_Project/src/main/python/multi-lan_stock-crawler_py-ver.py --listed-company 1101
    [INFO] Running Python Code Command Line:
    python src/main/scala/Dockerize_SBT_Project/src/main/python/multi-lan_stock-crawler_py-ver.py --listed-company 1102
    [DEBUG] running command line result: {'stat': 'SUCCESS', 'stockInfo': {'stat': 'OK', 'date': '20190101', 'title': '108年01月 1101 台泥             各日成交資訊', 'fields': ['日期', '成交股數', '成交金額', '開盤價', '最高價', '最低價', '收盤價', '漲跌價差', '成交筆數'], 'data': [['108/01/02', '6,566,997', '234,262,829', '35.75', '35.95', '35.50', '35.50', '-0.10', '2,572'], ['108/01/03', '9,160,859', '323,494,000', '35.50', '35.60', '35.10', '35.15', '-0.35', '3,546'], ['108/01/04', '10,692,409', '376,905,510', '35.00', '35.65', '34.90', '35.45', '+0.30', '4,175'], ['108/01/07', '14,906,206', '537,441,416', '35.85', '36.20', '35.80', '36.00', '+0.55', '5,611'], ['108/01/08', '10,987,241', '393,334,841', '35.90', '35.95', '35.55', '35.55', '-0.45', '3,621'], ['108/01/09', '20,974,178', '756,069,459', '35.95', '36.35', '35.90', '36.35', '+0.80', '5,711'], ['108/01/10', '11,530,577', '422,174,797', '36.55', '36.95', '36.40', '36.55', '+0.20', '4,201'], ['108/01/11', '11,923,354', '434,594,562', '36.70', '36.70', '36.20', '36.40', '-0.15', '4,868'], ['108/01/14', '18,788,577', '670,460,088', '36.40', '36.45', '35.35', '35.60', '-0.80', '7,189'], ['108/01/15', '13,837,986', '494,376,678', '35.80', '36.00', '35.60', '35.70', '+0.10', '4,206'], ['108/01/16', '12,432,470', '443,106,653', '35.70', '35.80', '35.55', '35.60', '-0.10', '3,696'], ['108/01/17', '11,657,174', '413,726,229', '35.60', '35.80', '35.40', '35.45', '-0.15', '4,988'], ['108/01/18', '10,088,263', '361,901,018', '35.80', '36.00', '35.55', '36.00', '+0.55', '3,421'], ['108/01/21', '9,974,837', '360,754,727', '36.20', '36.30', '35.85', '36.25', '+0.25', '3,292'], ['108/01/22', '6,060,492', '219,490,320', '36.25', '36.30', '36.05', '36.25', ' 0.00', '1,807'], ['108/01/23', '6,845,545', '248,862,420', '36.20', '36.45', '36.05', '36.45', '+0.20', '2,960'], ['108/01/24', '7,992,255', '290,662,915', '36.45', '36.55', '36.15', '36.40', '-0.05', '3,389'], ['108/01/25', '11,808,482', '431,875,477', '36.40', '36.75', '36.30', '36.60', '+0.20', '4,417'], ['108/01/28', '13,642,330', '503,258,210', '36.75', '37.00', '36.70', '37.00', '+0.40', '4,446'], ['108/01/29', '10,057,975', '371,981,552', '37.00', '37.10', '36.70', '37.10', '+0.10', '3,560'], ['108/01/30', '12,214,164', '454,594,356', '37.20', '37.50', '36.95', '37.35', '+0.25', '4,445']], 'notes': ['符號說明:+/-/X表示漲/跌/不比價', '當日統計資訊含一般、零股、盤後定價、鉅額交易，不含拍賣、標購。', 'ETF證券代號第六碼為K、M、S、C者，表示該ETF以外幣交易。']}}
    
    [DEBUG] running command line result: {'stat': 'SUCCESS', 'stockInfo': {'stat': 'OK', 'date': '20190101', 'title': '108年01月 1102 亞泥             各日成交資訊', 'fields': ['日期', '成交股數', '成交金額', '開盤價', '最高價', '最低價', '收盤價', '漲跌價差', '成交筆數'], 'data': [['108/01/02', '3,677,226', '125,069,106', '34.40', '34.40', '33.60', '33.80', '-0.15', '1,556'], ['108/01/03', '3,918,403', '132,992,602', '33.95', '34.15', '33.75', '33.90', '+0.10', '2,109'], ['108/01/04', '5,283,875', '179,121,108', '33.85', '34.20', '33.55', '33.85', '-0.05', '2,822'], ['108/01/07', '8,647,536', '299,604,442', '34.20', '34.95', '34.05', '34.85', '+1.00', '4,425'], ['108/01/08', '4,475,825', '155,482,475', '34.95', '34.95', '34.50', '34.65', '-0.20', '2,480'], ['108/01/09', '11,038,923', '387,586,383', '34.95', '35.25', '34.80', '35.25', '+0.60', '4,910'], ['108/01/10', '9,719,452', '345,339,245', '35.25', '35.75', '35.10', '35.60', '+0.35', '3,911'], ['108/01/11', '11,848,282', '426,282,486', '35.75', '36.15', '35.65', '36.00', '+0.40', '4,769'], ['108/01/14', '10,987,863', '384,519,988', '35.80', '35.95', '34.50', '35.05', '-0.95', '5,124'], ['108/01/15', '6,784,908', '237,209,810', '35.15', '35.15', '34.70', '35.05', ' 0.00', '3,639'], ['108/01/16', '6,130,344', '216,286,672', '35.00', '35.55', '34.90', '35.50', '+0.45', '3,383'], ['108/01/17', '6,139,634', '218,070,981', '35.20', '35.75', '35.00', '35.70', '+0.20', '2,758'], ['108/01/18', '8,428,083', '303,285,413', '35.80', '36.20', '35.60', '36.15', '+0.45', '3,348'], ['108/01/21', '8,682,352', '311,915,391', '35.95', '36.20', '35.55', '36.15', ' 0.00', '3,502'], ['108/01/22', '4,486,056', '160,593,189', '35.80', '36.00', '35.65', '35.80', '-0.35', '2,357'], ['108/01/23', '6,392,048', '229,155,565', '35.80', '36.00', '35.55', '36.00', '+0.20', '3,668'], ['108/01/24', '6,596,284', '236,903,934', '35.80', '36.00', '35.65', '36.00', ' 0.00', '3,698'], ['108/01/25', '8,781,080', '316,660,530', '35.80', '36.25', '35.70', '36.20', '+0.20', '3,908'], ['108/01/28', '8,567,247', '309,744,749', '36.25', '36.35', '35.95', '36.20', ' 0.00', '4,679'], ['108/01/29', '10,389,797', '371,510,224', '35.85', '36.00', '35.15', '36.00', '-0.20', '6,405'], ['108/01/30', '9,633,122', '349,158,012', '36.00', '36.60', '35.65', '36.45', '+0.45', '5,187']], 'notes': ['符號說明:+/-/X表示漲/跌/不比價', '當日統計資訊含一般、零股、盤後定價、鉅額交易，不含拍賣、標購。', 'ETF證券代號第六碼為K、M、S、C者，表示該ETF以外幣交易。']}}
    
    ************** Command Running Result **************
    runningResult:
    {'stat': 'SUCCESS', 'stockInfo': {'stat': 'OK', 'date': '20190101', 'title': '108年01月 1101 台泥             各日成交資訊', 'fields': ['日期', '成交股數', '成交金額', '開盤價', '最高價', '最低價', '收盤價', '漲跌價差', '成交筆數'], 'data': [['108/01/02', '6,566,997', '234,262,829', '35.75', '35.95', '35.50', '35.50', '-0.10', '2,572'], ['108/01/03', '9,160,859', '323,494,000', '35.50', '35.60', '35.10', '35.15', '-0.35', '3,546'], ['108/01/04', '10,692,409', '376,905,510', '35.00', '35.65', '34.90', '35.45', '+0.30', '4,175'], ['108/01/07', '14,906,206', '537,441,416', '35.85', '36.20', '35.80', '36.00', '+0.55', '5,611'], ['108/01/08', '10,987,241', '393,334,841', '35.90', '35.95', '35.55', '35.55', '-0.45', '3,621'], ['108/01/09', '20,974,178', '756,069,459', '35.95', '36.35', '35.90', '36.35', '+0.80', '5,711'], ['108/01/10', '11,530,577', '422,174,797', '36.55', '36.95', '36.40', '36.55', '+0.20', '4,201'], ['108/01/11', '11,923,354', '434,594,562', '36.70', '36.70', '36.20', '36.40', '-0.15', '4,868'], ['108/01/14', '18,788,577', '670,460,088', '36.40', '36.45', '35.35', '35.60', '-0.80', '7,189'], ['108/01/15', '13,837,986', '494,376,678', '35.80', '36.00', '35.60', '35.70', '+0.10', '4,206'], ['108/01/16', '12,432,470', '443,106,653', '35.70', '35.80', '35.55', '35.60', '-0.10', '3,696'], ['108/01/17', '11,657,174', '413,726,229', '35.60', '35.80', '35.40', '35.45', '-0.15', '4,988'], ['108/01/18', '10,088,263', '361,901,018', '35.80', '36.00', '35.55', '36.00', '+0.55', '3,421'], ['108/01/21', '9,974,837', '360,754,727', '36.20', '36.30', '35.85', '36.25', '+0.25', '3,292'], ['108/01/22', '6,060,492', '219,490,320', '36.25', '36.30', '36.05', '36.25', ' 0.00', '1,807'], ['108/01/23', '6,845,545', '248,862,420', '36.20', '36.45', '36.05', '36.45', '+0.20', '2,960'], ['108/01/24', '7,992,255', '290,662,915', '36.45', '36.55', '36.15', '36.40', '-0.05', '3,389'], ['108/01/25', '11,808,482', '431,875,477', '36.40', '36.75', '36.30', '36.60', '+0.20', '4,417'], ['108/01/28', '13,642,330', '503,258,210', '36.75', '37.00', '36.70', '37.00', '+0.40', '4,446'], ['108/01/29', '10,057,975', '371,981,552', '37.00', '37.10', '36.70', '37.10', '+0.10', '3,560'], ['108/01/30', '12,214,164', '454,594,356', '37.20', '37.50', '36.95', '37.35', '+0.25', '4,445']], 'notes': ['符號說明:+/-/X表示漲/跌/不比價', '當日統計資訊含一般、零股、盤後定價、鉅額交易，不含拍賣、標購。', 'ETF證券代號第六碼為K、M、S、C者，表示該ETF以外幣交易。']}}
    
    ****************************************************
    [INFO] [07/14/2020 15:14:41.523] [CauchySystem-akka.actor.default-dispatcher-3] [akka.tcp://CauchySystem@127.0.0.1:56318/user/King] [King] Good job, my man !
    ************** Command Running Result **************
    runningResult:
    {'stat': 'SUCCESS', 'stockInfo': {'stat': 'OK', 'date': '20190101', 'title': '108年01月 1102 亞泥             各日成交資訊', 'fields': ['日期', '成交股數', '成交金額', '開盤價', '最高價', '最低價', '收盤價', '漲跌價差', '成交筆數'], 'data': [['108/01/02', '3,677,226', '125,069,106', '34.40', '34.40', '33.60', '33.80', '-0.15', '1,556'], ['108/01/03', '3,918,403', '132,992,602', '33.95', '34.15', '33.75', '33.90', '+0.10', '2,109'], ['108/01/04', '5,283,875', '179,121,108', '33.85', '34.20', '33.55', '33.85', '-0.05', '2,822'], ['108/01/07', '8,647,536', '299,604,442', '34.20', '34.95', '34.05', '34.85', '+1.00', '4,425'], ['108/01/08', '4,475,825', '155,482,475', '34.95', '34.95', '34.50', '34.65', '-0.20', '2,480'], ['108/01/09', '11,038,923', '387,586,383', '34.95', '35.25', '34.80', '35.25', '+0.60', '4,910'], ['108/01/10', '9,719,452', '345,339,245', '35.25', '35.75', '35.10', '35.60', '+0.35', '3,911'], ['108/01/11', '11,848,282', '426,282,486', '35.75', '36.15', '35.65', '36.00', '+0.40', '4,769'], ['108/01/14', '10,987,863', '384,519,988', '35.80', '35.95', '34.50', '35.05', '-0.95', '5,124'], ['108/01/15', '6,784,908', '237,209,810', '35.15', '35.15', '34.70', '35.05', ' 0.00', '3,639'], ['108/01/16', '6,130,344', '216,286,672', '35.00', '35.55', '34.90', '35.50', '+0.45', '3,383'], ['108/01/17', '6,139,634', '218,070,981', '35.20', '35.75', '35.00', '35.70', '+0.20', '2,758'], ['108/01/18', '8,428,083', '303,285,413', '35.80', '36.20', '35.60', '36.15', '+0.45', '3,348'], ['108/01/21', '8,682,352', '311,915,391', '35.95', '36.20', '35.55', '36.15', ' 0.00', '3,502'], ['108/01/22', '4,486,056', '160,593,189', '35.80', '36.00', '35.65', '35.80', '-0.35', '2,357'], ['108/01/23', '6,392,048', '229,155,565', '35.80', '36.00', '35.55', '36.00', '+0.20', '3,668'], ['108/01/24', '6,596,284', '236,903,934', '35.80', '36.00', '35.65', '36.00', ' 0.00', '3,698'], ['108/01/25', '8,781,080', '316,660,530', '35.80', '36.25', '35.70', '36.20', '+0.20', '3,908'], ['108/01/28', '8,567,247', '309,744,749', '36.25', '36.35', '35.95', '36.20', ' 0.00', '4,679'], ['108/01/29', '10,389,797', '371,510,224', '35.85', '36.00', '35.15', '36.00', '-0.20', '6,405'], ['108/01/30', '9,633,122', '349,158,012', '36.00', '36.60', '35.65', '36.45', '+0.45', '5,187']], 'notes': ['符號說明:+/-/X表示漲/跌/不比價', '當日統計資訊含一般、零股、盤後定價、鉅額交易，不含拍賣、標購。', 'ETF證券代號第六碼為K、M、S、C者，表示該ETF以外幣交易。']}}
    
    ****************************************************
    [INFO] [07/14/2020 15:14:50.709] [CauchySystem-akka.actor.default-dispatcher-2] [akka.tcp://CauchySystem@127.0.0.1:56318/user/King] [King] Good job, my man !
    [INFO] [07/14/2020 15:14:50.709] [CauchySystem-akka.actor.default-dispatcher-2] [akka.tcp://CauchySystem@127.0.0.1:56318/user/King] * [FINISH] Done this project.
    [INFO] [07/14/2020 15:14:50.716] [CauchySystem-akka.remote.default-remote-dispatcher-14] [akka.tcp://CauchySystem@127.0.0.1:56318/system/remoting-terminator] Shutting down remote daemon.
    [INFO] [07/14/2020 15:14:50.717] [CauchySystem-akka.remote.default-remote-dispatcher-14] [akka.tcp://CauchySystem@127.0.0.1:56318/system/remoting-terminator] Remote daemon shut down; proceeding with flushing remote transports.
    [INFO] [07/14/2020 15:14:50.735] [CauchySystem-akka.actor.default-dispatcher-3] [akka.remote.Remoting] Remoting shut down
    [INFO] [07/14/2020 15:14:50.735] [CauchySystem-akka.remote.default-remote-dispatcher-18] [akka.tcp://CauchySystem@127.0.0.1:56318/system/remoting-terminator] Remoting shut down.
    2020-07-14 15:14:50 WARN  FileSystem:2995 - exception in the cleaner thread but it will continue to run
    java.lang.InterruptedException
	at java.lang.Object.wait(Native Method)
	at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:144)
	at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:165)
	at org.apache.hadoop.fs.FileSystem$Statistics$StatisticsDataReferenceCleaner.run(FileSystem.java:2989)
	at java.lang.Thread.run(Thread.java:748)
    [success] Total time: 40 s, completed Jul 14, 2020 3:14:50 PM
    2020-07-14 15:14:50 INFO  ShutdownHookManager:54 - Shutdown hook called
    2020-07-14 15:14:50 INFO  ShutdownHookManager:54 - Deleting directory /private/var/folders/xw/d8fvx73550lc9znzx6lqkk480000gn/T/spark-b5b39439-76d6-4b84-a193-eb472b267a44



