/* This file was generated by SableCC (http://www.sablecc.org/). */

package cps450.oodle.node;

import java.util.*;
import cps450.oodle.analysis.*;

@SuppressWarnings("nls")
public final class AAssstmtStatement extends PStatement
{
    private TIdentifier _identifier_;
    private final LinkedList<PExpression> _expression_ = new LinkedList<PExpression>();
    private PExpression _last_;

    public AAssstmtStatement()
    {
        // Constructor
    }

    public AAssstmtStatement(
        @SuppressWarnings("hiding") TIdentifier _identifier_,
        @SuppressWarnings("hiding") List<?> _expression_,
        @SuppressWarnings("hiding") PExpression _last_)
    {
        // Constructor
        setIdentifier(_identifier_);

        setExpression(_expression_);

        setLast(_last_);

    }

    @Override
    public Object clone()
    {
        return new AAssstmtStatement(
            cloneNode(this._identifier_),
            cloneList(this._expression_),
            cloneNode(this._last_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAAssstmtStatement(this);
    }

    public TIdentifier getIdentifier()
    {
        return this._identifier_;
    }

    public void setIdentifier(TIdentifier node)
    {
        if(this._identifier_ != null)
        {
            this._identifier_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._identifier_ = node;
    }

    public LinkedList<PExpression> getExpression()
    {
        return this._expression_;
    }

    public void setExpression(List<?> list)
    {
        for(PExpression e : this._expression_)
        {
            e.parent(null);
        }
        this._expression_.clear();

        for(Object obj_e : list)
        {
            PExpression e = (PExpression) obj_e;
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
            this._expression_.add(e);
        }
    }

    public PExpression getLast()
    {
        return this._last_;
    }

    public void setLast(PExpression node)
    {
        if(this._last_ != null)
        {
            this._last_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._last_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._identifier_)
            + toString(this._expression_)
            + toString(this._last_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._identifier_ == child)
        {
            this._identifier_ = null;
            return;
        }

        if(this._expression_.remove(child))
        {
            return;
        }

        if(this._last_ == child)
        {
            this._last_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._identifier_ == oldChild)
        {
            setIdentifier((TIdentifier) newChild);
            return;
        }

        for(ListIterator<PExpression> i = this._expression_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PExpression) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        if(this._last_ == oldChild)
        {
            setLast((PExpression) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
