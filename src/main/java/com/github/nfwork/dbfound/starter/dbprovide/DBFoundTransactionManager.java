package com.github.nfwork.dbfound.starter.dbprovide;

import com.github.nfwork.dbfound.starter.ModelExecutor;
import com.nfwork.dbfound.core.Context;
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
        TransactionObject transactionObject = new TransactionObject();
        Transaction transaction = (Transaction) TransactionSynchronizationManager.getResource(this);
        if(transaction != null) {
            transactionObject.setTransaction(transaction,false);
        }
        return transactionObject;
    }

    @Override
    protected void doBegin(Object o, TransactionDefinition definition) throws TransactionException {
        TransactionObject transactionObject = (TransactionObject) o;

        if(transactionObject.getTransaction() == null){
            Transaction transaction = new Transaction();
            transactionObject.setTransaction(transaction, true);
        }

        if (definition.getIsolationLevel() != TransactionDefinition.ISOLATION_DEFAULT) {
            transactionObject.getTransaction().setTransactionIsolation(definition.getIsolationLevel());
        } else if(transactionIsolation.value() != TransactionDefinition.ISOLATION_DEFAULT){
            transactionObject.getTransaction().setTransactionIsolation(transactionIsolation.value());
        }
        transactionObject.getTransaction().setReadOnly(definition.isReadOnly());
        transactionObject.getTransaction().begin();

        if(transactionObject.isNew()){
            TransactionSynchronizationManager.bindResource(this,transactionObject.getTransaction());
        }
    }

    @Override
    protected void doCommit(DefaultTransactionStatus defaultTransactionStatus) throws TransactionException {
        TransactionObject transactionObject = (TransactionObject)defaultTransactionStatus.getTransaction();
        transactionObject.getTransaction().commit();
    }

    @Override
    protected void doRollback(DefaultTransactionStatus defaultTransactionStatus) throws TransactionException {
        TransactionObject transactionObject =  (TransactionObject)defaultTransactionStatus.getTransaction();
        transactionObject.getTransaction().rollback();
    }

    @Override
    protected void doSetRollbackOnly(DefaultTransactionStatus status) throws TransactionException {
        TransactionObject transactionObject =  (TransactionObject)status.getTransaction();
        transactionObject.getTransaction().setRollbackOnly(true);
    }

    protected boolean isExistingTransaction(Object transaction) {
        TransactionObject transactionObject = (TransactionObject)transaction;
        return !transactionObject.isNew() && transactionObject.getTransaction() != null;
    }

    protected Object doSuspend(Object transaction) {
        TransactionObject transactionObject = (TransactionObject) transaction;
        transactionObject.setTransaction(null);
        return TransactionSynchronizationManager.unbindResource(this);
    }

    protected void doResume(@Nullable Object obj, Object suspendedResources) {
        TransactionObject transactionObject = (TransactionObject) obj;
        if(transactionObject != null) {
            transactionObject.setTransaction( (Transaction) suspendedResources);
        }
        try{
            TransactionSynchronizationManager.unbindResource(this);
        }catch (Exception ignore){}
        TransactionSynchronizationManager.bindResource(this, suspendedResources);
    }

    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
        TransactionObject transactionObject =  (TransactionObject) transaction;
        transactionObject.getTransaction().end();
        try{
            TransactionSynchronizationManager.unbindResource(this);
        }catch (Exception ignore){}
    }

    public void registContext(Context context){
        Transaction transaction = (Transaction) TransactionSynchronizationManager.getResource(this);
        context.setTransaction(transaction);
    }
}
