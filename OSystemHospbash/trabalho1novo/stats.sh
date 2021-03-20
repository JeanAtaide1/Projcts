#!/bin/bash 

if [[ $# < 2 ]];then
echo "Erro : esqueceu de por alguma informacao"
fi
valor=$2
echo "$valor"

#verifica se a localidade é composta por caracteres validos
if  ! [[ $1 =~ ^[\ a-zA-Z]+$ ]] ; then
 echo " Erro : A localidade tem caracteres invalidos "
 exit
fi

#verifica se o valor é composto por caracteres validos
if ! [[ $valor =~ ^[0-9]+$ ]] ; then
  echo "Erro : o valor tem de ser um inteiro"
  exit
fi

#verifica se existem pacientes registados
   if ! [ -f pacientes.txt ]; then
   echo "Nao ha pacientes registados"
   
#vai a lista pacientes.txt e isola a coluna das localidades , faz uma procura pela localidade passada por argumentos e conta quantas ficaram
   else
    numeropac="$(cat pacientes.txt |cut -d";" -f3 | grep "$1" | wc -l)" 
    echo "pacientes em $1: $numeropac"
fi

#verifica se existem medicos registados
 if ! [ -f medicos.txt ]; then
  echo "Nao ha medicos registados"
  else
#vai a lista medicos.txt na se o valor da coluna de valores for maior do que o argumento passado soma 1 ao contador
   numeromedicos="$( awk -F";" -v x="$valor" '$7>(x) {c++} END{print c+0}' medicos.txt )"

   echo "Numero de medicos com saldo superior a $valor: $numeromedicos"
 fi