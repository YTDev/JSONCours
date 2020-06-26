package com.example.jsoncours;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Spinner spinner,spinner2;

    ArrayList<String> cList=new ArrayList<>();
    ArrayList<String> cList2=new ArrayList<>();
    JSONArray T=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.txt);

        try {
            InputStream inputS=getResources().getAssets().open("produits.json");
            InputStreamReader inputSR=new InputStreamReader(inputS);
            BufferedReader reader =new BufferedReader(inputSR);

            StringBuilder sb=new StringBuilder();
            String ligne;
            while((ligne=reader.readLine())!=null){
                sb.append(ligne);
            }
            inputS.close();
            String data=sb.toString();


            T=new JSONArray(data);
            if(T!=null){
                for(int i=0; i<T.length();i++){
                    cList.add(T.getJSONObject(i).getString("nom"));
                }
            }


            //2em spinnner

            InputStream stream=getResources().getAssets().open("produits2.json");
            int size=stream.available();
            byte[] byteData=new byte[size];
            stream.read(byteData);
            stream.close();
            String Data=new String(byteData,"UTF-8");

            JSONObject JO=new JSONObject(Data);
            if(JO!=null){
                JSONArray T2=JO.getJSONArray("produits");
                for(int i=0;i<T2.length();i++){
                    cList2.add(T2.getJSONObject(i).getString("nom"));
                }
            }
            spinner2=findViewById(R.id.spinner2);
            ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cList2);
            spinner2.setAdapter(adapter2);



        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        spinner=findViewById(R.id.spinner);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cList);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Toast.makeText(getApplicationContext(), String.valueOf(T.getJSONObject(position).getDouble("prix")),Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }
}
