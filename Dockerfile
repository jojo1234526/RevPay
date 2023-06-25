##FROM scratch
##
##RUN install libraries
##RUN apt install maven
##RUN apt install openjdk
##
##COPY . .
##
##CMD[]
#
##Rather than scratch, we can use a prebuilt image with JDK already installed
#FROM openjdk:21-ea-17-slim-buster
#
##copying from host file path -----> pasting to new file path
#COPY target/*.jar /a/demo.jar
#
#
#CMD ["java", "-jar", "/a/demo.jar"]

FROM maven:3.9.2-amazoncorretto-17 AS Maven_Build_Stage

#Copy all our source files
COPY ./ ./

#Create a Jar file
RUN mvn clean package

#Create Lightweight distributable image
FROM openjdk:21-ea-17-slim-buster

COPY --from=Maven_Build_Stage /target/*jar /demo.jar

CMD ["java", "-jar", "/RevPay-0.0.1-SNAPSHOT.jar"]