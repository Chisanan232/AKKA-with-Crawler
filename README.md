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
##### For Developing
OS: MacOS (Current Version: 10.14.5)

#### For Running
OS: MacOS (Current Version: 10.14.5), Windows OS (Current Version: Win10)

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

