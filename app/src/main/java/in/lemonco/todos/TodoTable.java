package in.lemonco.todos;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TodoTable {

    // Database table
    public static final String TABLE_TODO = "todo";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_SALES = "sales";
    public static final String COLUMN_SUPPLIER = "supplier";
    public static final String COLUMN_IMAGE = "image";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_TODO
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_PRICE + " integer not null,"
            + COLUMN_QUANTITY + " integer not null,"
            + COLUMN_SALES + " integer not null,"
            + COLUMN_SUPPLIER + " text not null, "
            + COLUMN_IMAGE + " text not null"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(TodoTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        onCreate(database);
    }
}