package com.kae27.savetomemory;

import android.os.Environment;
import android.os.Message;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class MainActivity extends AppCompatActivity {

    public String path = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/kae27";
    TextView txtV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File dir = new File(path);
        dir.mkdirs();


        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        Button btnDownload = (Button) findViewById(R.id.btnDownload);
        Button btnRead= (Button) findViewById(R.id.btnRead);
        txtV = (TextView) findViewById(R.id.tv1);

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    File file = new File(path+"/stateArea.txt");

                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line;
                    line = br.readLine();
                    br.close();
                    file = null;

                    txtV.setText(line);

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Failed! = " + e.getMessage() ,
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnDownload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                int count;
                String from = "http://128.199.87.10/stateArea.txt";
                File file =new File(path+"/stateArea.txt");

                try {
                    URL url = new URL(from);
                    URLConnection conexion = url.openConnection();
                    conexion.connect();

                    int lenghtOfFile = conexion.getContentLength(); // Size of file
                    InputStream input = new BufferedInputStream(url.openStream());

                    //String fileName = from.substring(from.lastIndexOf('/')+1, from.length());
                    OutputStream output = new FileOutputStream(file); // save to parh sd card

                    byte data[] = new byte[1024];
                    while ((count = input.read(data)) != -1) {
                        output.write(data, 0, count);
                    }

                    output.flush();
                    output.close();
                    input.close();

                    Toast.makeText(MainActivity.this,"File has been downloaded. " , Toast.LENGTH_LONG).show();

                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });

    }
}
