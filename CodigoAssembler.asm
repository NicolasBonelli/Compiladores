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
@0 equ 0
_X dd 0
_X1 dd 0
_E1 dd 10
_E1limiteInferior dd 10
_E1limiteSuperior dd 20
@30 equ 30
@20 equ 20
@10 equ 10
.code
START:
MOV ECX, @0
MOV _X1, ECX
MOV ECX, @0
MOV _X, ECX
MOV ECX, _X1
CMP _X, ECX
JNE L19
@aux1 dd 0 
MOV ECX, @0
CMP ECX, 00h
JNE DIVPOR_CERO1
invoke MessageBox, NULL, addr @ERROR_DIVISION_POR_CERO, addr @ERROR_DIVISION_POR_CERO, MB_OK
invoke ExitProcess, 0
DIVPOR_CERO1:
MOV EAX, _X
CDQ
IDIV ECX
MOV @aux1, EAX
MOV ECX, @aux1
MOV _X, ECX
JMP L23
L19:
MOV EAX, @30
CMP EAX, _E1limiteInferior
JL ERROR_RANGO_3
CMP EAX, _E1limiteSuperior
JG ERROR_RANGO_3
JMP DENTRO_RANGO_2
ERROR_RANGO_3:
invoke MessageBox, NULL, addr @ERROR_RANGO, addr @ERROR_RANGO, MB_OK
invoke ExitProcess, 0
DENTRO_RANGO_2:
MOV _E1, EAX
L23:
invoke ExitProcess, 0
end START