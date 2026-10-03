#!/bin/sh
echo "PATH=$PATH"
command -v openssl
openssl version
docker build -f docker/Dockerfile -t wadget-budget-engine .
