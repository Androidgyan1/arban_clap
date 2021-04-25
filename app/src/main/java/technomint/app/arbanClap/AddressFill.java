package technomint.app.arbanClap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddressFill extends AppCompatActivity {

    EditText customer_id,country_name,city_name,state_name,address,service_date,service_time;

    String CUSTOMER_ID,COUNTRY_NAME,CITY_NAME,STATE_NAME,ADDRESS,SERVICE_DATE,SERVICE_TIME,LATITUDE,LONGITUDE;

    TextView latitude,longititude;

    Calendar calendar;
    int currentHour;
    int currentMinute;
    String amPm;

    TimePickerDialog timePickerDialog;

    ///location
    FusedLocationProviderClient fusedLocationProviderClient;

     Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_fill);



        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        customer_id = findViewById(R.id.customer_id);
        country_name = findViewById(R.id.country_name);
        city_name = findViewById(R.id.city_name);
        state_name = findViewById(R.id.state_name);
        address = findViewById(R.id.address);
        service_date = findViewById(R.id.service_date);
        service_time = findViewById(R.id.service_time);
        latitude = findViewById(R.id.latitude);
        longititude = findViewById(R.id.longititude);
        ///method

        getArea();

        ////datepicker

        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }
        };



        service_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddressFill.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        ///timepicker

        service_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);


                timePickerDialog = new TimePickerDialog(AddressFill.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        service_time.setText(String.format("%2d:%02d", hourOfDay, minutes) + amPm);
                    }
                },currentHour, currentMinute, false
                );
                timePickerDialog.show();
            }
        });






        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        String value = sharedPreferences.getString("value","");

        customer_id.setText(value);


    }

    private void updateLabel1() {


        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        service_date.setText(sdf.format(myCalendar.getTime()));

    }

    ////location

    private void getArea() {


        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location !=null){
                    try {
                        Geocoder geocoder = new Geocoder(AddressFill.this
                                , Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(),location.getLongitude(),1
                        );
                        latitude.setText(Html.fromHtml(
                                "</b><br></font>"
                                        + addresses.get(0).getLatitude()
                        ));
                        longititude.setText(Html.fromHtml(
                                "</b><br></font>"
                                        + addresses.get(0).getLongitude()
                        ));

                        country_name.setText(Html.fromHtml(
                                "<b></b><br></font>"
                                        + addresses.get(0).getCountryName()
                        ));

                        city_name.setText(Html.fromHtml(
                                "<b></b><br></font>"
                                        + addresses.get(0).getLocality()
                        ));

                        state_name.setText(Html.fromHtml(
                                "<b></b><br></font>"
                                        + addresses.get(0).getAdminArea()
                        ));



                          address.setText(Html.fromHtml(
                                "</b><br></font>"
                                        + addresses.get(0).getAddressLine(0)
                        ));





                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }
}