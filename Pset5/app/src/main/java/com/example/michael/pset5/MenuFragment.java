package com.example.michael.pset5;

import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends ListFragment {

    ArrayList<String> menuList = new ArrayList<>();
    ArrayList<Double> menuPrices = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = this.getArguments();
        final String category = args.getString("category");

        String url = "https://resto.mprog.nl/menu";
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest JsonObj = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {

                    private JSONObject jsonObject = null;
                    private JSONArray jsonArray = null;

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            jsonObject = new JSONObject(response.toString());
                            jsonArray = jsonObject.getJSONArray("items");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                if (jsonArray.getJSONObject(i).optString("category").equals(category)) {
                                    menuList.add(jsonArray.getJSONObject(i).optString("name"));
                                    menuPrices.add(jsonArray.getJSONObject(i).optDouble("price"));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        setAdapter();

                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {

        };
        queue.add(JsonObj);


    }

    private void setAdapter() {
        ArrayAdapter<String> myAdapter =
                new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, menuList);

        this.setListAdapter(myAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        RestoDatabase db = RestoDatabase.getInstance(getContext());

        String menuClicked = menuList.get((int)id) + " have been added to your order.";
        Toast.makeText(getContext(), menuClicked, Toast.LENGTH_SHORT).show();

        db.addItem(menuList.get((int)id), menuPrices.get((int)id),  id);
    }
}
