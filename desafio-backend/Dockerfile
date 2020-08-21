#
# Build stage
#
FROM adoptopenjdk/openjdk11:x86_64-alpine-jdk-11.0.4_11 as packager
EXPOSE 8090
ADD /target/DesafioBackend.jar DesafioBackend.jar
ENTRYPOINT ["java","-jar","DesafioBackend.jar"]
