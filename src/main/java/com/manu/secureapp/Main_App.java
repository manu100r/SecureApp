package com.manu.secureapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_App extends AppCompatActivity {
    private TextView result_accounts;
    private RequestQueue mQueue;
    private Object JsonArrayRequest;
    private static final String file_account = "account.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__app);

        result_accounts = findViewById(R.id.TextView_Accounts);
        Button button_refresh = findViewById(R.id.button_Refresh);

        mQueue = Volley.newRequestQueue(this);
    }

    private void Refresh(){
        result_accounts.clearComposingText();
        String url = getString(R.string.url_API);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONArray jsonArray = response;

                        for(int i = 0; i < jsonArray.length(); i++){
                            try {
                                JSONObject account = jsonArray.getJSONObject(i);
                                String id = account.getString("id");
                                String accountName = account.getString("accountName");
                                String amount = account.getString("amount");
                                String iban = account.getString("iban");
                                String currency = account.getString("currency");

                                result_accounts.append("Account n°" + id + " : " + accountName + "\n" + "IBAN" + iban + "\n" + amount + " " + currency + "\n\n");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

    public void Refresh_btn(View v){
        Refresh();
        Save(result_accounts.getText().toString());
        Load();
    }

    private void Save(String s) {
        FileOutputStream save_file = null;
        try {
            save_file = openFileOutput(file_account, MODE_PRIVATE); //only the app has the access to the file
            save_file.write(s.getBytes());
            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + file_account,
                    Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (save_file != null) {
                try {
                    save_file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void Load() {
        FileInputStream load_file = null;
        try {
            load_file = openFileInput(file_account);
            InputStreamReader isr = new InputStreamReader(load_file);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }
            result_accounts.setText(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (load_file != null) {
                try {
                    load_file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}