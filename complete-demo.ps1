#!/usr/bin/env powershell

# Full-Stack Demo Script - Backend + Frontend + OAuth + Heroku
Write-Host "Full-Stack EventLink Demo" -ForegroundColor Green
Write-Host "=========================" -ForegroundColor Cyan

$BackendPath = "C:\Users\stumb\School\project-3-group-8-backend"
$FrontendPath = "C:\Users\stumb\School\project-3-group8-frontend-2.0\StickerSmash"

Write-Host "`n1. BACKEND - Starting OAuth Server..." -ForegroundColor Blue
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$BackendPath'; Write-Host 'Backend OAuth Server' -ForegroundColor Green; .\gradlew.bat bootRun"

Write-Host "`n2. FRONTEND - Starting React Native..." -ForegroundColor Blue  
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$FrontendPath'; Write-Host 'Frontend React Native' -ForegroundColor Cyan; npx expo start"

Write-Host "`n3. DEVELOPMENT - Opening VS Code..." -ForegroundColor Blue
code $BackendPath
Start-Sleep -Seconds 2
code $FrontendPath

Write-Host "`n4. TESTING - Opening OAuth endpoints..." -ForegroundColor Blue
Start-Sleep -Seconds 15
Start-Process "http://localhost:8080/"
Start-Process "http://localhost:8080/oauth2/authorization/google"

Write-Host "`n5. HEROKU - Checking deployment status..." -ForegroundColor Blue
Start-Process "https://project-3-group-8-backend.herokuapp.com/"

Write-Host "`n✅ FULL DEMO RUNNING!" -ForegroundColor Green
Write-Host "Backend: http://localhost:8080" -ForegroundColor Cyan
Write-Host "Frontend: Expo Metro bundler" -ForegroundColor Cyan  
Write-Host "Production: Heroku deployment" -ForegroundColor Cyan
Write-Host "OAuth: Google authentication ready" -ForegroundColor Cyan