/* This file was generated by SableCC (http://www.sablecc.org/). */

package cps450.oodle.analysis;

import cps450.oodle.node.*;

public interface Analysis extends Switch
{
    Object getIn(Node node);
    void setIn(Node node, Object o);
    Object getOut(Node node);
    void setOut(Node node, Object o);

    void caseStart(Start node);
    void caseAStart(AStart node);
    void caseAClassDef(AClassDef node);
    void caseAExtends(AExtends node);
    void caseAVarDecl(AVarDecl node);
    void caseAMethodDecl(AMethodDecl node);
    void caseAArgumentDecl(AArgumentDecl node);
    void caseAIntegerType(AIntegerType node);
    void caseAStringType(AStringType node);
    void caseABooleanType(ABooleanType node);
    void caseAIdentifyType(AIdentifyType node);
    void caseARecursiveType(ARecursiveType node);
    void caseAAssstmtStatement(AAssstmtStatement node);
    void caseAIfstmtStatement(AIfstmtStatement node);
    void caseALpstmtStatement(ALpstmtStatement node);
    void caseAClstmtStatement(AClstmtStatement node);
    void caseAOrExpression(AOrExpression node);
    void caseAAndExpression(AAndExpression node);
    void caseAGtExpression(AGtExpression node);
    void caseAGteExpression(AGteExpression node);
    void caseAEqExpression(AEqExpression node);
    void caseACatExpression(ACatExpression node);
    void caseASubExpression(ASubExpression node);
    void caseAAddExpression(AAddExpression node);
    void caseAMulExpression(AMulExpression node);
    void caseADivExpression(ADivExpression node);
    void caseANotExpression(ANotExpression node);
    void caseANegExpression(ANegExpression node);
    void caseAPosExpression(APosExpression node);
    void caseACallxprExpression(ACallxprExpression node);
    void caseAIdExpression(AIdExpression node);
    void caseAStrlitExpression(AStrlitExpression node);
    void caseAIntlitExpression(AIntlitExpression node);
    void caseATrExpression(ATrExpression node);
    void caseAFlExpression(AFlExpression node);
    void caseANullExpression(ANullExpression node);
    void caseAMeExpression(AMeExpression node);
    void caseANewtypeExpression(ANewtypeExpression node);
    void caseAParenExpression(AParenExpression node);

    void caseTNewline(TNewline node);
    void caseTComment(TComment node);
    void caseTWhitespace(TWhitespace node);
    void caseTContinuation(TContinuation node);
    void caseTIntegerLiteral(TIntegerLiteral node);
    void caseTStringLiteral(TStringLiteral node);
    void caseTUnterminatedString(TUnterminatedString node);
    void caseTIllegalString(TIllegalString node);
    void caseTBoolean(TBoolean node);
    void caseTBegin(TBegin node);
    void caseTClasskey(TClasskey node);
    void caseTElse(TElse node);
    void caseTEnd(TEnd node);
    void caseTFalse(TFalse node);
    void caseTFrom(TFrom node);
    void caseTIf(TIf node);
    void caseTInherits(TInherits node);
    void caseTInt(TInt node);
    void caseTIs(TIs node);
    void caseTLoop(TLoop node);
    void caseTMe(TMe node);
    void caseTNew(TNew node);
    void caseTNot(TNot node);
    void caseTNull(TNull node);
    void caseTString(TString node);
    void caseTThen(TThen node);
    void caseTTrue(TTrue node);
    void caseTWhile(TWhile node);
    void caseTAnd(TAnd node);
    void caseTOr(TOr node);
    void caseTConcatenate(TConcatenate node);
    void caseTPlus(TPlus node);
    void caseTMinus(TMinus node);
    void caseTMultiply(TMultiply node);
    void caseTDivide(TDivide node);
    void caseTGreater(TGreater node);
    void caseTGreaterEqual(TGreaterEqual node);
    void caseTEquals(TEquals node);
    void caseTAssignment(TAssignment node);
    void caseTLParen(TLParen node);
    void caseTRParen(TRParen node);
    void caseTLBracket(TLBracket node);
    void caseTRBracket(TRBracket node);
    void caseTComma(TComma node);
    void caseTSemicolon(TSemicolon node);
    void caseTColon(TColon node);
    void caseTDot(TDot node);
    void caseTIdentifier(TIdentifier node);
    void caseTUnknownCharacter(TUnknownCharacter node);
    void caseEOF(EOF node);
    void caseInvalidToken(InvalidToken node);
}