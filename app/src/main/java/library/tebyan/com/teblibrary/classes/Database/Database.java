package library.tebyan.com.teblibrary.classes.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import library.tebyan.com.teblibrary.classes.Login.Login;
import library.tebyan.com.teblibrary.classes.Login.LoginDB;


public class Database {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public Database(Context context) {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public void removeLogin() {
        database.delete(LoginDB.TABLE, null, null);
    }

    public long saveLogin(String username, String password, int networkUserId) {
        removeLogin();
        ContentValues values = new ContentValues();
        values.put(LoginDB.USERNAME_FIELD, username);
        values.put(LoginDB.PASSWORD_FIELD, password);
        values.put(LoginDB.NETWORKUSERID_FIELD, networkUserId);
        return database.insert(LoginDB.TABLE, null, values);
    }

    public Login getLogin() {
        String[] cols = new String[]{LoginDB.USERNAME_FIELD,
                LoginDB.PASSWORD_FIELD};
        Cursor c = database.rawQuery(LoginDB.TABLE_SELECT, null);
        if (c != null && c.moveToFirst()) {
            Login result = new Login();
            result.username = c.getString(0);
            result.password = c.getString(1);
            result.networkUserId = c.getInt(2);
            c.close();
            return result;
        }
        return null;
    }
}
