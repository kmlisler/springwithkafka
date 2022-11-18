# springwithkafka

 #### This project is implemented using springboot rest api,mysql and apache kafka. Application has two endpoints, first URI: /kafka/send/{packageId} in order to send single package to kafka with some rules, second URI: /kafka/bootstrap in order to send all packages records(with some rules) in our database. <hr> 
 <b>used technologies and versions: <br>
 
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white) <b>17 <br>
![Springboot](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)   <b>2.7.5 <br>
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)   <b>8.0.30 <br>
![image](https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apache-kafka&logoColor=white)   <b>3.3.1 <br>
 my application.yaml file for connect mysql and local kafka config
 ```
 spring:
  application:
    name: springwithkafka
  kafka:
    bootstrap-servers: localhost:9092
    producer:
      topic: shcaselasttest
  datasource:
    password: 7asbaw2sqn
    username: root
    url: jdbc:mysql://localhost:3306/package_directory?useSSL=false&serverTimezone=UTC
    driver-class-name: com.mysql.cj.jdbc.Driver

server:
  port: 8081
  ```
> NOTE: MySql and Apache Kafka configs must be made according to your local settings.
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

 ### now, create a local Consumer in same cmd screen in order to monitor the packages we send from the application. ( for MACOS change ".bat" to ".sh" and use directly \bin file, not \bin\windows )

```shell
kafka-console-consumer.bat --topic shcaselasttest --from-beginning --bootstrap-server localhost:9092 
```
 ### we all set up apache kafka installation, Kafka Broker/Zookeeper is running and we also created our topic named "shcaselasttest" to test app <hr>
 ## Now lets see how our applicaton work
 <b> in order to test our application, we create our "Package" table.
```shell
CREATE TABLE `package_directory`.`package` (
  `id` long NOT NULL,
  `order_id` long NOT NULL,
  `user_id` long NOT NULL,
  `customer_id` long NOT NULL,
  `store_id` long NOT NULL,
  `origin_address_id` long NOT NULL,
  `type` varchar(255),
  `eta` int NOT NULL,
  `delivery_date` date,
  `created_at` TIMESTAMP(6) NOT NULL,  -- i change 'datetime' to  'TIMESTAMP(6)' because regular datetime is rounding our date, i want "yyyy-MM-dd HH:mm:ss.SSSSSS" format for date.
  `waiting_for_assignment_at` TIMESTAMP(6),
  `collected_at` TIMESTAMP(6),
  `arrival_for_pickup_at` TIMESTAMP(6),
  `picked_up_at` TIMESTAMP(6),
  `in_delivery_at` TIMESTAMP(6),
  `arrival_for_delivery_at` TIMESTAMP(6),
  `completed_at` TIMESTAMP(6),
  `last_updated_at` TIMESTAMP(6) NOT NULL,
  `cancelled_at` TIMESTAMP(6),
  `collected` bool,
  `cancelled` bool,
  `reassigned` bool,
  `cancel_reason` varchar(255),
  `status` varchar(255),
  PRIMARY KEY (`id`(255))
); 
 ```
<b> and we insert some meaningful data to our table, below the values, expected kafka records and package properties written
 ```shell
INSERT INTO package(`id`, `order_id`, `user_id`, `customer_id`, `store_id`, `origin_address_id`, `type`, `eta`, `delivery_date`, `created_at`, `waiting_for_assignment_at`, `collected_at`, `arrival_for_pickup_at`, `picked_up_at`, `in_delivery_at`, `arrival_for_delivery_at`, `completed_at`, `last_updated_at`, `cancelled_at`, `collected`, `cancel_reason`, `cancelled`, `status`, `reassigned`)
VALUES (19604181, 123972783, 50002010395213, 20002011575015, 20000000004103, 999000020443388, 'REGULAR', 277, '2021-11-13', '2021-11-13 10:47:52.675248', '2021-11-13 10:47:52.675248', '2021-11-13 10:47:52.828692', '2021-11-13 10:48:37.032078', '2021-11-13 10:49:50.278087', '2021-11-13 11:05:56.861614', '2021-11-13 11:05:58.045640', '2021-11-13 11:40:15.314340', '2021-11-13 11:40:15.314340', null, 1, null, 0, 'COMPLETED', null);
-- -> sample, zamanında tamamlanmış bir paket. olması gereken kafka çıktısı: collectionDuration: 0 çünkü saliseler içinde toplanmış."deliveryDuration":34, "leadTime":52, "orderInTime":true because eta:277
INSERT INTO package(`id`, `order_id`, `user_id`, `customer_id`, `store_id`, `origin_address_id`, `type`, `eta`, `delivery_date`, `created_at`, `waiting_for_assignment_at`, `collected_at`, `arrival_for_pickup_at`, `picked_up_at`, `in_delivery_at`, `arrival_for_delivery_at`, `completed_at`, `last_updated_at`, `cancelled_at`, `collected`, `cancel_reason`, `cancelled`, `status`, `reassigned`)
VALUES (2, 222222, 222222, 222222, 222222, 222222, 'BIG', 140, '2022-11-15', '2022-11-15 07:47:52.675248', '2022-11-15 07:47:52.675248', '2022-11-15 10:57:52.828692', '2022-11-15 11:00:37.032078', '2022-11-15 11:05:50.278087', '2022-11-15 11:15:56.861614', '2022-11-15 11:16:58.045640', '2022-11-15 14:40:15.314340', '2022-11-15 11:40:15.314340', null, 1, null, 0, 'COMPLETED', null);
-- -> estimated timeı geçmiş ve tamamlanmış bir paket. olması gereken kafka çıktısı: "collectionDuration":190,"deliveryDuration":204," "leadTime":412, "orderInTime":false because eta:140
INSERT INTO package(`id`, `order_id`, `user_id`, `customer_id`, `store_id`, `origin_address_id`, `type`, `eta`, `delivery_date`, `created_at`, `waiting_for_assignment_at`, `collected_at`, `arrival_for_pickup_at`, `picked_up_at`, `in_delivery_at`, `arrival_for_delivery_at`, `completed_at`, `last_updated_at`, `cancelled_at`, `collected`, `cancel_reason`, `cancelled`, `status`, `reassigned`)
VALUES (3, 333333, 333333, 333333, 333333, 333333, 'SMALL', 70, '2022-11-15', '2022-11-15 08:47:52.675248', '2022-11-15 08:47:52.675248', '2022-11-15 08:57:52.828692', null, null, null, null, null, '2022-11-15 08:58:52.828692', '2022-11-15 08:58:52.828692', 1, 'yanlış sipariş', 1, 'CANCELLED', null);
-- -> toplanmış(collected: 1 ), toplandıktan sonra cancel edilmiş paket. cancel edildiği için kafkaya gönderilmeyecek.
INSERT INTO package(`id`, `order_id`, `user_id`, `customer_id`, `store_id`, `origin_address_id`, `type`, `eta`, `delivery_date`, `created_at`, `waiting_for_assignment_at`, `collected_at`, `arrival_for_pickup_at`, `picked_up_at`, `in_delivery_at`, `arrival_for_delivery_at`, `completed_at`, `last_updated_at`, `cancelled_at`, `collected`, `cancel_reason`, `cancelled`, `status`, `reassigned`)
VALUES (4, 444444, 444444, 444444, 444444, 444444, 'BIG', 120, '2022-11-15', '2022-11-15 10:30:00.000000', '2022-11-15 10:30:00.000000', '2022-11-15 11:05:00.000000', '2022-11-15 11:10:00.000000', '2022-11-15 11:12:00.000000', '2022-11-15 11:20:00.000000', '2022-11-15 11:25:00.000000', null, '2022-11-15 11:30:00.000000', '2022-11-15 11:30:00.000000', 1, 'vazgeçtim', 1, 'CANCELLED', null);
-- -> toplanmış(collected: 1 ), yola çıkmış, yola çıktıktan sonra cancel edilmiş paket. cancel edildiği için kafkaya gönderilemeyecek
INSERT INTO package(`id`, `order_id`, `user_id`, `customer_id`, `store_id`, `origin_address_id`, `type`, `eta`, `delivery_date`, `created_at`, `waiting_for_assignment_at`, `collected_at`, `arrival_for_pickup_at`, `picked_up_at`, `in_delivery_at`, `arrival_for_delivery_at`, `completed_at`, `last_updated_at`, `cancelled_at`, `collected`, `cancel_reason`, `cancelled`, `status`, `reassigned`)
VALUES (5, 555555, 555555, 555555, 555555, 555555, 'REGULAR', 90, '2022-11-15', '2022-11-15 10:45:12.758476', '2022-11-15 10:45:12.758476', '2022-11-15 10:55:12.758476', '2022-11-15 10:57:12.758476', '2022-11-15 10:58:12.758476', '2022-11-15 11:00:12.758476', '2022-11-15 11:05:12.758476', null, '2022-11-15 11:05:12.758476', null, 1, null, 0, 'IN DELIVERY', null);
-- -> toplanmış(collected: 1 ), yola çıkmış, hala yolda olan paket.olması gereken kafka çıktısı: "collectionDuration":10,"deliveryDuration":null," "leadTime":null, "orderInTime":null because not completed
INSERT INTO package(`id`, `order_id`, `user_id`, `customer_id`, `store_id`, `origin_address_id`, `type`, `eta`, `delivery_date`, `created_at`, `waiting_for_assignment_at`, `collected_at`, `arrival_for_pickup_at`, `picked_up_at`, `in_delivery_at`, `arrival_for_delivery_at`, `completed_at`, `last_updated_at`, `cancelled_at`, `collected`, `cancel_reason`, `cancelled`, `status`, `reassigned`)
VALUES (6, 666666, 666666, 666666, 666666, 666666, 'SMALL', 50, '2022-11-15', '2022-11-15 10:50:12.758476', '2022-11-15 10:50:12.758476', null, null, null, null, null, null, '2022-11-15 10:55:12.758476', '2022-11-15 10:55:12.758476', 0, 'hatalı seçim', 1, 'CANCELLED', null);
-- -> toplanma bitmeden iptal edilmiş paket. cancel edilmiş paket, kafkaya gitmez.
INSERT INTO package(`id`, `order_id`, `user_id`, `customer_id`, `store_id`, `origin_address_id`, `type`, `eta`, `delivery_date`, `created_at`, `waiting_for_assignment_at`, `collected_at`, `arrival_for_pickup_at`, `picked_up_at`, `in_delivery_at`, `arrival_for_delivery_at`, `completed_at`, `last_updated_at`, `cancelled_at`, `collected`, `cancel_reason`, `cancelled`, `status`, `reassigned`)
VALUES (7, 777777, 777777, 777777, 777777, 777777, 'REGULAR', 130, '2022-11-15', '2022-11-15 10:50:12.758476', '2022-11-15 10:50:12.758476', '2022-11-15 10:55:12.758476', '2022-11-15 11:00:12.758476', '2022-11-15 11:05:12.758476', '2022-11-15 11:10:12.758476', '2022-11-15 11:15:12.758476', '2022-11-15 11:30:12.758476', '2022-11-15 11:30:12.758476', null, 1, null, 0, 'COMPLETED', 1);
-- -> sağlıklı bi şekilde vaktinde teslim edilmiş paket. olması gereken kafka çıktısı: "collectionDuration":5,"deliveryDuration":20," "leadTime":40, "orderInTime":true because eta:130
INSERT INTO package(`id`, `order_id`, `user_id`, `customer_id`, `store_id`, `origin_address_id`, `type`, `eta`, `delivery_date`, `created_at`, `waiting_for_assignment_at`, `collected_at`, `arrival_for_pickup_at`, `picked_up_at`, `in_delivery_at`, `arrival_for_delivery_at`, `completed_at`, `last_updated_at`, `cancelled_at`, `collected`, `cancel_reason`, `cancelled`, `status`, `reassigned`)
VALUES (8, 888888, 888888, 888888, 888888, 888888, 'BIG', 180, '2022-11-15', '2022-11-15 07:00:00.123456', '2022-11-15 07:00:00.123456', '2022-11-15 09:00:00.123456', '2022-11-15 09:10:00.123456', '2022-11-15 09:20:00.123456', '2022-11-15 09:32:00.123456', '2022-11-15 09:35:00.000000', '2022-11-15 12:35:00.000000', '2022-11-15 12:35:00.000000', null, 1, null, 0, 'COMPLETED', 0);
-- -> teslim edilmiş fakat tahmin edilen süreyi aşmış paket olması gereken kafka çıktısı: "collectionDuration":120,"deliveryDuration":182," "leadTime":334, "orderInTime":false because eta:180
 ```
#### Our table now contains:
4 "COMPLETED" packages. (ids of the completed packages: 19604181, 2, 7, 8 )
1 "IN DELIVERY" package. (id of the in delivery package: 5)
3 "CANCELLED" packages. (ids of the cancelled packages: 3, 4, 6)
### Let's run our application and test our URIs.
 > Kafka Broker, Zookeeper and our shcaselasttest topic is running at my local on the background. applications.yaml topic name is set to current topic. <br>
 ![image](https://user-images.githubusercontent.com/82888052/202665233-a9192b89-fd5d-4b74-b2e2-0060a35e7569.png) <br>
 <b> we succesfully send our first package to kafka <br>
 <b> our local consumer is monitoring our kafka: <br>
  ![image](https://user-images.githubusercontent.com/82888052/202666135-780623a4-e795-4c7d-a644-9d86fa3f350a.png) <br>
 <b> send another completed package `http://localhost:8081/kafka/send/7`. <br>
 ![image](https://user-images.githubusercontent.com/82888052/202666594-31b192e7-34ce-42f8-8a64-795b4f4aba17.png) <br>
 <b> kafka server: <br>
 ![image](https://user-images.githubusercontent.com/82888052/202666760-717d7a81-3700-4755-b130-edc011b8f14e.png) <br>
 <b> send kafka to "IN DELIVERY" package `http://localhost:8081/kafka/send/5`. <br>
 ![image](https://user-images.githubusercontent.com/82888052/202669720-3cf7040e-8657-4708-9332-47a184e3a5e4.png) <br>

 !![image](https://user-images.githubusercontent.com/82888052/202669824-c2ad3065-9ff3-456d-9dc2-363ed6c04842.png)
 
 ### we are getting correct MappedPackage values on kafka.
 ### Lets continue with sending "CANCALLED" packages and one package that is not in the database.
 ![image](https://user-images.githubusercontent.com/82888052/202668439-a0a26bb8-855f-4413-b65b-2cef52512d04.png) <br>
 ![image](https://user-images.githubusercontent.com/82888052/202670217-377067a4-7f6b-423c-bb17-b6c68c1685da.png) <br>
 nonexist package request: <br>
 ![image](https://user-images.githubusercontent.com/82888052/202670585-e243d10e-a3bb-453a-bc7b-6156dbaa2541.png) <br>
> we are getting BADREQUEST Response. <br>
<b> kafka server: <br>
![image](https://user-images.githubusercontent.com/82888052/202670641-e9bd2aad-f036-4685-b2c2-7276de607200.png) <br>
<b> as you can see, server is not changed because we dont send cancelled packages to kafka. 

### Lets try call our "/kafka/bootstrap" URI.
#### in our current database, there is 3 completed, 1 in delivery, 3 cancelled package. i expect that our application's bootstrap uri send all packages except cancelled ones.
<br> <b> call bootstrap; <br>
![image](https://user-images.githubusercontent.com/82888052/202671723-5bc49f8a-acb0-41bc-8a0a-effd4da273b3.png)
 <b> current kafka server: <br>
 ![image](https://user-images.githubusercontent.com/82888052/202672157-533b89f4-569c-4149-8f5c-9c87f83dd744.png)
 #### as you can see, our new 5 non-cancelled packages is converted "MappedPackage" instance and sent to kafa server. The applicaton works properly.
 
 

  

                              
 

