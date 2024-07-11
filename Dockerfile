FROM openjdk:17
ADD ./Twitter-Backend-0.0.1-SNAPSHOT.jar Twitter-Backend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","Twitter-Backend-0.0.1-SNAPSHOT.jar"]