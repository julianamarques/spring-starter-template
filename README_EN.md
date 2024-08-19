# Spring Starter Template

Template project with some common configurations already made and JWT authentication implemented to be used as a basis in other projects

### Necessary requirements 💻

* Java 21+
* Maven 3.9.6+

### How to use? ⚙️

1. In `application.yml`:
    1. Change with the application name and database credentials:
       ```yml
        spring:
            application:
                name: application-name
            datasource:
                url: url_db
                username: user_db
                password: password_db
                driver-class-name: driver_db
       ``` 
    2. Run the script from the ```src/main/resources/db/migration``` folder to initialize the database
    3. Change the ```context-path``` to one related to your application. If necessary, also change the port:
       ```yml
       server:
         port: ${PORT:8080}
         servlet:
           context-path: ${CONTEXT_PATH:/base-url}
       ```
    4. If you use file attachments, change the size of the attached files if necessary:
   ```yml
   servlet:
      multipart:
      enabled: true
      max-request-size: 50MB
      max-file-size: 50MB
   ```
    5. Change the email settings, if you want to use ```JavaEmailSend```. If you don't use it, you can remove:
   ```yml
   mail:
    host: smtp.gmail.com
    port: 587
    username: noreply@email.com.br
    password: 12345
    protocol: smtp
   ```
    6. Remembering that the value of the ```ddl-auto``` attribute is ```validate```, that is, it validates the mapping of entities according to what is modeled in the database, it is possible to change it to ```update```, ```create```, ```create-drop```, or even ```none```.
   ```yml
   hibernate:
    naming:
      physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
      implicit-strategy: org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl
    ddl-auto: validate
   ```
    7. If you want to enable ```Flyway``` change ```enabled``` to ```true``` and place the migration scripts in the path ```classpath:db/migration``` or rename it path to one of your preference:
   ```yml
   flyway:
    enabled: false
    locations: classpath:db/migration
   ```
   Remembering that in ```Flyway``` the scripts must be named as follows: ```V1.01__your_change.sql```. Example: ```V1.01__update_table_user.sql```.
2. Rename the package ```br.com.project.spring.starter.template.api``` to ```br.com.yourapplication.api```, both in ```/src/main/java``` as in ```/src/test/java```
3. Rename the file ```ProjectTemplateApiApplication``` and ```ProjectTemplateApiApplicationTests``` to ```NomeSuaAplicacaoApiApplication``` and ```NomeSuaAplicacaoApiApplicationTests```
4. In ```pom.xml```, change the ```<groupId>```, ```<name>``` and ```<description>```, to something corresponding to your application:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>br.com.project.spring.starter.template</groupId>
    <artifactId>api</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>spring-starter-template-api</name>
    <description>Spring Starter Template: Template Project</description>
</project>
```

### Running the project ▶️

After the configurations are completed, run the project with:

```sh
mvn clean install -DskipTests spring-boot:run
```

### Checkstyle ✅

You can check the checkstyle and maintain the formatting standard of your code using the command:

```sh
mvn checkstyle:check
```

### Contributting 🤝

Contributions are welcome! Feel free to open a pull request to propose improvements or fixes.

### Read in Portuguese 🇧🇷

[Click here to read in portuguese](README.md)
