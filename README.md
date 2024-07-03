# PS1_proj8
Using reverse engineered Java Classes to move JSON objects using the gilhari microservice
This project will use Software Tree's products JDX (an ORM mapping tool) and Gilhari to first reverse engineer Java classes from an ORM mapping, and then running the Gilhari app specific microservice in a docker image to transfer JSON data in and out of the RDBMS.
*****************************************************************
Note that the the microservice is database agnostic and that any database can be used, with only minor changes to the config files and the service_config files. This improves efficiency of the software it is being used in conjunction with as there is no need for different formats of object data for different databases. JDX provides an ORM mapping by itself given specifications of the table to be mapping and creates a schema for the user unless told not to. Overall, in conjunction, JDX and Gilhari provide for a low code, highly maintainable and efficient database management system.
NOTE: JDX and Gilhari are trademark tools of Software Tree, California and have been used with necessary authority only for the scope of this project.
*****************************************************************
Refer to the master branch for code. Following is a simple overview of the project.
OS: linux
DB: MySQL
JDBC driver: mysql-connector-j-8.4.0.jar
*****************************************************************
Schema.config contains the details for the ORM mapping JDX needs. Reverse engineering this will lead to the generation of Java classes for each table, which we have to later compile.

schema.config.revjdx contains vital information about the database, its specifications, login credentials and JDBC driver used. It also contains the semantic mapping that you would want in your table, but a user may alter these details according to their needs.

the bin directory contains all compiled class files (executed by the .sh file)

Classmaps.js is an optional file that simply eases the Object mapping. 

Gilhari_service.config is another important file that specifies the path of the JDBC driver, class files and .revjdx files with respect to the present working directory.

These files and directories must then be specified in a dockerfile as an ADD/COPY instruction to build the docker image. This will create an App specific image over the base gilhari image. 
Note that localhost is not an option and you can pass the IP address of your system for linux users or "host.docker.internal" for mac/windows users.

Once the image container is up and running, the microservice can communicate with a standalone program to transfer data in and out of the RDBMS.
*****************************************************************
