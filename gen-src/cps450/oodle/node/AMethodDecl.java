/* This file was generated by SableCC (http://www.sablecc.org/). */

package cps450.oodle.node;

import java.util.*;
import cps450.oodle.analysis.*;

@SuppressWarnings("nls")
public final class AMethodDecl extends PMethodDecl
{
    private TIdentifier _firstId_;
    private final LinkedList<PArgumentDecl> _argumentDecl_ = new LinkedList<PArgumentDecl>();
    private PType _type_;
    private final LinkedList<PVarDecl> _varDecl_ = new LinkedList<PVarDecl>();
    private final LinkedList<PStatement> _statement_ = new LinkedList<PStatement>();
    private TIdentifier _secondId_;

    public AMethodDecl()
    {
        // Constructor
    }

    public AMethodDecl(
        @SuppressWarnings("hiding") TIdentifier _firstId_,
        @SuppressWarnings("hiding") List<?> _argumentDecl_,
        @SuppressWarnings("hiding") PType _type_,
        @SuppressWarnings("hiding") List<?> _varDecl_,
        @SuppressWarnings("hiding") List<?> _statement_,
        @SuppressWarnings("hiding") TIdentifier _secondId_)
    {
        // Constructor
        setFirstId(_firstId_);

        setArgumentDecl(_argumentDecl_);

        setType(_type_);

        setVarDecl(_varDecl_);

        setStatement(_statement_);

        setSecondId(_secondId_);

    }

    @Override
    public Object clone()
    {
        return new AMethodDecl(
            cloneNode(this._firstId_),
            cloneList(this._argumentDecl_),
            cloneNode(this._type_),
            cloneList(this._varDecl_),
            cloneList(this._statement_),
            cloneNode(this._secondId_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAMethodDecl(this);
    }

    public TIdentifier getFirstId()
    {
        return this._firstId_;
    }

    public void setFirstId(TIdentifier node)
    {
        if(this._firstId_ != null)
        {
            this._firstId_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._firstId_ = node;
    }

    public LinkedList<PArgumentDecl> getArgumentDecl()
    {
        return this._argumentDecl_;
    }

    public void setArgumentDecl(List<?> list)
    {
        for(PArgumentDecl e : this._argumentDecl_)
        {
            e.parent(null);
        }
        this._argumentDecl_.clear();

        for(Object obj_e : list)
        {
            PArgumentDecl e = (PArgumentDecl) obj_e;
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
            this._argumentDecl_.add(e);
        }
    }

    public PType getType()
    {
        return this._type_;
    }

    public void setType(PType node)
    {
        if(this._type_ != null)
        {
            this._type_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._type_ = node;
    }

    public LinkedList<PVarDecl> getVarDecl()
    {
        return this._varDecl_;
    }

    public void setVarDecl(List<?> list)
    {
        for(PVarDecl e : this._varDecl_)
        {
            e.parent(null);
        }
        this._varDecl_.clear();

        for(Object obj_e : list)
        {
            PVarDecl e = (PVarDecl) obj_e;
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
            this._varDecl_.add(e);
        }
    }

    public LinkedList<PStatement> getStatement()
    {
        return this._statement_;
    }

    public void setStatement(List<?> list)
    {
        for(PStatement e : this._statement_)
        {
            e.parent(null);
        }
        this._statement_.clear();

        for(Object obj_e : list)
        {
            PStatement e = (PStatement) obj_e;
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
            this._statement_.add(e);
        }
    }

    public TIdentifier getSecondId()
    {
        return this._secondId_;
    }

    public void setSecondId(TIdentifier node)
    {
        if(this._secondId_ != null)
        {
            this._secondId_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._secondId_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._firstId_)
            + toString(this._argumentDecl_)
            + toString(this._type_)
            + toString(this._varDecl_)
            + toString(this._statement_)
            + toString(this._secondId_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._firstId_ == child)
        {
            this._firstId_ = null;
            return;
        }

        if(this._argumentDecl_.remove(child))
        {
            return;
        }

        if(this._type_ == child)
        {
            this._type_ = null;
            return;
        }

        if(this._varDecl_.remove(child))
        {
            return;
        }

        if(this._statement_.remove(child))
        {
            return;
        }

        if(this._secondId_ == child)
        {
            this._secondId_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._firstId_ == oldChild)
        {
            setFirstId((TIdentifier) newChild);
            return;
        }

        for(ListIterator<PArgumentDecl> i = this._argumentDecl_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PArgumentDecl) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        if(this._type_ == oldChild)
        {
            setType((PType) newChild);
            return;
        }

        for(ListIterator<PVarDecl> i = this._varDecl_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PVarDecl) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        for(ListIterator<PStatement> i = this._statement_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PStatement) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        if(this._secondId_ == oldChild)
        {
            setSecondId((TIdentifier) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
