#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "IOMngr.h"



FILE *sfp;
FILE *lfp;
int line;
int curIndex;
char curLine[MAXLINE];
int sameLine;


int openFiles(char * sourceName, char * listingName){
    sfp = fopen(sourceName, "r");
    if(listingName == NULL){
        lfp = stdout;
    }else{
        lfp = fopen(listingName, "w");
    }
    line = 1;
    curIndex = 0;


    if(sfp != NULL && lfp != NULL){
        return 1;
    }else{
        return 0;
    }

}

void closeFiles(){
    fclose(sfp);
    if(lfp != stdout){
        fclose(lfp);
    }
}

char getNextSourceChar(){
    if(feof(sfp)){return EOF;} 
    if(curLine[curIndex] != '\n' && curLine[curIndex] != '\r'  && curLine[curIndex] != '\0'){
        curIndex++;
       // printf("curChar: \'%c\'\n", curLine[curIndex]);
        return curLine[curIndex];
    } 
    
    fgets(curLine, MAXLINE, sfp); 
    
    if(lfp != stdout){
        fprintf(lfp, "%d. %s",line, curLine);
    }
    if(feof(sfp)){return EOF;}
    line++;
    sameLine = 0;
    curIndex = 0;
   // printf("curChar: \'%c\'\n", curLine[curIndex]);
    return curLine[curIndex];
}

void writeIndicator(int column){
    if(lfp != stdout){
        int i;
        for(i=0; i<column; i++){
            fputc(' ',lfp);
        }
        fputc('^' ,lfp);
        fputc('\n', lfp);
    }
    if(sameLine == 0) {
        printf("%s\n", curLine);
        sameLine = 1;
    }
    int i;
    for(i=0; i<column; i++){
        fputc(' ',stdout);
    }
    fputc('^' ,stdout);
    fputc('\n', stdout);

}

void writeMessage(char * message){
    fprintf(lfp,"%s\n",message);
}

int getCurrentLineNum(){
    return line;
}

int getCurrentColumnNum(){
    return curIndex;
}


