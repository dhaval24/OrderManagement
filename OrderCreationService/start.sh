#! /bin/bash
./wait-for-it.sh postgres:5432 -t 15 -- echo "postgres is up"
java -jar app.jar