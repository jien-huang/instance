FROM alpine:latest as packager

RUN apk --no-cache add openjdk17-jdk openjdk17-jmods

ENV JAVA_MINIMAL="/opt/java"

# build minimal JRE
RUN /usr/lib/jvm/java-17-openjdk/bin/jlink \
    --verbose \
    --add-modules \
        java.base,java.sql,java.naming,java.desktop,java.management,java.security.jgss,java.instrument \
    --compress 2 --strip-debug --no-header-files --no-man-pages \
    --release-info="add:IMPLEMENTOR=radistao:IMPLEMENTOR_VERSION=radistao_JRE" \
    --output "$JAVA_MINIMAL"

FROM alpine:latest
LABEL Author = "huangjien@gmail.com"

ENV JAVA_HOME=/opt/java
ENV PATH="$PATH:$JAVA_HOME/bin"
ENV local.browser=chromium

COPY --from=packager "$JAVA_HOME" "$JAVA_HOME"
COPY build/libs/instance.jar app.jar
COPY scripts scripts
COPY results results
COPY application.properties .
COPY config.properties .
COPY log4j.properties .
COPY my.properties .
# COPY public public

RUN apk add --no-cache --repository http://dl-cdn.alpinelinux.org/alpine/v3.15/main/ nodejs
RUN apk add --update npm
RUN apk update
RUN apk upgrade
RUN apk add --no-cache ttf-freefont udev chromium
RUN apk add  --no-cache ffmpeg
RUN apk add --no-cache curl
# support chromium only now
RUN apk add --no-cache firefox-esr
RUN npm cache clean --f
RUN npm install -g testcafe
RUN npm install -g testcafe-reporter-json
RUN npm install -g @ffmpeg-installer/ffmpeg
RUN rm -rf /var/lib/apt/lists/* && \
    rm /var/cache/apk/*

EXPOSE 8090
EXPOSE 4200
ENTRYPOINT ["java","-jar","app.jar"]
