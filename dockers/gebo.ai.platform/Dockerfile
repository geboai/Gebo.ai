FROM ubuntu:24.10
#RUN sudo apt install language-pack-en
#RUN sudo apt install language-pack-it
#RUN sudo apt install language-pack-fr
#RUN sudo apt install language-pack-es
#RUN sudo apt install language-pack-ja
RUN apt-get update && apt-get install -y locales && rm -rf /var/lib/apt/lists/* \
	&& localedef -i en_US -c -f UTF-8 -A /usr/share/locale/locale.alias en_US.UTF-8
RUN locale-gen en_US.UTF-8
RUN update-locale LANG=en_US.UTF-8
RUN mkdir -p /opt/
RUN mkdir -p /opt/java/
COPY jdk-21_linux-x64_bin.tar.gz /opt/java/
WORKDIR /opt/java/
RUN tar zxvfs jdk-21_linux-x64_bin.tar.gz
RUN rm jdk-21_linux-x64_bin.tar.gz
ENV JAVA_HOME=/opt/java/jdk-21.0.7
ENV PATH=$JAVA_HOME/bin:$PATH
ENV JAVA_EXTRA_SECURITY_DIR=/opt/
ENV LANG=en_US.UTF-8
ENV LC_ALL=en_US.UTF-8