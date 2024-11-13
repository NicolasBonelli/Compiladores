.386
.model flat, stdcall
option casemap :none
include \masm32\include\masm32rt.inc 
includelib \masm32\lib\kernel32.lib 
includelib \masm32\lib\masm32.lib 
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\user32.lib
printf PROTO C : VARARG 
.data
@ERROR_DIVISION_POR_CERO db "ERROR DIVISION 0", 0
@ERROR_OVERFLOW db "ERROR OVERFLOW", 0
@ERROR_RANGO db "ERROR RANGO", 0
@MAX_DOUBLE REAL8 1.7976931348623157e+308  
@aux2bytes dw 0.0 
format db "Valor modificado: %f", 0 
intFormat db "%d", 0 
doubleFormat db "%f", 0 
_A@FUN2 dd 0
_NICO dq 0.0
@retDouble dq 0.0
@1 dd 1
@2 dd 2
@retInt dd 0
@1@0 REAL8 1.0
_W@FUN1 dq 0.0
@retFUN2 dd 0
@aux3 dq 0.0
@retFUN1 dq 0.0
.code
_FUN1 PROC
FLD @1@0
FSTP _W@FUN1
FSTP ST(0) 
FLD _W@FUN1 
FSTP @retFUN1 
FSTP ST(0) 
RET
_FUN1 ENDP
_FUN2 PROC
MOV ECX, @2
MOV _A@FUN2, ECX
MOV EAX, _A@FUN2 
MOV @retFUN2, EAX
RET
_FUN2 ENDP
START:
MOV ECX, @1
MOV _A@FUN2, ECX
CALL _FUN2
FILD @retFUN2
FSTP @aux3
FSTP ST(0) 
FLD @aux3
FSTP _NICO
FSTP ST(0) 
fld _NICO
sub esp, 8 
fstp qword ptr [esp] 
push offset doubleFormat 
call printf 
add esp, 12 
invoke ExitProcess, 0
end START
