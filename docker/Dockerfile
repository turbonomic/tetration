FROM alpine:latest
MAINTAINER Michael Borodiansky <michael.borodiansky@turbonomic.com>

COPY entrypoint.sh / *.jar /

RUN apk --update --no-cache add openjdk8-jre-base bash && \
chmod 755 /entrypoint.sh && \
addgroup -S turbonomic && \
adduser -S -D -h /home/turbonomic -G turbonomic -s /bin/bash turbonomic && \
passwd -l root

USER turbonomic

RUN mkdir -p /home/turbonomic/data/keys

VOLUME ["/home/turbonomic/data", "/home/turbonomic/config"]

ENTRYPOINT ["/entrypoint.sh"]

