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
# Keep this list SHORT, and never add a distro JDK (openjdk-*): jpackage declares,
# as the .deb's Depends, the packages providing the libraries it finds on THIS
# machine. A distro JDK drags in ALSA, X11, freetype, jpeg... under names only
# Ubuntu 24.04 has (libasound2t64, libjpeg-turbo8), and the .deb then installed on
# Ubuntu 24.04 alone - measured on a distro matrix.
RUN apt-get update && apt-get install -y  \
      curl net-tools git fakeroot binutils \
    && rm -rf /var/lib/apt/lists/*
RUN mkdir -p /opt/
RUN mkdir -p /opt/java/
RUN mkdir -p /build-area
RUN mkdir -p /build-area/repository
RUN mkdir -p /build-area/delivery
# Oracle JDK 25 (LTS) - the JDK the release workflow (.github/workflows/release.yml)
# bundles into the published installers, so a package built here matches a released
# one. jpackage takes the runtime it bundles from this JDK, and Oracle links it
# against a very old glibc (its newest symbol is GLIBC_2.9): that is what lets the
# .deb run on any current distro, which a distro JDK does not (Ubuntu's OpenJDK 25
# needs GLIBC_2.38). Measured: the .deb this image builds installs (apt) and runs
# on Ubuntu 20.04, 22.04, 24.04 and Debian 12, 13. The sources still compile to
# Java 21 bytecode (java.version in the root pom).
#
# Pinned to an update under /archive/, which Oracle keeps permanently: the moving
# java/<major>/latest/ path disappears when Oracle stops free updates for an LTS
# (java/21/latest is gone already). Bump JDK_VERSION with each quarterly Oracle
# update. TARGETARCH is set by BuildKit (amd64 | arm64). curl -f fails the build on
# an HTTP error instead of saving the 404 HTML page (which then breaks tar with "not
# in gzip format").
ARG TARGETARCH
# 25.0.5.1 is the Nov 2026 Oracle CPU build that fixes CVE-2026-114938 (Java2Shell /
# Java4Shell: critical pre-auth RCE in the AVX-512 Base64 decode intrinsic, affecting
# Java 17.0.6+ incl. the 25 line up to 25.0.5.0). jpackage bundles this runtime into
# the installers, so the fix must come from here.
ARG JDK_VERSION=25.0.5.1
RUN JDK_ARCH=$(case "$TARGETARCH" in arm64) echo aarch64 ;; *) echo x64 ;; esac) \
 && curl -fL -o /tmp/jdk.tar.gz "https://download.oracle.com/java/25/archive/jdk-${JDK_VERSION}_linux-${JDK_ARCH}_bin.tar.gz" \
 && mkdir -p /opt/java/jdk-25 \
 && tar -xzf /tmp/jdk.tar.gz -C /opt/java/jdk-25 --strip-components=1 \
 && rm /tmp/jdk.tar.gz \
 && /opt/java/jdk-25/bin/javac -version
# dlcdn.apache.org only mirrors the CURRENT Maven (3.9.16 now), so 3.9.11 404s
# there; archive.apache.org keeps every released version permanently.
RUN curl -fL -o /opt/java/apache-maven-3.9.11-bin.tar.gz https://archive.apache.org/dist/maven/maven-3/3.9.11/binaries/apache-maven-3.9.11-bin.tar.gz
WORKDIR /opt/java/
RUN tar zxvfs apache-maven-3.9.11-bin.tar.gz
RUN rm apache-maven-3.9.11-bin.tar.gz
ENV JAVA_HOME=/opt/java/jdk-25
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
VOLUME /build-area/repository
ENTRYPOINT  /build-area/build.sh
