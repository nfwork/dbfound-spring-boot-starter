package com.github.nfwork.dbfound.starter.dbprovide;

import com.github.nfwork.dbfound.starter.ModelExecutor;
import com.nfwork.dbfound.core.Context;
import com.nfwork.dbfound.core.Transaction;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;


public class DBFoundTransactionManager extends AbstractPlatformTransactionManager {

    private final ThreadLocal<TransactionObject> threadLocal = new ThreadLocal<>();

    public DBFoundTransactionManager( ModelExecutor modelExecutor){
        modelExecutor.setDbFoundTransactionManager(this);
    }

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
        transactionObject.transaction.begin();
    }

    @Override
    protected void doCommit(DefaultTransactionStatus defaultTransactionStatus) throws TransactionException {
        TransactionObject transactionObject = threadLocal.get();
        if(transactionObject!=null){
           transactionObject.transaction.commit();
        }
    }

    @Override
    protected void doRollback(DefaultTransactionStatus defaultTransactionStatus) throws TransactionException {
        TransactionObject transactionObject =  threadLocal.get();
        if(transactionObject!=null){
            transactionObject.transaction.rollback();
        }
    }

    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
        TransactionObject transactionObject =  threadLocal.get();
        if(transactionObject!=null){
            transactionObject.transaction.end();
            threadLocal.remove();
        }
        super.doCleanupAfterCompletion(transaction);
    }

    public void registContext(Context context){
        TransactionObject transactionObject =  threadLocal.get();
        if(transactionObject != null){
            context.setTransaction(transactionObject.transaction);
        }
    }

    public static class  TransactionObject{
        Transaction transaction = new Transaction();
    }
}
