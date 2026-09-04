@echo off

echo ========================================
echo   BANK ACCOUNT MANAGEMENT SYSTEM
echo ========================================
echo.
echo Starting web server...
echo.

start "" cmd /c "timeout /t 2 /nobreak >nul & start http://localhost:8080"

java -cp bin com.bank.web.WebServer

pause