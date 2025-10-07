@echo off
echo Stopping travel-service
docker stop travel-service
echo Deleting container travel-service
docker rm travel-service
echo Deleting image travel-service
docker rmi travel-service
echo Running mvn package
call mvn package
echo Creating image travel-service
docker build -t travel-service .
echo Creating and running container travel-service
docker run -d -p 8585:8585 --name travel-service --network cadett-splitters-net travel-service
echo Done!