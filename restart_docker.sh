#!/bin/bash

echo "Stopping and removing containers and volumes..."
docker compose down -v

echo "Starting fresh containers..."
docker compose up -d

echo "Docker containers restarted successfully!"
