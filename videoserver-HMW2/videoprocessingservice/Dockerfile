FROM m1c0l/alpine-openjdk-ffmpeg
WORKDIR /app
COPY videoEncoder ./
ADD ./target/videoprocessingservice-0.0.1-SNAPSHOT.jar ./service.jar
CMD java -jar service.jar
