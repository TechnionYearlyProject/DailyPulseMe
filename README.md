
# DailyPulseMe
[![Build Status](https://travis-ci.org/TechnionYearlyProject/DailyPulseMe.svg?branch=master)](https://travis-ci.org/TechnionYearlyProject/DailyPulseMe)
[![codecov](https://codecov.io/gh/TechnionYearlyProject/DailyPulseMe/branch/master/graph/badge.svg)](https://codecov.io/gh/TechnionYearlyProject/DailyPulseMe)



Installation and setup explanation for the backend:

1. Installing IntelliJ: 
	1.1 Download and install IntelliJ from the following link: 
	https://www.jetbrains.com/idea/download/index.html#section=windows
	1.2 Additional details on working with java and IntelliJ can be found in the following links:
	https://www.jetbrains.com/help/idea/creating-running-and-packaging-your-first-java-application.html
	https://www.jetbrains.com/idea/documentation/

2. Installing and working with Git:
	2.1 Create an account for GitHub on https://github.com/join, if you already have an account this step is optional.
	2.2 Ask the team members to add you to the project on GitHub and give you access to the GitHub repository.
	2.3 Install and setup Git according to your OS in the following link:
	https://git-scm.com/download
	2.4 Other information and explanation about the use of GitHub can be found in the following link under “Git and Github intro”:
	https://webcourse.cs.technion.ac.il/234311/Winter2017-2018/ho.html
	2.5 Create a new folder and right click inside the folder and choose “Git bash here”.
	2.6 Use the command “git clone” with the following link to clone the project’s repository in said folder:
	https://github.com/TechnionYearlyProject/DailyPulseMe.git
	Note that this will not worked unless you have access to the GitHub project repository (step 2.2).
	2.7 After cloning is done, you can open the project via IntelliJ.
	2.8 You can find issues explaining the methods and technologies that the members of the team used on GitHub at the link provided in step 2.6.

3. Accessing the cloud-hosted MongoDB:
	3.1 To get access to the Mongo database we are using, first you need to ask for the username and password to be provided by the team members.
	3.2 Login to https://mlab.com/login/ with said provided username and password.
	3.3 After logging in successfully, choose “ds042677.mlab.com:42677/dailypulsemedatabase” in the MongoDB Deployments page.
	3.4 Choose “Users” and there you can manage existing users and add a new user.
	3.5 Add a new user with the username and password of your choosing.
	3.6 Now you need to add the following line to the “application.properties” under BackEnd-2/src/main/resources :
	spring.data.mongodb.uri=mongodb://<dbuser>:<dbpassword>@ds042677.mlab.com:42677/dailypulsemedatabase.
	Where <dbuser> and <dbpassword> are replaced with the username and password of the user you added earlier.

4. Setting up the JDK:
	4.1 Download the JDK from the following link according to your OS:
	http://www.oracle.com/technetwork/java/javase/downloads/jdk9-downloads-3848520.html
	4.2 When the download is complete, run the installation and setup. Note the exact path you install the JDK to.
	4.3 After the installation is complete, Open IntelliJ.
	4.4 Choose File->Open and then from the folder you created in 2.5 Choose DailyPulseMe/BackEnd-2
	4.5 A message under the tool bar should appear saying "Project SDK is not defined"
	4.5 click on : setup SDK -> configure... -> + -> JDK . And then add the path from 4.2 and click ok -> ok.
	4.6 Now you can right-click DailyPulseApp.java and click run. The back-end project is now running.

5. Installing nodeJS:
	5.1 In order to run the client-side vue part of the project you need to install nodeJS. 
	5.2 Install nodeJS according to your OS in the following link: (no need to create an account or register)
	https://nodejs.org/en/download/current/

6. Installing VueJs and related tools:
	6.1 Now that you have installed nodeJS you can execute npm commands.
	6.2 Open a command line (cmd) and execute at the path: /cloned_path/FrontEnd (cloned_path is the path where you cloned your project on step 2.6)
	6.3 execute the command 'npm install'. you should execute this command only after you clone a project.
	     This command installs all packages related to vue and the packages that we chose to add like chartJs-vue.
	
 7. Running the VueJS web application:
	7.1 In order to run the client side properly you need to run first the server side so make sure that 
	      you are running the server side before continuing. without running the server side we can not check
	      our tokens and can not get info required like user name or graphs data.
	7.2 Go again to the same path you went on step 5.2
	7.3 Execute the command 'npm run dev'. this command will run the site.
	7.4 Open your browser and go to address 'localhost:8080'. you will see the site now.

