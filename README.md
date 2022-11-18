# springwithkafka

 This project has a core functionality of getting packages, converting them into instances of MappedPackage class and then sending them to a kafka topic of my choice in my local machine
 used technologies: 
 ## Installing / Getting started
 Download Apache Kafka 2.12-3.3.1 at [Apache Kafka Download](https://kafka.apache.org/downloads).
 #### set server directory
 change  <b> log.dirs=/tmp/kafka-logs </b> value to <b>log.dirs=local_kafka_directory/kafka-logs</b> in <b>kafka_2.12-3.3.1\config\server.properties</b> file. <br>
 [ i.e: in my local `log.dirs=D:\ApacheKafka\kafka_2.12-3.3.1/kafka-logs`  ]
 #### set zookeper directory
 change  <b> dataDir=/tmp/zookeper-data </b> value to <b>dataDir=loca_kafka_directory/zookeper-data</b> in <b>kafka_2.12-3.3.1\config\zookeper.properties</b> file. <br>
 [ i.e: in my local `dataDir=D:\ApacheKafka\kafka_2.12-3.3.1/zookeper-data`  ]
 
 ### after set the directories
 run kafka on local machine: 
 go to the directory where kafka is installed and run<br>
to run zookeper
```shell
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties 
```
to run kafka broker
```shell
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties 
```
