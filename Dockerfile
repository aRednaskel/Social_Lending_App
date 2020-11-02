FROM openjdk:11-jdk
EXPOSE 8080
COPY ${JAR_FILE} ${JAR_FILE}
ADD target/backend2-*.jar backend2.jar
ENTRYPOINT ["java","-jar","/backend2.jar"]
