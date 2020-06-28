package com.example.travelmantics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DealActivity extends AppCompatActivity {
    EditText titleEditText, descriptionEditText, priceEditText;
    TravelDeal deal;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        FirebaseUtil.openFbReference("traveldeals", this);
        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;

        titleEditText = findViewById(R.id.title_textview);
        descriptionEditText = findViewById(R.id.description_textview);
        priceEditText = findViewById(R.id.price_textview);

        Intent intent = getIntent();
        TravelDeal deal = (TravelDeal) intent.getSerializableExtra("Deal");

        if (deal == null) {
            deal = new TravelDeal();
        }
        this.deal = deal;
        titleEditText.setText(deal.getTitle());
        descriptionEditText.setText(deal.getDescription());
        priceEditText.setText(deal.getPrice());

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
            case R.id.delete_menu:
                deleteDeal();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }


    private void saveDeal() {
        deal.setTitle(titleEditText.getText().toString());
        deal.setDescription(descriptionEditText.getText().toString());
        deal.setPrice(priceEditText.getText().toString());
        if (deal.getId() == null) {
            if (deal.getTitle().isEmpty() || deal.getDescription().isEmpty() || deal.getPrice().isEmpty()) {
                Toast.makeText(this, "Please enter values in all fields", Toast.LENGTH_SHORT).show();
            } else {
                mDatabaseReference.push().setValue(deal);
                Toast.makeText(this, "Deal saved", Toast.LENGTH_LONG).show();
                backToList();
            }
        } else {
            if (deal.getTitle().isEmpty() || deal.getDescription().isEmpty() || deal.getPrice().isEmpty()) {
                Toast.makeText(this, "Please enter values in all fields", Toast.LENGTH_SHORT).show();
            } else {
                mDatabaseReference.child(deal.getId()).setValue(deal);
                Toast.makeText(this, "Deal edited", Toast.LENGTH_LONG).show();
                backToList();
            }

        }

    }

    public void deleteDeal() {
        if (deal == null) {
            Toast.makeText(this, "Please save deal before deleting", Toast.LENGTH_LONG).show();
        } else {
            mDatabaseReference.child(deal.getId()).removeValue();
            Toast.makeText(this, "Deal deleted", Toast.LENGTH_SHORT).show();
            backToList();
        }


    }

    public void backToList() {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

}