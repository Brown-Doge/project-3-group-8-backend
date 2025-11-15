#!/usr/bin/env powershell

Write-Host "Google OAuth Flow Demo" -ForegroundColor Green
Write-Host "======================" -ForegroundColor Cyan

# Start backend server
Write-Host "`nStarting backend server..." -ForegroundColor Yellow
$serverJob = Start-Job -ScriptBlock {
    Set-Location "C:\Users\stumb\School\project-3-group-8-backend"
    .\gradlew.bat bootRun
}

# Wait for server to start
Write-Host "Waiting for server to start..." -ForegroundColor Yellow
Start-Sleep -Seconds 15

# Test if server is ready
Write-Host "`nTesting server..." -ForegroundColor Blue
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/" -TimeoutSec 5
    Write-Host "Server is running!" -ForegroundColor Green
} catch {
    Write-Host "Server might still be starting..." -ForegroundColor Yellow
}

# Show OAuth endpoints
Write-Host "`nOAuth Endpoints Available:" -ForegroundColor Blue
Write-Host "   Home: http://localhost:8080/" -ForegroundColor Cyan
Write-Host "   Google OAuth: http://localhost:8080/oauth2/authorization/google" -ForegroundColor Cyan
Write-Host "   Secured: http://localhost:8080/secured" -ForegroundColor Cyan

# Test OAuth redirect
Write-Host "`nTesting Google OAuth redirect..." -ForegroundColor Blue
try {
    $oauthResponse = Invoke-WebRequest -Uri "http://localhost:8080/oauth2/authorization/google" -MaximumRedirection 0 -ErrorAction SilentlyContinue
    if ($oauthResponse.Headers.Location) {
        Write-Host "OAuth redirect is working!" -ForegroundColor Green
        Write-Host "   Redirects to Google..." -ForegroundColor Cyan
    }
} catch {
    Write-Host "OAuth endpoint is responding" -ForegroundColor Green
}

# Open OAuth flow in browser
Write-Host "`nOpening Google OAuth flow in browser..." -ForegroundColor Yellow
Start-Process "http://localhost:8080/oauth2/authorization/google"

Write-Host "`nOAuth Flow Steps:" -ForegroundColor Blue
Write-Host "   1. Browser opens Google OAuth page" -ForegroundColor Cyan
Write-Host "   2. User logs in with Google account" -ForegroundColor Cyan
Write-Host "   3. Google redirects back to app" -ForegroundColor Cyan
Write-Host "   4. App creates user session" -ForegroundColor Cyan
Write-Host "   5. User can access protected endpoints" -ForegroundColor Cyan

Write-Host "`nOAuth demo is running!" -ForegroundColor Green
Write-Host "Browser should show Google login page." -ForegroundColor Yellow

Write-Host "`nPress Enter to stop server..." -ForegroundColor Yellow
Read-Host

Write-Host "`nStopping server..." -ForegroundColor Red
Stop-Job $serverJob -Force
Remove-Job $serverJob -Force
Write-Host "Demo completed!" -ForegroundColor Green