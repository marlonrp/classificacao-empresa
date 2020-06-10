@echo off
echo "Installing Angular CLI"
call npm install -g @angular/cli
echo "Installing project dependencies"
call npm i
echo "Starting frontend project on port 4200"
call ng s -o