FROM adoptopenjdk/openjdk8
WORKDIR /
ARG ListingServiceDao-0.0.1-SNAPSHOT.jar
ADD ListingServiceDao-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8001
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]