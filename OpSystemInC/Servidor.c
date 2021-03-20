#include "defines.h"
enum {LISTA,CONTADORES};
typedef struct {
int num;
char nome[10];
}Contador;

int size=10;
int datasize=4;
typedef struct {      // estrutura que inclui os contadores e as consultas , será utilizada na shared memory.
Contador contadores[4];
Consulta lista[10];
}Dados;


int id;                 // id da mailbox
int sem;               // id dos semaforos
Dados* dados;         //pointer de uma struct de 'Dados' que contém os contadores e a lista .

void adicionaPrintaDados(int tipo);

  void up(int i){
    struct sembuf UP= { i,1, 0};
    int res = semop(sem,&UP,1);
    exit_on_error(res,"Erro no UP.\n");
  }



  void down(int i){            // -----------recebe um inteiro respectivo ao semáforo que quero utilizar.
    struct sembuf DOWN= { i,-1, 0};  //--------utiliza o inteiro recebido para indicar o indice do semáforo desejado.
    int res = semop(sem,&DOWN ,1);  //---------faz um down do semáforo respectivo.
    exit_on_error(res,"Erro no sDOWN.\n");//----sai caso occora algum erro.
  }


  void iniciaSem(){ // -----------------------------faz a inicialização de semáforos.
      int sema=semget(IPC_KEY,2,IPC_CREAT |0666);//----o segundo argumento indica o numero de semáforos desejado.
      exit_on_error(sema,"Erro no semget.\n");
      int status = semctl(sema,0,SETVAL ,1);  //------iniciamos ambos os semaforos com o valor de '1'.
      exit_on_error(status,"Erro no semget.\n");
      status = semctl(sema,1,SETVAL ,1);
      exit_on_error(status,"Erro no semget.\n");
      sem=sema;
  }
  
  void consultasAndamento(){     //-------------------nada de importante---------------------
     printf("Consultas em andamento\n---------------------------------------------------------------------------\n|\tSALA\t|\tTIPO\t|\tDESCRICAO\t|\tPID\t|");
     for(int i=0;i<size;i++){
     if(dados->lista[i].tipo!=-1)
     printf("\n|\t%d\t|\t%d\t|\t%s\t|\t%d\t|", i, dados->lista[i].tipo, dados->lista[i].descricao, dados->lista[i].pid_consulta);
     }
     printf("\n---------------------------------------------------------------------------\n"); fflush(stdout);
   }
  
  void alarme(int s){
      printf(".");
  }
  
  void printaDados(){   //---------------------faz print dos dados dos contadores.
    printf("\nDados sobre consultas efetuadas:");
    printf("\n-------------------------\n");
     for(int i=0;i<datasize;i++){
       printf("|\t%s : %d\t|\n",dados->contadores[i].nome,dados->contadores[i].num);
      }
    printf("-------------------------\n");
  }
  
    void adicionaDados(int tipo){ //--------------realiza a adição do contador respectivo ao argumento passado.-----------
    down(CONTADORES);
    switch(tipo){   //-------------------------'0'=perdidas;'1'='normal';'2'='COVID19'; '3'='Urgente'
    case 0: dados->contadores[0].num++;break;       //soma 1 ao contador das 'Perdidas' se for o tipo '0'.
    case 1: dados->contadores[1].num++;break;      //soma 1 ao contador das 'Normais' se for o tipo '1'.
    case 2: dados->contadores[2].num++;break;     //soma 1 ao contador das consultas de 'COVID19' se for o tipo '2'.
    case 3: dados->contadores[3].num++;break;    //soma 1 ao contador das 'Urgentes' se for o tipo '3'.
    }
    printaDados();
    up(CONTADORES);
}

  void iniciarLista(){ //------------inicia os tipos da lista a '-1'.
    for(int i=0;i<size;i++){
      dados->lista[i].tipo=-1;
    }
  }
  
    void iniciarDados(){
      for(int i=0;i<datasize;i++){
         dados->contadores[i].num=0;
         switch(i){
         case 0:  strcpy(dados->contadores[i].nome,"Perdidas");break;
         case 1: strcpy(dados->contadores[i].nome,"Normal");break;
         case 2: strcpy(dados->contadores[i].nome,"COVID19");break;
         case 3: strcpy(dados->contadores[i].nome,"Urgente");break;
         }
         printf("dados %d : %d,%s \n",i,dados->contadores[i].num,dados->contadores[i].nome);
      }
  }
  
  
 void iniciarConsulta(int indice){
        mensagem m;    //---------------------------------envia uma mensagem ao cliente indicando o início da consulta.
        m.end=dados->lista[indice].pid_consulta;   //-------- envia a mensagem para o endereço do processo da consulta em questão.
        m.cons.status=2;
        int status=msgsnd(id,&m,sizeof(mensagem)-sizeof(long),0);
        exit_on_error(status,"Error communicating with client.\n");
        
        signal(SIGALRM,alarme);
        for(int i=0;i<10;i++){// --------------------------------------este ciclo serve apenas para criar a animação de pontinhos na tela
          alarm(1);
          status=msgrcv(id,&m,sizeof(m)-sizeof(long),dados->lista[indice].pid_consulta,0);   // espera por mensagens vindas do cliente para o endereço pid_consulta.
          if(errno!=4)//Esta condiçao verifica se o status tem algum erro, ja que se for negativo e o errno diferente de '4', significa que nao foi uma interrupção causada pelo sinal alarm.
          exit_on_error(status,"Error communicating with client.\n");          //sai do processo caso o status seja negativo.
          if(status>=0)   // --------------------------------------------verifica se o msgrcv foi bem sucedido.
            if(m.cons.tipo==5){  //----------------------------------- se a consulta enviada na mensagem for do tipo '5' então cancelamos a consulta.
              printf("Consulta %d cancelada a pedido do cliente.\n",dados->lista[i].pid_consulta);
              down(LISTA); //-----------------------------------------faz down no semáforo na posiçao '0' que é o semaforo da lista.
              dados->lista[indice].tipo=-1;
              up(LISTA);//--------------------------------------------faz up logo após o termino da operação na zona crítica.
              exit(0);
            }
             fflush(stdout);
        }    printf("\nConsulta PID-> %d terminada .\n",dados->lista[indice].pid_consulta);
        
         down(LISTA); //-------------------------------------------Apos terminar a consulta faz o down para o sem da lista e apaga a consulta.
         dados->lista[indice].tipo=-1;
         up(LISTA);
         
         m.cons.status=3;//---------------------------------------cria uma mensagem com status '3' que indica o termino da consulta e envia essa mensagem ao cliente.
         status=msgsnd(id,&m,sizeof(mensagem)-sizeof(long),0);
         exit_on_error(status,"Erro no msgsend.\n");
         exit(0);
  }
  


 void adicionaConsulta(Consulta c){
   int cpid=fork();
      if(cpid==0){
      down(LISTA);    //FAZ -----DOWN NO SEMAFORO '0'-----
      int i;      
       for(i=0;i<size;i++){        //verifica todas as posicoes ate encontrar uma posicao disponivel para inserir uma consulta
           if(dados->lista[i].tipo==-1){
               dados->lista[i]=c;
               up(LISTA);       // FAZ ----------UP NO SEMAFORO '0'---------------
               printf("Consulta de tipo: %d, descricao: %s, e pid: %d, aceite na sala %d do processo %d\n",c.tipo,c.descricao,c.pid_consulta,i,getpid());
               adicionaDados(c.tipo);
               consultasAndamento();   //---------------------faz print das consultas em andamento
               iniciarConsulta(i);    // ---------------------inicia a consulta
               break;     
           } 
        }  
       if(i==size){ 
               up(LISTA);         //--------- isto verifica que a lista de salas esta cheia e emite um sinal ao cliente
               printf("Vagas para consultas ocupadas.");
               adicionaDados(0); //------------Adiciona 1 ao dado respectivo, neste caso o '0' significa 'perdidas'.
               mensagem m;      //--------------cria uma mensagem com status '4' que irá indicar ao cliente que a consulta não é possivel.
               m.end=c.pid_consulta;
               m.cons.status=4;
               int status;
               status=msgsnd(id,&m,sizeof(mensagem)-sizeof(long),0);
               exit_on_error(status,"Error communicating with client.\n");  //-------verifica sem houve algum erro ao enviar a mensagem.
               printf("A consulta nao pode ser efectuada\n");
           }
    }else {
        signal(SIGCHLD,SIG_IGN);  //----------Isto irá fazer com que o pai receba o sinal a indicar o término de um processo filho e a função irá recebê-lo.
    }
 }
  
  
 void iniciaMemoria(){
   int shmid;

    shmid =shmget(IPC_KEY ,sizeof(Dados) , 0666 | IPC_CREAT | IPC_EXCL);
   
    if(shmid>=0){ //-------------------------------se o shmget 'exclusive' nao der erro , significa que foi criado pela primeira vez e então inicia os dados e a lista.
    
      dados=(Dados*)shmat(shmid,NULL,0);
      printf("iniciando a memoria partilhada \n");
      down(LISTA);   //----------------------------faz down do sem da lista.
      iniciarLista();//----------------------------faz a inicialização da lista com os tipos das consultas =-1.
      up(LISTA);//---------------------------------up no sem da lista.
      down(CONTADORES);//--------------------------down sem dos contadores.
      iniciarDados();  //-------------------------- faz a inicialização dos contadores todos a '0'.
      up(CONTADORES);//-----------------------------up sem dos contadores
     }else{//--------------------------------------se  o shmget 'exclusive' der erro , significa que ja existe uma shared mem com essa key entao nos fazemos um novo shmget.
       shmid =shmget(IPC_KEY ,sizeof(Dados), 0666 | IPC_CREAT); // mas não iniciamos os dados , apenas fazemos um shmat(shared memory atach) e obtemos os valores que já la estavam registados.
       exit_on_error(shmid,"Erro no shmget Consultas.\n");
       dados=(Dados*)shmat(shmid,NULL,0);
      
     }
    printf("A preparar shared memory...\nA preparar server...\n");
    printf("Memory adress: %d.\nID: %d.\n",shmid,id);
    printf("\nServidor preparado ->%d.\n",getpid());
  }
  
  void iniciaMsg(){

    id=msgget(IPC_KEY , 0666 | IPC_CREAT ); //---inicia a message queue com a key IPC_KEY.
    exit_on_error(id,"Erro no msgget.\n");
  }
  
 void loopMsgRcv(){
 int status;
  while(1){
       mensagem m;
       status=msgrcv(id,&m,sizeof(m)-sizeof(long),END_PADRAO,0); //-------------------------fica à espera de mensagens enviadas para o endereço padrão(END_PADRAO).
       if(errno!=4)   // ------------------------------------------se a o errno for !=4 , que indica uma interrupção por sinal, irá verificar se houve algum erro.
         exit_on_error(status,"Error communicating with client.\n");
       if(status>=0 && m.cons.status==1){        //--------verifica se o status da consulta é '1'(Pedido de consulta),  também verifica se a operação de msgrcv foi bem sucedida.
        system("clear");
        adicionaConsulta(m.cons);
        printf("Mensagem observatoria recebida de %d: %s\n",m.cons.pid_consulta,m.text);
      }
    }
 }
 void Cancela(int s){  //-------------trata o sinal SIGINT e sai do servidor.
 printf("\nO servidor vai desligar...\n");
 printaDados();
 exit(0);
 }
  int main(){
    signal(SIGINT,Cancela);
    iniciaMsg();   // inicia a messae queue
    iniciaSem();   // inicia os semáforos.
    iniciaMemoria();   // inicia a shared memory
    loopMsgRcv();     // faz um ciclo a receber mensagens de possíveis clientes.
  }