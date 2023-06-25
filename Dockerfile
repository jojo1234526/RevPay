#FROM scratch
#
#RUN install libraries
#RUN apt install maven
#RUN apt install openjdk
#
#COPY . .
#
#CMD[]

#Rather than scratch, we can use a prebuilt image with JDK already installed
FROM openjdk:21-ea-17-slim-buster

#copying from host file path -----> pasting to new file path
COPY target/*.jar /a/demo.jar


CMD ["java", "-jar", "/a/demo.jar"]