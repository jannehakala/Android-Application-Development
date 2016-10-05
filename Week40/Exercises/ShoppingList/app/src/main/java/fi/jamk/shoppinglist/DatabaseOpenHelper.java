package fi.jamk.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by H3298 on 10/5/2016.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "ShoppingList_database";
    private final String DATABASE_TABLE = "shopping_list";
    private final String PRODUCT = "product";
    private final String COUNT = "count";
    private final String PRICE = "price";

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+DATABASE_TABLE+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, "+PRODUCT+" TEXT, "+COUNT+" INTEGER, "+PRICE+" REAL);");

        ContentValues values = new ContentValues();
        values.put(PRODUCT, "Milk");
        values.put(COUNT, 5);
        values.put(PRICE, 1.06);

        db.insert(DATABASE_TABLE, null, values);

        values.put(PRODUCT, "Bread");
        values.put(COUNT, 2);
        values.put(PRICE, 2.24);
        db.insert(DATABASE_TABLE, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
        onCreate(db);
    }
}
