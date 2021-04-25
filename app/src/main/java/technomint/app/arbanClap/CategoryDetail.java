package technomint.app.arbanClap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import technomint.app.arbanClap.Adapter.CategorySubAdapter;
import technomint.app.arbanClap.Config.Config;
import technomint.app.arbanClap.Model.CategoryModel;
import technomint.app.arbanClap.Model.CategorySubModel;
import technomint.app.arbanClap.Network.CustomVolleyJsonRequest;

public class CategoryDetail extends AppCompatActivity {


    ProgressBar homeprogress;


    ////category_recycle
    private List<CategorySubModel> lstAnimecategory = new ArrayList<>();
    private RecyclerView category_sublist ;

    public String name, image_url,category_id;

    TextView subcategoery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);


        homeprogress = findViewById(R.id.homeprogress);


        subcategoery = findViewById(R.id.subcategory);


        category_sublist = findViewById(R.id.category_sublist);
         name  = getIntent().getExtras().getString("category_name");
         image_url = getIntent().getExtras().getString("category_image") ;
         category_id = getIntent().getExtras().getString("category_id") ;

        // Recieve data
        // calling method to load data.
        getCourseDetails(name.toString());

        TextView tv_name = findViewById(R.id.category_detail);
        ImageView img = findViewById(R.id.category_image);


        // setting values to each view

        tv_name.setText(name);


        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);


        // set image using Glide
        Glide.with(this).load(image_url).apply(requestOptions).into(img);
    }

    private void getCourseDetails(String toString) {

        homeprogress.setVisibility(View.VISIBLE);

        // url to post our data

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(CategoryDetail.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, Config.subcategory, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // on below line passing our response to json object.
                    JSONObject jsonObject1 = new JSONObject(response);

                    JSONArray heroArray = jsonObject1.getJSONArray("data");
                    // on below line we are checking if the response is null or not.

                    for (int i = 0; i < heroArray.length(); i++) {
                        //getting the json object of the particular index inside the array
                        jsonObject1 = heroArray.getJSONObject(i);

                        CategorySubModel categoryModel =  new CategorySubModel();

                        categoryModel.setId(jsonObject1.getString("category_id"));
                        categoryModel.setImageUrl(jsonObject1.getString("subcategory_image"));
                        categoryModel.setName(jsonObject1.getString("subcategory_name"));


                        //adding the hero to herolist
                        lstAnimecategory.add(categoryModel);
                        // on below line we are displaying
                    }
                    // a success toast message.

                    setRvadapterCategory(lstAnimecategory);

                    homeprogress.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    homeprogress.setVisibility(View.GONE);
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(CategoryDetail.this, "Fail to get course" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                // as we are passing data in the form of url encoded
                // so we are passing the content type below
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {

                // below line we are creating a map for storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key and value pair to our parameters.
                params.put("category_id", category_id);

                // at last we are returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }



    ///method jsoncategory


    public void setRvadapterCategory (List<CategorySubModel> lst) {

        final LinearLayoutManager manager1 = new LinearLayoutManager(this);
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        category_sublist.setLayoutManager(manager1);

        CategorySubAdapter myAdaptercategory = new CategorySubAdapter(this,lst) ;
        category_sublist.setAdapter(myAdaptercategory);




    }




}