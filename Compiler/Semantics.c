/* Semantics.c
   Support and semantic action routines.
   
*/

#include <strings.h>
#include <stdlib.h>

#include "CodeGen.h"
#include "Semantics.h"
#include "SymTab.h"
#include "IOMngr.h"

extern SymTab *table;

/* Semantics support routines */

struct ExprRes *  doIntLit(char * digits)  { 

   struct ExprRes *res;
  
  res = (struct ExprRes *) malloc(sizeof(struct ExprRes));
  res->Reg = AvailTmpReg();
  res->Instrs = GenInstr(NULL,"li",TmpRegName(res->Reg),digits,NULL);

  return res;
}


extern struct InstrSeq * doRead(char * id){
	struct InstrSeq *code;
	code = (struct InstrSeq *) malloc(sizeof(struct InstrSeq));
	
	
	AppendSeq(code, GenInstr(NULL, "li", "$v0", "5", NULL));
	AppendSeq(code, GenInstr(NULL, "syscall", NULL,NULL,NULL));
	AppendSeq(code, GenInstr(NULL, "sw", "$v0", id, NULL)); 
		
	
	return code;

}

struct ExprRes * doRval(char * name)  { 

   struct ExprRes *res;
  
   if (!findName(table, name)) {
		writeIndicator(getCurrentColumnNum());
		writeMessage("Undeclared variable");
   }
   res = (struct ExprRes *) malloc(sizeof(struct ExprRes));
  
  
  res->Reg = AvailTmpReg();
  res->Instrs = GenInstr(NULL,"lw",TmpRegName(res->Reg),name,NULL);

  return res;
}

struct ExprRes * doNeg(struct ExprRes * Res1){
  int reg; 
  reg = AvailTmpReg();
  AppendSeq(Res1->Instrs, GenInstr(NULL, "sub", TmpRegName(reg), "$zero", TmpRegName(Res1->Reg)));
  ReleaseTmpReg(Res1->Reg);
  Res1->Reg = reg;
  return Res1;


}

struct ExprRes * doSub(struct ExprRes * Res1, struct ExprRes * Res2){
   int reg; 
   reg = AvailTmpReg();
   AppendSeq(Res1->Instrs, Res2->Instrs);
   AppendSeq(Res1->Instrs, GenInstr(NULL,"sub", TmpRegName(reg), TmpRegName(Res1->Reg), TmpRegName(Res2->Reg)));
   ReleaseTmpReg(Res1->Reg);
   ReleaseTmpReg(Res2->Reg);
   Res1->Reg = reg;
   free(Res2);
   return Res1;

}

struct ExprRes * doAdd(struct ExprRes * Res1, struct ExprRes * Res2)  { 

   int reg;
   
  reg = AvailTmpReg();
  AppendSeq(Res1->Instrs,Res2->Instrs);
  AppendSeq(Res1->Instrs,GenInstr(NULL,"add",
                                       TmpRegName(reg),
                                       TmpRegName(Res1->Reg),
                                       TmpRegName(Res2->Reg)));
  ReleaseTmpReg(Res1->Reg);
  ReleaseTmpReg(Res2->Reg);
  Res1->Reg = reg;
  free(Res2);
  return Res1;
}

struct ExprRes * doDiv(struct ExprRes * Res1, struct ExprRes * Res2){
  int reg;
  reg=AvailTmpReg();
  AppendSeq(Res1->Instrs, Res2->Instrs);
  AppendSeq(Res1->Instrs, GenInstr(NULL,"div", TmpRegName(reg), TmpRegName(Res1->Reg), TmpRegName(Res2->Reg)));
  ReleaseTmpReg(Res1->Reg);
  ReleaseTmpReg(Res2->Reg);
  Res1->Reg = reg;
  free(Res2);
  return Res1;	

}

struct ExprRes* doMod(struct ExprRes * Res1, struct ExprRes * Res2){
 int reg; 
 reg = AvailTmpReg();
 AppendSeq(Res1->Instrs, Res2->Instrs);
 AppendSeq(Res1->Instrs, GenInstr(NULL, "div", TmpRegName(reg), TmpRegName(Res1->Reg), TmpRegName(Res2->Reg)));
 AppendSeq(Res1->Instrs, GenInstr(NULL, "mfhi", TmpRegName(reg),NULL,NULL));
 ReleaseTmpReg(Res1->Reg);
 ReleaseTmpReg(Res2->Reg);
 Res1->Reg = reg;
 free(Res2);
 return Res1;
}

struct ExprRes* doPow(struct ExprRes * Res1, struct ExprRes * Res2){
  int reg, regt; 
  char * Loop = GenLabel();
  char * L1 = GenLabel();
  char * L2 = GenLabel();
  char * Done = GenLabel();
  reg = AvailTmpReg();
  regt = AvailTmpReg(); 
  AppendSeq(Res1->Instrs, Res2->Instrs);
  AppendSeq(Res1->Instrs, GenInstr(NULL, "beq", TmpRegName(Res2->Reg), "$zero", L1));
  
  AppendSeq(Res1->Instrs, GenInstr(NULL, "add", TmpRegName(reg), "$zero", TmpRegName(Res1->Reg)));
  	
  AppendSeq(Res1->Instrs, GenInstr(NULL, "addi", TmpRegName(regt), "$zero", "1"));
  AppendSeq(Res1->Instrs, GenInstr(NULL, "beq", TmpRegName(Res2->Reg), TmpRegName(regt), L2));  
  AppendSeq(Res1->Instrs, GenInstr(Loop,"addi", TmpRegName(regt), TmpRegName(regt), "1"));
  AppendSeq(Res1->Instrs, GenInstr(NULL, "mul", TmpRegName(reg), TmpRegName(reg), TmpRegName(Res1->Reg)));
  AppendSeq(Res1->Instrs, GenInstr(NULL, "blt", TmpRegName(regt), TmpRegName(Res2->Reg), Loop));
  AppendSeq(Res1->Instrs, GenInstr(NULL, "j", Done,NULL,NULL));
  AppendSeq(Res1->Instrs, GenInstr(L1,"addi", TmpRegName(reg), "$zero", "1"));
  AppendSeq(Res1->Instrs, GenInstr(NULL, "j", Done, NULL, NULL));
  AppendSeq(Res1->Instrs, GenInstr(L2, "add", TmpRegName(reg), "$zero", TmpRegName(Res1->Reg)));
  AppendSeq(Res1->Instrs, GenInstr(Done,NULL,NULL,NULL,NULL));
  ReleaseTmpReg(Res1->Reg);
  ReleaseTmpReg(Res2->Reg);
  ReleaseTmpReg(regt);
  Res1->Reg = reg;
  ReleaseTmpReg(reg);  
  free(Res2);
  return Res1;
}

struct ExprRes *  doMult(struct ExprRes * Res1, struct ExprRes * Res2)  { 

   int reg;
   
  reg = AvailTmpReg();
  AppendSeq(Res1->Instrs,Res2->Instrs);
  AppendSeq(Res1->Instrs,GenInstr(NULL,"mul",
                                       TmpRegName(reg),
                                       TmpRegName(Res1->Reg),
                                       TmpRegName(Res2->Reg)));
  ReleaseTmpReg(Res1->Reg);
  ReleaseTmpReg(Res2->Reg);
  Res1->Reg = reg;
  free(Res2);
  return Res1;
}

struct InstrSeq * doPrint(struct ExprRes * Expr) { 

  struct InstrSeq *code;
    
  code = Expr->Instrs;
  
    AppendSeq(code,GenInstr(NULL,"li","$v0","1",NULL));
    AppendSeq(code,GenInstr(NULL,"move","$a0",TmpRegName(Expr->Reg),NULL));
    AppendSeq(code,GenInstr(NULL,"syscall",NULL,NULL,NULL));

 //   AppendSeq(code,GenInstr(NULL,"li","$v0","4",NULL));
  //  AppendSeq(code,GenInstr(NULL,"la","$a0","_nl",NULL));
 //  AppendSeq(code,GenInstr(NULL,"syscall",NULL,NULL,NULL));

    ReleaseTmpReg(Expr->Reg);
    free(Expr);

  return code;
}

struct InstrSeq * printStr(struct ExprRes * Res){
	struct InstrSeq *code;
	
	code = Res->Instrs;

	AppendSeq(code, GenInstr(NULL, "li", "$v0", "4", NULL));
	AppendSeq(code, GenInstr(NULL, "syscall", NULL, NULL, NULL));
	//AppendSeq(code, GenInstr(NULL, "li", "$v0", "4", NULL));
	//AppendSeq(code, GenInstr(NULL, "la", "$a0", "_nl", NULL));
	//AppendSeq(code, GenInstr(NULL, "syscall", NULL, NULL, NULL));
	
	ReleaseTmpReg(Res->Reg);
	free(Res);	

	return code;

}

char ** strArr;
int strArrSize = 0;
char ** labels;
int labelSize=0;
struct ExprRes * getStr(char * str){
	struct ExprRes * res;
	res = (struct ExprRes*) malloc(sizeof(struct ExprRes));
	char * L1 = GenLabel();
	labelSize++;
	labels = realloc(labels, labelSize * sizeof(*labels));
	labels[labelSize-1] = malloc(444 * sizeof(char*));
	strcpy(labels[labelSize-1], L1);
	
	res->Reg = "$a0";
	res->Instrs = GenInstr(NULL, "la", "$a0", L1, NULL);
	
	strArrSize++;
	strArr = realloc(strArr, strArrSize * sizeof(*strArr));
	strArr[strArrSize-1]=malloc(444*sizeof(char*));
	str++;
	str[strlen(str)-1] = 0;
	strcpy(strArr[strArrSize-1], str);
	return res;
}


struct InstrSeq * printLines(struct ExprRes * Expr){
  struct InstrSeq * code;
  code = Expr->Instrs;
  char * loop = GenLabel();
  char * done = GenLabel();
  int reg = AvailTmpReg();
  AppendSeq(code, GenInstr(NULL, "addi", TmpRegName(reg), TmpRegName(Expr->Reg), "0"));
  AppendSeq(code, GenInstr(loop,"beq", TmpRegName(reg), "$zero", done));
  AppendSeq(code, GenInstr(NULL, "li", "$v0","4",NULL));
  AppendSeq(code, GenInstr(NULL, "la", "$a0", "_nl",NULL));
  AppendSeq(code, GenInstr(NULL, "syscall", NULL, NULL, NULL));
  AppendSeq(code, GenInstr(NULL, "addi", TmpRegName(reg), TmpRegName(reg),"-1"));
  AppendSeq(code, GenInstr(NULL, "j", loop, NULL, NULL));
  AppendSeq(code, GenInstr(done, NULL, NULL, NULL, NULL));
  ReleaseTmpReg(reg);
  ReleaseTmpReg(Expr->Reg);
  free(Expr);
  return code;

}



struct InstrSeq * printSpaces(struct ExprRes * Expr){
  struct InstrSeq * code;
  code = Expr->Instrs;
  char * loop = GenLabel();
  char * done = GenLabel();
  int reg = AvailTmpReg();
  AppendSeq(code, GenInstr(NULL, "addi", TmpRegName(reg), TmpRegName(Expr->Reg), "0"));
  AppendSeq(code, GenInstr(loop,"beq", TmpRegName(reg), "$zero", done));
  AppendSeq(code, GenInstr(NULL, "li", "$v0","11",NULL));
  AppendSeq(code, GenInstr(NULL, "la", "$a0", "32",NULL));
  AppendSeq(code, GenInstr(NULL, "syscall", NULL, NULL, NULL));
  AppendSeq(code, GenInstr(NULL, "addi", TmpRegName(reg), TmpRegName(reg),"-1"));
  AppendSeq(code, GenInstr(NULL, "j", loop, NULL, NULL));
  AppendSeq(code, GenInstr(done, NULL, NULL, NULL, NULL));
  ReleaseTmpReg(reg);
  ReleaseTmpReg(Expr->Reg);
  free(Expr);
  return code;

}



struct InstrSeq * doAssign(char *name, struct ExprRes * Expr) { 

  struct InstrSeq *code;
  

   if (!findName(table, name)) {
		writeIndicator(getCurrentColumnNum());
		writeMessage("Undeclared variable");
   }

  code = Expr->Instrs;
  
  AppendSeq(code,GenInstr(NULL,"sw",TmpRegName(Expr->Reg), name,NULL));

  ReleaseTmpReg(Expr->Reg);
  free(Expr);
  
  return code;
}



extern struct ExprRes * doAND(struct ExprRes * Res1, struct ExprRes * Res2){
	struct ExprRes * Res;
	int reg = AvailTmpReg();
	AppendSeq(Res1-> Instrs, Res2->Instrs);
	Res = (struct ExprRes *) malloc(sizeof(struct ExprRes));
	AppendSeq(Res1-> Instrs, GenInstr(NULL, "and", TmpRegName(reg), TmpRegName(Res1->Reg), TmpRegName(Res2->Reg)));
	Res->Reg=reg;
	Res->Instrs = Res1->Instrs;
	ReleaseTmpReg(Res1->Reg);
	ReleaseTmpReg(Res2->Reg);
	free(Res1);
	free(Res2);
	return Res;
}


extern struct ExprRes * doOR(struct ExprRes * Res1, struct ExprRes * Res2){
	struct ExprRes * Res;
	int reg = AvailTmpReg();
	AppendSeq(Res1-> Instrs, Res2->Instrs);
	Res = (struct ExprRes *) malloc(sizeof(struct ExprRes));
	AppendSeq(Res1-> Instrs, GenInstr(NULL, "or", TmpRegName(reg), TmpRegName(Res1->Reg), TmpRegName(Res2->Reg)));
	Res->Reg=reg;
	Res->Instrs = Res1->Instrs;
	ReleaseTmpReg(Res1->Reg);
	ReleaseTmpReg(Res2->Reg);
	free(Res1);
	free(Res2);
	return Res;
}

extern struct ExprRes * doNOT(struct ExprRes * Res1){
	struct ExprRes * Res;
	int reg = AvailTmpReg();
	Res = (struct ExprRes *) malloc(sizeof(struct ExprRes));
	AppendSeq(Res1->Instrs, GenInstr(NULL, "seq", TmpRegName(reg),"$zero", TmpRegName(Res1->Reg)));
	Res->Reg = reg;
	Res->Instrs = Res1->Instrs;
	ReleaseTmpReg(Res1->Reg);
	free(Res1);
	return Res;

}

extern struct ExprRes * doLT(struct ExprRes * Res1, struct ExprRes * Res2){
	struct ExprRes * Res;
	int reg = AvailTmpReg();
	AppendSeq(Res1-> Instrs, Res2->Instrs);
	Res = (struct ExprRes *) malloc(sizeof(struct ExprRes));
	AppendSeq(Res1-> Instrs, GenInstr(NULL, "slt", TmpRegName(reg), TmpRegName(Res1->Reg), TmpRegName(Res2->Reg)));
	Res->Reg=reg;
	Res->Instrs = Res1->Instrs;
	ReleaseTmpReg(Res1->Reg);
	ReleaseTmpReg(Res2->Reg);
	free(Res1);
	free(Res2);
	return Res;
}

extern struct ExprRes * doGT(struct ExprRes * Res1, struct ExprRes * Res2){
	struct ExprRes * Res;
	int reg = AvailTmpReg();
	AppendSeq(Res1-> Instrs, Res2->Instrs);
	Res = (struct ExprRes *) malloc(sizeof(struct ExprRes));
	AppendSeq(Res1-> Instrs, GenInstr(NULL, "sgt", TmpRegName(reg), TmpRegName(Res1->Reg), TmpRegName(Res2->Reg)));
	Res->Reg=reg;
	Res->Instrs = Res1->Instrs;
	ReleaseTmpReg(Res1->Reg);
	ReleaseTmpReg(Res2->Reg);
	free(Res1);
	free(Res2);
	return Res;
}

extern struct ExprRes * doLTE(struct ExprRes * Res1, struct ExprRes * Res2){
	struct ExprRes * Res;
	int reg = AvailTmpReg();
	AppendSeq(Res1-> Instrs, Res2->Instrs);
	Res = (struct ExprRes *) malloc(sizeof(struct ExprRes));
	AppendSeq(Res1-> Instrs, GenInstr(NULL, "sle", TmpRegName(reg), TmpRegName(Res1->Reg), TmpRegName(Res2->Reg)));
	Res->Reg=reg;
	Res->Instrs = Res1->Instrs;
	ReleaseTmpReg(Res1->Reg);
	ReleaseTmpReg(Res2->Reg);
	free(Res1);
	free(Res2);
	return Res;
}

extern struct ExprRes * doGTE(struct ExprRes * Res1, struct ExprRes * Res2){
	struct ExprRes * Res;
	int reg = AvailTmpReg();
	AppendSeq(Res1-> Instrs, Res2->Instrs);
	Res = (struct ExprRes *) malloc(sizeof(struct ExprRes));
	AppendSeq(Res1-> Instrs, GenInstr(NULL, "sge", TmpRegName(reg), TmpRegName(Res1->Reg), TmpRegName(Res2->Reg)));
	Res->Reg=reg;
	Res->Instrs = Res1->Instrs;
	ReleaseTmpReg(Res1->Reg);
	ReleaseTmpReg(Res2->Reg);
	free(Res1);
	free(Res2);
	return Res;

}
extern struct ExprRes * doNEQ(struct ExprRes * Res1, struct ExprRes * Res2){
	struct ExprRes * Res;
	int reg = AvailTmpReg();
	AppendSeq(Res1-> Instrs, Res2->Instrs);
	Res = (struct ExprRes *) malloc(sizeof(struct ExprRes));
	AppendSeq(Res1-> Instrs, GenInstr(NULL, "sne", TmpRegName(reg), TmpRegName(Res1->Reg), TmpRegName(Res2->Reg)));
	Res->Reg=reg;
	Res->Instrs = Res1->Instrs;
	ReleaseTmpReg(Res1->Reg);
	ReleaseTmpReg(Res2->Reg);
	free(Res1);
	free(Res2);
	return Res;
}


extern struct ExprRes * doBExpr(struct ExprRes * Res1,  struct ExprRes * Res2) {
	struct ExprRes * bRes;
	int reg = AvailTmpReg();
	AppendSeq(Res1->Instrs, Res2->Instrs);
 	bRes = (struct ExprRes *) malloc(sizeof(struct ExprRes));
	
	AppendSeq(Res1->Instrs, GenInstr(NULL, "seq", TmpRegName(reg), TmpRegName(Res1->Reg),TmpRegName( Res2->Reg)));
	bRes->Reg = reg;
	bRes->Instrs = Res1->Instrs;
	ReleaseTmpReg(Res1->Reg);
  	ReleaseTmpReg(Res2->Reg);
	free(Res1);
	free(Res2);
	return bRes;
}


extern struct InstrSeq * doIf(struct ExprRes * bRes, struct InstrSeq * seq) {
	struct InstrSeq * seq2;
	char * label = GenLabel();
	AppendSeq(bRes->Instrs, GenInstr(NULL, "beq", "$zero",TmpRegName(bRes->Reg),label));
	seq2 = AppendSeq(bRes->Instrs, seq);
	AppendSeq(seq2, GenInstr(label, NULL, NULL, NULL, NULL));
	free(bRes);
	return seq2;
}

extern struct InstrSeq * doIfElse(struct ExprRes * Res, struct InstrSeq* seq, struct InstrSeq * elseSeq){
	struct InstrSeq * seq2;
	char * L1 = GenLabel();
        char * L2 = GenLabel();
	AppendSeq(Res->Instrs, GenInstr(NULL, "beq", "$zero",TmpRegName(Res->Reg),L1));
	seq2 = AppendSeq(Res->Instrs, seq);
	AppendSeq(seq2, GenInstr(NULL, "j", L2, NULL, NULL));
	AppendSeq(seq2, GenInstr(L1, NULL, NULL, NULL, NULL));
	AppendSeq(seq2, elseSeq);
	AppendSeq(seq2, GenInstr(L2,NULL,NULL,NULL,NULL));
	
	free(Res);
	return seq2;

}


extern struct InstrSeq * doWhile(struct ExprRes * Res, struct InstrSeq * seq) {
	struct InstrSeq * seq2;
//	char * label = GenLabel();
	char * loop = GenLabel();
	
	seq2 =AppendSeq(GenInstr(loop, NULL, NULL,NULL, NULL), doIf(Res,seq));
	//AppendSeq(Res->Instrs, seq);
	
        AppendSeq(seq2, GenInstr(NULL, "j", loop, NULL, NULL));
        //AppendSeq(seq2, GenInstr(label, NULL, NULL, NULL, NULL));
	
	return seq2;
}


/*

extern struct InstrSeq * doIf(struct ExprRes *res1, struct ExprRes *res2, struct InstrSeq * seq) {
	struct InstrSeq *seq2;
	char * label;
	label = GenLabel();
	AppendSeq(res1->Instrs, res2->Instrs);
	AppendSeq(res1->Instrs, GenInstr(NULL, "bne", TmpRegName(res1->Reg), TmpRegName(res2->Reg), label));
	seq2 = AppendSeq(res1->Instrs, seq);
	AppendSeq(seq2, GenInstr(label, NULL, NULL, NULL, NULL));
	ReleaseTmpReg(res1->Reg);
  	ReleaseTmpReg(res2->Reg);
	free(res1);
	free(res2);
	return seq2;
}

*/
void							 
Finish(struct InstrSeq *Code)
{ struct InstrSeq *code;
  //struct SymEntry *entry;
    int hasMore;
  struct Attr * attr;


  code = GenInstr(NULL,".text",NULL,NULL,NULL);
  //AppendSeq(code,GenInstr(NULL,".align","2",NULL,NULL));
  AppendSeq(code,GenInstr(NULL,".globl","main",NULL,NULL));
  AppendSeq(code, GenInstr("main",NULL,NULL,NULL,NULL));
  AppendSeq(code,Code);
  AppendSeq(code, GenInstr(NULL, "li", "$v0", "10", NULL)); 
  AppendSeq(code, GenInstr(NULL,"syscall",NULL,NULL,NULL));
  AppendSeq(code,GenInstr(NULL,".data",NULL,NULL,NULL));
  AppendSeq(code,GenInstr(NULL,".align","4",NULL,NULL));
  AppendSeq(code,GenInstr("_nl",".asciiz","\"\\n\"",NULL,NULL));
	
  for(int i = 0; i<strArrSize; i++){
	char ascii[420];
	snprintf(ascii, sizeof(ascii), "\"%s\"", strArr[i]);
	AppendSeq(code, GenInstr(labels[i], ".asciiz", ascii, NULL, NULL));
  }
  

 
 hasMore = startIterator(table);
 while (hasMore) {
	AppendSeq(code,GenInstr((char *) getCurrentName(table),".word","0",NULL,NULL));
    hasMore = nextEntry(table);
 }
  
  WriteSeq(code);
  
  return;
}



