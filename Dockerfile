FROM openjdk:21

WORKDIR /app

COPY . /app

RUN ./mvnw -B clean package -DskipTests

EXPOSE 9111

CMD ["java", "-jar", "target/desafioApiUsuarios-0.0.1-SNAPSHOT.jar"]