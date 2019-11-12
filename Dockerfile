FROM alpine:latest as packager

RUN apk --no-cache add openjdk11-jdk openjdk11-jmods

ENV JAVA_MINIMAL="/opt/java"

# build minimal JRE
RUN /usr/lib/jvm/java-11-openjdk/bin/jlink \
    --verbose \
    --add-modules \
        java.base,java.sql,java.naming,java.desktop,java.management,java.security.jgss,java.instrument \
    --compress 2 --strip-debug --no-header-files --no-man-pages \
    --release-info="add:IMPLEMENTOR=radistao:IMPLEMENTOR_VERSION=radistao_JRE" \
    --output "$JAVA_MINIMAL"

FROM alpine:latest
LABEL author=huangjien

ENV JAVA_HOME=/opt/java
ENV PATH="$PATH:$JAVA_HOME/bin"

COPY --from=packager "$JAVA_HOME" "$JAVA_HOME"
COPY build/libs/instance.jar app.jar
COPY scripts scripts
COPY results results

RUN apk add --no-cache --repository http://dl-cdn.alpinelinux.org/alpine/v3.10/main/ nodejs=10.16.3-r0
RUN apk add --update npm
RUN apk update
RUN apk upgrade
RUN apk add --no-cache ttf-freefont udev chromium
# support chromium only now
RUN apk add --no-cache firefox-esr
RUN npm install -g testcafe
RUN npm install -g testcafe-reporter-json
RUN npm install -g @ffmpeg-installer/ffmpeg

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]