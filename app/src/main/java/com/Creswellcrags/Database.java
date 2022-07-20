package com.Creswellcrags;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.Creswellcrags.Model.BeaconModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Database extends SQLiteOpenHelper {
    static Database instance = null;
    static SQLiteDatabase database = null;

    static final String DATABASE_NAME = "DB.db";
    static final int DATABASE_VERSION = 1;

    public static final String DEVICE_MAIN_TABLE = "Devicetable";
    public static final String ID = "id";
    public static final String URL = "URL";
    public static final String UrlTitle = "UrlTitle";
    public static final String image = "image";
    public static final String uid = "uid";
    public static final String date = "Date";
    public static final String Description ="Description";


    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("CREATE TABLE IF NOT EXISTS " + DEVICE_MAIN_TABLE + " ( "
                + ID + " INTEGER primary key autoincrement, "
                + URL + " TEXT, "
                + image + " INTEGER, "
                + UrlTitle + " TEXT, "
                + Description + " TEXT, "
                + uid + " TEXT, "
                + date + " TEXT )");

        checknewentry(db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }


    public static void createDevice(Context activity, BeaconModel bm) {

        ContentValues cv = new ContentValues();
        try {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String dates = df.format(Calendar.getInstance().getTime());
            cv.put(date, dates);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cv.put(date, "");
        cv.put(URL, bm.Url);
        cv.put(image, bm.image);
        cv.put(UrlTitle, bm.Title);
        cv.put(uid, bm.uid.toLowerCase());
        cv.put(Description,bm.desc);

        if (isURLexist(bm) == 0) {
            getDatabase().insert(DEVICE_MAIN_TABLE, null, cv);
            L.e("Notification DeviceFound");
            Intent intent = new Intent("DeviceFound");
            intent.putExtra("device", bm);
            intent.setClass(activity, NotificationGenrate.class);
            activity.sendBroadcast(intent);
        } else {
            bm = getdevice(bm);

        }
    }


    public static int isURLexist(BeaconModel qb) {
        Cursor cursor_Board = null;
        int count = 0;
        try {
            String query = "select * from " + DEVICE_MAIN_TABLE + " where " +
                    uid.toLowerCase() + " = '" + qb.uid.toLowerCase() + "'";
            cursor_Board = getDatabase().rawQuery(query, null);
            count = cursor_Board.getCount();
            cursor_Board.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }


    @SuppressLint("Range")
    public static BeaconModel getdevice(BeaconModel qb) {
        Cursor cursor_Board = null;
        int count = 0;
        try {
            String query = "select * from " + DEVICE_MAIN_TABLE + " where " +
                    uid.toLowerCase() + " = '" + qb.uid.toLowerCase() + "'";

            cursor_Board = getDatabase().rawQuery(query, null);
            count = cursor_Board.getCount();
            if (count > 0) {
                if (cursor_Board.moveToFirst()) {
                    do {
                        qb.Url = cursor_Board.getString(cursor_Board.getColumnIndex(URL));
                        qb.Title = cursor_Board.getString(cursor_Board.getColumnIndex(UrlTitle));
                        qb.Date = cursor_Board.getString(cursor_Board.getColumnIndex(date));
                        qb.uid = cursor_Board.getString(cursor_Board.getColumnIndex(uid));
                        qb.image = cursor_Board.getInt(cursor_Board.getColumnIndex(image));
                        qb.desc = cursor_Board.getString(cursor_Board.getColumnIndex(Description));

                    } while (cursor_Board.moveToNext());
                }
                cursor_Board.close();
                return qb;
            } else {
                cursor_Board.close();
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static ArrayList<BeaconModel> graphlist;

    @SuppressLint("Range")
    public static ArrayList<BeaconModel> getDeviceList() {
        Cursor cursor_Board;
        try {
            graphlist = new ArrayList<BeaconModel>();
            String q = "select * from " + DEVICE_MAIN_TABLE;
            cursor_Board = getDatabase().rawQuery(q, null);

            while (cursor_Board.moveToNext()) {
                BeaconModel qb = new BeaconModel();
                qb.Url = cursor_Board.getString(cursor_Board.getColumnIndex(URL));
                qb.Title = cursor_Board.getString(cursor_Board.getColumnIndex(UrlTitle));
                qb.Date = cursor_Board.getString(cursor_Board.getColumnIndex(date));
                qb.uid = cursor_Board.getString(cursor_Board.getColumnIndex(uid));
                qb.image = cursor_Board.getInt(cursor_Board.getColumnIndex(image));
                qb.desc = cursor_Board.getString(cursor_Board.getColumnIndex(Description));
                if (Appconstant.getConnectedDiffinsec(qb.Date, 10) == 1) {
                    if (!qb.Title.equals("")) {
                        graphlist.add(qb);
                    }
                }
            }
            cursor_Board.close();
            return graphlist;
        } catch (Exception e) {
        }
        return graphlist;
    }


    public static int updateDate(Context activity, BeaconModel bm) {
        ContentValues cv = new ContentValues();
        try {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String dates = df.format(Calendar.getInstance().getTime());
            cv.put(date, dates);
            L.v("bm " + bm.Url + "  " + dates);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Appconstant.getConnectedDiff(bm.Date, 20) == 2) {
            L.e("Notification DeviceFound");
            Intent intent = new Intent("DeviceFound");
            intent.putExtra("device", bm);
            intent.setClass(activity, NotificationGenrate.class);
            activity.sendBroadcast(intent);
        } else {

        }


        return getDatabase().update(DEVICE_MAIN_TABLE, cv, uid.toLowerCase() + "= ? ",
                new String[]{String.valueOf(bm.uid.toLowerCase())});
    }

    public static int updatename(Context context, BeaconModel bm) {
        L.e("updatename" + bm.toString());
        ContentValues cv = new ContentValues();
        cv.put(URL, bm.Url);
        cv.put(UrlTitle, bm.Title);
        cv.put(image, bm.image);
        return getDatabase().update(DEVICE_MAIN_TABLE, cv, uid.toLowerCase() + "= ? ",
                new String[]{String.valueOf(bm.uid.toLowerCase())});
    }


    Database(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static void init(Context context) {
        if (null == instance) {
            instance = new Database(context);
        }
    }

    public static SQLiteDatabase getDatabase() {
        if (null == database) {
            database = instance.getWritableDatabase();
        }
        return database;
    }

    public static void deactivate() {
        if (null != database && database.isOpen()) {
            database.close();
        }
        database = null;
        instance = null;
    }

    public boolean isTableExists(SQLiteDatabase db, String tableName) {
        boolean isExist = false;
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                isExist = true;
            }
            cursor.close();
        }
        return isExist;
    }

    public void alertTable(SQLiteDatabase db, String tablename, String field, String type, String DEFAULT) {
        String ALTER_USER_TABLE_ADD_USER_SOCIETY =
                "ALTER TABLE " + tablename + " ADD " + field + " " + type;
        if (!DEFAULT.isEmpty()) {
            ALTER_USER_TABLE_ADD_USER_SOCIETY = ALTER_USER_TABLE_ADD_USER_SOCIETY + " DEFAULT " + DEFAULT;
        } else {
            ALTER_USER_TABLE_ADD_USER_SOCIETY = ALTER_USER_TABLE_ADD_USER_SOCIETY + " DEFAULT  ' ' ";

        }
        if (!isFieldExist(db, tablename, field)) {
            db.execSQL(ALTER_USER_TABLE_ADD_USER_SOCIETY);
        }
    }

    private boolean isFieldExist(SQLiteDatabase inDatabase, String inTable, String columnToCheck) {
        Cursor mCursor = null;
        try {
            mCursor = inDatabase.rawQuery("SELECT * FROM " + inTable + " LIMIT 0", null);
            if (mCursor.getColumnIndex(columnToCheck) != -1)
                return true;
            else {
                return false;
            }
        } catch (Exception Exp) {
            return false;
        } finally {
            if (mCursor != null) mCursor.close();
        }
    }

    public void checknewentry(SQLiteDatabase db) {

    }


}