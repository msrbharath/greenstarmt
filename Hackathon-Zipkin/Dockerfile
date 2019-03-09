FROM openjdk:8-jdk-alpine
MAINTAINER Mageshwaran <mageshwaran.k.s@cognizant.com>
ADD target/greenstarapp-zipkin-service.jar greenstarapp-zipkin-service.jar
ENTRYPOINT ["java", "-jar", "/greenstarapp-zipkin-service.jar"]
EXPOSE 9411