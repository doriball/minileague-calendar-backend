FROM eclipse-temurin:21-jre-jammy

RUN apt-get update && apt-get install -y \
    procps \
    curl \
    && rm -rf /var/lib/apt/lists/*

RUN curl -L https://github.com/apangin/jattach/releases/download/v2.0/jattach -o /usr/local/bin/jattach \
    && chmod +x /usr/local/bin/jattach

RUN groupadd -r appgroup && useradd -r -g appgroup -d /app -s /sbin/nologin appuser

WORKDIR /app
RUN chown appuser:appgroup /app

USER appuser