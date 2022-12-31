package com.example.tugaspertemuanke4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //View Object
    private Button buttonscanning;
    private TextView textViewNama,textViewClass,textViewId;
//ar scanning object
    private IntentIntegrator qrScan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //view Object
        buttonscanning = (Button) findViewById(R.id.buttonscan);
        textViewNama = (TextView) findViewById(R.id.textViewNama);
        textViewClass = (TextView) findViewById(R.id.textViewKelas);
        textViewId = (TextView) findViewById(R.id.textViewNim);
        //inisialisasi scan Object
        qrScan = new IntentIntegrator(this);

        //implementasi onclick Listener
        buttonscanning.setOnClickListener(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result =IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //jika qrcode tidak ada sama sekali
            if (result.getContents()==null) {
                Toast.makeText(this, "Hasil Sacning Tidak Ada", Toast.LENGTH_LONG).show();
            } else if (Patterns.WEB_URL.matcher(result.getContents()).matches()) {
                Intent visiturl = new Intent(Intent.ACTION_VIEW, Uri.parse(result.getContents()));
                startActivity(visiturl);
            }else if ((Patterns.PHONE.matcher(result.getContents()).matches())){
                String telp = String.valueOf(result.getContents());

            }else {
                //jika qrcode tidak di temukan datanya
                try {
                    //konversi datanya ke json
                    JSONObject obj = new JSONObject(result.getContents());
                    //dishare nilai datanya ke text view
                    TextViewNama.setText(obj.getString("nama"));
                    textViewClass.setText(obj.getString("kelas"));
                    textViewId.setText(obj.getString("nim"));
                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(this, result.getContents(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }else {
            super.onActivityResult(requestCode,resultCode,data);

        }

    }

    @Override
    public void onClick(View v) {
        //inisialisasi qrcode scanning
        qrScan.initiateScan();

    }

    }

