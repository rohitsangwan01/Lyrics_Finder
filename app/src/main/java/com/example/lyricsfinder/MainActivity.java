package com.example.lyricsfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class MainActivity extends AppCompatActivity {
    EditText edtSingerName,edtSongName;
    TextView edtLyrics;
    Button btnLyrics;
    ProgressBar pgBAr;

    AnimatedCircleLoadingView animatedCircleLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtSingerName = findViewById(R.id.edtSingerName);
        edtSongName =findViewById(R.id.edtSongName);
        edtLyrics = findViewById(R.id.edtLyrics);
        animatedCircleLoadingView = findViewById(R.id.circle_loading_view);
       // pgBAr = findViewById(R.id.pgBar);
       // pgBAr.setVisibility(View.GONE);
       // animatedCircleLoadingView.setVisibility(View.GONE);
        animatedCircleLoadingView.startDeterminate();


//        btnLyrics.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                edtLyrics.setText("");
//                pgBAr.setVisibility(View.VISIBLE);
//                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
//                // adele , skyfall
//
//                String url = "https://api.lyrics.ovh/v1/"+ edtSingerName.getText().toString() +"/" +edtSongName.getText().toString() ;
//                url.replace("","%20");
//                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                try{
//                                    edtLyrics.setText(response.getString("lyrics"));
//                                    pgBAr.setVisibility(View.GONE);
//
//                                }catch(JSONException e){
//                                    e.printStackTrace();
//                                    Toast.makeText(MainActivity.this,"Something Is Wrong",Toast.LENGTH_SHORT).show();
//                                }
//
//
//                            }
//                        }, new Response.ErrorListener() {
//
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                pgBAr.setVisibility(View.GONE);
//                                Toast.makeText(MainActivity.this,"No Result Found!, Please Check Your Search Again",Toast.LENGTH_SHORT).show();
//
//                            }
//                        });
//                requestQueue.add(jsonObjectRequest);
//
//
//
//            }
//        });
    }
    public void didTapButton(View view) {
        Button button = (Button)findViewById(R.id.button);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);

        button.startAnimation(myAnim);
        edtLyrics.setText("");
        animatedCircleLoadingView.setVisibility(View.VISIBLE);
        animatedCircleLoadingView.resetLoading();
        animatedCircleLoadingView.setPercent(10);
        animatedCircleLoadingView.startIndeterminate();

      //  pgBAr.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        // adele , skyfall

        String url = "https://api.lyrics.ovh/v1/"+ edtSingerName.getText().toString() +"/" +edtSongName.getText().toString() ;
        url.replace("","%20");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            edtLyrics.setText(response.getString("lyrics"));
                           // pgBAr.setVisibility(View.GONE);
                            animatedCircleLoadingView.stopOk();

                            animatedCircleLoadingView.setVisibility(View.GONE);

                        }catch(JSONException e){
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this,"Something Is Wrong",Toast.LENGTH_SHORT).show();
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //pgBAr.setVisibility(View.GONE);
                        animatedCircleLoadingView.stopFailure();

                       // animatedCircleLoadingView.resetLoading();
                        Toast.makeText(MainActivity.this,"No Result Found!, Please Check Your Search Again",Toast.LENGTH_SHORT).show();
                      //  animatedCircleLoadingView.setVisibility(View.GONE);

                    }
                });
        requestQueue.add(jsonObjectRequest);


    }
}
