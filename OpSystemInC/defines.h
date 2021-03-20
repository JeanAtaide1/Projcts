#ifndef __defines_h__
#define __defines_h__

#include <sys/wait.h> 
#include <string.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/ipc.h>
#include <sys/types.h>
#include <sys/msg.h>
#include <sys/shm.h>
#include <sys/sem.h>
#include <sys/errno.h>
#define exit_on_error(i,m) do{ if (i<0) { perror(m); exit(1);} } while(0)
#define size_of_message 80
#define IPC_KEY 0x0a92844 // Replace with your student number
#define END_PADRAO 1 
#endif
typedef struct {
 int tipo; // Tipo de Consulta: 1-Normal, 2-COVID19, 3-Urgente
 char descricao[100]; // Descrição da Consulta
 int pid_consulta; // PID do processo que quer fazer a consulta
 int status; // Estado da consulta: 1-Pedido, 2-Iniciada,
} Consulta; // 3-Terminada, 4-Recusada, 5-Cancelada

typedef struct {
long end;           //-----long que será o endereço/tipo das mensagens.
Consulta cons;
char text[size_of_message];  //(opcional) mensagem personalizada em texto.
}mensagem;
