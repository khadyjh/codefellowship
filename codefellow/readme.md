# lab 16
build spring authenticate project using spring security dependency 

## to use this application
in order to use this app you need first to run the postgres database server , create new database then link it to this app, and change the database configuration using application.properties File

in first time you running this app **spring.jpa.hibernate.ddl-auto=create** then change it to become **spring.jpa.hibernate.ddl-auto=update**
you can run this app using command line like **gradle bootRun**

## routes 
**can access to this rout without need to log in or signed up**
- http://localhost:8081/login

  rout allow user to log in and redirect to hello page
- http://localhost:8081/signup

  rout allow user to sign up and redirect to the login page
- http://localhost:8081

  rout to the root page 

  

**only registered user can access this rout**
- http://localhost:8081/myprofile

  profile page for logged-in user contain basic information
- http://localhost:8081/feed

  page contain post wrote by following post 
- http://localhost:8081/userInfo
  information about searched user 
- http://localhost:8081/follow

  to follow other users 


the logged-in user can access the root page and logout from there .
the user can add new post and search for other users using username 

### needed dependency's
```
implementation 'org.springframework.boot:spring-boot-starter-security'
implementation 'org.thymeleaf.extras:thymeleaf-extras-springsecurity5'
```


