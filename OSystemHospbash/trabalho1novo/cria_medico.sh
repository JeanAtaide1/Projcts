#!/bin/bash 
#verificaca se o numero de argumentos esta correto
if [[ $# < 4 ]];then
echo "Erro : esqueceu de por alguma informacao"
exit
fi

#nomeacao  de cada uma dos argumentos para facilitar o entendimento do codigo
nome=$1
numero=$2
especializacao=$3
email=$4
rating=0
consultas=0
saldo=0


# se o nome , especializacao ou email tiverem caracteres invalidos aciona um erro 
if   ! [[ $nome =~ ^[\ a-zA-Z]+$ ]]; then
echo "Erro : nome do medico so permite caracteres do alfabeto. " 
exit
fi

#a cedula aceita apenas numeros.
if  ! [[ $numero =~ ^[0-9]+$ ]]; then
echo "Erro: cedula de $nome apenas aceita caracteres numericos"
exit
fi
#email é o mais complexo ,aceita letras e alguns simbolos , mas tem de ter obrigatoriamente um '@' e posteriormente um '.'
if ! [[ $email =~ ^[a-zA-Z0-9_.+-]+[@][a-zA-Z0-9]+(\.[a-zA-Z]+)+$ ]]; then
echo " Erro : o email inserido tem caracteres invalidos."
exit
fi 

# se o nome , especializacao ou email tiverem caracteres invalidos aciona um erro
    

 #verifica se o medico ja existe atraves do numero da cedula
 if [ -f medicos.txt ] && cat medicos.txt | awk -F";" '{print $2}' | grep -q $numero ; then
    echo "Erro: MEDICO JA REGISTADO NO SISTEMA"
 # se nao exitir cria um novo registo
else if [ -n "$nome" ]; then
    echo "$nome;$numero;$especializacao;$email;$rating;$consultas;$saldo">>medicos.txt
    echo "PERFIL REGISTADO COM SUCESSO"
    fi

cat medicos.txt
fi


  