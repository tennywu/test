package com.example.administrator.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.greendao.dao.DaoSession;
import com.example.administrator.greendao.dao.UserDao;
import com.example.administrator.greendao.model.User;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

public class GreendaoActivity extends AppCompatActivity {


    private DaoSession daoSession;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greendao);

        daoSession = App.getDaoSession(this); // 获取DaoSession
        userDao = daoSession.getUserDao(); // 获取UserDao 可对User进行操作


    }

    /**
     * Insert数据
     * @param view
     */
    public void insert(View view){

        for(int i = 0 ;i < 10 ; i ++){

            User user = new User();
            user.setUsername("haha" + i);
            user.setPassword("test" + i);

            // 直接操作对象，将对象插入数据库，这个和Hibernate的一样
            userDao.insert(user);
        }

        Toast.makeText(GreendaoActivity.this, "插入数据!", Toast.LENGTH_SHORT).show();

    }

    /**
     * Delete数据 根据id删除对应数据
     * @param view
     */
    public void delete(View view){
        userDao.deleteByKey(1l);
    }

    /**
     * Update数据 根据id更新对应的数据
     * @param view
     */
    public void update(View view){

        User user = new User();
        user.setId(2l);
        user.setUsername("update");
        user.setPassword("update_psw");

        userDao.update(user);
    }

    /**
     * Query数据
     * @param view
     */
    public void query(View view){
        QueryBuilder<User> queryBuilder = userDao.queryBuilder();
        List<User> userList = queryBuilder.list(); // 使用list进行查询
        System.out.println("userList : " + userList.toString());
    }

    /**
     * DeleteAll 删除所有数据
     * @param view
     */
    public void deleteAll(View view){
        userDao.deleteAll();
    }

    /**
     * 根据id查询对应的数据
     * @param view
     */
    public void QueryById(View view){

        QueryBuilder<User> queryBuilder = userDao.queryBuilder();

        // where
        QueryBuilder<User> query = queryBuilder.where(UserDao.Properties.Id.eq(6));

        System.out.println("query:" + query.list().toString());

    }

}