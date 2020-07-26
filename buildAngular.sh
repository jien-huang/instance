#!/usr/bin/env sh
cd ./src/main/frontend || exit
npm ci
npm run build