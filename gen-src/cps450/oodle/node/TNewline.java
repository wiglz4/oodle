/* This file was generated by SableCC (http://www.sablecc.org/). */

package cps450.oodle.node;

import cps450.oodle.analysis.*;

@SuppressWarnings("nls")
public final class TNewline extends Token
{
    public TNewline(String text)
    {
        setText(text);
    }

    public TNewline(String text, int line, int pos)
    {
        setText(text);
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TNewline(getText(), getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTNewline(this);
    }
}
