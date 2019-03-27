#! /bin/sh
./wait-for-it.sh postgres:5432 -t 15 echo "postgres turned on!"
java -javaagent:applicationinsights-agent-2.4.0-BETA-SNAPSHOT.jar -jar app.jar