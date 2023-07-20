package com.github.nfwork.dbfound.starter.dbprovide;

public class Transaction extends com.nfwork.dbfound.core.Transaction {
    private boolean rollbackOnly = false;

    public boolean isRollbackOnly() {
        return rollbackOnly;
    }

    public void setRollbackOnly(boolean rollbackOnly) {
        this.rollbackOnly = rollbackOnly;
    }
}