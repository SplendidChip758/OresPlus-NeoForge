@echo off
setlocal

set "WORLD_DIR=run\saves\Testing_World_1"
set "BACKUP_DIR=run\backups"
if not exist "%BACKUP_DIR%" mkdir "%BACKUP_DIR%"

for /f "tokens=1-3 delims=/- " %%a in ("%date%") do (
    set "DATE=%%c-%%a-%%b"
)
for /f "tokens=1-2 delims=: " %%a in ("%time%") do (
    set "TIME=%%a-%%b"
)
set "TIMESTAMP=%DATE%_%TIME%"
set "TIMESTAMP=%TIMESTAMP: =0%"
set "TIMESTAMP=%TIMESTAMP::=-%"

set "ZIP_NAME=%TIMESTAMP%_Testing_World_1.zip"
set "ZIP_PATH=%BACKUP_DIR%\%ZIP_NAME%"

powershell -Command "Compress-Archive -Path '%WORLD_DIR%\*' -DestinationPath '%ZIP_PATH%'"

:: Remove old backups, keep only 5 most recent
pushd "%BACKUP_DIR%"
for /f "skip=5 delims=" %%F in ('dir /b /o-d "*_Testing_World_1.zip"') do (
    del "%%F"
)
popd

endlocal
