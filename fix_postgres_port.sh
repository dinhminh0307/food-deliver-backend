#!/bin/bash

echo "🔍 Checking if PostgreSQL is running on port 5432..."
if sudo lsof -i :5432 | grep LISTEN; then
    echo "⚠️ PostgreSQL is running. Stopping it..."
    sudo systemctl stop postgresql
    echo "✅ PostgreSQL service stopped."
else
    echo "✅ No PostgreSQL service running on port 5432."
fi

echo "🔍 Checking for Docker containers using port 5432..."
CONTAINER_ID=$(docker ps --filter "publish=5432" --format "{{.ID}}")

if [ -n "$CONTAINER_ID" ]; then
    echo "⚠️ Docker container ($CONTAINER_ID) is using port 5432. Stopping it..."
    docker stop $CONTAINER_ID
    echo "✅ Container stopped."
else
    echo "✅ No Docker containers using port 5432."
fi

echo "🔄 Removing unused Docker networks..."
docker network prune -f

echo "🚀 Done! You can now restart your PostgreSQL container."
