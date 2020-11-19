# Wendy-s-Family-Tree

## Backend
Compile the backend and execute the .jar either with or without test data file.  

	mvnw clean package  

Activate the datagen profile to initilize with test data 

	java -Dspring.profiles.active=datagen -jar target/e01634028-0.0.1-SNAPSHOT.jar
    
Or start the programm without test data

	java -jar target/e01634028-0.0.1-SNAPSHOT.jar
        
## Frontend
Start the Frontend via

	ng serve
    
Navigate to the URL http://localhost:4200/ to use the application.
