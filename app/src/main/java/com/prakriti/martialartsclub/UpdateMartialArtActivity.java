package com.prakriti.martialartsclub;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.prakriti.martialartsclub.Model.DatabaseHandler;
import com.prakriti.martialartsclub.Model.MartialArt;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class UpdateMartialArtActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_martial_art);

        databaseHandler = new DatabaseHandler(this);
        updateUserInterface();
    }

    private void updateUserInterface() {

        ArrayList<MartialArt> martialArtObjects = databaseHandler.returnAllMartialArtObjects();
        if(martialArtObjects.size()>0) {
            ScrollView scrollView = new ScrollView(this);
            GridLayout gridLayout = new GridLayout(this);
            gridLayout.setRowCount(martialArtObjects.size());
            gridLayout.setColumnCount(5);

            TextView[] idTextViews = new TextView[martialArtObjects.size()];
            EditText[][] edtNamePriceAndColor = new EditText[martialArtObjects.size()][3];
            Button[] updateButtons = new Button[martialArtObjects.size()];

            Point screenSize = new Point();
            getWindowManager().getDefaultDisplay().getSize(screenSize);
            
            int screenWidth = screenSize.x;
            int index = 0;

            for(MartialArt martialArt : martialArtObjects) {
                idTextViews[index] = new TextView(this);
                idTextViews[index].setGravity(Gravity.CENTER);
                idTextViews[index].setText(martialArt.getMartialArtID() + "");

                edtNamePriceAndColor[index][0] = new EditText(this);
                edtNamePriceAndColor[index][1] = new EditText(this);
                edtNamePriceAndColor[index][2] = new EditText(this);

                edtNamePriceAndColor[index][0].setText(martialArt.getMartialArtName());
                edtNamePriceAndColor[index][1].setText(martialArt.getMartialArtPrice() + "");
                edtNamePriceAndColor[index][1].setInputType(InputType.TYPE_CLASS_NUMBER);
                edtNamePriceAndColor[index][2].setText(martialArt.getMartialArtColor());

                    // set unique id to edittext views
                edtNamePriceAndColor[index][0].setId(martialArt.getMartialArtID() + 10);
                edtNamePriceAndColor[index][1].setId(martialArt.getMartialArtID() + 20);
                edtNamePriceAndColor[index][2].setId(martialArt.getMartialArtID() + 30);

                updateButtons[index] = new Button(this);
                updateButtons[index].setText("Update");
                updateButtons[index].setId(martialArt.getMartialArtID());
                updateButtons[index].setOnClickListener(this::onClick);

                    // width * % of screen
                gridLayout.addView(idTextViews[index], (int) (screenWidth*0.05), ViewGroup.LayoutParams.WRAP_CONTENT);
                gridLayout.addView(edtNamePriceAndColor[index][0], (int) (screenWidth*0.30), ViewGroup.LayoutParams.WRAP_CONTENT);
                gridLayout.addView(edtNamePriceAndColor[index][1], (int) (screenWidth*0.20), ViewGroup.LayoutParams.WRAP_CONTENT);
                gridLayout.addView(edtNamePriceAndColor[index][2], (int) (screenWidth*0.20), ViewGroup.LayoutParams.WRAP_CONTENT);
                gridLayout.addView(updateButtons[index], (int) (screenWidth*0.25), ViewGroup.LayoutParams.WRAP_CONTENT);

                index++;
            }
            scrollView.addView(gridLayout);
            setContentView(scrollView);
        }
    }

    @Override
    public void onClick(View v) {
        int martialArtObjectID = v.getId();
        EditText edtMartialArtName = findViewById(martialArtObjectID + 10); // as specified above
        EditText edtMartialArtPrice = findViewById(martialArtObjectID + 20);
        EditText edtMartialArtColor = findViewById(martialArtObjectID + 30);

        String nameString = edtMartialArtName.getText().toString();
        String priceString = edtMartialArtPrice.getText().toString();
        String colorString = edtMartialArtColor.getText().toString();

        try {
            databaseHandler.updateMartialArt(martialArtObjectID, nameString, Double.parseDouble(priceString), colorString);
            Toast.makeText(this, "Martial Art Updated", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}