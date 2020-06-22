package com.example.travelmantics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InsertActivity extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    EditText titleEditText, descriptionEditText, priceEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        titleEditText = findViewById(R.id.title_textview);
        descriptionEditText = findViewById(R.id.description_textview);
        priceEditText = findViewById(R.id.price_textview);

        //creates an instance of firebase database
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        //gets a reference of the location of database
        mDatabaseReference = mFirebaseDatabase.getReference().child("traveldeals");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_menu:

                saveDeal();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void clean() {
        titleEditText.setText("");
        priceEditText.setText("");
        descriptionEditText.setText("");
        titleEditText.requestFocus();
    }

    private void saveDeal() {
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String price = priceEditText.getText().toString();

        if (title.isEmpty() || description.isEmpty() || price.isEmpty()) {
            Toast.makeText(this, "Please enter values in all fields", Toast.LENGTH_SHORT).show();
        } else {
            TravelDeal deal = new TravelDeal(title, description, price, "");
            mDatabaseReference.push().setValue(deal);
            Toast.makeText(this, "Deal saved", Toast.LENGTH_LONG).show();
            clean();
        }

    }
}