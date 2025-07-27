FROM icr.io/appcafe/open-liberty:full-java21-openj9-ubi-minimal

COPY src/main/liberty/config/server.xml /config/server.xml

COPY target/note-cloud.war /config/apps/

COPY target/lib/global/mysql-connector-j-*.jar /config/lib/global/

USER root
RUN chmod -R g+w /config && \
    chmod -R g+w /opt/ol && \
    configure.sh
USER 1001
# Muestra los logs en consola
ENV WLP_LOGGING_CONSOLE_FORMAT=json

# Arranca el servidor Liberty
CMD ["server", "run", "defaultServer"]
