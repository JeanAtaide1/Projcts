#!/bin/bash 
# verifica a existencia do ficheiro medicos.txt 

 if [ -f medicos.txt ] ; then
 #faz o print de todos os dados do medico caso este tenha o rating abaixo de 5 e consultas abaixo de 6 
  awk -F";" '($6>6 && $5<5) {print $1";"$2";"$3";"$4";"$5";"$6";"$7}' medicos.txt >lista_negra_medicos.txt
  cat lista_negra_medicos.txt
 fi