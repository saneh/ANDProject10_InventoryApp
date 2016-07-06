package in.lemonco.todos;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by sanehyadav1 on 7/6/16.
 */
public class CustomSimpleCursorAdapter extends SimpleCursorAdapter {
    private Context mContext;
    private Context appContext;
    private int layout;
    private Cursor cr;
    private final LayoutInflater inflater;

    public CustomSimpleCursorAdapter(Context context,int layout, Cursor c,String[] from,int[] to,int flags) {
        super(context,layout,c,from,to,flags);
        this.layout=layout;
        this.mContext = context;
        this.inflater=LayoutInflater.from(context);
        this.cr=c;
    }

    @Override
    public View newView (Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(layout, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);

        TextView name=(TextView)view.findViewById(R.id.productName_value);
        TextView quantity=(TextView)view.findViewById(R.id.availableInventory_value);
        TextView price = (TextView)view.findViewById(R.id.price_value);
        TextView sales = (TextView) view.findViewById(R.id.unitsSold_value);

        int name_index=cursor.getColumnIndexOrThrow(InventoryTable.COLUMN_NAME);
        int quantity_index=cursor.getColumnIndexOrThrow(InventoryTable.COLUMN_QUANTITY);
        int price_index = cursor.getColumnIndexOrThrow(InventoryTable.COLUMN_PRICE);
        int sales_index = cursor.getColumnIndexOrThrow(InventoryTable.COLUMN_SALES);

        name.setText(cursor.getString(name_index));
        quantity.setText(String.valueOf(cursor.getInt(quantity_index)));
        price.setText(String.valueOf(cursor.getInt(price_index)));
        sales.setText(String.valueOf(cursor.getInt(sales_index)));

        Button sellButton = (Button)view.findViewById(R.id.sellButton);
        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("sellBUtton","check");
            }
        });

    }

}
