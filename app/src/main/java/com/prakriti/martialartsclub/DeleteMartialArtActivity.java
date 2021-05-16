package com.prakriti.martialartsclub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.Toast;

import com.prakriti.martialartsclub.Model.DatabaseHandler;
import com.prakriti.martialartsclub.Model.MartialArt;

import org.w3c.dom.CDATASection;

import java.util.ArrayList;

public class DeleteMartialArtActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_martial_art);

        databaseHandler = new DatabaseHandler(this);
        updateUserInterface();
    }

    private void updateUserInterface() {
        ArrayList<MartialArt> allMartialArtObjects = databaseHandler.returnAllMartialArtObjects();
        RelativeLayout relativeLayout = new RelativeLayout(this);
        ScrollView scrollView = new ScrollView(this);
        RadioGroup radioGroup = new RadioGroup(this);

        for(MartialArt martialArt : allMartialArtObjects) {
            RadioButton currentRadioButton = new RadioButton(this);
            currentRadioButton.setId(martialArt.getMartialArtID());
            currentRadioButton.setText(martialArt.toString());
            radioGroup.addView(currentRadioButton);
        }
        radioGroup.setOnCheckedChangeListener(this);

        Button btnGoBack = new Button(this);
        btnGoBack.setText("Go Back");
        btnGoBack.setOnClickListener(this);

        scrollView.addView(radioGroup);

        RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        buttonParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        buttonParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        buttonParams.setMargins(20,0,20,70);
        relativeLayout.addView(btnGoBack, buttonParams);

        ScrollView.LayoutParams scrollParams = new ScrollView.LayoutParams
                (ScrollView.LayoutParams.MATCH_PARENT, ScrollView.LayoutParams.MATCH_PARENT);
        relativeLayout.addView(scrollView, scrollParams);

        setContentView(relativeLayout);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        databaseHandler.deleteMartialArtByID(checkedId);
        Toast.makeText(this, "Martial Art deleted from SQLite Database", Toast.LENGTH_SHORT).show();
        updateUserInterface();
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}