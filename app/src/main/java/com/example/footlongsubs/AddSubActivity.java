package com.example.footlongsubs;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddSubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Read user input
        final EditText nameInput = (EditText) findViewById(R.id.nameField);
        final EditText dateInput = (EditText) findViewById(R.id.dateField);
        final EditText chargeInput = (EditText) findViewById(R.id.chargeField);
        final EditText commentInput = (EditText) findViewById(R.id.commentField);

        Button addSubButton = (Button) findViewById(R.id.addSubButton);
        addSubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameInput.getText().toString();
                String date = dateInput.getText().toString();
                String charge = chargeInput.getText().toString();
                String comment = commentInput.getText().toString();

                // Verify user input

                // Return input via returnIntent
                Intent returnIntent = new Intent();
                returnIntent.putExtra("name", name);
                returnIntent.putExtra("date", date);
                returnIntent.putExtra("charge", charge);
                returnIntent.putExtra("comment", comment);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }

}
