FROM eclipse-temurin:21-jdk

LABEL authors="Mirmuhsin"

WORKDIR /app

COPY target/subScribe_master-0.0.1-SNAPSHOT.jar /app

ENTRYPOINT ["java", "-jar", "subScribe_master-0.0.1-SNAPSHOT.jar"]