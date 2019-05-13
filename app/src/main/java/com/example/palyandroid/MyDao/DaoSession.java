package com.example.palyandroid.MyDao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.palyandroid.sql.MyDb;

import com.example.palyandroid.MyDao.MyDbDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig myDbDaoConfig;

    private final MyDbDao myDbDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        myDbDaoConfig = daoConfigMap.get(MyDbDao.class).clone();
        myDbDaoConfig.initIdentityScope(type);

        myDbDao = new MyDbDao(myDbDaoConfig, this);

        registerDao(MyDb.class, myDbDao);
    }
    
    public void clear() {
        myDbDaoConfig.clearIdentityScope();
    }

    public MyDbDao getMyDbDao() {
        return myDbDao;
    }

}