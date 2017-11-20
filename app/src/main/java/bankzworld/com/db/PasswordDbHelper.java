package bankzworld.com.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jaycee on 8/11/2017.
 */

public class PasswordDbHelper extends SQLiteOpenHelper {

    private static final String TAG = "PasswordDbHelper";

    public static final String DATABASE_NAME = "password_saver.db";

    public static final int DATABASE_VERSION = 1;

    public PasswordDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_FAV_TABLE =

                "CREATE TABLE " + PasswordContract.PasswordEntry.ACCOUNT_TABLE + " (" +
                        PasswordContract.PasswordEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        PasswordContract.PasswordEntry.ACCOUNT_TYPE + " TEXT NOT NULL, " +
                        PasswordContract.PasswordEntry.ACCOUNT_USERNAME + " TEXT NOT NULL, " +
                        PasswordContract.PasswordEntry.ACCOUNT_PASSWORD + " TEXT NOT NULL);";
        db.execSQL(SQL_CREATE_FAV_TABLE);

        Log.v(TAG, "Database created Successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + PasswordContract.PasswordEntry.ACCOUNT_TABLE);
        onCreate(db);
    }

    public boolean insertData(String account, String username, String password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PasswordContract.PasswordEntry.ACCOUNT_TYPE, account);
        values.put(PasswordContract.PasswordEntry.ACCOUNT_USERNAME, username);
        values.put(PasswordContract.PasswordEntry.ACCOUNT_PASSWORD, password);
        long result = sqLiteDatabase.insert(PasswordContract.PasswordEntry.ACCOUNT_TABLE, null, values);
        sqLiteDatabase.close();

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateData(String id, String account, String username, String password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PasswordContract.PasswordEntry.ACCOUNT_TYPE, account);
        values.put(PasswordContract.PasswordEntry.ACCOUNT_USERNAME, username);
        values.put(PasswordContract.PasswordEntry.ACCOUNT_PASSWORD, password);
        long result = sqLiteDatabase.update(PasswordContract.PasswordEntry.ACCOUNT_TABLE, values, "ID=?", new String[]{id});
        sqLiteDatabase.close();

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }


    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("Select * from " + PasswordContract.PasswordEntry.ACCOUNT_TABLE, null);
        return res;
    }

    public Integer deleteItem(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(PasswordContract.PasswordEntry.ACCOUNT_TABLE, "ID=?", new String[]{id});
        return i;
    }
}
