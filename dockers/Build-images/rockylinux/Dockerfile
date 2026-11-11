FROM rockylinux:9

RUN mkdir -p /opt/
RUN mkdir -p /opt/java/
RUN mkdir -p /build-area
RUN mkdir -p /build-area/repository
RUN mkdir -p /build-area/delivery

RUN dnf -y update
RUN dnf -y install epel-release

RUN dnf -y install git tar gzip zip binutils
# fakeroot sta in EPEL; se ti dovesse dare errore puoi anche toglierlo
RUN dnf -y install fakeroot
RUN dnf -y install  rpm-build
RUN dnf -y install redhat-rpm-config
RUN dnf -y install systemd-rpm-macros
#RUN dnf -y install java-21-openjdk java-21-openjdk-devel
#RUN dnf -y install maven
RUN dnf -y install rpm-build
# JDK 21 is no longer served under /java/21/latest/ (that path 404s now that 25
# is the current latest) - it lives only under /archive/ with a pinned version.
# Keep JDK_VERSION in sync with dockers/gebo.ai.platform + dockers/easyinstall.gebo.ai.
# curl -f fails the build on an HTTP error instead of saving the 404 HTML page as
# the .rpm (which then breaks rpm -i).
ARG JDK_VERSION=21.0.9
RUN curl -fL -o /opt/java/jdk-21_linux-x64_bin.rpm https://download.oracle.com/java/21/archive/jdk-${JDK_VERSION}_linux-x64_bin.rpm
RUN rpm -i /opt/java/jdk-21_linux-x64_bin.rpm
RUN rm -rf /opt/java/jdk-21_linux-x64_bin.rpm
# dlcdn.apache.org only mirrors the CURRENT Maven (3.9.16 now), so 3.9.11 404s
# there - same silent-404-then-tar-failure trap as the JDK above. archive.apache.org
# keeps every released version permanently; curl -f fails loudly on an HTTP error.
RUN curl -fL -o /opt/java/apache-maven-3.9.11-bin.tar.gz https://archive.apache.org/dist/maven/maven-3/3.9.11/binaries/apache-maven-3.9.11-bin.tar.gz
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

# Build-toolchain image, not a product runtime: these hold BUILD state, not
# customer data. delivery = the artifacts the build hands back; repository = the
# Maven ~/.m2 cache, whose whole point is to survive between builds. The ubuntu
# sibling image already declares both; this one was missing the repository, so
# every rockylinux build re-downloaded the world.
VOLUME /build-area/delivery
VOLUME /build-area/repository

ENTRYPOINT ["/build-area/build.sh"]
