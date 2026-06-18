#!/usr/bin/env bash

########################################
# Docker starten
########################################
echo "Starte Docker..."
gnome-terminal -- bash -c "
docker compose up;
exec bash
"

sleep 3

########################################
# Backend starten
########################################
echo "Starte Backend..."
gnome-terminal -- bash -c "
cd 'application/backend' &&
mvn spring-boot:run;
exec bash
"

sleep 3

########################################
# Frontend starten
########################################
echo "Starte Frontend..."
gnome-terminal -- bash -c "
cd 'application/frontend/esbot' &&
ng serve --open;
exec bash
"

echo "🚀 Fullstack gestartet!"

