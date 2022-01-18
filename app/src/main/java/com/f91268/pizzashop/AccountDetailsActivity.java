package com.f91268.pizzashop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.f91268.pizzashop.data.DBHandler;

public class AccountDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner citySpinner;
    TextView cityTextView;
    EditText firstNameET, lastNameET, addressET;
    ImageButton btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);

        citySpinner = findViewById(R.id.city_spinner);
        cityTextView = findViewById(R.id.city_text);

        citySpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.city_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter);

        firstNameET = findViewById(R.id.first_name);
        lastNameET = findViewById(R.id.last_name);
        addressET = findViewById(R.id.address);

        btnConfirm = findViewById(R.id.btn_confirm_account_details);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInputValid()) {
                    DBHandler dbHandler = new DBHandler(AccountDetailsActivity.this);
                    dbHandler.addAccountDetails(firstNameET.getText().toString().trim(),
                            lastNameET.getText().toString().trim(),
                            cityTextView.getText().toString(),
                            addressET.getText().toString().trim());
                }
            }
        });
    }

    private boolean isInputValid() {
        String firstName = firstNameET.getText().toString().trim();
        String lastName = lastNameET.getText().toString().trim();
        String address = addressET.getText().toString().trim();

        if(firstName.isEmpty()){
            firstNameET.setError("First name is required!");
            firstNameET.requestFocus();
            return false;
        }

        if(lastName.isEmpty()){
            lastNameET.setError("Last name is required!");
            lastNameET.requestFocus();
            return false;
        }

        if(address.isEmpty()){
            addressET.setError("Address is required!");
            addressET.requestFocus();
            return false;
        }

        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = citySpinner.getItemAtPosition(position).toString();
        cityTextView.setText(item);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(AccountDetailsActivity.this, "Please select your city", Toast.LENGTH_SHORT).show();
    }
}