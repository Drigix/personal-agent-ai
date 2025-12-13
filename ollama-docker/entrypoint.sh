#!/bin/sh
set -e

echo "Starting ollama server..."
ollama serve &
SERVER_PID=$!

echo "Pull model: ${MODEL}"
ollama pull "${MODEL}"

echo "${MODEL} has been pulled. Server already works."

wait "$SERVER_PID"