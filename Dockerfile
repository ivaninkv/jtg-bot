FROM openjdk:11-jre-buster
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.datasource.url=${DB_URL}", \
            "-Dspring.datasource.username=${DB_USER}", \
            "-Dspring.datasource.password=${DB_PASS}", \
            "-Dbot.username=${BOT_NAME}", "-Dbot.token=${BOT_TOKEN}", \
            "-jar", "app.jar"]