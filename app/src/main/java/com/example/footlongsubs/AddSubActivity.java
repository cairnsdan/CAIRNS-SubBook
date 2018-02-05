package com.example.footlongsubs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * The AddSubActivity class collects user inputs for adding a new activity and passes them
 * back to the main activity. It is an I/O class and as such only passes it along as received.
 *
 * @author Daniel Cairns
 * @see MainActivity
 */
public class AddSubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub);

        // Read user input
        final EditText nameInput = (EditText) findViewById(R.id.nameField);
        final EditText dateInput = (EditText) findViewById(R.id.dateField);
        final EditText chargeInput = (EditText) findViewById(R.id.chargeField);
        final EditText commentInput = (EditText) findViewById(R.id.commentField);

        Button addSubButton = (Button) findViewById(R.id.editSubButton);
        addSubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Capture user input
                String name = nameInput.getText().toString();
                String date = dateInput.getText().toString();
                String charge = chargeInput.getText().toString();
                String comment = commentInput.getText().toString();

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
