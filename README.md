# springwithkafka

 #### This project is implemented using springboot rest api,mysql and apache kafka. Application has two endpoints, first URI: /kafka/send/{packageId} in order to send single package to kafka with some rules, second URI: /kafka/bootstrap in order to send all packages records(with some rules) in our database. <hr> 
 <b>used technologies and versions: <br>
 
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white) <b>17 <br>
 ![image](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)   <b>8.0.30 <br>
 ![image](https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apache-kafka&logoColor=white)   <b>3.3.1 <br>
 my application.yaml file for connect mysql and local kafka config
 ```
 spring:
  application:
    name: springwithkafka
  kafka:
    bootstrap-servers: localhost:9092
    producer:
      topic: shcasetest
  datasource:
    password: 7asbaw2sqn
    username: root
    url: jdbc:mysql://localhost:3306/package_directory?useSSL=false&serverTimezone=UTC
    driver-class-name: com.mysql.cj.jdbc.Driver

server:
  port: 8081
  ```

 ## Installing / Getting started
 Download Apache Kafka 2.12-3.3.1 at [Apache Kafka Download](https://kafka.apache.org/downloads).
 #### set server directory
 change  <b> log.dirs=/tmp/kafka-logs </b> value to <b>log.dirs=(localKafkaDirectory)/kafka-logs</b> in <b>kafka_2.12-3.3.1\config\server.properties</b> file. <br>
 [ i.e: in my local `log.dirs=D:\ApacheKafka\kafka_2.12-3.3.1/kafka-logs`  ]
 #### set zookeper directory
 change  <b> dataDir=/tmp/zookeper-data </b> value to <b>dataDir=(localKafkaDirectory)/zookeper-data</b> in <b>kafka_2.12-3.3.1\config\zookeper.properties</b> file. <br>
 [ i.e: in my local `dataDir=D:\ApacheKafka\kafka_2.12-3.3.1/zookeper-data`  ]
 
 ### after set the directories
 run kafka on local machine: 
 go to the directory where kafka is installed and open cmd.exe [ in my local `D:\ApacheKafka\kafka_2.12-3.3.1>`  ]:<br>
### run zookeper
```shell
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties 
```
### run kafka broker
```shell
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties 
```
### create kafka topic
go to kafka directory -> bin file and run cmd !! if you are using windows, you should run cmd in bin\windows file
[ i.e: in my local `D:\ApacheKafka\kafka_2.12-3.3.1\bin\windows>`  ]
#### run this code in bin file in order to create topic,
```shell
kafka-topics.bat --create --topic shcaselasttest    --bootstrap-server localhost:9092 
```

### or on MACOS 
```shell
kafka-topics.sh --create --topic shcaselasttest    --bootstrap-server localhost:9092 
```
 > example in my local
![image](https://user-images.githubusercontent.com/82888052/202650413-75af1888-c361-4750-bcab-4b3c98c849b9.png)
### for list our topics:
```shell
kafka-topics.bat --list --bootstrap-server localhost:9092 
```
 ### or on MACOS 
```shell
kafka-topics.sh --list --bootstrap-server localhost:9092  
```
 > example in my local <br>
![image](https://user-images.githubusercontent.com/82888052/202650928-870004be-1d9a-459f-b5e5-98c324bcca4d.png)

 ### now, create a local Consumer in same cmd screen in order to monitor the packages we send from the application.

```shell
kafka-console-consumer.bat --topic shcasetest --from-beginning --bootstrap-server localhost:9092 
```
