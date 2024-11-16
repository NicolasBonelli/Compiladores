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
@1000 dd 1000
@$50 dd -50
@0 dd 0
@100 dd 100
_Valor_de_X__str db " Valor de X: ", 0
@retFUN1@PROGRAMA@FUN1 dd 0
_W@PROGRAMA@FUN1 dd 0
_X@PROGRAMA dq 0.0
@50 dd 50
@aux3 dq 0.0
@aux2 dq 0.0
.code
_FUN1@PROGRAMA PROC
MOV ECX, @0
CMP _W@PROGRAMA@FUN1, ECX
JGE L11
JMP LsaliFuncion
JMP L15
L11:
MOV ECX, @1000
MOV _W@PROGRAMA@FUN1, ECX
L15:
MOV EAX, _W@PROGRAMA@FUN1 
MOV @retFUN1@PROGRAMA@FUN1, EAX
RET
_FUN1@PROGRAMA ENDP

START:
MOV ECX, @$50
MOV _W@PROGRAMA@FUN1, ECX
CALL _FUN1@PROGRAMA
FILD @retFUN1@PROGRAMA@FUN1
FSTP @aux2
FSTP ST(0) 
FLD @aux2
FSTP _X@PROGRAMA
FSTP ST(0) 
push offset _Valor_de_X__str 
call printf 
add esp, 4 
fld _X@PROGRAMA
sub esp, 8 
fstp qword ptr [esp] 
push offset doubleFormat 
call printf 
add esp, 12 
JMP LnoSaliFuncion
LsaliFuncion:
FILD @100
FSTP @aux3
FSTP ST(0) 
FLD @aux3
FSTP _X@PROGRAMA
FSTP ST(0) 
push offset _Valor_de_X__str 
call printf 
add esp, 4 
fld _X@PROGRAMA
sub esp, 8 
fstp qword ptr [esp] 
push offset doubleFormat 
call printf 
add esp, 12 
LnoSaliFuncion:
invoke ExitProcess, 0
end START
