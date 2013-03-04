@echo off
setlocal enableDelayedExpansion 
for %%f in (*.dot) do (
set filetmp=%%f
set filename=!filetmp:~0,-4!
dot.exe -o !filename!".pdf" -T "pdf" %%f 
)
endlocal
pause