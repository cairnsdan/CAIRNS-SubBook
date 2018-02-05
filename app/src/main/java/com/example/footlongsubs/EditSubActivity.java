package com.example.footlongsubs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * The EditSubActivity class collects gives the user the ability to edit a subscription's
 * information or delete it outright from the SubBook
 *
 * @author Daniel Cairns
 * @see MainActivity
 * @see AddSubActivity
 */
public class EditSubActivity extends AppCompatActivity {

    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sub);
        Intent intent = getIntent();
        subscription = (Subscription) intent.getSerializableExtra("subscription");
        Log.i("test", subscription.getName());

        // Fill text boxes with existing data
        EditText nameField = (EditText) findViewById(R.id.nameField_e);
        nameField.setText(subscription.getName(), TextView.BufferType.EDITABLE);
        EditText dateField = (EditText) findViewById(R.id.dateField_e);
        dateField.setText(subscription.getDate_s(), TextView.BufferType.EDITABLE);
        EditText chargeField = (EditText) findViewById(R.id.chargeField_e);
        chargeField.setText(subscription.getCharge_s(), TextView.BufferType.EDITABLE);
        EditText commentField = (EditText) findViewById(R.id.commentField_e);
        commentField.setText(subscription.getComment(), TextView.BufferType.EDITABLE);

        // Handle Edit Subscription Button
        Button editSubButton = (Button) findViewById(R.id.editSubButton);
        editSubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Read user input
                final EditText nameInput = (EditText) findViewById(R.id.nameField);
                final EditText dateInput = (EditText) findViewById(R.id.dateField);
                final EditText chargeInput = (EditText) findViewById(R.id.chargeField);
                final EditText commentInput = (EditText) findViewById(R.id.commentField);

                // Capture user input
                String name = nameInput.getText().toString();
                String date = dateInput.getText().toString();
                String charge = chargeInput.getText().toString();
                String comment = commentInput.getText().toString();

                // Return input via returnIntent
                Intent returnIntent = new Intent();
                returnIntent.putExtra("subscription", subscription);
                returnIntent.putExtra("deleted", "false");
                returnIntent.putExtra("name", name);
                returnIntent.putExtra("date", date);
                returnIntent.putExtra("charge", charge);
                returnIntent.putExtra("comment", comment);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });

        // Handle Delete Subscription Button
        Button deleteSubButton = (Button) findViewById(R.id.deleteSubButton);
        deleteSubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Return subscription object via returnIntent
                Intent returnIntent = new Intent();
                returnIntent.putExtra("subscription", subscription);
                returnIntent.putExtra("deleted", "true");
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
