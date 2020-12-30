"Gas & Water Usage Monitoring Application"
Create an application to monitor gas, cold and hot water usage. No UI needed, only REST API. Two REST API methods should be implemented: one for submitting the current measurements for a given user, other for getting the history of previously submitted measurements for a given user. User inputs should be validated to reject incomplete or invalid data.

Technical Requirements
1.	Use Java 1.8, Spring Framework and Maven.
2.	Use other Java libraries as needed.
3.	Use HSQLDB for storing data. It is ok NOT to persist data across application launches.
4.	Try following all the good principles of writing qualitative and testable code.
5.	Fill in missing requirements as you feel suitable.
6.	Include a short README file describing how the application works and how to build and run the project.


# How to start
To start the application, use `mvn spring-boot:run`

# How to use
Go to `localhost:8080/swagger-ui.html`
You will be presented with 3 endpoints
 - /measurements/users - get method to get a list of the users
 - /measurements/users/{userId} - get method to get a list of the measurements for the specified user
 - /measurements/users/{userId} - post method to add a measurement for the specified user

To see some examples of the expected behavior, check the test `com.coolcompany.gasandwaterusage.controllers.GasAndWaterControllerTest`