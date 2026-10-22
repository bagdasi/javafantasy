#include <windows.h>
#include <stdio.h>
#include <io.h>
int main(int argc, char *argv[]){
FreeConsole();
STARTUPINFO si = {0};
PROCESS_INFORMATION pi = {0};
si.cb = sizeof(si);
char *path = getenv("PATH");
char *javaPath = (char*)malloc(1024);
char *tmpPath = (char*)malloc(1024);
if (path == NULL) {MessageBox(NULL,"You don't have Path.","noPath",MB_OK | MB_ICONINFORMATION);}
char *token = strtok(path, ";");
while (token != NULL) {
token = strtok(NULL, ";");
snprintf(javaPath, 1024, "%sjavaw.exe", token);
if (_access(javaPath, 0) == 0) { 
strcpy(tmpPath,javaPath);
break;}
}
if (CreateProcess((LPSTR)tmpPath,(LPSTR)" -jar QPDv0_0021.jar",NULL, NULL, FALSE,CREATE_NO_WINDOW,NULL, NULL, &si, &pi)) {
WaitForSingleObject(pi.hProcess, INFINITE);
CloseHandle(pi.hProcess);
CloseHandle(pi.hThread);
}
free(javaPath);
free(tmpPath);
return 0;
}