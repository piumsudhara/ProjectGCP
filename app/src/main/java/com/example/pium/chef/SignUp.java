package com.example.pium.chef;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.pium.chef.Other.Utility;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;

public class SignUp extends AppCompatActivity {
    EditText User, Pass, Conpass;
    Button Signupbutton;
    private int progressStatus = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        User = (EditText) findViewById(R.id.signup_user);
        Pass = (EditText) findViewById(R.id.signup_pass);
        Conpass = (EditText) findViewById(R.id.signup_cpass);
        Signupbutton = (Button)findViewById(R.id.signup_btn);

        Signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    signUpUser(view);
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    public void signUpUser(View view) throws UnsupportedEncodingException {
        String username = User.getText().toString();
        String password = Pass.getText().toString();
        String conPassword = Conpass.getText().toString();

        if(User.getText().length() == 0)
        {
            User.setError("Please Enter Username");
        }
        else if(Pass.getText().length() == 0)
        {
            Pass.setError("Please Enter Password");
        }
        else if(Conpass.getText().length() == 0)
        {
            Conpass.setError("Please Enter Confirm Password");
        }

        if (Utility.isNotNull(username) && Utility.isNotNull(password) && Utility.isNotNull(conPassword)) {
            if (password.equals(conPassword)) {
                String paraUser = username;
                String paraPass = password;
                invokeWS(paraUser, paraPass);
            }
            else
            {
                Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_sign_up), "Passwords Not Matching!!!", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
    }
    public void invokeWS(String username, String password) throws UnsupportedEncodingException
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://192.168.1.103:8080/ChefService/rest/android/insert/"+username+'/'+password, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String response)
            {
                try
                {
                    if(response.equals("true"))
                    {
                        navigateToLoginActivity();
                    }
                    else
                    {
                        final ProgressDialog pd = new ProgressDialog(SignUp.this);
                        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        pd.setMessage("Signing Up....");
                        pd.setIndeterminate(false);
                        pd.show();
                        progressStatus = 0;

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while (progressStatus < 100) {
                                    progressStatus += 1;
                                    try {
                                        Thread.sleep(50);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            pd.setProgress(progressStatus);

                                            if (progressStatus == 50) {
                                                pd.dismiss();
                                                Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_sign_up), "Username Already Taken.Try Another!", Snackbar.LENGTH_LONG);
                                                snackbar.show();
                                            }
                                        }
                                    });
                                }
                            }
                        }).start();
                    }
                }
                catch (Exception ex)
                {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_sign_up), "Server's response might be invalid!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    ex.printStackTrace();
                }
            }
        });
    }
    public void navigateToLoginActivity()
    {

        final ProgressDialog pd = new ProgressDialog(SignUp.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Signing Up....");
        pd.setIndeterminate(false);
        pd.show();
        progressStatus = 0;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            pd.setProgress(progressStatus);

                            if (progressStatus == 50) {
                                pd.dismiss();

                                Intent loginIntent = new Intent(getApplicationContext(), Login.class);
                                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(loginIntent);
                            }
                        }
                    });
                }
            }
        }).start();
    }
}
