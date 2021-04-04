package com.example.multipurposeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout ed_email,ed_pwd,ed_pwd_conf,ed_user;
    private TextView tv_confirm,tv_error;
    private Button btn_register;
    private String str_email,str_pwd,str_pwd_conf,str_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_register = findViewById(R.id.btn_register);
        ed_email = findViewById(R.id.ed_email_reg);
        ed_pwd = findViewById(R.id.ed_pwd_reg);
        ed_pwd_conf = findViewById(R.id.ed_pwd_conf);
        ed_user = findViewById(R.id.ed_user_reg);
        tv_confirm = findViewById(R.id.tv_confirm);
        tv_error = findViewById(R.id.msg_error);



        ed_pwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                tv_confirm.setText("Please confirm your password");
                tv_confirm.setVisibility(View.VISIBLE);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_user.getEditText().getText().toString().isEmpty()||
                        ed_email.getEditText().getText().toString().isEmpty()||
                        ed_pwd.getEditText().getText().toString().isEmpty()||
                        ed_pwd_conf.getEditText().getText().toString().isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please Complete the form", Toast.LENGTH_SHORT).show();

                }
                else
                {
                   str_username = ed_user.getEditText().getText().toString();
                   str_email = ed_email.getEditText().getText().toString();
                   str_pwd = ed_pwd.getEditText().getText().toString();
                   str_pwd_conf = ed_pwd_conf.getEditText().getText().toString();
                    if (str_pwd.equals(str_pwd_conf)){
                        signUp(str_email,str_username,str_pwd,str_pwd);
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this, "Password is not matching...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    public void signUp(String email,String username,String pwd, String conf_pwd) {

        String api_singup_url = "https://pure-crag-04729.herokuapp.com/api/auth/signup";
        try {
            JsonObject jsonBody = new JsonObject();
            jsonBody.addProperty("username", username);
            jsonBody.addProperty("email", email);
            jsonBody.addProperty("password", pwd);

            Ion.with(RegisterActivity.this)
                    .load(api_singup_url).setJsonObjectBody(jsonBody)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            if (e != null) {
                                Toast.makeText(RegisterActivity.this, "Something went wrong ... !", Toast.LENGTH_SHORT).show();
                                Log.e("TAG", "onCompleted: Something went wrong ... ! error = " + e.getMessage());
                            } else {
//                            scenarios  :
//                            1- Correct user signup
//                            2- email exist : expected `email` to be unique

//                                scenario 1
                                if (!result.has("error")) {
                                    String msg = result.get("message").getAsString();
                                    Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    finish();
                                } else {
                                    tv_error.setText(email + " exist ... !");
                                    tv_error.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    });


        } catch (Exception e) {
            Log.e("TAG", "SignUpUser: ERROR " + e.getMessage());
        }


    }
}




