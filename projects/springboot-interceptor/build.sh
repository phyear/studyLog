mvn clean

mvn package

cp target/test.jar docker/java/

docker-compose up -d