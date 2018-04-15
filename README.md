# DBCourseProject
##  Introduction
The goal of this project is to build a database management system for Wolf Inns. Our system can manage the following information:
+ Hotel information: hotel ID, hotel, name, hotel address, hotel phone number, hotel manager ID
+ Customer information: customer name, date of birth, contact information
+ Staff information: staff ID, name, age, job title , department, contact information, hotel currently serving
+ Room information: room number, room category, max occupancy, nightly rate, availability;
+ Billing information: customer ID, SSN of the person responsible for the payment, billing address, payment information
+ Check-in information: customer name and date of birth, hotel ID, room number, number of guests, start date, end date, check-in time, check-out time, services offered.<br>
If you want more details about this project requirements, please click this [link](https://github.com/KunmiaoYang/DBCourseProject/blob/master/narrative.pdf)<br>

One big challenge is to create a lot of complex SQL code, like create tables and queries. If you want more information about our SQL code, please open this [link](https://github.com/KunmiaoYang/DBCourseProject/blob/master/schema.sql).
##  Configuration
[Language level](https://github.com/KunmiaoYang/DBCourseProject/blob/a5ac60efe424ca46d5b25984e297dfc3cddf440b/.idea/misc.xml): jDK 1.8, because NCSU VCL uses JDK 1.8.<br>
[Database](https://github.com/KunmiaoYang/DBCourseProject/blob/62f38ed3917ea59014d38f030494d81ea85d3580/.idea/libraries/connector.xml): Based on project requirements, we use mariaDB. But for development in our local machine, we use MySQL.<br>
[Test](https://github.com/KunmiaoYang/DBCourseProject/blob/master/.idea/libraries/junit_4_11.xml#L1): Junit 4.12. We use test driven development, and write lots of unit tests.
##  Structure
Our system uses 3-tier sturcture. Since the layer is a weakly coupled structure, the dependency between the layers is downward, the bottom layer is "ignorant" to the upper layer, and changing the design of the upper layer has no effect on the underlying layer of the call. The three layers are:
+ [user interface/presentation layer](https://github.com/KunmiaoYang/DBCourseProject/tree/master/src/ui)：this layer is mainly responsible for human-computer interaction. It can receives the requests of users and returns data users want.
+ [bussiness logic layer](https://github.com/KunmiaoYang/DBCourseProject/tree/master/src/common):this layer is for implementing the tasks and operations of the porject narritive. This layer is very essential, because it‘s middle layer and decouples the upper and lower layers.
+ [data access layer](https://github.com/KunmiaoYang/DBCourseProject/tree/master/src/model):This layer is mainly for responsible for database access, like select, insert and update.<br>
##  Menu
\r\n menu - Print menu \
  \r\n ----------------------------- Information Processing ----------------------------- \
  \r\n enter (hotel, room, staff, customer) - Create object \
  \r\n update (hotel, room, staff, customer) - Update object \
  \r\n delete (hotel, room, staff, customer) - Delete object \
  \r\n check (room) - Check available rooms \
  \r\n check (in, out) - Check in\\out room \
  \r\n --------------------------- Maintaining Service Records --------------------------- \
  \r\n enter (service) - Create service \
  \r\n update (service) - Update service \
  \r\n --------------------------- Maintaining Billing Accounts --------------------------- \
  \r\n enter (account) - Create Billing Accounts \
  \r\n update (account) - Update Billing Accounts \
  \r\n delete (account) - Delete Billing Accounts \
  \r\n check (bill) - Generate Bill \
  \r\n ------------------------------------- Reports ------------------------------------- \
  \r\n report occupy (hotel, roomType, dateRange, city) - Report occupancy by attribute \
  \r\n report staff (role, stay) - Report staff by role or customer stay\
  \r\n check (revenue) - Generate revenue \
  \r\n exit - Exit <br>
  
  This menu shows all functionalities we support.
  
##  How to run
  1. Download project.jar file from this [link](https://github.com/KunmiaoYang/DBCourseProject/blob/master/out/artifacts/project_jar/project.jar).
  2. Change the username and password in [account.properties.example](https://github.com/KunmiaoYang/DBCourseProject/blob/master/src/account.properties.example)
  3. Put project.jar into your remote linux machine(<unityID>@remote.eos.ncsu.edu)
  4. Run following command in remote linux machine.<br>
```
  java -jar project.jar
```
## Test
### Automative test
  We use Junit to test all the functions in layer 1 and layer 2. All test cases are passed. [link](https://github.com/KunmiaoYang/DBCourseProject/tree/master/test)
### Manual test
  Firstly, we  test all operations following the menu lists. All operations are correct.
