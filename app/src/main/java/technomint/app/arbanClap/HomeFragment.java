package technomint.app.arbanClap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import technomint.app.arbanClap.Adapter.CategoryAdapter;
import technomint.app.arbanClap.Adapter.RvAdapter;
import technomint.app.arbanClap.Config.Config;
import technomint.app.arbanClap.Model.BestModel;
import technomint.app.arbanClap.Model.CategoryModel;


public class HomeFragment extends Fragment {


    ////category_recycle

    ProgressBar homeprogress;

    private List<CategoryModel> lstAnimecategory = new ArrayList<>();
    private RecyclerView category_rv ;



    private List<BestModel> lstAnime = new ArrayList<>();
    private RecyclerView myrv;

    String categoryname;

    CarouselView carouselView;
    int[] sampleImages = {R.drawable.banner_image};

    TextView addres;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        homeprogress = view.findViewById(R.id.homeprogress);

        ///category_item


        category_rv = view.findViewById(R.id.category_rv);
        jsoncallCategory();


        myrv = view.findViewById(R.id.rv);
        jsoncall();

        addres = view.findViewById(R.id.location);
        addres.setSelected(true);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        if (ActivityCompat.checkSelfPermission(getActivity()
                , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location();

        } else {
            ActivityCompat.requestPermissions(getActivity()
                    , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }


        ///slider
        carouselView = view.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);


        return view;
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    private void Location() {

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(getActivity()
                                , Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );

                        addres.setText(Html.fromHtml(
                                "<font color = '#6200EE'><br></font>"
                                        + addresses.get(0).getAddressLine(0)
                        ));


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    public void jsoncall() {

        homeprogress.setVisibility(View.VISIBLE);
        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Method.GET, Config.URL_JSON,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion


                        try {


                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray heroArray = obj.getJSONArray("data");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < heroArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject heroObject = heroArray.getJSONObject(i);

                                //creating a hero object and giving them the values from json object
                                BestModel hero1 = new BestModel(heroObject.getString("service_image"));

                                //adding the hero to herolist
                                lstAnime.add(hero1);


                            }

                            setRvadapter(lstAnime);

                            homeprogress.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();

                            homeprogress.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    public void setRvadapter (List<BestModel> lst) {

        final LinearLayoutManager manager1 = new LinearLayoutManager(getActivity());
        manager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        myrv.setLayoutManager(manager1);

        RvAdapter myAdapter = new RvAdapter(getActivity(),lst) ;
        myrv.setAdapter(myAdapter);




    }

    ///method jsoncategory

    public void jsoncallCategory() {

        final HashMap<String, Integer> params = new HashMap<>();
        params.put("category_id",getId());



        //creating a string request to send request to the url
        final StringRequest JsonObject = new StringRequest(Method.GET, Config.URL_CATEGORY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion


                        try {
                            //getting the whole json object from the response

                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray heroArray = obj.getJSONArray("data");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < heroArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject heroObject = heroArray.getJSONObject(i);

                                //creating a hero object and giving them the values from json object

                                CategoryModel categoryModel =  new CategoryModel();
                                categoryModel.setId(heroObject.getString("id"));
                                categoryModel.setImageUrl(heroObject.getString("category_image"));
                                categoryModel.setName(heroObject.getString("category_name"));

                                //adding the hero to herolist
                                lstAnimecategory.add(categoryModel);

                                Log.e("sucess",response);

                            }

                            setRvadapterCategory(lstAnimecategory);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(JsonObject);
    }
    public void setRvadapterCategory (List<CategoryModel> lst) {

        final LinearLayoutManager manager1 = new LinearLayoutManager(getActivity());
        manager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        category_rv.setLayoutManager(manager1);

        CategoryAdapter myAdaptercategory = new CategoryAdapter(getActivity(),lst) ;
        category_rv.setAdapter(myAdaptercategory);




    }


}