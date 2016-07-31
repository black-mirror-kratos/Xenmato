package com.citrix.xen.xenfood;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by pawankumard on 7/9/2016.
 */
public class RatingActivity extends Activity {

    int value;
    private RatingBar ratingBar;
    private Button submitButton;
    //private TextView ratingTag;
    //private ImageView photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_my);
        setContentView(R.layout.rating_view);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        submitButton = (Button) findViewById(R.id.rateButton);
        //ratingTag = (TextView) findViewById(R.id.rating);
        //photo = (ImageView) findViewById(R.id.photo) ;

        Bundle b = getIntent().getExtras();
        value = -1; // or other values
        if(b != null){
            value = b.getInt("key");
            //ratingTag.setText(b.getString("rating"));
        }
        Log.d("Key ", "" + value);

        //photo.setColorFilter(Color.rgb(200, 200, 220), android.graphics.PorterDuff.Mode.MULTIPLY);


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                submitButton.setText("" + rating);
                if(rating <= 2.5){
                    submitButton.setBackgroundColor(Color.parseColor("#C9960C"));
                }
                else{
                    submitButton.setBackgroundColor(Color.parseColor("#147700"));
                }

            }
        });
    }

    void ratingSubmit(View view){
        Log.d("id check ", ""+value + ratingBar.getRating());
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://10.252.249.82:3000/rate?id="+value+"&vote="+ratingBar.getRating();
        client.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Tabs.tabsviewPager.setCurrentItem(2);
                Tabs.tabsviewPager.setCurrentItem(0);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }
}
