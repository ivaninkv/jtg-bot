mvn clean
mvn package -DskipTests
docker-compose stop
docker-compose up --build -d
