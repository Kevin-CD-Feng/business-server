FROM registry.xingtu.com:8088/openvone/biz-service/java-alpine-openjdk8:v1.8
RUN mkdir -p /prepare /prepare/json

WORKDIR /prepare
ENV TZ=Asia/Shanghai
COPY target/recognition-prepare-service-1.0-SNAPSHOT.jar  app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

#ARG JAR_PATH=""
#ARG PROFILE_ACTIVE=pro
#ARG JAR_NAME="app.jar"
#ARG WORK_HOME="/prepare"
#
#ENV TZ=Asia/Shanghai
#ENV JAVA_OPTIONS=""
#ENV JAR_PATH=${JAR_PATH}
#ENV JAR_NAME=${JAR_NAME}
#ENV PROFILE_ACTIVE=${PROFILE_ACTIVE}
#RUN mkdir -p $WORK_HOME
#
#WORKDIR $WORK_HOME
#COPY $JAR_PATH/target/recognition-prepare-service-1.0-SNAPSHOT.jar  app.jar
#COPY $JAR_PATH/target/*.yml  $WORK_HOME/
#
#EXPOSE 9529
#CMD java $JAVA_OPTIONS -jar -Dspring.profiles.active=$PROFILE_ACTIVE $JAR_NAME