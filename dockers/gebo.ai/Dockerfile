# The hardened Java 25 base (dockers/gebo.ai.platform): no shell, no package
# manager, uid 10001. Overridable so a test build can point at another tag.
ARG PLATFORM_IMAGE=geboai/platform:3.0
FROM ${PLATFORM_IMAGE}
LABEL mantainer=paolo.zavalloni@gebo.ai
LABEL org.opencontainers.image.authors="paolo.zavalloni@gebo.ai"
LABEL org.opencontainers.image.title="Gebo.ai"
LABEL org.opencontainers.image.version="1.0.6.0"
LABEL org.opencontainers.image.description="Gebo.ai enterprise rag & agents/chatbot platform"
LABEL org.opencontainers.image.vendor="Gebo.ai"
LABEL org.opencontainers.image.source="https://github.com/geboai/Gebo.ai"
LABEL org.opencontainers.image.licenses="MPL-2.0,https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/"
LABEL org.opencontainers.image.sbom="/opt/gebo.ai/sbom.cdx.json"
# NO RUN steps: the base has no shell. Everything that used to be RUN here lives
# in the base image now - the /opt/gebo.ai layout (owned by uid 10001) and
# /usr/bin/tini. The COPYs below stay root-owned and read-only to the app on
# purpose: the jar and the baked config are not the application's to modify.
COPY gebo.ai.app-1.0.6.0-bootable.jar /opt/gebo.ai
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
# Runs as uid 10001 (USER is inherited from the base). Volumes created by an
# older, root-running image are root-owned: the compose files fix that with a
# one-shot init service, and dockers/PERSISTENCE.md section 4 has the one-liner
# for plain `docker run` installs.
#
# JAVA_TOOL_OPTIONS is read by the JVM itself, so the entrypoint does not repeat
# it. No -XX:+AggressiveHeap: it silently swaps G1 for the throughput collector.
# No -XX:+PrintGCDetails: it is deprecated and re-routes the whole GC stream to
# stdout (i.e. into `docker logs`) instead of gc.log. No -Dsun.jnu.encoding: the
# JDK ignores it - file-name encoding comes from the base's LANG=C.UTF-8 locale,
# which is what makes Unicode names on mounted Windows/SMB/NTFS shares readable.
# The compose deployment overrides JAVA_TOOL_OPTIONS with these same flags; this
# keeps a bare `docker run` of the image consistent with it. See
# docker-compose-deploy/docker-compose.yml.
ENV  JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8 --add-opens=java.base/java.nio.charset=ALL-UNNAMED --enable-native-access=ALL-UNNAMED -Xms2g -Xmx4g -XX:MaxMetaspaceSize=512m -Xlog:gc:file=/opt/gebo.ai/logs/gc.log:time,uptime:filecount=5,filesize=20M"
# Read by EnvironmentHolder (system property OR environment variable).
ENV  GEBO_WORK_DIRECTORY="/opt/gebo.ai/work"
ENV  GEBO_HOME="/opt/gebo.ai/home"
EXPOSE 12999
WORKDIR /opt/gebo.ai
# Exec form, no shell: tini is PID 1, hands docker's SIGTERM to the JVM (which
# then shuts down gracefully instead of being SIGKILLed at the stop timeout) and
# reaps the orphaned children of the processes the application starts (stdio MCP
# servers).
ENTRYPOINT ["/usr/bin/tini", "--", "java", "-jar", "/opt/gebo.ai/gebo.ai.app-1.0.6.0-bootable.jar"]
