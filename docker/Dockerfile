FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY ./build/libs/poolregistration-0.0.1-SNAPSHOT.jar app.jar
RUN apk --update add tzdata && \
    cp /usr/share/zoneinfo/Asia/Yekaterinburg /etc/localtime && \
    echo "Asia/Yekaterinburg" > /etc/timezone && \
    apk del tzdata && \
    rm -rf /var/cache/apk/* \
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]