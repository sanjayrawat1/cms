# This is the multi-stage build.
# First step named builder handles the extraction.
# Second stage builds the actual docker image that will be used at runtime, picking the files as required from the first stage.
# Using this techinique, we can handle all packaging logic in the Dockerfile but at the same time keep the size of the final Dokcer image to minimum.

# Stage - 1
FROM eclipse-termurin:17-jre-focal as builder
WORKDIR extracted
ARG JAR_FILE=target/cms.jar
ADD ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

# Stage -2
FROM eclipse-termurin:17-jre-focal
WORKDIR application
COPY --from=builder extracted/dependencies/ ./
RUN true # COPY fail in multistage build: layer does not exists. see https://github.com/moby/moby/issues/37965
COPY --from=builder extracted/spring-boot-loader/ ./
RUN true
COPY --from=builder extracted/spanshot-dependencies/ ./
RUN true
COPY --from=builder extracted/application/ ./
RUN true

COPY bin/entrypoint.sh ./entrypoint.sh

RUN ["chmod", "+x", "./entrypoint.sh"]

EXPOSE 8080
EXPOSE 8849

ENTRYPOINT ["./entrypoint.sh"]