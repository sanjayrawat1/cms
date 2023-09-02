#!/bin/sh

APPLICATION_SLEEP=30
echo "The application will start in ${APPLICATION_SLEEP}s..." && sleep ${APPLICATION_SLEEP}

exec java ${JAVA_OPTS} -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} org.springframework.boot.loader.JarLauncher