package com.github.nfwork.dbfound.starter.dbprovide;

import org.springframework.transaction.support.SmartTransactionObject;

public class TransactionObject implements SmartTransactionObject {
    private Transaction transaction;
    private boolean isNew = true;

    public void setTransaction(Transaction transaction, boolean isNew){
        this.transaction = transaction;
        this.isNew = isNew;
    }

    public boolean isNew(){
        return isNew;
    }

    @Override
    public boolean isRollbackOnly() {
        if(transaction != null){
            return transaction.isRollbackOnly();
        }else{
            return false;
        }
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public void flush() {
    }
}