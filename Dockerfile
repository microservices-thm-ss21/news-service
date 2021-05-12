FROM openjdk:14
VOLUME /tmp
ADD build/libs/news-service-0.0.1-SNAPSHOT.jar news-service.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","news-service.jar"]
