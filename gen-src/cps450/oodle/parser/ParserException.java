/* This file was generated by SableCC (http://www.sablecc.org/). */

package cps450.oodle.parser;

import cps450.oodle.node.*;

@SuppressWarnings("serial")
public class ParserException extends Exception
{
    private Token token;

    public ParserException(@SuppressWarnings("hiding") Token token, String  message)
    {
        super(message);
        this.token = token;
    }

    public Token getToken()
    {
        return this.token;
    }
}
