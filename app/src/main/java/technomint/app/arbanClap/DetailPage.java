package technomint.app.arbanClap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class DetailPage extends AppCompatActivity {

    ImageView detail_image;

    public String DETAIL_IMAGE,DETAIL_NAME,DETAIL_SERVICEAMMOUNT,DETAIL_DESCRIPATION,DETAIL_SERVICETYPE,SUB_CATEGORY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);


        DETAIL_NAME  = getIntent().getExtras().getString("service_name");
        DETAIL_IMAGE = getIntent().getExtras().getString("service_image") ;
        SUB_CATEGORY = getIntent().getExtras().getString("subcategory_id") ;
       // DETAIL_SERVICEAMMOUNT = getIntent().getExtras().getString("service_amount") ;
        DETAIL_DESCRIPATION = getIntent().getExtras().getString("service_desc") ;
       // DETAIL_SERVICETYPE = getIntent().getExtras().getString("service_type") ;


      TextView  detail_name = findViewById(R.id.detail_name);
       // TextView detail_serviceammount = findViewById(R.id.detail_serviceammount);
        TextView detail_descripation = findViewById(R.id.detail_descripation);
       //TextView detail_servicetype = findViewById(R.id.detail_servicetype);

        detail_image = findViewById(R.id.detail_image);


        detail_name.setText(DETAIL_NAME);
      //  detail_serviceammount.setText(DETAIL_SERVICEAMMOUNT);
        detail_descripation.setText(DETAIL_DESCRIPATION);
       // detail_servicetype.setText(DETAIL_SERVICETYPE);


        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);


        // set image using Glide
        Glide.with(this).load(DETAIL_IMAGE).apply(requestOptions).into(detail_image);
    }

    public void book(View view) {
        Intent i = new Intent(DetailPage.this,AddressFill.class);
        startActivity(i);

    }
}