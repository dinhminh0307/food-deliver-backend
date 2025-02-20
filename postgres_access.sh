#!/bin/bash

# Define the PostgreSQL container name (change if different)
CONTAINER_NAME="postgres"

# Check if the container is running
if ! docker ps | grep -q $CONTAINER_NAME; then
    echo "❌ Error: PostgreSQL container '$CONTAINER_NAME' is not running!"
    exit 1
fi

# Use an alternative terminal to avoid gnome-terminal issues
x-terminal-emulator -e "bash -c 'docker exec -it $CONTAINER_NAME psql -U postgres -d postgres -c \"SET search_path TO \\\"food-product\\\";\"; exec bash'"
