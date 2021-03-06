package com.example.footlongsubs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * The MainActivity is the most important part of the app and bridges the UI and data elements
 * It is reponsible for maintaining persistence, responding to button presses, and displaying
 * the list and sum total.
 *
 * The only thing missing is the edit activity switches
 *
 * @author Daniel Cairns
 * @see AllSubscriptions
 * @see Subscription
 * @see AddSubActivity
 */
public class MainActivity extends AppCompatActivity {

    private AllSubscriptions subList;
    private ListView displayList;
    private CustomAdapter displayListAdapter;

    static final int ADD_SUBSCRIPTION = 1;
    static final int EDIT_SUBSCRIPTION = 2;

    // Filename for saved data
    private static final String FILENAME = "footlongsubs.sav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup list display
        displayList = (ListView) findViewById(R.id.displayList);

        displayList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, EditSubActivity.class);
                Subscription clickedSub = (Subscription) adapterView.getItemAtPosition(i);
                intent.putExtra("subscription", clickedSub);
                startActivityForResult(intent, EDIT_SUBSCRIPTION);
            }
        });

        // Floating action button allows for addition of new subscription.
        // Still working on finding a more appropriate icon.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddSubActivity.class);
                startActivityForResult(intent, ADD_SUBSCRIPTION);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
        updateTotal();

        displayListAdapter = new CustomAdapter(this, subList);
        displayList.setAdapter(displayListAdapter);


    }


    /**
     * The onActivityResult method receives the user input data from other activity screens and
     * sends it to an AllSubscriptions method for handling. The data is transferred in Strings
     * to keep thing simple, and type conversions are done later.
     *
     * Currently, it handles the input from adding a new subscription (requestCode ADD_SUBSCRIPTION)
     * Once the ListView is implemented properly, this method will also be responsible for
     * passing the data for the edit and delete functions.
     *
     * @see AddSubActivity
     *
     * @param requestCode Code representing type of request returned, either addition or edit/delete
     * @param resultCode Code notifying whether the activity result was ok or not
     * @param data Intent which holds the strings from user input to be transferred
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_SUBSCRIPTION) {
            if (resultCode == RESULT_OK) {
                // Get data from intent
                String name = data.getStringExtra("name");
                String date = data.getStringExtra("date");
                String charge = data.getStringExtra("charge");
                String comment = data.getStringExtra("comment");

                try {
                    subList.addSubscription(name, date, charge, comment);
                    updateTotal();
                    displayListAdapter.notifyDataSetChanged();
                    saveInFile();
                } catch (InputErrorException e) {
                    // Notify user that their input was rejected
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                    alertBuilder.setTitle("ERROR");
                    alertBuilder.setMessage("Incorrect input format detected, " +
                            "subscription not added");
                    alertBuilder.setPositiveButton("OK", null);
                    alertBuilder.show();
                }
            }
        }
        if (requestCode == EDIT_SUBSCRIPTION) {
            if (resultCode == RESULT_OK) {
                // If deleted, delete
                if (data.getStringExtra("deleted")=="true") {
                    subList.deleteSubscription(
                        (Subscription) data.getSerializableExtra("subscription")
                    );

                    // Update Lists
                    updateTotal();
                    displayListAdapter.notifyDataSetChanged();
                    saveInFile();;
                }
                // Else, save user edits
                else {
                    Subscription editedSub =
                            (Subscription) data.getSerializableExtra("subscription");
                    String name = data.getStringExtra("name");
                    String date = data.getStringExtra("date");
                    String charge = data.getStringExtra("charge");
                    String comment = data.getStringExtra("comment");

                    try {
                        editedSub.setName(name);
                        editedSub.setDate(date);
                        editedSub.setCharge(charge);
                        editedSub.setComment(comment);

                        // If no exceptions occur
                        updateTotal();
                        displayListAdapter.notifyDataSetChanged();
                        saveInFile();



                    } catch (NameTooLongException | DateFormatException | ChargeFormatException |
                            CommentTooLongException e){
                        // Notify user that their input was rejected
                        // Note that this might result in half-edited entries as currently coded
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                        alertBuilder.setTitle("ERROR");
                        alertBuilder.setMessage("Incorrect input format detected, " +
                                "edit unsuccessful");
                        alertBuilder.setPositiveButton("OK", null);
                        alertBuilder.show();
                    }
                }
            }
        }
    }

    /**
     * Updates the monthly charges sum total when called
     *
     */
    protected void updateTotal() {
        //setContentView(R.layout.activity_main);
        String total = subList.sumCharges().toString();
        String message = "Total Monthly Charge: $" + total;
        TextView displayBox = (TextView) findViewById(R.id.showTotal);
        displayBox.setText(message);
    }

    /**
     * Loads previously saved data from the file for persistence
     */
    private void loadFromFile() {

        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            // Taken https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2018-01-23
            Type listType = new TypeToken<AllSubscriptions>(){}.getType();
            subList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            subList = new AllSubscriptions();
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    /**
     * Saves data in file for persistence
     */
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(subList, out);
            out.flush();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
}
