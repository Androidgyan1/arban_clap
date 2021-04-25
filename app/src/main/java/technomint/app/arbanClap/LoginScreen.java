package technomint.app.arbanClap;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;
import java.util.Map;

import technomint.app.arbanClap.Config.Config;

public class LoginScreen extends AppCompatActivity {

    private CountryCodePicker countryCodePicker;
    private EditText number;
    private Button next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        countryCodePicker = findViewById(R.id.ccp);
        number = findViewById(R.id.editText_carrierNumber);
        next = findViewById(R.id.next);
        countryCodePicker.registerCarrierNumberEditText(number);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String mobile= number.getText().toString();

                SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("value", mobile);
                editor.apply();

                RequestQueue queue = Volley.newRequestQueue(LoginScreen.this);

                if (TextUtils.isEmpty(number.getText().toString())) {
                    Toast.makeText(LoginScreen.this, "Enter No ....", Toast.LENGTH_SHORT).show();
                } else if (number.getText().toString().replace(" ", "").length() != 10) {
                    Toast.makeText(LoginScreen.this, "Enter Correct No ...", Toast.LENGTH_SHORT).show();
                } else {

                    StringRequest request = new StringRequest(Request.Method.POST, Config.register, new Response.Listener<String>() {

                        @Override

                        public void onResponse(String response) {


                           Intent intent = new Intent(LoginScreen.this, OtpScreen.class);
                            intent.putExtra("number", countryCodePicker.getFullNumberWithPlus().replace(" ", ""));
                            startActivity(intent);
                            finish();

                            Toast.makeText(LoginScreen.this, "Data Uploaded", Toast.LENGTH_SHORT).show();

                            Log.i("My success",""+response);


                        }

                    }, new Response.ErrorListener() {

                        @Override

                        public void onErrorResponse(VolleyError error) {


                            Toast.makeText(LoginScreen.this, "my error :"+error, Toast.LENGTH_LONG).show();

                            Log.i("My error","error"+error);

                        }

                    }){

                        @Override

                        protected Map<String, String> getParams() throws AuthFailureError {


                            Map<String,String> map = new HashMap<String, String>();

                            map.put("mobile_no",mobile);


                            return map;

                        }

                    };

                    queue.add(request);



                }
            }
        });

    }



}