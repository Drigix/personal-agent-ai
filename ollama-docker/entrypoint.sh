#!/bin/sh
set -e

echo "Starting ollama server..."
ollama serve &
SERVER_PID=$!

echo "Waiting for ollama server to be ready..."
until ollama list > /dev/null 2>&1; do
  echo "Server not ready yet. Retrying in 5 seconds..."
  sleep 5
done

echo "Pull model: ${MODEL}"
ollama pull "${MODEL}"

echo "${MODEL} has been pulled. Server already works."

wait "$SERVER_PID"