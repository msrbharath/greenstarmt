FROM openjdk:8-jdk-alpine
MAINTAINER Mageshwaran <mageshwaran.k.s@cognizant.com>
ADD target/greenstarapp-discovery-service.jar greenstarapp-discovery-service.jar
ENTRYPOINT ["java", "-jar", "/greenstarapp-discovery-service.jar"]
EXPOSE 8761