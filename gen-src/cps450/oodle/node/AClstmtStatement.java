/* This file was generated by SableCC (http://www.sablecc.org/). */

package cps450.oodle.node;

import java.util.*;
import cps450.oodle.analysis.*;

@SuppressWarnings("nls")
public final class AClstmtStatement extends PStatement
{
    private PExpression _lhs_;
    private TIdentifier _identifier_;
    private final LinkedList<PExpression> _rhs_ = new LinkedList<PExpression>();

    public AClstmtStatement()
    {
        // Constructor
    }

    public AClstmtStatement(
        @SuppressWarnings("hiding") PExpression _lhs_,
        @SuppressWarnings("hiding") TIdentifier _identifier_,
        @SuppressWarnings("hiding") List<?> _rhs_)
    {
        // Constructor
        setLhs(_lhs_);

        setIdentifier(_identifier_);

        setRhs(_rhs_);

    }

    @Override
    public Object clone()
    {
        return new AClstmtStatement(
            cloneNode(this._lhs_),
            cloneNode(this._identifier_),
            cloneList(this._rhs_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAClstmtStatement(this);
    }

    public PExpression getLhs()
    {
        return this._lhs_;
    }

    public void setLhs(PExpression node)
    {
        if(this._lhs_ != null)
        {
            this._lhs_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._lhs_ = node;
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

    public LinkedList<PExpression> getRhs()
    {
        return this._rhs_;
    }

    public void setRhs(List<?> list)
    {
        for(PExpression e : this._rhs_)
        {
            e.parent(null);
        }
        this._rhs_.clear();

        for(Object obj_e : list)
        {
            PExpression e = (PExpression) obj_e;
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
            this._rhs_.add(e);
        }
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._lhs_)
            + toString(this._identifier_)
            + toString(this._rhs_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._lhs_ == child)
        {
            this._lhs_ = null;
            return;
        }

        if(this._identifier_ == child)
        {
            this._identifier_ = null;
            return;
        }

        if(this._rhs_.remove(child))
        {
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._lhs_ == oldChild)
        {
            setLhs((PExpression) newChild);
            return;
        }

        if(this._identifier_ == oldChild)
        {
            setIdentifier((TIdentifier) newChild);
            return;
        }

        for(ListIterator<PExpression> i = this._rhs_.listIterator(); i.hasNext();)
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

        throw new RuntimeException("Not a child.");
    }
}
