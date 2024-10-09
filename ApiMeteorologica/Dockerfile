FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

ENV DATABASE_URL jdbc:mysql://localhost:3306/dbspring
ENV DATABASE_USERNAME root
ENV DATABASE_PASSWORD Gerardo123*
ENV DATABASE_PLATFORM org.hibernate.dialect.MySQL8Dialect
ENV DATABASE_DRIVER com.mysql.cj.jdbc.Driver

RUN mvn clean package -DskipTests


FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
