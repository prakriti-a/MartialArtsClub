package com.prakriti.martialartsclub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.prakriti.martialartsclub.Model.DatabaseHandler;
import com.prakriti.martialartsclub.Model.MartialArt;

public class AddMartialArtActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtName, edtPrice, edtColor;
    Button btnAddMartialArt, btnGoBack;
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_martial_art);
        Toast.makeText(this, "Add Martial Art", Toast.LENGTH_SHORT).show();

        edtName = findViewById(R.id.edtName);
        edtPrice = findViewById(R.id.edtPrice);
        edtColor = findViewById(R.id.edtColor);

        btnAddMartialArt = findViewById(R.id.btnAddMartialArt);
        btnGoBack = findViewById(R.id.btnGoBack);
        btnAddMartialArt.setOnClickListener(this);
        btnGoBack.setOnClickListener(this);

        databaseHandler = new DatabaseHandler(this);
    }

    private void addMartialArtToDB() {
        String nameValue = edtName.getText().toString();
        String priceValue = edtPrice.getText().toString();
        String colorValue = edtColor.getText().toString();
        try {
            double priceValueDouble = Double.parseDouble(priceValue);
            MartialArt martialArtObject = new MartialArt(0, nameValue, priceValueDouble, colorValue);
            databaseHandler.addMartialArt(martialArtObject);
            Toast.makeText(this, "Martial Art added to SQLite Database\n" + martialArtObject, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddMartialArt: addMartialArtToDB(); break;
            case R.id.btnGoBack: this.finish(); break;
        }
    }
}