FROM icr.io/appcafe/open-liberty:22.0.0.3-full-java11-openj9-ubi

COPY --chown=1001:0 src/main/liberty/config /config
COPY --chown=1001:0 src/main/liberty/* /opt/ol/wlp/usr/shared/resources/
COPY --chown=1001:0 target/*.war /config/apps

EXPOSE 3002

RUN configure.sh
CMD ["/opt/ol/wlp/bin/server", "debug", "defaultServer"]
