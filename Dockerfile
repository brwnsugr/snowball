FROM adoptopenjdk/openjdk11

# Add Author info
LABEL maintainer="swdream.a@gmail.com"

# Add a volume to /tmp
VOLUME /tmp

ENV SPRING_PROFILES_ACTIVE DEV

WORKDIR /snowball

# Make port 8080 available to the world outside this container
EXPOSE 8080

ADD build/libs/snowball-0.0.1-SNAPSHOT.jar snowball-0.0.1-SNAPSHOT.jar
# The Application's jar file
ARG JAR_FILE=snowball-0.0.1-SNAPSHOT.jar
#COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "snowball-0.0.1-SNAPSHOT.jar"]
#ENTRYPOINT ["./gradlew build", "java -jar build/libs/snowball-0.0.1-SNAPSHOT.jar"]