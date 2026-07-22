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