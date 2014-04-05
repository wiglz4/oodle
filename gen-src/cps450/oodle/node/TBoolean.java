/* This file was generated by SableCC (http://www.sablecc.org/). */

package cps450.oodle.node;

import cps450.oodle.analysis.*;

@SuppressWarnings("nls")
public final class TBoolean extends Token
{
    public TBoolean()
    {
        super.setText("boolean");
    }

    public TBoolean(int line, int pos)
    {
        super.setText("boolean");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TBoolean(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTBoolean(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TBoolean text.");
    }
}
