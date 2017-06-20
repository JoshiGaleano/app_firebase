package com.jgaleano.firebase;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NotificationActivity extends AppCompatActivity {
    private TextView titulo;
    private TextView subtitulo;
    private Button btnNavegar;
    private Button btnReproducir;
    private ImageView imageView;
    private Bitmap bitmap;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        imageView = (ImageView) findViewById(R.id.image);

        titulo = (TextView) findViewById(R.id.titulo);
        subtitulo = (TextView) findViewById(R.id.subtitulo);
        btnNavegar = (Button) findViewById(R.id.navegar);
        btnReproducir = (Button) findViewById(R.id.reproducir);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.campana);
        btnReproducir.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                mp.start();
            }
        });

        btnNavegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.google.com:";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        String title = "";
        String subtitle = "";
        String image = "";

        Intent startingIntent = getIntent();
        if (startingIntent != null) {
            title = startingIntent.getStringExtra("titulo");
            subtitle = startingIntent.getStringExtra("subtitulo");
            image = startingIntent.getStringExtra("imagen");
        }

        titulo.setText(title);
        subtitulo.setText(subtitle);

        Picasso.with(getApplicationContext()).load(image).into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }

}
