FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD controller-0.1.0.jar app.jar
ENV JAVA_OPTS=""
# sleep 2 seconds in order to avoid exception throwing on connecting to container with mongo that may be not ready
ENTRYPOINT sleep 2 && exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar