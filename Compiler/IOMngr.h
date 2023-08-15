//
// Created by noahs on 3/28/2022.
//
#define MAXLINE 1024
int openFiles(char * sourceName, char * listingName);
void closeFiles();
char getNextSourceChar();
void writeIndicator(int column);
void writeMessage(char * message);
int getCurrentLineNum();
int getCurrentColumnNum();