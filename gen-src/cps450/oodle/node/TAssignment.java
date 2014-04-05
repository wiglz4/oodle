/* This file was generated by SableCC (http://www.sablecc.org/). */

package cps450.oodle.node;

import cps450.oodle.analysis.*;

@SuppressWarnings("nls")
public final class TAssignment extends Token
{
    public TAssignment()
    {
        super.setText(":=");
    }

    public TAssignment(int line, int pos)
    {
        super.setText(":=");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TAssignment(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTAssignment(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TAssignment text.");
    }
}