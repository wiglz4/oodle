/* This file was generated by SableCC (http://www.sablecc.org/). */

package cps450.oodle.analysis;

import java.util.*;
import cps450.oodle.node.*;

public class AnalysisAdapter implements Analysis
{
    private Hashtable<Node,Object> in;
    private Hashtable<Node,Object> out;

    @Override
    public Object getIn(Node node)
    {
        if(this.in == null)
        {
            return null;
        }

        return this.in.get(node);
    }

    @Override
    public void setIn(Node node, Object o)
    {
        if(this.in == null)
        {
            this.in = new Hashtable<Node,Object>(1);
        }

        if(o != null)
        {
            this.in.put(node, o);
        }
        else
        {
            this.in.remove(node);
        }
    }

    @Override
    public Object getOut(Node node)
    {
        if(this.out == null)
        {
            return null;
        }

        return this.out.get(node);
    }

    @Override
    public void setOut(Node node, Object o)
    {
        if(this.out == null)
        {
            this.out = new Hashtable<Node,Object>(1);
        }

        if(o != null)
        {
            this.out.put(node, o);
        }
        else
        {
            this.out.remove(node);
        }
    }

    @Override
    public void caseStart(Start node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAStart(AStart node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAClassDef(AClassDef node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAExtends(AExtends node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAVarDecl(AVarDecl node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAMethodDecl(AMethodDecl node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAArgumentDecl(AArgumentDecl node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAIntegerType(AIntegerType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAStringType(AStringType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseABooleanType(ABooleanType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAIdentifyType(AIdentifyType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseARecursiveType(ARecursiveType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAAssstmtStatement(AAssstmtStatement node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAIfstmtStatement(AIfstmtStatement node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALpstmtStatement(ALpstmtStatement node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAClstmtStatement(AClstmtStatement node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAOrExpression(AOrExpression node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAAndExpression(AAndExpression node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAGtExpression(AGtExpression node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAGteExpression(AGteExpression node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAEqExpression(AEqExpression node)
    {
        defaultCase(node);
    }

    @Override
    public void caseACatExpression(ACatExpression node)
    {
        defaultCase(node);
    }

    @Override
    public void caseASubExpression(ASubExpression node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAAddExpression(AAddExpression node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAMulExpression(AMulExpression node)
    {
        defaultCase(node);
    }

    @Override
    public void caseADivExpression(ADivExpression node)
    {
        defaultCase(node);
    }

    @Override
    public void caseANotExpression(ANotExpression node)
    {
        defaultCase(node);
    }

    @Override
    public void caseANegExpression(ANegExpression node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAPosExpression(APosExpression node)
    {
        defaultCase(node);
    }

    @Override
    public void caseACallxprExpression(ACallxprExpression node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAIdExpression(AIdExpression node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAStrlitExpression(AStrlitExpression node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAIntlitExpression(AIntlitExpression node)
    {
        defaultCase(node);
    }

    @Override
    public void caseATrExpression(ATrExpression node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFlExpression(AFlExpression node)
    {
        defaultCase(node);
    }

    @Override
    public void caseANullExpression(ANullExpression node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAMeExpression(AMeExpression node)
    {
        defaultCase(node);
    }

    @Override
    public void caseANewtypeExpression(ANewtypeExpression node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAParenExpression(AParenExpression node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTNewline(TNewline node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTComment(TComment node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTWhitespace(TWhitespace node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTContinuation(TContinuation node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTIntegerLiteral(TIntegerLiteral node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTStringLiteral(TStringLiteral node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTUnterminatedString(TUnterminatedString node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTIllegalString(TIllegalString node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTBoolean(TBoolean node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTBegin(TBegin node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTClasskey(TClasskey node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTElse(TElse node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTEnd(TEnd node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTFalse(TFalse node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTFrom(TFrom node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTIf(TIf node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTInherits(TInherits node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTInt(TInt node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTIs(TIs node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTLoop(TLoop node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTMe(TMe node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTNew(TNew node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTNot(TNot node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTNull(TNull node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTString(TString node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTThen(TThen node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTTrue(TTrue node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTWhile(TWhile node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTAnd(TAnd node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTOr(TOr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTConcatenate(TConcatenate node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTPlus(TPlus node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTMinus(TMinus node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTMultiply(TMultiply node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTDivide(TDivide node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTGreater(TGreater node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTGreaterEqual(TGreaterEqual node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTEquals(TEquals node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTAssignment(TAssignment node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTLParen(TLParen node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTRParen(TRParen node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTLBracket(TLBracket node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTRBracket(TRBracket node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTComma(TComma node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTSemicolon(TSemicolon node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTColon(TColon node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTDot(TDot node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTIdentifier(TIdentifier node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTUnknownCharacter(TUnknownCharacter node)
    {
        defaultCase(node);
    }

    @Override
    public void caseEOF(EOF node)
    {
        defaultCase(node);
    }

    @Override
    public void caseInvalidToken(InvalidToken node)
    {
        defaultCase(node);
    }

    public void defaultCase(@SuppressWarnings("unused") Node node)
    {
        // do nothing
    }
}
