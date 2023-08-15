//
// Created by noahs on 3/28/2022.
//
typedef struct SymEntry{
    char * name;
    void * attribute;
    struct SymEntry * next;
}SymEntry;

typedef struct SymTab {
    int size;
    SymEntry **contents;
    SymEntry *current;
}SymTab;

SymTab * createSymTab(int size);
void destroySymTab(SymTab *table);
unsigned hash(SymTab *table, char *str);
int findName(SymTab * table, char *name);
int enterName(SymTab *table, char *name);
int hasCurrent(SymTab*table);
void setCurrentAttr(SymTab *table, void * attr);
void * getCurrentAttr(SymTab *table);
char * getCurrentName(SymTab *table);
int startIterator(SymTab *table);
int nextEntry(SymTab *table);
