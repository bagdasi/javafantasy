#include <windows.h>
#include <stdio.h>
#include <io.h>
#include <string.h>
int main(int argc, char *argv[]){
FreeConsole();
STARTUPINFO si = {0};
PROCESS_INFORMATION pi = {0};
si.cb = sizeof(si);
char *path = "jre-21\\bin\\";
char *javaPath = (char*)malloc(1024);
char *tmpPath = (char*)malloc(1024);
if (path == NULL) {MessageBox(NULL,"You don't have Path.","noPath",MB_OK | MB_ICONINFORMATION);}
snprintf(javaPath, 1024, "%sjavaw.exe", path);
if (_access(javaPath, 0) == 0) { 
strcpy(tmpPath,javaPath);
}else {
MessageBox(NULL,"You don't have javaPath.","noPath",MB_OK | MB_ICONINFORMATION);}
if (CreateProcess((LPSTR)tmpPath,(LPSTR)"  -jar AntJARCreater.jar",NULL, NULL, FALSE,CREATE_NO_WINDOW,NULL, NULL, &si, &pi)) {
WaitForSingleObject(pi.hProcess, INFINITE);
CloseHandle(pi.hProcess);
CloseHandle(pi.hThread);
}
free(javaPath);
free(tmpPath);
return 0;
}