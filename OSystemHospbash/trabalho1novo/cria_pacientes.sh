#!/bin/bash 
#se /etc/passwd exitir continua a operacao
if [ -f /etc/passwd ];then
cat /etc/passwd | grep ^a[0-9] | head -10 |sed 's/^a//' |awk -F'[:,]' '{print $1"; "$5";;; ""a"$1"@iscte-iul.pt""; 100"}'  > pacientes.txt
echo " OPERACAO EFECTUADA COM SUCESSO"
else
echo "Erro : /etc/passwd nao existe"
fi