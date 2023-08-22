// WMP: Pipes hang shell: echo foo | grep foo.
// WMP: Some erroneous command lines undetected:
//      echo > > x
//      echo < < x
//      echo | | x
// WMP: Extra files in repository. Did you run "git add --all"? Avoid "--all".
//      .nfs00000000026c6aea0000075e
//      .nfs00000000026c6b030000075d

#include <linux/limits.h>
#include <sys/wait.h>
#include <assert.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <errno.h>
#include <sys/types.h> /* has the typedef for mode_t (an integer) */
#include <sys/stat.h> /* defines many of the constants */
#include <fcntl.h> /* prototype for open() */
const int CMD_MAX = 1024;

/*
 * Takes a string, command_line, breaks command_line into tokens as
 * delimited by strings, and fills the array argv with pointers
 * to each token. This leaves argv as an array of pointers to NULL-terminated
 * strings representing the tokens. The argv array is itself NULL-terminated.
 * Modifies command_line.
 *
 * Returns the number of tokens.
 *
 * WARNING: this function assumes command_line ends with a '\n'; you must
 * arrange for this to be the case. It is okay to reject overly-long
 * command lines (> CMD_MAX) elsewhere in your program.
 */
int
parse(char *argv[], char *command_line)
{
	int i;
	char *saveptr = NULL;

	assert(command_line[strlen(command_line) - 1] == '\n');

	/* Drop newline. */
	command_line[strlen(command_line) - 1] = '\0';

	/* strtok_r is thread-safe; strtok is not. */
	argv[0] = strtok_r(command_line, " ", &saveptr);

	/*
	 * Careful with those buffer sizes!
	 * + 2: cover duplicate AND '\0' terminator.
	 */
	for (i = 0; i + 2 < ARG_MAX && argv[i]; i++) {
		argv[i + 1] = strtok_r(NULL, " ", &saveptr);
	}

	argv[i + 1] = '\0';

	return i;
}

/*
 * Consume characters from stdin up to and including reading a newline
 * or detecting end of file.
 */
void eat_up_to_nl() {
	int c;

	do {
		c = getchar();
	} while (c != '\n' && c != EOF);
}


#define FULL 16

//struct node
//{
//    char *data;
//    struct node *next;
//};
//typedef struct node node;

//struct queue
//{
//    int count;
//    node *front;
//    node *rear;
//};
//typedef struct queue queue;

//void initialize(queue *q)
//{
//    q->count = 0;
//    q->front = NULL;
//    q->rear = NULL;
//}

//int isempty(queue *q)
//{
//    return (q->rear == NULL);
//}
//char* dequeue(queue *q);
//void enqueue(queue *q, char * value);
//void enqueue(queue *q, char * value)
//{
//    if (q->count < FULL)
//    {
//        node *tmp;
//        tmp = malloc(sizeof(node));
//        tmp->data = value;
//        tmp->next = NULL;
//        if(!isempty(q))
//        {
//            q->rear->next = tmp;
//            q->rear = tmp;
//        }
//        else
//       {
//            q->front = q->rear = tmp;
//        }
//        q->count++;
//    }
//    else
//    {
//          dequeue(q);
//	  enqueue(q,value);
//	  
//    }
//}

//char* dequeue(queue *q)
//{
//    node *tmp;
//    char *n = malloc(sizeof(q->front->data));
//    strcpy(n, q->front->data);
//    tmp = q->front;
//    q->front = q->front->next;
//    q->count--;
//    free(tmp);
//    return(n);
//}


int MAX_HIST = 16;
int
main (void)
{
	/* Be careful using these sized arrays! */
	char  command_line[CMD_MAX];
	char *argv2[ARG_MAX];
        char *hist[MAX_HIST];
	int realCount =0;
	bool loop = false;
//	queue *history;
//	history = malloc(sizeof(queue));
//	initialize(history);
	while (!feof(stdin)) {
		int count;	
		
		/* Prompt. */
		fprintf(stderr,"$ ");
		/* Replace this. You need to read command lines from stdin. */
		//strcpy(command_line, "this is a simulated command line\n");
		//while(fgets(command_line, CMD_MAX,stdin)){
		//     strcat(command_line, " ");
	        //}
	        if(feof(stdin)) break;
	       	fgets(command_line, sizeof(command_line), stdin);
		if(feof(stdin)) break;
		//eat_up_to_nl();
		//while(fgets(hist,CMD_MAX,stdin)){
		//  strcat(command_line, " ");
		//  strcat(command_line,hist);
		//}
		//count = parse(argv2, command_line);
		//printf("command_line: %s\n",command_line);

		if(realCount >= 16){
		  loop =true;	
		}
		if(loop){
			for(int i = 0; i<15;i++){
				hist[i] = hist[i+1];
			} 
			hist[MAX_HIST-1] = strdup(command_line);
			goto next;	
		}
		
		hist[realCount] = strdup(command_line);
	        realCount++;
		next:
	 //     enqueue(history, strdup(command_line));
				
		//	strcat(command_line, curCommand);
		/*
		 * This is how you parse the command line into an
		 * array of string pointers.
		 */
	        count = parse(argv2, command_line);
		
		/* Do not open multi-line comments like this or Linus Torvalds
		 * will get mad!
		 */

		/*
		 * You need to properly process the command line; here we
		 * merely print it as an example.
		 */

		
	      	if(strcmp(command_line, "exit")==0){
			//exit(-1);
			break;
			
	      	}else if(strcmp(command_line, "history")==0){
		 	int leng = (loop ? MAX_HIST : realCount);  
			for (int i = 0; i < leng; i++) {
			    printf("%s",hist[i]);
		  	}
			continue;
		}else if(strcmp(command_line, "&")==0){
			printf("cannot use '&' without preceding command\n");
			continue;
		}else if(strcmp(argv2[count-1], ">")==0){
			printf("cannot use '>' without subsequent file\n");
			continue;
		}else if(strcmp(argv2[count-1], "<")==0){
			printf("cannot use '<' without subsequent file\n");
			continue;
		}else if(strcmp(argv2[count-1], "|")==0){
			printf("cannot use '|' without subsequent command\n");
			continue;
		}
		bool cont = false;
		for(int i = 0; i<count; i++){
			if(strcmp(argv2[i],"<<")==0){
				printf("cannot use '<' twice\n");
				cont = true;
				break;
			}else if(strcmp(argv2[i],">>")==0){
				printf("cannot use '>' twice\n");
                                cont = true;
                                break;
			}else if(strcmp(argv2[i], "||")==0){
				printf("cannot use '|' twice\n");
                                cont = true;
                                break;
			}
		}
		
		if(cont) continue;
		
		//CHECK HERE FOR double >> << or ||
		
	//	char * args[count];
	//	for(int i = 0; i<count-1; i++){
	//		args[i] = argv2[i+1];
	//		printf("args[%d]: %s\n", i, args[i]);
	//	}	
	//	args[count-1] = '\0';
                int fd = -1; 
                char * file;
                bool isredirectR=false;
		bool isredirectL=false;
                char * reDirArgs[count];
                bool isPipe = false;
                int pipeIndex;
                for(int i = 0; i<count; i++){
                  	if(strcmp(argv2[i],">")==0){
                              isredirectR = true;
                              file = argv2[i+1];
                              reDirArgs[i] = NULL;
                              break;
                        }else if(strcmp(argv2[i],"<")==0){
         	              isredirectL = true;
                              file = argv2[i+1];
                              reDirArgs[i] = NULL;
                              break;
                        }else if(strcmp(argv2[i],"|")==0){
				isPipe = true;
                                pipeIndex = i;
      		                break;
                        }
                        reDirArgs[i] = argv2[i];
                }
                char * rightArgs[count];
       	        int index =0;
                for(int i = pipeIndex+1; i<count;i++){
                                rightArgs[index] = argv2[i];
                                index++;  
                }

		
		int fds[2];
		if(isPipe){
                	pipe(fds);
		}
	        bool background = strcmp(argv2[count-1],"&");         
		int child_pid = fork();
		int child_exit;
		if(child_pid < 0){
			printf("fork Failed\n");
		}else if(child_pid == 0){
		//	if(strcmp(argv2[count-1],"&")==0){
		//		waitpid(child_pid, NULL, WNOHANG);
		//	}	
		
		//	printf("argv2[0]: %s\n",argv2[0]);
		//	printf("args[0]: %s\n",args[0]);
		                                	
			
			if(isredirectR){
				fd = open(file, O_CREAT | O_RDWR | O_TRUNC, S_IRUSR | S_IWUSR);
                                dup2(fd, STDOUT_FILENO);
			}
			if(isredirectL){
				fd = open(file, O_RDONLY, 0);
                                dup2(fd, STDIN_FILENO);
			}
			
			if((isredirectR||isredirectL) && fd == -1){
                                perror("error opening file");
                                continue;
                        }

			close(fd);
			if(background == 0){
				argv2[count-1] = NULL;
			}
			
			if(isPipe){
                    		close(fds[0]); 
				dup2(fds[1], STDOUT_FILENO);
				close(fds[1]);
				execvp(argv2[0], reDirArgs);	
                        } 

			if(isredirectL || isredirectR){
				execvp(argv2[0], reDirArgs);	
			}else{	
				execvp(argv2[0], argv2);
			}
		//	waitpid(child_pid,child_exit,0);
			//fprintf(stderr,"child exit code: %d\n",child_exit);
			
			perror("error executing");
			if(errno == 2){
				//fprintf(stderr, "child exit code: %d\n",127);
				exit(127);
			}
			else if(errno == 1 || errno == 13 || errno == 8){
				//fprintf(stderr, "child exit code: %d\n",126);
				exit(126);
			}
			//exit(-1);	
			//continue;
		}else{ //parent portion
			if(background==0){
                                waitpid(child_pid, NULL, WNOHANG);
				continue;
                        }
			if(isPipe){
				int child_pid1 = fork();
				if(child_pid1 == 0){
					close(fds[1]);
					dup2(fds[0], STDIN_FILENO);
					close(fds[0]);
					execvp(rightArgs[0], rightArgs);
				}
				waitpid(child_pid, NULL, 0);
				waitpid(child_pid1, &child_exit, 0);
				fprintf(stderr, "child exit code: %d\n", WEXITSTATUS(child_exit));
				continue;
			}
			waitpid(child_pid, &child_exit, 0);	
		        fprintf(stderr, "child exit code: %d\n",WEXITSTATUS(child_exit));
				
		//	if(child_exit > 0){
 		//		fprintf(stderr,"child exit code: %d\n",WEXITSTATUS(child_exit));
		//	}else{
		//		fprintf(stderr,"child exit code: %d\n",child_exit);
		//       }
	     
		}
		
		
		/*
		 * You should break on end of stdin. Demo breaks here.
		 */
		//break;
	}
	

	exit(EXIT_SUCCESS);
}
