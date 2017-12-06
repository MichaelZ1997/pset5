package com.example.michael.pset5;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends ListFragment {

    ArrayList<String> newArray = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String url ="https://resto.mprog.nl/categories";
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest JsonObj = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray JsonAr;

                        try {
                            JsonAr = response.getJSONArray("categories");
                            for (int i = 0; i < JsonAr.length(); i++) {
                                newArray.add(JsonAr.getString(i));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        setAdapter();
                    }
                }
                , null);
        queue.add(JsonObj);
    }

    private void setAdapter() {
        ArrayAdapter<String> myAdapter =
                new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, newArray);

        this.setListAdapter(myAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        MenuFragment menuFragment = new MenuFragment();

        Bundle args = new Bundle();
        args.putString("category", l.getItemAtPosition(position).toString());
        menuFragment.setArguments(args);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, menuFragment)
                .addToBackStack(null)
                .commit();
    }

}