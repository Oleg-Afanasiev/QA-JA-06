::echo off
    ::set fName=@artifact@-@version@
    set fName=automation-1.0
    set fExt=.zip
    ::set rootDir=d:\programming\qa\seleniumtest\target
        set rootDir=%HOMEDRIVE%%HOMEPATH%\.jenkins\workspace\qa-ja\target
    set src=%rootDir%\%fName%%fExt%
setlocal
    call :copyBackup
    call :makeBuild
goto :EOF

:copyBackup
    set backupDir=%HOMEDRIVE%%HOMEPATH%\Documents\qa-ja\backup
    if not exist "%backupDir%" mkdir %backupDir%
    set fNameSuffix=
    call :assignTime fNameSuffix
    set dst=%backupDir%\%fName%%fNameSuffix%%fExt%
    echo %src%
    echo %dst%

    :: copy zip arc to build directory
    copy "%src%" "%dst%"
goto :EOF

::function- return suffix with date-time
:assignTime
    for /F "tokens=2" %%i in ('date /t') do set curDate=%%i

    set yy=%curDate:~6,4%
    set mh=%curDate:~0,2%
    set dd=%curDate:~3,2%
    set hh=%time:~0,2%
    set mm=%time:~3,2%
    set ss=%time:~6,2%

    set %1=_%yy%_%mh%_%dd%_%hh%_%mm%_%ss%
    goto :EOF

:makeBuild
   echo build
   set buildDir=%HOMEDRIVE%%HOMEPATH%\Documents\qa-ja\build

   :: delete previos build dir if exist
   if not exist "%buildDir%" mkdir %buildDir%
   if exist "%buildDir%\%fName%" rmdir /s /q %buildDir%\%fName%

   ::unzip to build dir
   copy %src% %buildDir%
   cd /d %buildDir%
   jar -xvf %src%
   del %buildDir%\%fName%%fExt%

goto :EOF