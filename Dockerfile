FROM maven:3.8.8-amazoncorretto-21 as builder
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package -Dspring.profiles.active=dev -Dmaven.test.skip=true -e -X

FROM amazoncorretto:21-alpine as runner
WORKDIR /app
COPY --from=builder /usr/src/app/target/credit-service*.jar /credit-service.jar
EXPOSE 8080
CMD ["java", "-jar", "-Dspring.profiles.active=dev", "/credit-service.jar"]