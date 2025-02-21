#!/bin/bash

echo "Stopping the Postgres on port..."
./fix_postgres_port.sh

echo "Stopping and removing containers and volumes..."
docker compose -f docker-compose.dev.yml down -v

echo "Rebuilding images..."
docker compose -f docker-compose.dev.yml build --no-cache

echo "Starting fresh containers..."
docker compose -f docker-compose.dev.yml up -d

# echo "Cleaning up unused Docker images..."
# docker image prune -f

echo "Open terminal to view Database logs"
./postgres_access.sh

# Show logs for the backend service
docker logs -f food-deliver-backend 

echo "Docker containers rebuilt and restarted successfully!"
