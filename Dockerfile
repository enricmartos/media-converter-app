#Multi-stage approach
FROM openjdk:8-slim-buster as builder
USER root
RUN mkdir /app
WORKDIR /app
COPY . .
RUN ./gradlew build

FROM openjdk:8-slim-buster
USER root
RUN apt-get update && apt-get install -y imagemagick
RUN apt-get update && apt-get install -y ffmpeg
# Add server.props to the container in /opt/conf new dir
WORKDIR /opt
RUN mkdir conf
ENV CONF_DIR=/opt/conf
WORKDIR $CONF_DIR
COPY devresources/mediaconverter.properties $CONF_DIR
ENV ARTIFACT_NAME=configuration-layer-0.0.1-SNAPSHOT.jar
WORKDIR /app
#copy artifacts from one stage to the next
COPY --from=builder  /app/configuration-layer/build/libs/$ARTIFACT_NAME .
EXPOSE 8080
#ENTRYPOINT ["java","-jar", "user-rest-api-1.0.jar"]
#CMD [ "sh", "-c", "java -Dserver.port=$PORT -jar configuration-layer-0.0.1-SNAPSHOT.jar" ]
CMD [ "sh", "-c", "java -Dserver.port=8080 -jar configuration-layer-0.0.1-SNAPSHOT.jar" ]


#FROM openjdk:8-slim-buster
#USER root
#RUN apt-get update && apt-get install -y imagemagick
#RUN apt-get update && apt-get install -y ffmpeg
## Add server.props to the container in /opt/conf new dir
#WORKDIR /opt
#RUN mkdir conf
#ENV CONF_DIR=/opt/conf
#WORKDIR $CONF_DIR
#COPY devresources/mediaconverter.properties $CONF_DIR
#WORKDIR /usr
#RUN mkdir app
#
#COPY . /usr/app
#WORKDIR /usr/app
#
#
#EXPOSE 8080
#CMD [ "sh", "-c", "./gradlew build" ]
##CMD [ "sh", "-c", "java -Dserver.port=8080 -jar configuration-layer/build/libs/configuration-layer-0.0.1-SNAPSHOT.jar" ]
#CMD [ "sh", "-c", "java -Dserver.port=$PORT -jar configuration-layer/build/libs/configuration-layer-0.0.1-SNAPSHOT.jar" ]
##CMD [ "sh", "-c", "./gradlew bootRun" ]
