package fi.jamk.shoppinglist;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class MainActivity extends Activity implements ShoppingListDialogFragment.DialogListener {
    private final String DATABASE_TABLE = "shopping_list";
    private final int DELETE_ID = 0;
    private SQLiteDatabase db;
    private Cursor cursor;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)  findViewById(R.id.listView);
        registerForContextMenu(listView);

        db = (new DatabaseOpenHelper(this)).getWritableDatabase();
        queryData();

    }

    public void queryData() {
        //cursor = db.rawQuery("SELECT _id, product, count, price FROM shopping_list ORDER BY product ASC", null);
        String[] resultColumns = new String[]{"_id","product","count","price"};
        cursor = db.query(DATABASE_TABLE,resultColumns,null,null,null,null,"product ASC",null);

        ListAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.list_item, cursor,
                new String[] {"product", "count", "price"},
                new int[] {R.id.product, R.id.count, R.id.price}
                ,0);

        listView.setAdapter(adapter);

        float prices = 0f;
        if (cursor.moveToFirst()) {
            do {
                int count = cursor.getInt(2);
                float price = cursor.getFloat(3);
                prices += count * price;
            } while(cursor.moveToNext());
            Toast.makeText(getBaseContext(), "Total price: " + prices, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                ShoppingListDialogFragment eDialog = new ShoppingListDialogFragment();
                eDialog.show(getFragmentManager(), "Add a new Product");
        }
        return false;
    }

    public void addNew(View v){
        ShoppingListDialogFragment eDialog = new ShoppingListDialogFragment();
        eDialog.show(getFragmentManager(), "Add a new Product");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String product, int count, float price) {
        ContentValues values = new ContentValues(3);
        values.put("product", product);
        values.put("count", count);
        values.put("price", price);
        db.insert("shopping_list", null, values);
        queryData();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(Menu.NONE, DELETE_ID, Menu.NONE, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case DELETE_ID:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                String[] args = {String.valueOf(info.id)};
                db.delete("shopping_list", "_id=?", args);
                queryData();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
