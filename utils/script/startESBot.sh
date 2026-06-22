#!/usr/bin/env bash

########################################
# Start Docker 
########################################
echo "Start Docker..."
gnome-terminal -- bash -c "
docker compose up;
exec bash
"

sleep 3

########################################
# Start Backend
########################################
echo "Start Backend..."
gnome-terminal -- bash -c "
cd 'application/backend' &&
mvn spring-boot:run;
exec bash
"

sleep 3

########################################
# Create test user 
########################################
echo "Creating User..."
gnome-terminal -- bash -c "
cd 'utils/script' &&
./createTestUser.sh ;
exec bash
"


########################################
# Start Frontend 
########################################
echo "Start Frontend..."
gnome-terminal -- bash -c "
cd 'application/frontend/esbot' &&
ng serve --open;
exec bash
"

echo "🚀 Fullstack started!"

