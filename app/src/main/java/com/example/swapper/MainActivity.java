package com.example.swapper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText fname,lname,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fname = (EditText) findViewById(R.id.etfname);
        lname = (EditText) findViewById(R.id.etlname);
        email = (EditText) findViewById(R.id.etmail);

        Button b1 = (Button) findViewById(R.id.button);

        b1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v){
                onRegister();
            }

        });

    }

    private void onRegister() {

        String username = fname.getText().toString();
        String surname = lname.getText().toString();
        String mail = email.getText().toString();

        String type = "Register";

        Backgroundworker bgworker = new Backgroundworker(this);
        bgworker.execute(type,username,surname,mail);

         //Intent it = new Intent(this,Verify.class);

       // startActivity(it);

    }
}
