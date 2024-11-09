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
@3 equ 3
@5 equ 5
_X dd 0
_X2 dd 0
.code
START:
MOV ECX, @5
MOV _X2, ECX
MOV ECX, @3
ADD ECX, @3
@aux1 dd 0 
MOV @aux1, ECX
MOV ECX, @aux1
MOV _X, ECX
invoke ExitProcess, 0
end START