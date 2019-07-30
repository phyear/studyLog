mvn clean

mvn package

cp -f target/test.jar docker/java/

docker-compose up -d