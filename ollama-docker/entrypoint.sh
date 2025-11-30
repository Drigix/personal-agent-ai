#!/bin/sh
set -e

echo "Starting ollama server..."
ollama serve &
SERVER_PID=$!

echo "Waiting for API response..."
until curl -sf http://localhost:11434/api/tags >/dev/null 2>&1; do
  sleep 1
done

echo "Pull model: ${MODEL}"
ollama pull "${MODEL}"

echo "${MODEL} has been pulled. Server already works."

wait "$SERVER_PID"