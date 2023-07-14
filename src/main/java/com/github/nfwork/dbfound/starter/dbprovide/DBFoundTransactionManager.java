package com.github.nfwork.dbfound.starter.dbprovide;

import com.github.nfwork.dbfound.starter.ModelExecutor;
import com.nfwork.dbfound.core.Context;
import com.nfwork.dbfound.core.Transaction;
import org.springframework.lang.Nullable;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class DBFoundTransactionManager extends AbstractPlatformTransactionManager {

    private final Isolation transactionIsolation ;

    public DBFoundTransactionManager( ModelExecutor modelExecutor, Isolation transactionIsolation){
        this.transactionIsolation = transactionIsolation;
        modelExecutor.setDbFoundTransactionManager(this);
    }

    @Override
    protected Object doGetTransaction() throws TransactionException {
        TransactionHolder transactionHolder = new TransactionHolder();
        Transaction transaction = (Transaction) TransactionSynchronizationManager.getResource(this);
        if(transaction != null) {
            transactionHolder.transaction = transaction;
            transactionHolder.isNew = false;
        }
        return transactionHolder;
    }

    @Override
    protected void doBegin(Object o, TransactionDefinition definition) throws TransactionException {
        TransactionHolder transactionHolder = (TransactionHolder) o;

        if(transactionHolder.transaction == null){
            transactionHolder.transaction = new Transaction();
            transactionHolder.isNew = true;
        }
        if(transactionHolder.isNew){
            transactionHolder.isNew = false;
            TransactionSynchronizationManager.bindResource(this,transactionHolder.transaction);
        }
        if (definition.getIsolationLevel() != TransactionDefinition.ISOLATION_DEFAULT) {
            transactionHolder.transaction.setTransactionIsolation(definition.getIsolationLevel());
        } else if(transactionIsolation.value() != TransactionDefinition.ISOLATION_DEFAULT){
            transactionHolder.transaction.setTransactionIsolation(transactionIsolation.value());
        }
        transactionHolder.transaction.setReadOnly(definition.isReadOnly());
        transactionHolder.transaction.begin();
    }

    @Override
    protected void doCommit(DefaultTransactionStatus defaultTransactionStatus) throws TransactionException {
        TransactionHolder transactionHolder = (TransactionHolder)defaultTransactionStatus.getTransaction();
        transactionHolder.transaction.commit();
    }

    @Override
    protected void doRollback(DefaultTransactionStatus defaultTransactionStatus) throws TransactionException {
        TransactionHolder transactionHolder =  (TransactionHolder)defaultTransactionStatus.getTransaction();
        transactionHolder.transaction.rollback();
    }

    protected boolean isExistingTransaction(Object transaction) {
        TransactionHolder transactionHolder = (TransactionHolder)transaction;
        return !transactionHolder.isNew && transactionHolder.transaction != null;
    }

    protected Object doSuspend(Object transaction) {
        TransactionHolder transactionHolder = (TransactionHolder) transaction;
        transactionHolder.transaction = null;
        return TransactionSynchronizationManager.unbindResource(this);
    }

    protected void doResume(@Nullable Object obj, Object suspendedResources) {
        TransactionHolder transactionHolder = (TransactionHolder) obj;
        if(transactionHolder != null) {
            transactionHolder.transaction = (Transaction) suspendedResources;
        }
        try{
            TransactionSynchronizationManager.unbindResource(this);
        }catch (Exception ignore){}
        TransactionSynchronizationManager.bindResource(this, suspendedResources);
    }

    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
        TransactionHolder transactionHolder =  (TransactionHolder) transaction;
        transactionHolder.transaction.end();
        try{
            TransactionSynchronizationManager.unbindResource(this);
        }catch (Exception ignore){}
        super.doCleanupAfterCompletion(transaction);
    }

    public void registContext(Context context){
        Transaction transaction = (Transaction) TransactionSynchronizationManager.getResource(this);
        context.setTransaction(transaction);
    }

    public static class TransactionHolder {
        Transaction transaction;
        boolean isNew = true;
    }
}
