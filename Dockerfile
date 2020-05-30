# Multi-stage approach
FROM gradle:6.0.1-jdk8 as builder
USER root
RUN mkdir /app
WORKDIR /app
COPY . .
RUN gradle build

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
# Copy artifacts from one stage to the next
COPY --from=builder  /app/configuration-layer/build/libs/$ARTIFACT_NAME .
CMD [ "sh", "-c", "java -Dserver.port=$PORT -jar configuration-layer-0.0.1-SNAPSHOT.jar" ]
