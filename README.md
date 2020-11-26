# Gamification engine



# Test the running application with Postman
Since the application need a api-key everytime someone want to get / add badges, events, etc, we need to pass the api-key in the **headers**. <br/>
To do that, you'll need to :
- Register your app with a POST on /applications (with a JSON payload { name : "your_name" })
- Get the api-key in the response body
- Add the api-key in your nexts request headers (the field in the headers should be named : **x-api-key**)
<br/>
Example here : https://www.getpostman.com/collections/0b87de38afa92b43a3ba

# Build and run the Badge microservice

You can use maven to build and run the REST API implementation from the command line. After invoking the following maven goal, the Spring Boot server will be up and running, listening for connections on port 8080.

```
cd badges-impl/
mvn spring-boot:run
```

You can then access:

* the [API documentation](http://localhost:8080/swagger-ui.html), generated from annotations in the code
* the [API endpoint](http://localhost:8080/), accepting GET and POST requests

You can use curl to invoke the endpoints:

* To retrieve the list of badges previously created:

```
curl -X GET --header 'Accept: application/json' 'http://localhost:8080/badges'
```

* To create a new badge (beware that in the live documentation, there are extra \ line separators in the JSON payload that cause issues in some shells)

```
curl -X POST --header 'Content-Type: application/json' --header 'Accept: */*' -d '{
  "colour": "red",
  "expirationDate": "2020-11-06",
  "expirationDateTime": "2020-11-06T05:43:27.909Z",
  "kind": "apple",
  "size": "small",
  "weight": "light"
}' 'http://localhost:8080/badges'
```

# Test the Badge microservice by running the executable specification

You can use the Cucumber project to validate the API implementation. Do this when the server is running.

```
cd cd badges-specs/
mvn clean test
```
You will see the test results in the console, but you can also open the file located in `./target/cucumber`

