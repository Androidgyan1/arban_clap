package technomint.app.arbanClap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import technomint.app.arbanClap.Adapter.CategorySubAdapter;
import technomint.app.arbanClap.Adapter.SubsubCategoryAdapter;
import technomint.app.arbanClap.Config.Config;
import technomint.app.arbanClap.Model.CategorySubModel;
import technomint.app.arbanClap.Model.SubsubCategoryModel;

public class SubsubCategory extends AppCompatActivity {

    private List<SubsubCategoryModel> lstAnimecategory = new ArrayList<>();
    private RecyclerView category_sublist1 ;


    private TextView service_name;

    public String name, image_url,subcategory_id,category_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subsub_category);

        service_name = findViewById(R.id.subcategory);

        category_sublist1 = findViewById(R.id.subcategory_recycle);

        name  = getIntent().getExtras().getString("subcategory_name");
        image_url = getIntent().getExtras().getString("subcategory_image") ;
        subcategory_id = getIntent().getExtras().getString("subcategory_id") ;
        category_id = getIntent().getExtras().getString("category_id") ;


/////method
        getCourseDetails(service_name.getText().toString());




        TextView tv_name = findViewById(R.id.subcategory_text);
        ImageView img = findViewById(R.id.subcategory_image);




        tv_name.setText(name);


        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);


        // set image using Glide
        Glide.with(this).load(image_url).apply(requestOptions).into(img);


    }

    private void getCourseDetails(String toString) {


        // url to post our data

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(SubsubCategory.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, Config.subsubcategory, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    if (response != null || response.length() > 0) {
                        // on below line passing our response to json object.
                        JSONObject jsonObject1 = new JSONObject(response);

                        JSONArray heroArray = jsonObject1.getJSONArray("data");
                        // on below line we are checking if the response is null or not.


                        for (int i = 0; i < heroArray.length(); i++) {
                            //getting the json object of the particular index inside the array
                            jsonObject1 = heroArray.getJSONObject(i);

                            SubsubCategoryModel categoryModel =  new SubsubCategoryModel();
                            //categoryModel.setCategoryid(jsonObject1.getString("category_id"));
                            categoryModel.setId(jsonObject1.getString("subcategory_id"));
                            categoryModel.setImageUrl(jsonObject1.getString("service_image"));
                            categoryModel.setName(jsonObject1.getString("service_name"));


                            //adding the hero to herolist
                            lstAnimecategory.add(categoryModel);
                            // on below line we are displaying
                        }
                        // a success toast message.

                        setRvadapterCategory(lstAnimecategory);

                    }
                    }
                    catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("error", error.toString());
                // method to handle errors.
                Toast.makeText(SubsubCategory.this, "Fail to get course" + error, Toast.LENGTH_SHORT).show();
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
                params.put("category_id", "2");
                params.put("subcategory_id", "4");
               // params.put("service_name", name);


                // at last we are returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);


    }

    private void setRvadapterCategory(List<SubsubCategoryModel> lstAnimecategory) {

        final LinearLayoutManager manager1 = new LinearLayoutManager(this);
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        category_sublist1.setLayoutManager(manager1);

        SubsubCategoryAdapter myAdaptercategory = new SubsubCategoryAdapter(this,lstAnimecategory) ;
        category_sublist1.setAdapter(myAdaptercategory);


    }
}