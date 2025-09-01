# ---- Base
FROM ubuntu:24.04
#RUN sudo apt install language-pack-en
#RUN sudo apt install language-pack-it
#RUN sudo apt install language-pack-fr
#RUN sudo apt install language-pack-es
#RUN sudo apt install language-pack-ja
RUN apt-get update && apt-get install -y locales && rm -rf /var/lib/apt/lists/* \
	&& localedef -i en_US -c -f UTF-8 -A /usr/share/locale/locale.alias en_US.UTF-8
RUN locale-gen en_US.UTF-8
RUN update-locale LANG=en_US.UTF-8
LABEL mantainer=paolo.zavalloni@gebo.ai
LABEL org.opencontainers.image.authors="paolo.zavalloni@gebo.ai"
LABEL org.opencontainers.image.title="Gebo.ai"
LABEL org.opencontainers.image.version="0.9.9.8-SNAPSHOT"
LABEL org.opencontainers.image.description="Gebo.ai enterprise rag & agents/chatbot platform"
LABEL org.opencontainers.image.vendor="Gebo.ai"
LABEL org.opencontainers.image.source="https://github.com/geboai/Gebo.ai"
LABEL org.opencontainers.image.licenses="MPL-2.0,https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/"
LABEL org.opencontainers.image.sbom="/opt/gebo.ai/sbom.cdx.json"


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



ENV DEBIAN_FRONTEND=noninteractive \
    MONGO_DATA=/data/db \
    QDRANT_DIR=/var/lib/qdrant \
    QDRANT_CONFIG=/etc/qdrant/config.yaml

ARG MONGO_MAJOR=8.0
ARG QDRANT_VERSION=1.15.3

RUN apt-get update && apt-get install -y  \
      ca-certificates curl gnupg lsb-release supervisor tini \
    && rm -rf /var/lib/apt/lists/*

RUN    curl -fsSL https://www.mongodb.org/static/pgp/server-8.0.asc | \
	   gpg -o /usr/share/keyrings/mongodb-server-8.0.gpg \
	   --dearmor 
RUN echo  "deb [ arch=amd64,arm64 signed-by=/usr/share/keyrings/mongodb-server-8.0.gpg ] https://repo.mongodb.org/apt/ubuntu noble/mongodb-org/8.0 multiverse" |  tee /etc/apt/sources.list.d/mongodb-org-8.0.list 
	
RUN apt-get update 
RUN apt-get install -y  mongodb-org 
RUN rm -rf /var/lib/apt/lists/* 
RUN mkdir -p ${MONGO_DATA} /var/log/mongodb 
#RUN chown -R mongodb:mongodb ${MONGO_DATA} /var/log/mongodb

RUN curl -L -o /tmp/qdrant.deb https://github.com/qdrant/qdrant/releases/download/v1.15.3/qdrant_1.15.3-1_amd64.deb 
RUN apt-get update 
RUN apt-get install -y  /tmp/qdrant.deb 
RUN rm -rf /var/lib/apt/lists/* /tmp/qdrant.deb && mkdir -p ${QDRANT_DIR} /var/log/qdrant
RUN mkdir -p /etc/qdrant
COPY qdrant-config.yaml ${QDRANT_CONFIG}

COPY supervisord.conf /etc/supervisor/conf.d/supervisord.conf

RUN mkdir -p /opt/
RUN mkdir -p /opt/java/
WORKDIR /opt/java/
RUN curl -L -o jdk-21_linux-x64_bin.tar.gz https://download.oracle.com/java/21/latest/jdk-21_linux-x64_bin.tar.gz
RUN tar zxvfs jdk-21_linux-x64_bin.tar.gz
RUN rm jdk-21_linux-x64_bin.tar.gz
ENV JAVA_HOME=/opt/java/jdk-21.0.8
ENV PATH=$JAVA_HOME/bin:$PATH
ENV JAVA_EXTRA_SECURITY_DIR=/opt/
ENV LANG=en_US.UTF-8
ENV LC_ALL=en_US.UTF-8
RUN mkdir -p /opt
RUN mkdir -p /opt/gebo.ai
RUN mkdir -p /opt/gebo.ai/home
RUN mkdir -p /opt/gebo.ai/config
RUN mkdir -p /opt/gebo.ai/work
RUN mkdir -p /opt/gebo.ai/shares
COPY gebo.ai.app-0.9.9.8-SNAPSHOT-bootable.jar /opt/gebo.ai
COPY application.cdx.json  /opt/gebo.ai/sbom.cdx.json
COPY config/application.yml /opt/gebo.ai/config
ENV  JAVA_TOOL_OPTIONS="-Dsun.jnu.encoding=UTF-8 -Dfile.encoding=UTF-8 --add-opens=java.base/java.nio.charset=ALL-UNNAMED --enable-native-access=ALL-UNNAMED -Xms2g -Xmx4g -XX:+AggressiveHeap -XX:MaxMetaspaceSize=512m -XX:+PrintGCDetails -Xlog:gc:/opt/gebo.ai/logs/gc.log"
ENV  GEBO_WORK_DIRECTORY="/opt/gebo.ai/work"
ENV  GEBO_HOME="/opt/gebo.ai/home"
COPY mongod.conf /etc/mongod.conf                 
COPY mongo-init.sh /bin/mongo-init.sh   
COPY container-start.sh /bin/container-start.sh  
RUN chmod +x /bin/mongo-init.sh /bin/container-start.sh  


VOLUME ["/data/db", "/var/lib/qdrant","/opt/gebo.ai/shares"]
EXPOSE 27017 6333 12999

ENTRYPOINT ["/usr/bin/tini","--"]  
CMD ["/bin/container-start.sh"]
