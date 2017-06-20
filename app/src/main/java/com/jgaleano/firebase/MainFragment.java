package com.jgaleano.firebase;

import android.app.Activity;
import android.app.Notification;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josimargaleanoflorez on 18/06/17.
 */

public class MainFragment extends BaseVolleyFragment{
    private String url = "http://127.0.0.1/proyectos/api_rest/public/api/notificaciones";
    private String urlUser = "http://127.0.0.1/proyectos/api_rest/public/api/usuarios";
    private TextView mTextView;
    private List<Notificacion> notificacionList = new ArrayList<Notificacion>();
    private ListView listView;
    private AdapterNotificacion adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        mTextView = (TextView) v.findViewById(R.id.label);

        listView = (ListView) v.findViewById(R.id.lista);
        adapter = new AdapterNotificacion(getActivity(), notificacionList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView parent, View v, int position, long id){
                TextView idN = (TextView) v.findViewById(R.id.id);
                getRequest((String) idN.getText());
            }
        });

        onTokenRefresh();
        makeRequest();

        return v;
    }

    private void makeRequest(){
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {

                        JSONObject obj = response.getJSONObject(i);
                        Notificacion notificacion = new Notificacion();

                        notificacion.setId(obj.getString("id"));
                        notificacion.setTittle(obj.getString("titulo"));
                        notificacion.setDescription(obj.getString("tipo"));

                        // adding notificaciones al array de notificaciones
                        notificacionList.add(notificacion);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                // notifying list adapter about data changes
                // so that it renders the list view with updated data
                adapter.notifyDataSetChanged();

                onConnectionFinished();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                onConnectionFailed(volleyError.toString());
            }
        });
        addToQueue(request);
    }

    private void getRequest(String id){
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        mTextView.setText("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        addToQueue(stringRequest);
    }

    private void getRequestUser(String token){
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlUser + token,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        mTextView.setText("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        addToQueue(stringRequest);
    }

    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("Token", "Refreshed token: " + refreshedToken);

        getRequestUser(refreshedToken);
    }
}
