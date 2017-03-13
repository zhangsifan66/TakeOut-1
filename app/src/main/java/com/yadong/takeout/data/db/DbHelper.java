package com.yadong.takeout.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.yadong.takeout.data.db.bean.AddressBean;
import com.yadong.takeout.data.db.bean.UserBean;
import com.yadong.takeout.ui.app.App;
import com.yadong.takeout.utils.Constants;

import java.sql.SQLException;

/**
 * 数据库的帮助类  用来创建数据库和表
 */

public class DbHelper extends OrmLiteSqliteOpenHelper {

    private static DbHelper dbHelper;

    private DbHelper(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    public static DbHelper getInstance() {
        if (dbHelper == null) {
            synchronized (DbHelper.class) {
                if (dbHelper == null) {
                    dbHelper = new DbHelper(App.getInstance());
                    dbHelper.getWritableDatabase();
                }
            }
        }
        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        //创建表
        try {
            TableUtils.createTable(connectionSource, UserBean.class);
            TableUtils.createTable(connectionSource, AddressBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
