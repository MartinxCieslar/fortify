#!/bin/bash
set -ex
apt-get update \
&& apt-get install --no-install-recommends --no-install-suggests -y \
    unzip \
    curl \
    sudo \
    gnupg \
# Download chrome install it
&& GOOGLE_LINUX_DL=https://dl.google.com/linux \
&& curl -sL "$GOOGLE_LINUX_DL/linux_signing_key.pub" | apt-key add - \
&& curl -sL "$GOOGLE_LINUX_DL/direct/google-chrome-stable_current_amd64.deb" > /tmp/chrome.deb \
&& apt install --no-install-recommends --no-install-suggests -y \
    /tmp/chrome.deb \
&& CHROMIUM_FLAGS='--no-sandbox --disable-dev-shm-usage --window-size=1920,1080' \
&& sed -i '${s/$/'" $CHROMIUM_FLAGS"'/}' /opt/google/chrome/google-chrome \
#Celan up
&& apt-get clean \
&& rm -rf \
    /tmp/* \
    /usr/share/doc/* \
    /var/cache/* \
    /var/lib/apt/lists/* \
    /var/tmp/* \
