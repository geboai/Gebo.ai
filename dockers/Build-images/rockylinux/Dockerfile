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
#RUN dnf -y install maven
RUN dnf -y install rpm-build
# Oracle JDK 25 (LTS) - the JDK the release workflow (.github/workflows/release.yml)
# bundles into the published installers. jpackage takes the runtime the .rpm
# bundles from this JDK, and Oracle links it against a very old glibc (its newest
# symbol is GLIBC_2.9), so the .rpm runs on EL8, EL9 and EL10 alike - measured on
# Rocky 8.9, Rocky 9.3 (GA and updated) and AlmaLinux 10.2. Do NOT use
# the distro java-25-openjdk instead: measured, its runtime needs GLIBC_2.35
# symbols that only an up-to-date EL9 has - on EL8 or an un-updated EL9 the .rpm
# still INSTALLS (it requires an unversioned glibc) and the service then fails to
# start - and it drags in ALSA/X11 libraries that jpackage then declares as
# Requires. Oracle's own rpm requires nothing but rpm. The sources still compile
# to Java 21 bytecode (java.version in the root pom).
#
# Pinned to an update under /archive/, which Oracle keeps permanently: the moving
# java/<major>/latest/ path disappears when Oracle stops free updates for an LTS
# (java/21/latest is gone already). Bump JDK_VERSION with each quarterly Oracle
# update. TARGETARCH is set by BuildKit (amd64 | arm64). curl -f fails the build on
# an HTTP error instead of saving the 404 HTML page as the .rpm.
ARG TARGETARCH
# 25.0.5.1 is the Nov 2026 Oracle CPU build that fixes CVE-2026-114938 (Java2Shell /
# Java4Shell: critical pre-auth RCE in the AVX-512 Base64 decode intrinsic, affecting
# Java 17.0.6+ incl. the 25 line up to 25.0.5.0). jpackage bundles this runtime into
# the installers, so the fix must come from here.
ARG JDK_VERSION=25.0.5.1
RUN JDK_ARCH=$(case "$TARGETARCH" in arm64) echo aarch64 ;; *) echo x64 ;; esac) \
 && curl -fL -o /tmp/jdk.rpm "https://download.oracle.com/java/25/archive/jdk-${JDK_VERSION}_linux-${JDK_ARCH}_bin.rpm" \
 && rpm -i /tmp/jdk.rpm \
 && rm -f /tmp/jdk.rpm \
 && ln -sfn "$(dirname "$(dirname "$(readlink -f "$(command -v javac)")")")" /usr/lib/jvm/jdk-25 \
 && /usr/lib/jvm/jdk-25/bin/javac -version
# dlcdn.apache.org only mirrors the CURRENT Maven (3.9.16 now), so 3.9.11 404s
# there; archive.apache.org keeps every released version permanently. curl -f
# fails the build on an HTTP error instead of saving the 404 HTML page.
RUN curl -fL -o /opt/java/apache-maven-3.9.11-bin.tar.gz https://archive.apache.org/dist/maven/maven-3/3.9.11/binaries/apache-maven-3.9.11-bin.tar.gz
WORKDIR /opt/java/
RUN tar zxvfs apache-maven-3.9.11-bin.tar.gz 
RUN rm -rf  apache-maven-3.9.11-bin.tar.gz 
ENV MVN_HOME=/opt/java/apache-maven-3.9.11
ENV PATH=$MVN_HOME/bin:$PATH

ENV JAVA_HOME=/usr/lib/jvm/jdk-25
ENV PATH=$JAVA_HOME/bin:$PATH
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
