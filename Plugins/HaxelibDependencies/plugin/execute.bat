@echo off
IF EXIST "./build" (
echo Del-> ./build
rd "./build" /s /q
)

IF EXIST "../repo" (
echo Del-> ../repo
rd "../repo" /s /q
)
echo "uploadArchive"
gradle uploadArchive --profile --full-stacktrace > log.log
