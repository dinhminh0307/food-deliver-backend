#!/bin/bash

echo "Stopping and removing containers and volumes..."
docker compose down -v

echo "Rebuilding images..."
docker compose build --no-cache

echo "Starting fresh containers..."
docker compose up -d

# echo "Cleaning up unused Docker images..."
# docker image prune -f

echo "Docker containers rebuilt and restarted successfully!"
