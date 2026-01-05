FROM mrcocoball/doriball-base-kotlin:latest
COPY --chown=appuser:appgroup build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]