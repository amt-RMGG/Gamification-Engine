# Gamification engine 

# Test the running application with Postman & newman
We have defined a Postman collection (Badge.postman_collection.json) that you can import. <br>
It will use envrionnement variables, like the api key or last badge id created.
You can also run the collection with Newman to : <br>
- Register a demo application
- Add a badge, a event counter and a rule

First you need to install Newman : <br>
`npm install -g newman` <br>
Then run the .json collection file : <br>
`newman run Badge.postman_collection.json` <br>
It will create all the stuff mentionned above and run the test on the status code.
# Build and run the Badge microservice

## With maven
First, from the root directory, start the database with the ```./start-db.sh``` script. Then, go into the `badges-impl` directory and run ```mvn spring-boot:run```.  

## With docker
From the root directory, run the script ```./launch-service.sh```.  

# Use the Badge microservice
After starting the server with any of the methods described above, the Spring Boot server will be running and listening to communications on port 8090.

You can then access:

* the [API documentation](http://localhost:8090/swagger-ui/), generated from annotations in the code
* the [API endpoint](http://localhost:8090/), accepting GET and POST requests

You can use curl to invoke the endpoints:

* To retrieve the list of badges previously created:

```
curl -X GET --header 'Accept: application/json' 'http://localhost:8090/badges'
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

You can use the Cucumber project to validate the API implementation. Do this when the server is up and running.

```
cd badges-specs/
mvn clean test
```
You will see the test results in the console, but you can also open the file located in `./target/cucumber`

