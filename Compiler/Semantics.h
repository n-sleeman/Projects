/* Semantics.h
   The action and supporting routines for performing semantics processing.
*/

/* Semantic Records */
struct IdList {
  struct SymEntry * TheEntry;
  struct IdList * Next;
};

struct ExprRes {
  int Reg;
  struct InstrSeq * Instrs;
};

struct ExprResList {
	struct ExprRes *Expr;
	struct ExprResList * Next;
};

struct BExprRes {
  char * Label;
  struct InstrSeq * Instrs;
};


/* Semantics Actions */
extern struct ExprRes *  doIntLit(char * digits);
extern struct ExprRes *  doRval(char * name);
extern struct InstrSeq *  doAssign(char * name,  struct ExprRes * Res1);
extern struct ExprRes *  doAdd(struct ExprRes * Res1,  struct ExprRes * Res2);
extern struct ExprRes * doSub(struct ExprRes * Res1, struct ExprRes * Res2);
extern struct ExprRes * doMod(struct ExprRes *Res1, struct ExprRes * Res2);
extern struct ExprRes * doNeg(struct ExprRes *Res1);
extern struct ExprRes * doDiv(struct ExprRes * Res1, struct ExprRes * Res2);
extern struct ExprRes *  doMult(struct ExprRes * Res1,  struct ExprRes * Res2);
extern struct InstrSeq *  doPrint(struct ExprRes * Expr);
extern struct ExprRes * doAND(struct ExprRes * Res1, struct ExprRes * Res2);
extern struct ExprRes * doOR(struct ExprRes * Res1, struct ExprRes * Res2);
extern struct ExprRes * doNOT(struct ExprRes * Res); 
extern struct ExprRes * doBExpr (struct ExprRes * Res1,  struct ExprRes * Res2);
extern struct ExprRes * doNEQ(struct ExprRes* Res1, struct ExprRes * Res2);
extern struct ExprRes * doLT(struct ExprRes* Res1, struct ExprRes * Res2);
extern struct ExprRes * doGT(struct ExprRes* Res1, struct ExprRes * Res2);
extern struct ExprRes * doGTE(struct ExprRes* Res1, struct ExprRes * Res2);
extern struct ExprRes * doLTE(struct ExprRes* Res1, struct ExprRes * Res2);
extern struct InstrSeq * doIf(struct ExprRes *bRes, struct InstrSeq * seq);
extern struct ExprRes * doPow(struct ExprRes * Res1, struct ExprRes * Res2);
extern struct InstrSeq * printLines(struct ExprRes * Expr);
extern struct InstrSeq * printSpaces(struct ExprRes * Expr);
extern struct InstrSeq * doWhile(struct ExprRes* Res, struct InstrSeq * seq);
extern struct InstrSeq * printStr(struct ExprRes* Res);
extern struct InstrSeq * doRead(char * id);
extern struct InstrSeq * doIfElse(struct ExprRes* Res, struct InstrSeq* seq, struct InstrSeq* elseSeq);
extern struct ExprRes * getStr(char * str);

extern void	Finish(struct InstrSeq *Code);
