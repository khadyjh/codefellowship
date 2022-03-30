# lab 16
build spring authenticate project using spring security dependency 

## routes 
**can access to this rout without need to log in or signed up**
- http://localhost:8081/login

  rout allow user to log in and redirect to hello page
- http://localhost:8081/signup

  rout allow user to sign up and redirect to the login page
- http://localhost:8081

  rout to the root page 

  

**only registered user can access this rout**
- http://localhost:8081/hello


the logged-in user can access the root page and logout from there .

### needed dependency's
```
implementation 'org.springframework.boot:spring-boot-starter-security'
implementation 'org.thymeleaf.extras:thymeleaf-extras-springsecurity5'
```


