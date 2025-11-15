#!/usr/bin/env powershell

Write-Host "🚀 Auto Demo" -ForegroundColor Green

# Start backend
Write-Host "Starting backend..." -ForegroundColor Yellow
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd 'C:\Users\stumb\School\project-3-group-8-backend'; .\gradlew.bat bootRun"

# Start frontend
Write-Host "Starting frontend..." -ForegroundColor Yellow
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd 'C:\Users\stumb\School\project-3-group8-frontend-2.0\StickerSmash'; npx expo start"

# Open VS Code
Write-Host "Opening VS Code..." -ForegroundColor Yellow
code .

# Open browser
Start-Sleep -Seconds 10
Write-Host "Opening browser..." -ForegroundColor Yellow
Start-Process "http://localhost:8080"

Write-Host "✅ Demo running!" -ForegroundColor Green