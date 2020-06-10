#!/bin/bash
echo "Installing Angular CLI"
npm install -g @angular/cli
echo "Installing project dependencies"
npm install
echo "Starting frontend project on port 4200"
ng s -o