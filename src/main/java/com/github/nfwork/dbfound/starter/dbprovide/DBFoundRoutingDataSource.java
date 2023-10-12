package com.github.nfwork.dbfound.starter.dbprovide;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.HashMap;

public class DBFoundRoutingDataSource extends AbstractRoutingDataSource {

    public DBFoundRoutingDataSource(){
        this.setTargetDataSources(new HashMap<>());
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }
}
