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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pium.chef.Other.Utility;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class Login extends AppCompatActivity {
    EditText User, Pass;
    LinearLayout activity_login;
    Button login;
    TextView signUp;
    private int progressStatus = 0;
    private Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        User = (EditText) findViewById(R.id.login_username);
        Pass = (EditText) findViewById(R.id.login_password);
        login = (Button) findViewById(R.id.login_btn_login);
        signUp = (TextView) findViewById(R.id.login_btn_signup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(view);
                ;
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(getApplicationContext(), SignUp.class);
                registerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(registerIntent);
            }
        });
    }

    public void loginUser(View view) {
        String username = User.getText().toString();
        String password = Pass.getText().toString();
        RequestParams params = new RequestParams();

        if (User.getText().length() == 0) {
            User.setError("Please Enter Your Username");
        } else if (Pass.getText().length() == 0) {
            Pass.setError("Please Enter Your Password");
        }

        if (Utility.isNotNull(username) && Utility.isNotNull(password)) {
            String paraUser = username;
            String paraPass = password;
            invokeWS(paraUser, paraPass);
        }
    }

    public void invokeWS(String username, String password) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://10.10.86.246:8080/ChefService/rest/users/get/auth/" + username + '/' + password, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                try {
                    if (response.equals("user") || response.equals("admin")) {
                        navigateToHomeActivity();
                    } else {
                        final ProgressDialog pd = new ProgressDialog(Login.this);
                        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        pd.setMessage("Authenticating....");
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
                                                Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_login), "Username or Password Is Incorrect", Snackbar.LENGTH_LONG);
                                                snackbar.show();
                                            }
                                        }
                                    });
                                }
                            }
                        }).start();
                    }
                } catch (Exception ex) {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_login), "Server's Response Might Be Invalid", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
    }

    public void navigateToHomeActivity() {
        passData();
    }

    public void passData()
    {
        final ProgressDialog pd = new ProgressDialog(Login.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Authenticating....");
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

                                String username = User.getText().toString();
                                Intent passDataIntent = new Intent(Login.this, Home.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("username", username);
                                passDataIntent.putExtras(bundle);
                                passDataIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(passDataIntent);
                            }
                        }
                    });
                }
            }
        }).start();
    }
}
