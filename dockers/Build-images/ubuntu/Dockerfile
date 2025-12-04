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
      curl net-tools git fakeroot binutils \
    && rm -rf /var/lib/apt/lists/*
RUN mkdir -p /opt/
RUN mkdir -p /opt/java/
RUN mkdir -p /build-area
RUN mkdir -p /build-area/delivery
RUN curl -L -o /opt/java/jdk-21_linux-x64_bin.tar.gz https://download.oracle.com/java/21/latest/jdk-21_linux-x64_bin.tar.gz
RUN curl -L -o /opt/java/apache-maven-3.9.11-bin.tar.gz https://dlcdn.apache.org/maven/maven-3/3.9.11/binaries/apache-maven-3.9.11-bin.tar.gz
WORKDIR /opt/java/
RUN tar zxvfs jdk-21_linux-x64_bin.tar.gz
RUN tar zxvfs apache-maven-3.9.11-bin.tar.gz 
RUN rm jdk-21_linux-x64_bin.tar.gz
RUN rm apache-maven-3.9.11-bin.tar.gz
ENV JAVA_HOME=/opt/java/jdk-21.0.9
ENV MVN_HOME=/opt/java/apache-maven-3.9.11
ENV PATH=$JAVA_HOME/bin:$PATH
ENV PATH=$MVN_HOME/bin:$PATH
ENV REPOSITORY=https://github.com/geboai/Gebo.ai.git
ENV JAVA_EXTRA_SECURITY_DIR=/opt/
ENV LANG=en_US.UTF-8
ENV LC_ALL=en_US.UTF-8
ENV BRANCH=develop
WORKDIR /build-area
COPY build.sh /build-area
RUN chmod 744 /build-area/build.sh
VOLUME /build-area/delivery
ENTRYPOINT  /build-area/build.sh