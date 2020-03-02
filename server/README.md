## Instructions to get started

* Download IntelliJ IDE (Ultimate edition - prefer this as this comes with built in support for Spring boot) - use your ASU email to register and you will get the license for free
* Install MySQL and configure the root password
* Create new user and database for mysql by logging in and:
    * ```create database sbsDb;```
    * ```create user 'springuser'@'%' identified by 'Group7Group7';```
    * ```grant all on sbsDb.* to 'springuser'@'%';```
    * Additionally, download and use phpMyAdmin or MySQL Workbench to perform operations on the database outside the application.
* Launch IntelliJ
* Open -> select the 'server' directory
* Wait for all the dependencies to get loaded and the indices to get built
* Once done, you can 'Run' the application by clicking on the 'Play' icon in the tool bar.
* Download [postman](https://www.postman.com/downloads/) & install it
* [Postman team invite link](https://app.getpostman.com/join-team?invite_code=903243ac99adfd1175f6ce8a67a9c9d8) - this is where our API contracts and samples would be stored.

