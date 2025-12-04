FROM rockylinux:9

RUN mkdir -p /opt/
RUN mkdir -p /opt/java/
RUN mkdir -p /build-area
RUN mkdir -p /build-area/delivery

RUN dnf -y update
RUN dnf -y install epel-release

RUN dnf -y install git tar gzip zip binutils
# fakeroot sta in EPEL; se ti dovesse dare errore puoi anche toglierlo
RUN dnf -y install fakeroot
RUN dnf -y install  rpm-build
RUN dnf -y install redhat-rpm-config
RUN dnf -y install systemd-rpm-macros
RUN dnf -y install java-21-openjdk java-21-openjdk-devel
#RUN dnf -y install maven
RUN dnf -y install rpm-build
#RUN curl -L -o /opt/java/jdk-21_linux-x64_bin.rpm https://download.oracle.com/java/21/latest/jdk-21_linux-x64_bin.rpm
#RUN rpm -i /opt/java/jdk-21_linux-x64_bin.rpm
#RUN rm -rf /opt/java/jdk-21_linux-x64_bin.rpm
RUN curl -L -o /opt/java/apache-maven-3.9.11-bin.tar.gz https://dlcdn.apache.org/maven/maven-3/3.9.11/binaries/apache-maven-3.9.11-bin.tar.gz
WORKDIR /opt/java/
RUN tar zxvfs apache-maven-3.9.11-bin.tar.gz 
RUN rm -rf  apache-maven-3.9.11-bin.tar.gz 
ENV MVN_HOME=/opt/java/apache-maven-3.9.11
ENV PATH=$MVN_HOME/bin:$PATH

#ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk
#ENV PATH=$JAVA_HOME/bin:/usr/share/maven/bin:$PATH
ENV REPOSITORY=https://github.com/geboai/Gebo.ai.git
ENV JAVA_EXTRA_SECURITY_DIR=/opt/
ENV LANG=en_US.UTF-8
ENV BRANCH=develop

WORKDIR /build-area

COPY build.sh /build-area
RUN chmod 744 /build-area/build.sh

VOLUME /build-area/delivery

ENTRYPOINT ["/build-area/build.sh"]
