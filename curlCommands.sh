#!/bin/sh
# A shell script to invoke some sample curl commands on a Linux machine 
# against a running container image of the app-specific Gilhari microservice 
# gilhari_relationships_example:1.0
# 
# The responses are recorded in a log file (curl.log).
#
# Note that these curl commands use a mapped port number of 80
# even though the port number exposed by the app-specific 
# microservice may be different (e.g., 8081) inside the container shell.
# If you want to use these curl commands from inside the
# container shell on the host machine, you may have to use
# the exposed port number (e.g., 8081) instead.

echo -e "** BEGIN OUTPUT **" > curl.log

#echo "** Delete all Customer objects (and their referenced B and C objects) to start fresh" >> curl.log
#curl -X DELETE "http://localhost:80/gilhari/v1/anand.Customer" >> curl.log
echo "" >> curl.log

echo "** Insert one A object with one referenced B object and a referenced array of two C objects" >> curl.log
curl -X POST "http://localhost:80/gilhari/v1/anand.Customer"  -H 'Content-Type: application/json' -d '{"entity":{"phone":"12345","last_name":"ABC","email":"a@987654","first_name":"abc","customer_id":1}}' >> curl.log
echo -e "" >> curl.log

echo "** Query all anand.Customer objects (and their referenced B and C objects)" >> curl.log
curl -X GET "http://localhost:80/gilhari/v1/anand.Customer"  -H "Content-Type: application/json" >> curl.log
echo "" >> curl.log


echo "** END OUTPUT **" >> curl.log
echo "" >> curl.log

cat curl.log
