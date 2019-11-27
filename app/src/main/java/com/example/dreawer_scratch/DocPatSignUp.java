package com.example.dreawer_scratch;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class DocPatSignUp extends AppCompatActivity {
    private Button Signup, Login;
    EditText username, password, email;
    RadioGroup radioGroup;
    ProgressDialog progressDialog;
    String signupAs = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_pat_sign_up);



        radioGroup=findViewById(R.id.radioGroup);
        Signup = findViewById(R.id.signupId);
        username = findViewById(R.id.editTextUsernameId);
        password = findViewById(R.id.editTextpasswordId);
        email = findViewById(R.id.editTextEmailId);


        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ParseUser parseUser = new ParseUser();
                parseUser.setUsername(username.getText().toString());
                parseUser.setPassword(password.getText().toString());
                parseUser.setEmail(email.getText().toString());
                parseUser.put("LoginAS", signupAs);
                progressDialog = new ProgressDialog(DocPatSignUp.this);
                progressDialog.setMessage("Sign Up ...." + username.getText().toString());
                progressDialog.show();


                parseUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {

                            FancyToast.makeText(DocPatSignUp.this, parseUser.getUsername() +
                                            " is signUp Successfully !"
                                    , FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();

                            // parseUser.put("LoginAS", signupAs);
                        } else {
                            FancyToast.makeText(DocPatSignUp.this, e + "", FancyToast.
                                    LENGTH_LONG, FancyToast.ERROR, true).show();
                        }
                        progressDialog.dismiss();
                    }
                });
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_signup, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menuloginId:
                Intent intent = new Intent(DocPatSignUp.this, com.example.dreawer_scratch.Login.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radiobtnDoctor:
                if (checked)

                    signupAs = "Doctor";
                break;
            case R.id.radiobtnpatient:
                if (checked)
                    signupAs = "Patient";
                break;
        }
    }
}
