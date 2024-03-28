FROM java-alpine-openjdk8:V1.8
RUN mkdir -p /third

WORKDIR /third
ENV TZ=Asia/Shanghai

COPY third-algorithm-platform-service.jar  third.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "third.jar"]