#include "defines.h"

int id;
int iniciada=0;

  void cancelarCons(int s){
    if(iniciada){    //-------------se a consulta ja foi iniciada irá enviar uma mensagem ao servidor a pedir o cancelamento da consulta.
    int status;
    mensagem m;       //------------ envia a mensagem para o endereço do pid do processo(getpid()) para o servido com o tipo '5', que indica o término da consulta.
    m.end=getpid();
    m.cons.tipo=5;
    
    status=msgsnd(id,&m,sizeof(mensagem)-sizeof(long),0);
    exit_on_error(status,"Erro ao comunicar o cancelamento com o servidor.\n");
    }
    printf("\nConsulta Cancelada.\n");
    exit(0);

  }

  void get_str_from_in(char *text,char *buffer , int size){ //-----------------vai ler uma string a um dado ficheiro , e apaga o ultimo caracter que será = a '\n'.
    printf("%s\n",text);
    fgets(buffer,size,stdin); buffer[strlen(buffer)-1]='\0';
  
  }
 void clear(){   //----------------------------apaga quaisquer caracteres que ficarem no buffer do input até um \n.
   while ( getchar() != '\n' );

  }
  
  void react(int status){     // ------------------------------------------------esta função realiza uma ação correspondentemente ao status passado.
  switch(status){
     case 2 : printf("Consulta iniciada para o processo: %d.\n",getpid());   //se for '2' sabe que a consulta foi iniciada pelo servidor .
          iniciada=1; break; // põe a variável iniciada a '1'(true).
          
     case 3 :   if(iniciada==1){     // ----------------------------------se iniciada=1 então indicamos o término da consulta e fechamos o processo.
                  printf("Consulta terminada para o processo: %d\n",getpid());
                   exit(0);
                }else{perror("A consulta ainda nao comecou"); break; // caso significa que a consulta ainda não começou e temos algum erro.
                } 
                
     case 4 :    printf("A consulta nao e possivel para o processo %d\n",getpid()); break;  // caso seja 4 indica a impossibilidade de realização da consulta.
     
     default : printf("\ntipo nao correspondente a qualquer acao\n"); 
  }

  
  }
 
  Consulta nova_consulta(){
    Consulta c;
    int n=5; 
     while(n>0){       //------------Se a variavel 5 chegar a 0 sai do while e consequentemente do processo (o cliente tem 5 tentativas).
       printf("Inserir o tipo de consulta (opcoes:1-normal, 2-COVID19, 3-Urgente).\n");
       scanf("%d",&c.tipo);
       clear();      //--------------- apaga quaisquer caracteres que ficarem no buffer do input até um \n.
       switch(c.tipo){    // ------------caso escolha alguma das opçoes aceitáveis sai do while e do switch.
         case 1: n=-1; break;
         case 2: n=-1; break;
         case 3: n=-1; break;
         default : n-- ; puts("tipo invalido tente novamente.(se errar 5 vezes o programa fecha)");// caso erre decrementa na variável n (menos uma chance).
       }
     }
    if(n==0)    //-----------quando as tentativas se esgotarem faz exit do programa.
    exit(0);
   
    get_str_from_in("Inserir descricao (Clinica Geral, Ortopedia, ,Pneumologia ).", c.descricao , 100);  //lê a descrição inseridda pelo cliente.
    c.pid_consulta=getpid();    //---------------escreve o PID do processo na Consulta.
    c.status=1;       
    printf("%d,%s,%d\n" , c.pid_consulta, c.descricao , c.tipo );
    return c;        //----------------------------retorna a consulta c com todos os dados preenchidos.
  }
  
  void sendMSG(mensagem *m){         //--------------------------------envia a mensagem criada para o servidor.
      int status=msgsnd(id,m,sizeof(mensagem)-sizeof(long),0);
    exit_on_error(status,"Erro no msgsnd.\n"); //--------------verifica a ocorrencia de erros.
    printf("enviei mensagem\n");
    
  }
  void newMSG(){
    Consulta c=nova_consulta();         //---------------cria uma nova consulta ao pedir dados ao cliente.
    mensagem m;
    m.end=END_PADRAO;    //-----------põe o endereço da mensagem como '1' que é o endereço padrao.
    m.cons=c;             //----------atribui c à consulta da mensagem .                   
    get_str_from_in("Inserir mensagem personalizada:",m.text , 100);
    sendMSG(&m);         // ----------envia a mensagem criada para o servidor
  }
    
  void iniciaMSG(){
      id=msgget(IPC_KEY , 0);        //----------------pede o a msg queue com a key IPC_KEY e supõe que já foi criada anteriormente pelo servidor
      exit_on_error(id,"Erro no msgget.\n");     // caso não tenha sido criada(ou outro erro qualquer tenha acontecido) o processo irá terminar.
  }
  
  void loopRcvMSG(){        //----------------- faz um loop a receber novas mensagens do servidor.
     mensagem mr;         //----------------------cria uma variavel do tipo 'mensagem'.
     while(1){
     int status=msgrcv(id,&mr,sizeof(mensagem)-sizeof(long),getpid(),0); //---- verifica se alguma mensagem foi enviada para o endereço referente ao seu getpid();
     exit_on_error(status,"Erro no msgsnd.\n");
     status=mr.cons.status;    //----------altera a variável status pondo o status da consulta que foi enviada atraves da mensagem recebida.
     react(status);            //--------- reage ao status e executa as respectivas ações.
      
     }
  }
 int main(){
    signal(SIGINT,cancelarCons);
    iniciaMSG();  //-------------faz o msgget e obtem o id da queue
    newMSG();  //---------------Cria uma nova mensagem do estilo consulta.
    loopRcvMSG();   //-----------faz um loop a receber novas mensagens do servidor.

   }