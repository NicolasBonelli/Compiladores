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
@MAX_DOUBLE REAL8 1.7976931348623157e+308  
@aux2bytes dw 0.0 
@0 dd 0
@1 dd 1
@0@0 REAL8 0.0
@3@0 REAL8 3.0
_X dd 0
_X2 dd 0
_F1 dq 0.0
_F2 dq 0.0
.code
START:
FLD @3@0
FSTP _F1
FLD _F1
FCOM @0@0
FSTSW @aux2bytes
MOV AX, @aux2bytes
SAHF
@aux1 dd 0 
MOV @aux1, 0FFh
JA aux1
MOV @aux1, 00h
aux1:
MOV ECX, @aux1
OR ECX, 0
JE L14
@aux2 dd 0 
MOV ECX, @0
CMP ECX, 00h
JNE DIVPOR_CERO1
invoke MessageBox, NULL, addr @ERROR_DIVISION_POR_CERO, addr @ERROR_DIVISION_POR_CERO, MB_OK
invoke ExitProcess, 0
DIVPOR_CERO1:
MOV EAX, @1
CDQ
IDIV ECX
MOV @aux2, EAX
MOV ECX, @aux2
MOV _X, ECX
L14:
invoke ExitProcess, 0
end START