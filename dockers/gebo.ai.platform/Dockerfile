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
RUN apt-get update && apt-get install -y  \
      curl net-tools \
    && rm -rf /var/lib/apt/lists/*
ARG TARGETARCH
RUN mkdir -p /opt/
RUN mkdir -p /opt/java/
WORKDIR /opt/java/
# Multiplatform: TARGETARCH is set automatically by docker buildx (amd64 or arm64).
# Oracle JDK uses x64 for amd64 and aarch64 for arm64.
RUN ARCH=$(case "$TARGETARCH" in amd64) echo "x64" ;; arm64) echo "aarch64" ;; *) echo "x64" ;; esac) \
    && curl -L -o jdk-21.tar.gz "https://download.oracle.com/java/21/latest/jdk-21_linux-${ARCH}_bin.tar.gz" \
    && tar xzf jdk-21.tar.gz \
    && rm jdk-21.tar.gz \
    && ln -sfn "$(ls -d jdk-21.* | head -n1)" jdk-21
ENV JAVA_HOME=/opt/java/jdk-21
ENV PATH=$JAVA_HOME/bin:$PATH
ENV JAVA_EXTRA_SECURITY_DIR=/opt/
ENV LANG=en_US.UTF-8
ENV LC_ALL=en_US.UTF-8

# ---------------------------------------------------------------------------
# The Gebo.ai runtime layout, created HERE so every image built on this base
# already has it. The microservice images (Jib, gebo.microservices.apps.parent
# `docker` profile) only SET GEBO_HOME / GEBO_WORK_DIRECTORY - Jib cannot run a
# mkdir - so without this the work directory would not exist, and
# GLocalPersistentFolderDiscoveryServiceImpl throws ("The configurated work
# directory ... does not exists or is not a directory") the first time any
# content endpoint is materialized.
#
#   home    GEBO_HOME            - the application's own home area
#   work    GEBO_WORK_DIRECTORY  - DURABLE user data: chat SESSIONS_AREA,
#                                  per-endpoint content mirrors, the document
#                                  cache, uploads, Google Workspace OAuth
#                                  tokens. Mongo only INDEXES it; the bytes live
#                                  here, so losing it leaves dangling references.
#   config  Spring's additional config location - mounted by the deployment,
#           deliberately NOT a volume anywhere (a stale volume would mask the
#           application.yml shipped by a newer image on upgrade).
#   logs    security-log.jsonl, the append-only GDPR Art.32 / NIS2 Art.21 audit
#           trail (logback's ${GEBO_LOG_BASE:-logs} under WORKDIR /opt/gebo.ai)
#   shares  host filesystem shares exposed to the filesystem content handler
#
# No VOLUME is declared here on purpose: a base image should describe the layout,
# not impose an anonymous volume on every derived image. The concrete images
# (dockers/gebo.ai, dockers/easyinstall.gebo.ai) and the Jib `docker` profile
# declare their own. See dockers/PERSISTENCE.md.
# ---------------------------------------------------------------------------
RUN mkdir -p /opt/gebo.ai/home \
             /opt/gebo.ai/work \
             /opt/gebo.ai/config \
             /opt/gebo.ai/logs \
             /opt/gebo.ai/shares
