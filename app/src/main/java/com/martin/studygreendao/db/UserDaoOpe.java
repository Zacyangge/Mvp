package com.martin.studygreendao.db;

import android.content.Context;

import com.example.chenzaoyang.model.UserInfo;
import com.martin.studygreendao.greendao.UserInfoDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * @author chenzaoyang
 * @date 2018/10/31
 */

public class UserDaoOpe {

    /**
     * 添加数据至数据库
     *
     * @param stu
     */
    public static void insertData(Context context,UserInfo stu) {

        DbManager.getDaoSession(context).getUserInfoDao().insert(stu);
    }


    /**
     * 将数据实体通过事务添加至数据库
     *
     * @param context
     * @param list
     */
    public static void insertData(Context context, List<UserInfo> list) {
        if (null == list || list.size() <= 0) {
            return;
        }
        DbManager.getDaoSession(context).getUserInfoDao().insertInTx(list);
    }

    /**
     * 添加数据至数据库，如果存在，将原来的数据覆盖
     * 内部代码判断了如果存在就update(entity);不存在就insert(entity)；
     *
     * @param context
     * @param student
     */
    public static void saveData(Context context, UserInfo student) {
        DbManager.getDaoSession(context).getUserInfoDao().save(student);
    }

    /**
     * 删除数据至数据库
     *
     * @param context
     * @param student 删除具体内容
     */
    public static void deleteData(Context context, UserInfo student) {
        DbManager.getDaoSession(context).getUserInfoDao().delete(student);
    }

    /**
     * 根据id删除数据至数据库
     *
     * @param context
     * @param id      删除具体内容
     */
    public static void deleteByKeyData(Context context, long id) {
        DbManager.getDaoSession(context).getUserInfoDao().deleteByKey(id);
    }

    /**
     * 删除全部数据
     *
     * @param context
     */
    public static void deleteAllData(Context context) {
        DbManager.getDaoSession(context).getUserInfoDao().deleteAll();
    }

    /**
     * 更新数据库
     *
     * @param context
     * @param student
     */
    public static void updateData(Context context, UserInfo student) {
        DbManager.getDaoSession(context).getUserInfoDao().update(student);
    }

    /**
     * 查询所有数据
     *
     * @param context
     * @return
     */
    public static List<UserInfo> queryAll(Context context) {
        QueryBuilder<UserInfo> builder = DbManager.getDaoSession(context).getUserInfoDao().queryBuilder();

        return builder.build().list();
    }

    /**
     * 根据id，其他的字段类似
     *
     * @param context
     * @param id
     * @return
     */
    public static List<UserInfo> queryForId(Context context, long id) {
        QueryBuilder<UserInfo> builder = DbManager.getDaoSession(context).getUserInfoDao().queryBuilder();
        /**
         * 返回当前id的数据集合,当然where(这里面可以有多组，做为条件);
         * 这里build.list()；与where(StudentDao.Properties.Id.eq(id)).list()结果是一样的；
         * 在QueryBuilder类中list()方法return build().list();
         *
         */
        // Query<Student> build = builder.where(StudentDao.Properties.Id.eq(id)).build();
        // List<Student> list = build.list();
        return builder.where(UserInfoDao.Properties.Id.eq(id)).list();
    }
}
