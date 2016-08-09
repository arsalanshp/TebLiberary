package library.tebyan.com.teblibrary.classes.Database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import library.tebyan.com.teblibrary.classes.Login.LoginDB;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SNDB4";
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(LoginDB.TABLE_CREATE);
    }

    // Method is called during an upgrade of the database,
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + LoginDB.TABLE);
        onCreate(database);
    }
}
