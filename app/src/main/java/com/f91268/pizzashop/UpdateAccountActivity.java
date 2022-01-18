package com.f91268.pizzashop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

public class UpdateAccountActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner updateCitySpinner;
    TextView updateCityTextView;
    EditText updateFirstName, updateLastName, updateAddress;
    ImageButton btnUpdateDetails, btnDeleteAccount;

    String id, firstName, lastName, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);



        updateCitySpinner = findViewById(R.id.update_city_spinner);
        updateCityTextView = findViewById(R.id.update_city_text);
        updateCitySpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.city_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        updateCitySpinner.setAdapter(adapter);

        updateFirstName = findViewById(R.id.update_first_name);
        updateLastName = findViewById(R.id.update_last_name);
        updateAddress = findViewById(R.id.update_address);

        getIntentData();

        btnUpdateDetails = findViewById(R.id.btn_update_account_details);
        btnUpdateDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler dbHandler = new DBHandler(UpdateAccountActivity.this);
                dbHandler.updateAccountDetails(id,updateFirstName.getText().toString().trim(),
                        updateLastName.getText().toString().trim(),
                        updateCityTextView.getText().toString().trim(),
                        updateAddress.getText().toString().trim());
            }
        });

        btnDeleteAccount = findViewById(R.id.btn_delete_account);
        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteDialog();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(UpdateAccountActivity.this, "Press the back arrow on the screen", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = updateCitySpinner.getItemAtPosition(position).toString();
        updateCityTextView.setText(item);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(UpdateAccountActivity.this, "Please select your city", Toast.LENGTH_SHORT).show();
    }

    private void getIntentData() {
        if(getIntent().hasExtra("account_id") && getIntent().hasExtra("account_first_name") && getIntent().hasExtra("account_last_name") ) {
            id = getIntent().getStringExtra("account_id");
            firstName = getIntent().getStringExtra("account_first_name");
            lastName = getIntent().getStringExtra("account_last_name");
            address = getIntent().getStringExtra("account_address");

            updateFirstName.setText(firstName);
            updateLastName.setText(lastName);
            updateAddress.setText(address);
        }else {
            Toast.makeText(this, "No data found!", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Account")
                .setMessage("Are you sure you want to delete your account?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHandler dbHandler = new DBHandler(UpdateAccountActivity.this);
                        dbHandler.deleteAccount(id);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}