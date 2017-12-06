package com.example.michael.pset5;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

/**
 * Created by Michael on 4-12-2017.
 */

public class RestoAdapter extends ResourceCursorAdapter {
    public RestoAdapter(Context context, Cursor cursor) {
        super(context, R.layout.row_order, cursor);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView itemName = view.findViewById(R.id.textView);

        itemName.setText(cursor.getString(cursor.getColumnIndex("title")));
    }
}
