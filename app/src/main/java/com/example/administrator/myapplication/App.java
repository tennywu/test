package com.example.administrator.myapplication;

import android.app.Application;
import android.content.Context;

import com.example.administrator.greendao.dao.DaoMaster;
import com.example.administrator.greendao.dao.DaoSession;


/**
 * Created by JW on 2015/11/5 10:54.
 * Email : 1481013719@qq.com
 * Description :
 */
public class App extends Application {

    private App Instance;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    public App getInstance() {

        if (Instance == null) {
            Instance = this;
        }

        return Instance;
    }

    /**
     * 获取DaoMaster
     *
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster(Context context) {

        if (daoMaster == null) {

            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, Constants.DB_NAME, null);

            daoMaster = new DaoMaster(helper.getWritableDatabase());

        }
        return daoMaster;
    }

    /**
     * 获取DaoSession对象
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context) {

        if (daoSession == null) {
            if (daoMaster == null) {
                getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }

        return daoSession;
    }

}