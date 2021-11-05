mvn clean
docker-compose up db -d
mvn -P local package
docker-compose stop
docker-compose up --build -d
