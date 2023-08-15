#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>
#include <stdbool.h>
#include "SymTab.h"	

SymTab * createSymTab(int size){
    //printf("New SymTab: size:%d\n", size); 
    SymTab *symTable = malloc(sizeof(struct SymTab));
    symTable->size = size;
    symTable->contents = malloc(size*sizeof(struct SymEntry*));
    symTable->current = NULL;
    int i ;
    for(i=0; i<symTable->size; i++) symTable->contents[i] = NULL;
  
    return symTable;

}

unsigned hash(SymTab *table , char *str){
    unsigned hashValue, size;
    size = table->size;
    for(hashValue = 0; *str!='\0'; str++){
        hashValue = *str +31 * hashValue;
    }
    return hashValue%size;
}

void destroySymTab(SymTab *table){
   // printf("DestroySymTab...\n");
    if(table == NULL){return;}
    int i;
    for(i = 0; i<table->size;i++){
	SymEntry *next, *cur = table->contents[i];
	while(cur != NULL){
	    next = cur->next;
	    free(cur);	
	    cur = next;	
	}
    }
   // free(table->contents);
    free(table);
}

int enterName(SymTab *table, char *name){
   // printf("Entering: %s\n", name);
    if(findName(table, name) == 1){
        return 0;
    }
     		
    SymEntry *head = malloc(sizeof(struct SymEntry));
    head->name = strdup(name);
    
    head->attribute = NULL;
    head->next = table->contents[hash(table, name) ];
    table->contents[hash(table, name)] = head;
   
    table->current = head;
   
    return 1;
}

 int findName(SymTab *table, char *name ){
   // printf("Searching: %s\n", name);
    int key = hash(table, name);
    SymEntry *head = table->contents[key];
    while(head != NULL){
        if(strcmp(name, head->name)==0) {
            table->current = head;
            return 1;
        }
        head = head->next;
    }
    return 0;
}

int hasCurrent(SymTab *table){
   // printf("Checking Cur...\n");	
    if(table->current == NULL){
        return 0;
    }
    return 1;
}

void setCurrentAttr(SymTab *table, void *attr){
   // printf("Setting Cur Attr....\n");
    if(hasCurrent(table) == 1){
        table->current->attribute = attr;
    }
}

void * getCurrentAttr(SymTab *table){
   // printf("Returning Cur Attr...\n");	
    if(hasCurrent(table)==1){
        return table->current->attribute;
    }
    return NULL;
}

char * getCurrentName(SymTab * table){
   // printf("Returning Cur Name...\n");
    if(hasCurrent(table)==1){
        return table->current->name;
    }
    return NULL;
}

int startIterator(SymTab *table){
   // printf("Starting Iterator...\n");
    for(int i = 0; i<table->size; i++){
        if(table->contents[i] != NULL){
            table->current = table->contents[i];
            return 1;
        }
    }
    return 0; 
}

int nextEntry(SymTab *table){
   // printf("nextEntry...\n");
    if(hasCurrent(table) == 1) {
        if(table->current->next == NULL){
	  int key = hash(table, table->current->name)+1;
          if(key == table->size){return 0;}
	  for(; key<table->size; key++){
            if(table->contents[key] !=NULL){
	      table->current = table->contents[key];
	      return 1; 
	    }
	  }	
        }else{
	  table->current = table->current->next;
	  return 1;
	}
    }
    return 0;
}
