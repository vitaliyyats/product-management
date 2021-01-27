FROM bellsoft/liberica-openjdk-alpine-musl:latest
WORKDIR /usr/local/app
ADD target/productmanagement-0.0.1-SNAPSHOT.jar app.jar
CMD java -jar ./app.jar