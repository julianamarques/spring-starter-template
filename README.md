# Spring Starter Template

Projeto template de API com algumas configurações já feitas

### Como usar?

1. No `application.yml`:
    1. Altere com o nome da aplicação e as credenciais do banco:
       ```yml
        spring:
            application:
                name: nome-aplicacao
            datasource:
                url: url_banco
                username: usuario_banco
                password: senha_banco
                driver-class-name: driver_banco
       ```    
    2. Altere o ```context-path``` para um relacionado ao da sua aplicação. Se necessário, altere também a porta:
       ```yml
       server:
         port: ${PORT:8080}
         servlet:
           context-path: ${CONTEXT_PATH:/base-url}
       ```
    3. Caso use anexo de arquivos, altere o tamanho, se necessário:
   ```yml
   servlet:
      multipart:
      enabled: true
      max-request-size: 50MB
      max-file-size: 50MB
   ```
    4. Altere as configurações de email, caso queira utilizar, o ```JavaEmailSend```. Se não usar, pode remover:
    ```yml
    mail:
      host: smtp.gmail.com
      port: 587
      username: noreply@email.com.br
      password: 12345
      protocol: smtp
    ```
    5. Lembrando que o valor do atributo ```ddl-auto``` está ```validate```, ou seja, ele valida o mapeamento das entidades de acordo com o que está modelado no banco de dados, é possível substituir para ```update```, ```create```, ```create-drop```, ou até mesmo ```none```.
   ```yml
    hibernate:
      naming:
        physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
        implicit-strategy: org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl
      ddl-auto: validate
    ```
    6. Caso queira habilitar o Flyway altere o ```enabled``` para ```true``` e coloque os scripts de migração no caminho ```classpath:db/migration``` ou renomeie esse caminho para uma de sua preferência:
   ```yml
    flyway:
      enabled: false
      locations: classpath:db/migration
    ```
2. Renomeie o package ```br.com.project.spring.starter.template.api``` para ```br.com.nomesuaaplicacao.api```, tanto no ```/src/main/java``` quanto no ```/src/test/java```
3. Renomeie o arquivo ```ProjectTemplateApiApplication``` e ```ProjectTemplateApiApplicationTests``` para ```NomeSuaAplicacaoApiApplication``` e ```NomeSuaAplicacaoApiApplicationTests```
4. No ```pom.xml```, altere o ```<groupId>```, ```<name>``` e ```<description>```, para algo correspondente a sua aplicação:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>br.com.project.spring.starter.template</groupId>
    <artifactId>api</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>spring-starter-template-api</name>
    <description>Template Spring Starter: Projeto Template API</description>
</project>
```

### Checkstyle

Você pode verificar o checkstyle e manter o padrão de formatação do seu código através do comando:

```sh
mvn checkstyle:check
```
