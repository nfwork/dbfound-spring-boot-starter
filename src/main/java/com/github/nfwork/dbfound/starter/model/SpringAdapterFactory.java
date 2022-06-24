package com.github.nfwork.dbfound.starter.model;

import com.nfwork.dbfound.model.adapter.AdapterFactory;
import com.nfwork.dbfound.model.adapter.ExecuteAdapter;
import com.nfwork.dbfound.model.adapter.QueryAdapter;
import com.nfwork.dbfound.util.LogUtil;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

public class SpringAdapterFactory extends AdapterFactory {

    private final ApplicationContext applicationContext;

    public SpringAdapterFactory( ApplicationContext applicationContext){
       this.applicationContext = applicationContext;
       setAdapterFactory(this);
    }

    @Override
    public QueryAdapter createQueryAdapter(Class cls) throws InstantiationException, IllegalAccessException {
        try{
            return (QueryAdapter) applicationContext.getBean(cls);
        }catch (NoSuchBeanDefinitionException exception){
            LogUtil.info("class:" +cls.getName() +" is not found in applicationContext, return a new instance");
            return super.createQueryAdapter(cls);
        }
    }

    @Override
    public ExecuteAdapter createExecuteAdapter(Class cls) throws InstantiationException, IllegalAccessException {
        try{
            return (ExecuteAdapter) applicationContext.getBean(cls);
        }catch (NoSuchBeanDefinitionException exception){
            LogUtil.info("class:" +cls.getName() +" is not found in applicationContext, return a new instance");
            return super.createExecuteAdapter(cls);
        }
    }
}
