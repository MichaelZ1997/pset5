package com.example.michael.pset5;


import android.database.Cursor;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends DialogFragment implements View.OnClickListener {
    Cursor cursor;
    RestoDatabase db;

    private int getTotalPrice(Cursor cursor) {
        int totalPrice = 0;
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                int price = Integer.parseInt(cursor.getString(cursor.getColumnIndex("price")));
                int amount = Integer.parseInt(cursor.getString(cursor.getColumnIndex("amount")));
                totalPrice += (price * amount);
            }
        }
        return totalPrice;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        ListView orderFragment = view.findViewById(R.id.orderFragment);

        db = RestoDatabase.getInstance(getContext());
        cursor = db.selectAll();

        RestoAdapter adapter = new RestoAdapter(getContext(), cursor);
        orderFragment.setAdapter(adapter);

        Button b = view.findViewById(R.id.cancelButton);
        b.setOnClickListener(this);

        Button b2 = view.findViewById(R.id.confirmButton);
        b2.setOnClickListener(this);

        int totalPr = getTotalPrice(cursor);
        TextView title = view.findViewById(R.id.totalP);
        title.setText("The total price of the order is: €" + totalPr);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirmButton:
                TextView title2 = getView().findViewById(R.id.orderPlaced);
                title2.setText("Your order has been placed!");
                break;
            case R.id.cancelButton:
                db.clear();
                this.dismiss();
                break;
        }
    }
}
