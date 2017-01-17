package johnwestwig.fittfufantasy.API;

import org.json.JSONObject;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;


import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import johnwestwig.fittfufantasy.Utilities.StoredSettings;


/**
 * Created by John on 1/15/17
 * API Request
 */

public class APIRequest implements Requestable {

    private static final String TAG = "API Request";
    //private static final String BASE_URL = "http://localhost:8000";
    //private static final String BASE_URL = "http://10.0.2.2:8000";
    private static final String BASE_URL = "http://192.168.1.251:8000";

    private final Context context;

    public APIRequest(Context context) {
        this.context = context;
    }

    public void send(String url, int method, Map<String, String> params) {
        url = BASE_URL + url;
        if (params == null) {
            params = new HashMap<>();
        }
        JSONObject postParams = new JSONObject(params);
        DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy();

        JsonObjectRequest request = new JsonObjectRequest(method, url, postParams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                onCompleted(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v(TAG, error.toString());
                if(error.networkResponse.data != null) {
                    try {
                        String body = new String(error.networkResponse.data, "UTF-8");
                        onError(new JSONObject(body));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    onError();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> headers = new HashMap<>();
                StoredSettings storedSettings = new StoredSettings(context);
                headers.put("x-token", storedSettings.getToken());
                return headers;
            }
        };

        request.setRetryPolicy(retryPolicy);

        APIRequestSingleton.getInstance(this.context).addToRequestQueue(request);
    }

    public void onCompleted(JSONObject response) {}
    public void onError(JSONObject error) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(getRequestContext()).
                    setTitle(error.getString("message")).
                    setMessage(error.getString("description")).
                    setPositiveButton("OK", null);
            builder.show();
        } catch (JSONException e) {
            Log.v(TAG, e.getMessage());
        }
    }

    @Override
    public void onError() {

    }

    public Context getRequestContext() {
        return context;
    }


}