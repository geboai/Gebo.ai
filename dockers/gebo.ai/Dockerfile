FROM geboai/platform:2.5
LABEL mantainer=paolo.zavalloni@gebo.ai
LABEL org.opencontainers.image.authors="paolo.zavalloni@gebo.ai"
LABEL org.opencontainers.image.title="Gebo.ai"
LABEL org.opencontainers.image.version="1.0.4.0-SNAPSHOT"
LABEL org.opencontainers.image.description="Gebo.ai enterprise rag & agents/chatbot platform"
LABEL org.opencontainers.image.vendor="Gebo.ai"
LABEL org.opencontainers.image.source="https://github.com/geboai/Gebo.ai"
LABEL org.opencontainers.image.licenses="MPL-2.0,https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/"
LABEL org.opencontainers.image.sbom="/opt/gebo.ai/sbom.cdx.json"
# The base image (geboai/platform) already creates the whole /opt/gebo.ai layout;
# these are kept so this Dockerfile stays buildable against an older base tag.
RUN mkdir -p /opt/gebo.ai/home \
             /opt/gebo.ai/work \
             /opt/gebo.ai/config \
             /opt/gebo.ai/logs \
             /opt/gebo.ai/shares
COPY gebo.ai.app-1.0.4.0-SNAPSHOT-bootable.jar /opt/gebo.ai
COPY application.cdx.json  /opt/gebo.ai/sbom.cdx.json
COPY config/application.yml /opt/gebo.ai/config

# ---------------------------------------------------------------------------
# PERSISTENT surface. Declared AFTER every COPY/RUN that writes below these
# paths. BuildKit keeps such writes, but the classic builder (and some other OCI
# builders) mount a scratch volume for the RUN and throw the changes away, which
# is what the Docker docs warn about - declaring volumes last costs nothing and
# is portable across builders.
#
#   work    DURABLE user data - chat SESSIONS_AREA, per-endpoint content
#           mirrors, the document cache, uploads, Google Workspace OAuth
#           tokens. Mongo only INDEXES it; the bytes live here, so losing it
#           leaves dangling references. THIS is what has to survive an upgrade.
#   home    GEBO_HOME, the application's own home area.
#   logs    security-log.jsonl, the append-only GDPR Art.32 / NIS2 Art.21 audit
#           trail (WORKDIR /opt/gebo.ai + logback's ${GEBO_LOG_BASE:-logs}),
#           plus gc.log from the -Xlog:gc flag below.
#   shares  host filesystem shares exposed to the filesystem content handler.
#
# /opt/gebo.ai/config is deliberately NOT a volume. It used to be one, and that
# is a REAL, reproduced upgrade hazard: Docker creates an anonymous volume there
# on first run and seeds it from this image, Compose then carries that same
# volume across the container recreate an upgrade performs, and the OLD
# application.yml goes on masking the one shipped by every later image. Verified
# by pulling a new image and finding v1's config still being served.
# Config is a MOUNT - bind your own over it (see docker-compose.yml) or take the
# baked default, which upgrades with the image because nothing shadows it.
#
# These are anonymous volumes unless the deployment mounts something over them,
# and an anonymous volume does NOT survive `docker compose down`. Always mount a
# named volume or a host path - see dockers/PERSISTENCE.md.
# ---------------------------------------------------------------------------
VOLUME /opt/gebo.ai/home
VOLUME /opt/gebo.ai/work
VOLUME /opt/gebo.ai/logs
VOLUME /opt/gebo.ai/shares
#RUN useradd --home-dir /opt/gebo.ai --shell /bin/bash gebo.ai
#RUN chown gebo.ai:users -R /opt/gebo.ai
#USER gebo.ai
# No -XX:+AggressiveHeap: it silently swaps G1 for the throughput collector. No
# -XX:+PrintGCDetails: it is deprecated and re-routes the whole GC stream to
# stdout (i.e. into `docker logs`) instead of gc.log. The compose deployment
# overrides JAVA_TOOL_OPTIONS with these same corrected flags; this keeps a bare
# `docker run` of the image consistent with it. See docker-compose-deploy/docker-compose.yml.
ENV  JAVA_TOOL_OPTIONS="-Dsun.jnu.encoding=UTF-8 -Dfile.encoding=UTF-8 --add-opens=java.base/java.nio.charset=ALL-UNNAMED --enable-native-access=ALL-UNNAMED -Xms2g -Xmx4g -XX:MaxMetaspaceSize=512m -Xlog:gc:file=/opt/gebo.ai/logs/gc.log:time,uptime:filecount=5,filesize=20M"
ENV  GEBO_WORK_DIRECTORY="/opt/gebo.ai/work"
ENV  GEBO_HOME="/opt/gebo.ai/home"
EXPOSE 12999
WORKDIR /opt/gebo.ai
ENTRYPOINT  java $JAVA_TOOL_OPTIONS -DGEBO_WORK_DIRECTORY=$GEBO_WORK_DIRECTORY -DGEBO_HOME=$GEBO_HOME  -jar /opt/gebo.ai/gebo.ai.app-1.0.4.0-SNAPSHOT-bootable.jar