FROM geboai/platform:2.2
LABEL mantainer=paolo.zavalloni@gebo.ai
LABEL org.opencontainers.image.authors="paolo.zavalloni@gebo.ai"
LABEL org.opencontainers.image.title="Gebo.ai"
LABEL org.opencontainers.image.version="0.9.9.8"
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
COPY gebo.ai.app-0.9.9.8-bootable.jar /opt/gebo.ai
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
WORKDIR /opt/gebo.ai
ENTRYPOINT  java $JAVA_TOOL_OPTIONS -DGEBO_WORK_DIRECTORY=$GEBO_WORK_DIRECTORY -DGEBO_HOME=$GEBO_HOME  -jar /opt/gebo.ai/gebo.ai.app-0.9.9.8-bootable.jar