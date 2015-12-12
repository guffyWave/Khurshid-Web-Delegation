package lab.computing.khurshid.khurshidwebdelegation.management;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import lab.computing.khurshid.khurshidwebdelegation.R;
import lab.computing.khurshid.khurshidwebdelegation.dto.WebRequest;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "KhurshidWebDelegateDataBase.db";
    private static final int DATABASE_VERSION = 1;

    Dao<WebRequest, Integer> webRequestDao = null;
    RuntimeExceptionDao<WebRequest, Integer> webRequestExceptionDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION,
                R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
        try {
            TableUtils.createTable(connectionSource, WebRequest.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
                          int arg3) {
        try {
            TableUtils.dropTable(connectionSource, WebRequest.class, true);
            onCreate(arg0, arg1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<WebRequest, Integer> getWebRequestDao() throws SQLException {
        if (webRequestDao == null) {
            webRequestDao = getDao(WebRequest.class);
        }
        return webRequestDao;
    }

    public RuntimeExceptionDao<WebRequest, Integer> getWebRequestRuntimeExceptionDao() {
        if (webRequestExceptionDao == null) {
            webRequestExceptionDao = getRuntimeExceptionDao(WebRequest.class);
        }
        return webRequestExceptionDao;
    }

}
