package com.github.nfwork.dbfound.starter.service;

import com.nfwork.dbfound.core.Context;
import com.nfwork.dbfound.dto.ResponseObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChainedTransactionService extends DBFoundDefaultService{

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager="chainedTransactionManager")
    public ResponseObject execute(Context context, String modelName, String executeName) {
        return super.execute(context, modelName, executeName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager="chainedTransactionManager")
    public ResponseObject batchExecute(Context context, String modelName, String executeName) {
        return super.batchExecute(context, modelName, executeName);
    }
}
