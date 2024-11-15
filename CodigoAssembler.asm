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
_A@PROGRAMA@FUN1@FUN2 dq 0.0
@retFUN1@PROGRAMA@FUN1 dq 0.0
_W@PROGRAMA@FUN1 dq 0.0
@20@0 REAL8 20.0
@retFUN2@PROGRAMA@FUN1@FUN2 dq 0.0
_X1@PROGRAMA dd 0
_X2@PROGRAMA dq 0.0
@aux1 dq 0.0
@10 dd 10
START:
MOV ECX, @10
MOV _X1@PROGRAMA, ECX
FILD _X1@PROGRAMA
FSTP @aux1
FSTP ST(0) 
FLD @aux1
FSTP _A@PROGRAMA@FUN1@FUN2
FSTP ST(0) 
FLD _A@PROGRAMA@FUN1@FUN2 
FSTP @retFUN2@PROGRAMA@FUN1@FUN2 
FSTP ST(0) 
RET
FLD _W@PROGRAMA@FUN1
FSTP _A@PROGRAMA@FUN1@FUN2
FSTP ST(0) 
CALL _FUN2@PROGRAMA@FUN1
FLD @retFUN2@PROGRAMA@FUN1@FUN2
FSTP _W@PROGRAMA@FUN1
FSTP ST(0) 
FLD _W@PROGRAMA@FUN1 
FSTP @retFUN1@PROGRAMA@FUN1 
FSTP ST(0) 
RET
FLD @20@0
FSTP _W@PROGRAMA@FUN1
FSTP ST(0) 
CALL _FUN1@PROGRAMA
FLD @retFUN1@PROGRAMA@FUN1
FSTP _X2@PROGRAMA
FSTP ST(0) 
fld _X2@PROGRAMA
sub esp, 8 
fstp qword ptr [esp] 
push offset doubleFormat 
call printf 
add esp, 12 
invoke ExitProcess, 0
end START
.code
_FUN1@PROGRAMA PROC
_FUN2@PROGRAMA@FUN1 PROC
_FUN2@PROGRAMA@FUN1 ENDP
_FUN1@PROGRAMA ENDP

