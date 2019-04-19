echo off

set cp=../lib/*;
set automation.prop=../cfg/automationpractice.properties
set mobile.prop=../cfg/mobile.properties
set common.prop=../cfg/common.properties
set log=../cfg/log4j2.properties
set main=org.testng.TestNG
set suiteDefault=../suits/testng.xml
set suiteParam=%1
set report.dir=../report
if defined suiteParam (
	set suite=%suiteParam%
) else (
	set suite=%suiteDefault%
)

java -cp %cp% -Dautomationpractice.cfg=%automation.prop% -Dmobile.cfg=%mobile.prop% -Dcommon.cfg=%common.prop% -Dlog4j.configurationFile=%log% %main%  %suite% -d %report.dir%