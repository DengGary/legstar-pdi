


package com.legstar.test.coxb.tcobwvb.bind;

import com.legstar.coxb.ICobolBinding;
import com.legstar.coxb.common.CComplexBinding;
import com.legstar.coxb.ICobolBinaryBinding;
import com.legstar.coxb.CobolBindingFactory;
import com.legstar.coxb.ICobolBindingFactory;
import com.legstar.coxb.ICobolArrayComplexBinding;
import com.legstar.test.coxb.tcobwvb.Transaction;
import java.util.List;
import com.legstar.coxb.ICobolComplexBinding;
import com.legstar.coxb.host.HostException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.legstar.test.coxb.tcobwvb.Transactions;
import com.legstar.test.coxb.tcobwvb.ObjectFactory;

/**
 * LegStar Binding for Complex element :
 *   Transactions.
 * 
 * This class was generated by LegStar Binding generator.
 */
public class TransactionsBinding 
             extends CComplexBinding {

    /** Value object to which this cobol complex element is bound. */
    private Transactions mValueObject;
  
    /** Indicates that the associated Value object just came from the constructor
     * and doesn't need to be recreated. */
    private boolean mUnusedValueObject = false;
    
    /** Maximum host bytes size for this complex object. */
    private static final int BYTE_LENGTH = 129;
    
    /** Child bound to value object property TransactionNbr(Long). */
    public ICobolBinaryBinding _transactionNbr;
    /** Child bound to value object property Transaction(Transaction). */
    public ICobolArrayComplexBinding _transactionWrapper;
    /** Binding item for complex array binding Transaction. */
    public ICobolComplexBinding _transactionWrapperItem;
            
    /** Logger. */
    private final Log _log = LogFactory.getLog(getClass());

    /** Binding factory. */
    private static final ICobolBindingFactory BF
        = CobolBindingFactory.getBindingFactory();
 
    /** Static reference to Value object factory to be used as default. */
    private static final ObjectFactory JF = new ObjectFactory();
    
    /** Current Value object factory (Defaults to the static one but can be
     *  changed). */
    private ObjectFactory mValueObjectFactory = JF;
    
    /**
     * Constructor for a root Complex element without a bound Value object.
     */
    public TransactionsBinding() {
        this(null);
    }

    /**
     * Constructor for a root Complex element with a bound Value object.
     * 
     * @param valueObject the concrete Value object instance bound to this
     *        complex element
     */
    public TransactionsBinding(
            final Transactions valueObject) {
        this("", "", null, valueObject);
    }

    /**
    * Constructor for a Complex element as a child of another element and
    * an associated Value object.
    * 
    * @param bindingName the identifier for this binding
    * @param fieldName field name in parent Value object
    * @param valueObject the concrete Value object instance bound to this
    *        complex element
    * @param parentBinding a reference to the parent binding
    */
    public TransactionsBinding(
            final String bindingName,
            final String fieldName,
            final ICobolComplexBinding parentBinding,
            final Transactions valueObject) {
        
        super(bindingName, fieldName, Transactions.class, null, parentBinding);
        mValueObject = valueObject;
        if (mValueObject != null) {
            mUnusedValueObject = true;
        }
        initChildren();
        setByteLength(BYTE_LENGTH);
    }

    /** Creates a binding property for each child. */
    private void initChildren() {
        if (_log.isDebugEnabled()) {
            _log.debug("Initializing started");
        }
        /* Create binding children instances */

        _transactionNbr = BF.createBinaryBinding("TransactionNbr",
               "TransactionNbr", Long.class, this);
        _transactionNbr.setCobolName("TRANSACTION-NBR");
        _transactionNbr.setByteLength(4);
        _transactionNbr.setTotalDigits(9);
        _transactionNbr.setIsODOObject(true);
        _transactionWrapperItem = new TransactionBinding("TransactionWrapperItem",
               "Transaction", this, null);
        _transactionWrapper = new TransactionWrapperBinding("TransactionWrapper",
               "Transaction", this, _transactionWrapperItem);
        _transactionWrapper.setCobolName("TRANSACTION");
        _transactionWrapper.setByteLength(125);
        _transactionWrapper.setItemByteLength(25);
        _transactionWrapper.setMaxOccurs(5);
        _transactionWrapper.setDependingOn("TRANSACTION-NBR");

        /* Add children to children list */
        getChildrenList().add(_transactionNbr);
        getChildrenList().add(_transactionWrapper);
 
        if (_log.isDebugEnabled()) {
            _log.debug("Initializing successful");
        }
    }
    
    /** {@inheritDoc} */
    public void createValueObject() throws HostException {
        /* Since this complex binding has a constructor that takes a
         * Value object, we might already have a Value object that
         * was not used yet. */
        if (mUnusedValueObject && mValueObject != null) {
            mUnusedValueObject = false;
            return;
        }
        mValueObject = mValueObjectFactory.createTransactions();
    }

    /** {@inheritDoc} */
    public void setChildrenValues() throws HostException {

         /* Make sure there is an associated Value object*/
        if (mValueObject == null) {
            createValueObject();
        }
        /* Get Value object property _transactionNbr */
        if (_log.isDebugEnabled()) {
            _log.debug("Getting value from Value object property "
                    + "_transactionNbr"
                    + " value=" + mValueObject.getTransactionNbr());
        }
        _transactionNbr.setObjectValue(mValueObject.getTransactionNbr());
        /* Get Value object property _transactionWrapper */
        if (_log.isDebugEnabled()) {
            _log.debug("Getting value from Value object property "
                    + "_transactionWrapper"
                    + " value=" + mValueObject.getTransaction());
        }
        _transactionWrapper.setObjectValue(mValueObject.getTransaction());
        /* For variable size array or list, we make sure any
         * associated counter is updated */
        setCounterValue(_transactionWrapper.getDependingOn(),
                ((List < ? >) mValueObject.getTransaction()).size());
     }

    /** {@inheritDoc} */
    public void setPropertyValue(final int index) throws HostException {
 
        ICobolBinding child = getChildrenList().get(index);
        
       /* Children that are not bound to a value object are ignored.
        * This includes Choices and dynamically generated counters
        * for instance.  */
        if (!child.isBound()) {
            return;
        }
        
        /* Set the Value object property value from binding object */
        Object bindingValue = null;
        switch (index) {
        case 0:
            bindingValue = child.getObjectValue(Long.class);
            mValueObject.setTransactionNbr((Long) bindingValue);
            break;
        case 1:
            bindingValue = child.getObjectValue(Transaction.class);
            List < Transaction > listTransactionWrapper = cast(bindingValue);
            mValueObject.getTransaction().clear();
            mValueObject.getTransaction().addAll(listTransactionWrapper);
            break;
         default:
            break;
        }
        if (_log.isDebugEnabled()) {
            _log.debug("Setting value of Value object property "
                    + child.getJaxbName()
                    + " value=" + bindingValue);
        }
    }

    /** {@inheritDoc} */
    public Object getObjectValue(
            final Class < ? > type) throws HostException {
        if (type.equals(Transactions.class)) {
            return mValueObject;
        } else {
            throw new HostException("Attempt to get binding " + getBindingName()
                    + " as an incompatible type " + type);
        }
    }

    /** {@inheritDoc} */
    public void setObjectValue(
            final Object bindingValue) throws HostException {
        if (bindingValue == null) {
            mValueObject = null;
            return;
        }
        if (bindingValue.getClass().equals(Transactions.class)) {
            mValueObject = (Transactions) bindingValue;
        } else {
            throw new HostException("Attempt to set binding " + getBindingName()
                    + " from an incompatible value " + bindingValue);
        }
    }

    /**
     * @return the java object factory for objects creation
     */
    public ObjectFactory getObjectFactory() {
        return mValueObjectFactory;
    }

    /**
     * @param valueObjectFactory the java object factory for objects creation 
     */
    public void setObjectFactory(final Object valueObjectFactory) {
        mValueObjectFactory = (ObjectFactory) valueObjectFactory;
    }

    /** {@inheritDoc} */
    public boolean isSet() {
        return (mValueObject != null);
    }

    /**
     * @return the bound Value object
     */
    public Transactions getTransactions() {
        return mValueObject;
    }
    
}

