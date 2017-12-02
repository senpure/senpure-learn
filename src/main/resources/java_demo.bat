cd ../
set propath=%cd%/protoc.exe
cd  players
set temppath=
set jout=.
%propath% --java_out=%jout% %temppath%demoProtoc.proto
pause
