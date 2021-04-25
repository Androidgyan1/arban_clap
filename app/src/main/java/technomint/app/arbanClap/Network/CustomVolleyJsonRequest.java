package technomint.app.arbanClap.Network;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class CustomVolleyJsonRequest extends Request<JSONObject> {

    private Response.Listener<JSONObject> listener;
    private Map<String, String> params;
    String Url;
    public CustomVolleyJsonRequest(String url, Map<String, String> params,
                                   Response.Listener<JSONObject> reponseListener,
                                   Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.listener = reponseListener;
        this.params = params;
    }

    public CustomVolleyJsonRequest(int method, String url, Map<String, String> params,
                                   Response.Listener<JSONObject> reponseListener,
                                   Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = reponseListener;
        this.params = params;
        this.Url = url;
    }

    protected Map<String, String> getParams()
            throws AuthFailureError {
        return params;
    };

   /* public String getBodyContentType() {
        return "application/json; charset=utf-8";
    }*/

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        // TODO Auto-generated method stub
        listener.onResponse(response);
        try {
            Log.e("URL",Url.toString());
            Log.e("Request",params.toString());
            Log.e("Response",response.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}