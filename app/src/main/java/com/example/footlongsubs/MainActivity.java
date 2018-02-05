package com.example.footlongsubs;

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
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private AllSubscriptions subList;
    static final int ADD_SUBSCRIPTION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        subList = new AllSubscriptions();
        updateTotal();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddSubActivity.class);
                startActivityForResult(i, ADD_SUBSCRIPTION);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_SUBSCRIPTION) {
            if (resultCode == RESULT_OK) {
                String name = data.getStringExtra("name");
                String date = data.getStringExtra("date");
                String charge = data.getStringExtra("charge");
                String comment = data.getStringExtra("comment");

                try {
                    subList.addSubscription(name, date, charge, comment);
                    updateTotal();
                } catch (InputErrorException e) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                    alertBuilder.setTitle("ERROR");
                    alertBuilder.setMessage("Incorrect input format detected, " +
                            "subscription not added");
                    alertBuilder.setPositiveButton("OK", null);
                    alertBuilder.show();
                }
            }
        }
    }

    protected void updateTotal() {
        setContentView(R.layout.activity_main);
        String total = subList.sumCharges().toString();
        String message = "Total Monthly Charge: $" + total;
        TextView displayBox = (TextView) findViewById(R.id.showTotal);
        displayBox.setText(message);
    }
}
