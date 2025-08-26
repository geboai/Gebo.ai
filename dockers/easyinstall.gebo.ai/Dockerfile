# ---- Base
FROM geboai/platform:2.2
LABEL mantainer=paolo.zavalloni@gebo.ai
LABEL org.opencontainers.image.authors="paolo.zavalloni@gebo.ai"
LABEL org.opencontainers.image.title="Gebo.ai"
LABEL org.opencontainers.image.version="0.9.9.7-SNAPSHOT"
LABEL org.opencontainers.image.description="Gebo.ai enterprise rag & agents/chatbot platform"
LABEL org.opencontainers.image.vendor="Gebo.ai"
LABEL org.opencontainers.image.source="https://github.com/geboai/Gebo.ai"
LABEL org.opencontainers.image.licenses="MPL-2.0,https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/"
LABEL org.opencontainers.image.sbom="/opt/gebo.ai/sbom.cdx.json"
RUN mkdir -p /opt
RUN mkdir -p /opt/gebo.ai
RUN mkdir -p /opt/gebo.ai/home
RUN mkdir -p /opt/gebo.ai/config
RUN mkdir -p /opt/gebo.ai/work
RUN mkdir -p /opt/gebo.ai/shares
COPY gebo.ai.app-0.9.9.7-SNAPSHOT-bootable.jar /opt/gebo.ai
COPY application.cdx.json  /opt/gebo.ai/sbom.cdx.json
COPY config/application.yml /opt/gebo.ai/config

VOLUME /opt/gebo.ai/home 
VOLUME /opt/gebo.ai/work
VOLUME /opt/gebo.ai/config
VOLUME /opt/gebo.ai/shares
VOLUME /opt/gebo.ai/logs
#RUN useradd --home-dir /opt/gebo.ai --shell /bin/bash gebo.ai
#RUN chown gebo.ai:users -R /opt/gebo.ai
#USER gebo.ai
ENV  JAVA_TOOL_OPTIONS="-Dsun.jnu.encoding=UTF-8 -Dfile.encoding=UTF-8 --add-opens=java.base/java.nio.charset=ALL-UNNAMED --enable-native-access=ALL-UNNAMED -Xms2g -Xmx4g -XX:+AggressiveHeap -XX:MaxMetaspaceSize=512m -XX:+PrintGCDetails -Xlog:gc:/opt/gebo.ai/logs/gc.log"
ENV  GEBO_WORK_DIRECTORY="/opt/gebo.ai/work"
ENV  GEBO_HOME="/opt/gebo.ai/home"
EXPOSE 12999


ENV DEBIAN_FRONTEND=noninteractive \
    MONGO_DATA=/data/db \
    QDRANT_DIR=/var/lib/qdrant \
    QDRANT_CONFIG=/etc/qdrant/config.yaml

ARG MONGO_MAJOR=8.0.13
ARG QDRANT_VERSION=1.12.4

RUN apt-get update && apt-get install -y --no-install-recommends \
      ca-certificates curl gnupg lsb-release supervisor tini \
    && rm -rf /var/lib/apt/lists/*

RUN install -d -m 0755 /etc/apt/keyrings \
 && curl -fsSL https://pgp.mongodb.com/server-${MONGO_MAJOR}.asc \
    | gpg --dearmor -o /etc/apt/keyrings/mongodb-server-${MONGO_MAJOR}.gpg \
 && echo "deb [signed-by=/etc/apt/keyrings/mongodb-server-${MONGO_MAJOR}.gpg arch=$(dpkg --print-architecture)] \
    https://repo.mongodb.org/apt/ubuntu $(. /etc/os-release && echo ${UBUNTU_CODENAME:-noble})/mongodb-org/${MONGO_MAJOR} multiverse" \
    > /etc/apt/sources.list.d/mongodb-org-${MONGO_MAJOR}.list \
 && apt-get update && apt-get install -y --no-install-recommends mongodb-org \
 && rm -rf /var/lib/apt/lists/* \
 && mkdir -p ${MONGO_DATA} /var/log/mongodb \
 && chown -R mongodb:mongodb ${MONGO_DATA} /var/log/mongodb

RUN curl -L -o /tmp/qdrant.deb \
      https://github.com/qdrant/qdrant/releases/download/v${QDRANT_VERSION}/qdrant_${QDRANT_VERSION}-1_amd64.deb \
 && apt-get update \
 && apt-get install -y --no-install-recommends /tmp/qdrant.deb \
 && rm -rf /var/lib/apt/lists/* /tmp/qdrant.deb \
 && mkdir -p ${QDRANT_DIR} /var/log/qdrant

RUN mkdir -p /etc/qdrant
COPY qdrant-config.yaml ${QDRANT_CONFIG}

COPY supervisord.conf /etc/supervisor/conf.d/supervisord.conf

VOLUME ["/data/db", "/var/lib/qdrant"]
EXPOSE 27017 6333

ENTRYPOINT ["/usr/bin/tini", "--"]
CMD ["supervisord", "-n", "-c", "/etc/supervisor/supervisord.conf"]
