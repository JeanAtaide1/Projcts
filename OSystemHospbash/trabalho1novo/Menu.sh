#!/bin/bash 

#a variavel leitura serve como controlo , se ela for igual a 0 saimos do loop
#essa variavel é alterada atravez do utilizador quando INSERIR 0 e verificar sua saidaSS

leitura=1
while [ -z $leitura ] || [ $leitura -ne 0 ]; do
echo "Menu de opcoes : (digite a opcao desejada)"
 echo "1. Cria pacientes"
 echo "2. Cria medicos"
 echo "3. Stats"
 echo "4. Avalia medicos"
 echo "0. Sair"
 
read a

   case $a in
   #faz a verificacao de saida do utilizador,caso queira sair
     0) 
        # salvo a variavel b1  sair do ciclo quando o utilizador inserir um caracter aceitavel
       x=1
       
       #verifica se b1 foi alterado e se nao for nao sai do loop
        while [ $x -eq 1 ]; do
             echo "Sair do menu? yes/no"
             read b
             
           #altera a variavel leitura para 0 e assim sai do menu com a verificacao do cliente
             case $b in
             "yes") leitura=0 
                    x=0      ;;
             #altera a variavel x para '0' assim sai deste loop mas continua no menu
             "no")  x=0 ;;
             #se o cliente continuar a digitar errado era repetidamente emitir a mensagem de erro e informar os comandos corretos
             *) echo "Erro : digite 'yes' ou 'no'" ;;
             esac
       done;;
       
       # cria pacientes de o cliente apertar 1
    1) echo "CRIA PACIENTES"
     sh ./cria_pacientes.sh
        ;;
        
    #cria um medico se o cliente apertar 2
    2) echo "CRIA MEDICOS"
    
       #pede o nome do medico e guarda na variavel nome
       read -p " Escrever nome do medico :"  nome
       
       #pede o numero da cedula e guarda em numero
       read -p "Escrever numero da cedula de $nome :" numero
       
       # pede a especializacao e guarda em especializacao
       read -p "Escrever epecializacao de $nome :" especializacao

      read -p "Escrever email de $nome : " email
      
        ./cria_medico.sh "$nome" "$numero" "$especializacao" "$email"   ;;
        
    #abre stats se o cliente apertar 3
     3) echo "STATS"
         read -p "Digite a localidade desejada para procura de pacientes :" l
         
         read -p "Digite o valor minimo de saldo desejado  :" v
          ./stats.sh "$l" "$v" ;;
          
     #se o cliente apertar 3 cria a lista negra
      4) echo "AVALIA MEDICOS"
         sh ./avalia_medicos.sh;;
      #se o cliente digitar um comando invalido o menu ira avisa-lo com anuncio de um erro .
      *) echo "  Erro : comando invalido! digitar '1' '2' '3' '4' ou '0'"
   esac
done
 
