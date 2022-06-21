package com.example.traveller;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

public class SqliteHelper extends SQLiteOpenHelper {

    //DATABASE NAME
    public static final String DATABASE_NAME = "traveller";

    //DATABASE VERSION
    public static final int DATABASE_VERSION = 1;

    //TABLE NAMES
    public static final String TABLE_USERS = "users";
    public static final String TABLE_NAME = "cities";
    public static final String TABLE_NAME1 = "infos";
    public static final String TABLE_NAME2 = "travellist";
    public static final String TABLE_NAME3 = "userplace";

    //TABLE USERS COLUMNS
    //ID COLUMN @primaryKey
    public static final String KEY_ID = "id";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ID1 = "id";
    private static final String COLUMN_ID2 = "id";
    private static final String COLUMN_ID3 = "id";

    //COLUMN user name
    public static final String KEY_USER_NAME = "username";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_NAME1 = "name";
    private static final String COLUMN_NAME2 = "text";
    private static final String COLUMN_NAME3 = "places";
    private static final String COLUMN_NAME4 = "mail";
    private static final String COLUMN_NAME5 = "place";
    private static final String COLUMN_NAME6 = "info";

    //COLUMN email
    public static final String KEY_EMAIL = "email";
    public static final String KEY_LAST="last";

    //COLUMN password
    public static final String KEY_PASSWORD = "password";
    private static final String Latitude = "latitude";
    private static final String Longtitude = "longtitude";
    ArrayList<Place> localList = new ArrayList<Place>();

    //SQL for creating users table
    public static final String SQL_TABLE_USERS = " CREATE TABLE " + TABLE_USERS + " ( " + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_USER_NAME + " TEXT, " + KEY_EMAIL + " TEXT, " + KEY_PASSWORD + " TEXT,"+ KEY_LAST+" TEXT ) ";
   /* public static final String SQL_TABLE_CITY = " CREATE TABLE " + TABLE_NAME
            + " ( "
            + COLUMN_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_NAME + " TEXT, "
            + " ) ";
*/

    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_ITEM_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME + " TEXT)";
        String CREATE_ITEM_TABLE3="CREATE TABLE "+TABLE_NAME2+" ( "+COLUMN_ID2 +" INTEGER PRIMARY KEY AUTOINCREMENT,"+KEY_EMAIL+" TEXT,"+COLUMN_NAME3+" TEXT )";
        String CREATE_ITEM_TABLE2 = "CREATE TABLE " + TABLE_NAME1 + "(" + COLUMN_ID1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME1 + " ," + COLUMN_NAME2 + " TEXT," + Latitude + " TEXT," + Longtitude + " TEXT)";
        String CREATE_ITEM_TABLE4="CREATE TABLE "+TABLE_NAME3+"(" + COLUMN_ID3 + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ COLUMN_NAME4 +" TEXT,"+ COLUMN_NAME5 +" TEXT, "+COLUMN_NAME6 +" TEXT,"+Latitude + " TEXT," + Longtitude + " TEXT)";
        sqLiteDatabase.execSQL(SQL_TABLE_USERS);
        sqLiteDatabase.execSQL(CREATE_ITEM_TABLE);
        sqLiteDatabase.execSQL(CREATE_ITEM_TABLE2);
        sqLiteDatabase.execSQL(CREATE_ITEM_TABLE3);
        sqLiteDatabase.execSQL(CREATE_ITEM_TABLE4);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //drop table to create new one if database version updated
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERS);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME1);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(sqLiteDatabase);
    }

    public boolean ilce_metin_ekle(String ilce,String text,String lat,String lon){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COLUMN_NAME1,ilce);
        contentValues.put(COLUMN_NAME2,text);
        contentValues.put(Latitude,lat);
        contentValues.put(Longtitude,lon);
        long result =db.insert(TABLE_NAME1,null,contentValues);
        if (result==-1)
        {
            return false;
        }
        else{
            return true;
        }

    }
    public void addplace(String mail,String place)
        {
            ArrayList<String> list = new ArrayList<String>();
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues contentValues=new ContentValues();
            String selectQuery = "SELECT * FROM travellist where email="+"'"+mail+"' and places="+"'"+place+"'";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    list.add(cursor.getString(2));//adding 2nd column data
                } while (cursor.moveToNext());
            }
            else
            {
                contentValues.put(KEY_EMAIL, mail);
                contentValues.put(COLUMN_NAME3, place);
                db.insert(TABLE_NAME2, null, contentValues);
            }
        }
    public ArrayList<String> getPlace(String mail)
    {
        ArrayList<String> list = new ArrayList<String>();
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        String selectQuery = "SELECT * FROM travellist where email="+"'"+mail+"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(2));//adding 3nd column data
            } while (cursor.moveToNext());
        }
        return list;

    }
    public String getUsername(String mail)
    {
        String user = "";
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        String selectQuery = "SELECT * FROM users where email="+"'"+mail+"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                user=cursor.getString(1);//adding 3nd column data

            } while (cursor.moveToNext());
        }
        return user;
    }
    public void deleteplace(String delplace,String mail)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM travellist WHERE email = '" + mail + "' and places='"+delplace+"'");

    }


    public Cursor ilce_metin_cek() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME1, null);
        return res;
    }

    public void insertLabel(String label) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, label);//column name, column value

        // Inserting Row
        db.insert(TABLE_NAME, null, values);//tableName, nullColumnHack, CotentValues
        db.close(); // Closing database connection
    }

    public List<String> getAllLabels() {
        List<String> list = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1));//adding 2nd column data
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return list;
    }

    //using this method we can add users to user table
    public void addUser(User user) {

        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        //create content values to insert
        ContentValues values = new ContentValues();

        //Put username in  @values
        values.put(KEY_USER_NAME, user.userName);

        //Put email in  @values
        values.put(KEY_EMAIL, user.email);

        //Put password in  @values
        values.put(KEY_PASSWORD, user.password);

        // insert row
        long todo_id = db.insert(TABLE_USERS, null, values);
    }

    public User Authenticate(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String value="1";
        String query;
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_EMAIL, KEY_PASSWORD,KEY_LAST},//Selecting columns want to query
                KEY_EMAIL + "=?",
                new String[]{user.email},//Where clause
                null, null, null,null);

        if (cursor != null && cursor.moveToFirst()) {
            //if cursor has value then in user database there is user associated with this given email
            User user1 = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4));

            //Match both passwords check they are same or not
            if (user.password.equalsIgnoreCase(user1.password)) {
                db.execSQL("update users set last='1' where email='"+user.email+"'");

                return user1;
            }
        }

        //if user password does not matches or there is no record with that email then return @false
        return null;
    }
    public String getLast()
    {
        String x="";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+TABLE_USERS+" WHERE last=1",null);
        while(cursor.moveToNext())
        {
            x=cursor.getString(2);
        }
        cursor.close();
        return x;
    }
    public String getLast2()
    {
        String x="";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+TABLE_USERS+" WHERE last=1",null);
        while(cursor.moveToNext())
        {
            x=cursor.getString(4);
        }
        cursor.close();
        return x;
    }
    public void updateLst()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update users set last=null where last=1");
    }

    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_EMAIL, KEY_PASSWORD},//Selecting columns want to query
                KEY_EMAIL + "=?",
                new String[]{email},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            //if cursor has value then in user database there is user associated with this given email so return true
            return true;
        }

        //if email does not exist return false
        return false;
    }
    public int getCount(String mail)
    {
        int a=0;
        SQLiteDatabase db = this.getWritableDatabase();
        //create content values to insert
        String selectQuery = "SELECT  * FROM " + TABLE_NAME3+" where mail='"+mail+"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        while(cursor.moveToNext())
        {
            a++;
        }
        cursor.close();
        return a;
    }
    public boolean insertUserLocal(String mail,String place,String info,String lat,String longg,int a)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        if(a<5){
            ContentValues values = new ContentValues();

            values.put(COLUMN_NAME4,mail);
            values.put(COLUMN_NAME5,place);
            values.put(COLUMN_NAME6,info);
            values.put(Latitude,lat);
            values.put(Longtitude,longg);
            db.insert(TABLE_NAME3, null, values);
            return true;
        }
        else
        {
            return false;
        }
        }

    public ArrayList<Place> location() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + TABLE_NAME1, null);
        int nameIx = cursor.getColumnIndex(COLUMN_NAME1);
        int latIx = cursor.getColumnIndex(Latitude);
        int longIx = cursor.getColumnIndex(Longtitude);
        while (cursor.moveToNext()) {
            String nameFD = cursor.getString(nameIx);
            String latFD = cursor.getString(latIx);
            String longFD = cursor.getString(longIx);

            Double latD = Double.parseDouble(latFD);
            Double longD = Double.parseDouble(longFD);
            Place infos = new Place(nameFD, latD, longD);
            localList.add(infos);


        }
        cursor.close();

        return localList;
    }

}
