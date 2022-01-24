FROM openjdk:11
EXPOSE 8080
ADD target/spring-boot-docker-s3-localstack.jar spring-boot-docker-s3-localstack.jar
ENTRYPOINT [ "java", "-jar", "/spring-boot-docker-s3-localstack.jar" ]