package com.example.palyandroid.sql;

import com.example.palyandroid.MyDao.DaoMaster;
import com.example.palyandroid.MyDao.DaoSession;
import com.example.palyandroid.MyDao.MyDbDao;
import com.example.palyandroid.base.BaseApp;

import java.util.List;

/**
 * Created by 走马 on 2019/5/8.
 */

public class DaoUtils {

    public static DaoUtils daoUtils;
    private final MyDbDao myDbDao;

    public DaoUtils() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(BaseApp.sBaseApp, "db45.db");
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();
        myDbDao = daoSession.getMyDbDao();
    }

    public static DaoUtils getDaoUtils() {
        if (daoUtils == null) {
            //同步锁
            synchronized (DaoUtils.class) {
                if (daoUtils == null) {
                    daoUtils = new DaoUtils();
                }
            }
        }
        return daoUtils;
    }

    public void insert(List<MyDb> list) {
        myDbDao.insertInTx(list);
    }

    public List<MyDb> select() {
        return myDbDao.loadAll();
    }

    public void delete(long id) {
        myDbDao.deleteByKey(id);
    }

    public void updata(MyDb myDb) {
        myDbDao.update(myDb);
    }
    //.myapp 需要注册
    public void insert_one(MyDb myDb){

        if ( has( myDb )){
            return;
        }

        myDbDao.insertOrReplace(myDb);

    }

    public boolean has(MyDb bean){

        List<MyDb> list = myDbDao.queryBuilder().where(MyDbDao.Properties.Url.eq(bean.getUrl())).list();

        if ( list != null && list.size() > 0 ){
            return true;
        }
        return false;
    }

}
