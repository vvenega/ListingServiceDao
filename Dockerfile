FROM adoptopenjdk/openjdk8
WORKDIR /
ARG ListingServiceDao-0.0.1-SNAPSHOT.jar
ADD ListingServiceDao-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8001
ENV JAVA_OPTS="-Xms64M -Xmx128M"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar"]