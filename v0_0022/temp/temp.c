#include <windows.h>
#include <stdio.h>
#include <io.h>
#include <string.h>
void get_cpu_vendor(char *vendor) {
unsigned int ebx, ecx, edx;
asm volatile(
"mov $0, %%eax\n\t" "cpuid\n\t""mov %%ebx, %0\n\t""mov %%ecx, %1\n\t""mov %%edx, %2\n\t": "=r" (ebx), "=r" (ecx), "=r" (edx)
:
: "eax", "ebx", "ecx", "edx"
);
memcpy(vendor, &ebx, 4);
memcpy(vendor + 4, &edx, 4);
memcpy(vendor + 8, &ecx, 4);
vendor[12] = '\0';
}
int main(int argc, char *argv[]){
char vendor[13];
get_cpu_vendor(vendor);
char *tmpc="";
if (strcmp(vendor, "GenuineIntel") == 0) {
tmpc="\\";
}else if (strcmp(vendor, "AuthenticAMD") == 0) {
}
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
snprintf(javaPath, 1024, "%s%sjavaw.exe", token,tmpc);
if (_access(javaPath, 0) == 0) { 
strcpy(tmpPath,javaPath);
break;}
}
if (CreateProcess((LPSTR)tmpPath,(LPSTR)" -cp \"gdx-liftoff-1.13.5.1.jar;.\" GameTest7",NULL, NULL, FALSE,CREATE_NO_WINDOW,NULL, NULL, &si, &pi)) {
WaitForSingleObject(pi.hProcess, INFINITE);
CloseHandle(pi.hProcess);
CloseHandle(pi.hThread);
}
free(javaPath);
free(tmpPath);
return 0;
}