.386
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
.data
@ERROR_DIVISION_POR_CERO db "ERROR DIVISION 0", 0
@ERROR_OVERFLOW db "ERROR OVERFLOW", 0
@ERROR_RANGO db "ERROR RANGO", 0
_W dd 0
_X dd 0
@3@4 equ 3.4
.code
F3 PROC
invoke ExitProcess, 0
end START