## Instructions to Get Started

### Installation
#### IntelliJ Ultimate
* Download [IntelliJ IDE](https://www.jetbrains.com/idea/download/#section=mac) (Ultimate Edition is preferred since it comes with built-in support for Spring Boot) 
   - Use your ASU email to register and you will get the license for free
#### MySQL
* Install MySQL with [Homebrew](https://brew.sh) and run the following commands:
   - Start MySQL: ```brew services start mysql```
   - Run MySQL: ```mysql -uroot```
* To create a new user and database for MySQL by logging in, run:
   - ```create database sbsDb;```
   - ```create user 'springuser'@'%' identified by 'Group7Group7';```
   - ```grant all on sbsDb.* to 'springuser'@'%';```
* Additionally, download and use phpMyAdmin or MySQL Workbench to perform operations on the database outside the application.
#### PostMan
* Download [Postman](https://www.postman.com/downloads/) and log in to join the [team community folder](https://app.getpostman.com/join-team?invite_code=0c416ee27ac0e75c461bcf79ab55bcb9) - this is where our API contracts and samples would be stored.
* Test that MySQL commands are working by going to PostMan and running the POST command in the [team community folder](https://app.getpostman.com/join-team?invite_code=0c416ee27ac0e75c461bcf79ab55bcb9)
   - POST https://localhost:8443/user/add (make sure the body is filled out)
* Then, run the GET command in the [team community folder](https://app.getpostman.com/join-team?invite_code=0c416ee27ac0e75c461bcf79ab55bcb9)
   - GET http://localhost:8080/user/
* Go back to MySQL in your terminal and run ```SELECT * FROM user;```. You should be able to see one query in the users table after running the POST command.


### How to Run the Client and Server
* Make sure you are on the ```develop``` branch on the cloned repo (```git checkout develop``` and then ```git pull```)
* Launch IntelliJ
* Open -> Select the ```server``` directory from the cloned repo
* Wait for all the dependencies to load and the indices to build
* Right-click on the ```server``` folder and click on ```Maven``` -> ```Reimport```
* Once done, you can 'Run' the application by clicking on the 'Play' icon (green triangle) in the tool bar at the top
* On your browser (Chrome recommended), go to [https://localhost:8443/](https://localhost:8443/) to view the web application
* When prompted to log in, make sure you run ```insert into user values("1",1,"address","tempe","us","user","john","doe","pass",123,"ADMIN",321,"az",5454);``` on MySQL, then use ```user``` and ```pass``` as credentials.
* IMPORTANT: After making changes to the template (HTML) pages, you must re-run the project in order to view the changes to the web application
