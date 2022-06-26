package com.github.nfwork.dbfound.starter.dbprovide;

import com.nfwork.dbfound.core.Context;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;

public class DBFoundTransactionManager extends AbstractPlatformTransactionManager {

    private final ThreadLocal<TransactionObject> threadLocal = new ThreadLocal<>();

    @Override
    protected Object doGetTransaction() throws TransactionException {
        TransactionObject transactionObject =  threadLocal.get();
        if(transactionObject == null){
            transactionObject = new TransactionObject();
            threadLocal.set(transactionObject);
        }
        return transactionObject;
    }

    @Override
    protected void doBegin(Object o, TransactionDefinition transactionDefinition) throws TransactionException {
        TransactionObject transactionObject = (TransactionObject) o;
        if(transactionObject.getContext() != null){
            transactionObject.getContext().getTransaction().begin();
        }
    }

    @Override
    protected void doCommit(DefaultTransactionStatus defaultTransactionStatus) throws TransactionException {
        TransactionObject transactionObject =  threadLocal.get();
        if(transactionObject.getContext() != null){
            transactionObject.getContext().getTransaction().commit();
        }
    }

    @Override
    protected void doRollback(DefaultTransactionStatus defaultTransactionStatus) throws TransactionException {
        TransactionObject transactionObject =  threadLocal.get();
        if(transactionObject.getContext() != null){
            transactionObject.getContext().getTransaction().rollback();
        }
    }


    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
        TransactionObject transactionObject =  threadLocal.get();
        if(transactionObject.getContext() != null){
            transactionObject.getContext().getTransaction().end();
            threadLocal.remove();
        }
        super.doCleanupAfterCompletion(transaction);
    }

    public void begin(Context context){
        TransactionObject transactionObject =  threadLocal.get();
        if(transactionObject != null){
            context.getTransaction().begin();
            transactionObject.setContext(context);
        }
    }

    public static class  TransactionObject{
        Context context;

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }
    }
}
