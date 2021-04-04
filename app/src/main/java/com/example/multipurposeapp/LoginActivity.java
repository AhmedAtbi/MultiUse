package com.example.multipurposeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.multiuseapp.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class LoginActivity extends AppCompatActivity {


    private TextInputLayout ed_email,ed_pwd;
    private TextView tv_register,msg_error_log;
    private String str_email,str_pwd;
    private Button btn_login;





    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login = findViewById(R.id.btn_login);

        ed_email = findViewById(R.id.ed_login);
        ed_pwd = findViewById(R.id.ed_pwd_login);
        tv_register = findViewById(R.id.tv_register);
        msg_error_log = findViewById(R.id.msg_error_log);
        sharedPreferences = getSharedPreferences("multiAppShared", Context.MODE_PRIVATE);
        checkUser();


        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);


                startActivity(i);
            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_email = ed_email.getEditText().getText().toString();
                str_pwd = ed_pwd.getEditText().getText().toString();
                if (str_pwd.isEmpty()||str_email.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Enter your infos to login", Toast.LENGTH_SHORT).show();
                }
                else{
                        checkUserLogin(str_email,str_pwd);

                }

            }
        });



    }



    public void checkUserLogin(String email,String pwd) {

        String api_login_url = "https://pure-crag-04729.herokuapp.com/api/auth/login";

        JsonObject body = new JsonObject();
        body.addProperty("email", email);
        body.addProperty("password", pwd);


        Ion.with(LoginActivity.this)
                .load(api_login_url)
                .setJsonObjectBody(body)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e != null) {
                            Toast.makeText(LoginActivity.this, "Something went wrong ", Toast.LENGTH_SHORT).show();
                            Log.e("TAG", "onCompleted: Something wen wrong ERROR = " + e.getMessage());
                        } else {

                            if (result.has("userId")) {


                                String userId = result.get("userId").getAsString();
                                sharedPreferences.edit().putBoolean("logged", true).putString("email_user", email).putString("email", email).apply();
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                i.putExtra("email", email);
                                startActivity(i);
                            }
                            else{
                                String error = result.get("error").getAsString();
                                msg_error_log.setText(error);
                                msg_error_log.setVisibility(View.VISIBLE);

                            }

                        }


                    }
                });
    }

    public void checkUser(){
        if (sharedPreferences.contains("logged")){
            if (sharedPreferences.getBoolean("logged",false))
            {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
        }

        else{
            sharedPreferences.edit().putBoolean("logged",false).apply();
        }
    }


}