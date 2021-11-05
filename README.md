# Trading Here

## Application

Describe microservice here...

### Code template

We are using  
**TODO**

### IntelliJ plugin
**TODO**

#### Run tests and code coverage
```
./gradlew clean test
```
\
Test HTML report: `build/reports/tests/test/index.html`

Coverage HTML report: `build/reports/coverage/index.html`

#### Run components tests
```
./gradlew testComponent
```
\
Component test HTML report: `build/reports/tests/componentTest/cucumber-html-reports/overview-features.html`

#### Setup application
Go on folder `dependencies/` and start _docker-compose_
```
docker-compose up -d --build
```
\
To turn off
```bash
docker-compose down
```

#### Start application
* Needs execute command from: _Setup application_ start _docker-compose_
```
./gradlew bootRun
```
Open [`http://localhost:8080/swagger-ui/`](http://localhost:8080/swagger-ui/) to use endpoints from application

* default port app is `8080`

#### Build application
```
./gradlew clean build -x test -x componentTest -Pbuild.number=[VERSION APP]
```

