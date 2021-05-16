package com.prakriti.martialartsclub;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.prakriti.martialartsclub.Model.DatabaseHandler;
import com.prakriti.martialartsclub.Model.MartialArt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.arch.core.executor.DefaultTaskExecutor;

import android.text.style.TtsSpan;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseHandler databaseHandler;
    private double totalMartialArtPrice;
    private ScrollView scrollView;
    private int martialArtButtonWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseHandler = new DatabaseHandler(this);
        totalMartialArtPrice = 0.0;
        scrollView = findViewById(R.id.scrollView);

        Point screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);
        martialArtButtonWidth = screenSize.x / 2; // button takes half the screen width

        updateUserInterface();
    }

    private void updateUserInterface() {

        ArrayList<MartialArt> allMartialArtObjects = databaseHandler.returnAllMartialArtObjects();
        scrollView.removeAllViewsInLayout();

        if(allMartialArtObjects.size() > 0) {

            GridLayout gridLayout = new GridLayout(this);
                // we need even number of rows, for 2 buttons per row
            gridLayout.setRowCount((allMartialArtObjects.size()+1) / 2);
            gridLayout.setColumnCount(2);
            MartialArtButton[] martialArtButtons = new MartialArtButton[allMartialArtObjects.size()];

            int index = 0;
            for(MartialArt martialArtObject : allMartialArtObjects) {
                martialArtButtons[index] = new MartialArtButton(this, martialArtObject);
                martialArtButtons[index].setText(martialArtObject.getMartialArtID() + "\n" + martialArtObject.getMartialArtName() + "\n" +
                        martialArtObject.getMartialArtPrice());

                //  martialArtButtons[index].setBackgroundColor(Color.parseColor(martialArtObject.getMartialArtColor().toLowerCase())); -> doesn't work
                // or use switch case for multiple colors

                switch (martialArtObject.getMartialArtColor()) {
                    case "Red": martialArtButtons[index].setBackgroundColor(Color.RED); break;
                    case "Blue": martialArtButtons[index].setBackgroundColor(Color.BLUE); break;
                    case "Black": martialArtButtons[index].setBackgroundColor(Color.BLACK);
                                    martialArtButtons[index].setTextColor(Color.WHITE); break;
                    case "Yellow": martialArtButtons[index].setBackgroundColor(Color.YELLOW); break;
                    case "Green": martialArtButtons[index].setBackgroundColor(Color.GREEN); break;
                    case "Purple": martialArtButtons[index].setBackgroundColor(Color.CYAN); break;
                    case "White": martialArtButtons[index].setBackgroundColor(Color.WHITE); break;
                    default: martialArtButtons[index].setBackgroundColor(Color.GRAY); break;
                }

                martialArtButtons[index].setOnClickListener(this::onClick);
                gridLayout.addView(martialArtButtons[index], martialArtButtonWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            scrollView.addView(gridLayout);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
            // to update the main activity on returning from other activities (delete, update, etc)
        updateUserInterface();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml

        int id = item.getItemId();
        switch (id) {

            case R.id.add_martial_art:
                Intent addMartialArtIntent = new Intent(this, AddMartialArtActivity.class);
                startActivity(addMartialArtIntent);
                return true;

            case R.id.delete_martial_art:
                Intent deleteMartialArtIntent = new Intent(this, DeleteMartialArtActivity.class);
                startActivity(deleteMartialArtIntent);
                return true;

            case R.id.update_martial_art:
                Intent updateMartialArtIntent = new Intent(this, UpdateMartialArtActivity.class);
                startActivity(updateMartialArtIntent);
                return true;

            case R.id.reset_prices:
                totalMartialArtPrice = 0.0;
                Toast.makeText(this, "Price reset to 0.0", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        MartialArtButton martialArtButton = (MartialArtButton) v;
        totalMartialArtPrice = totalMartialArtPrice + martialArtButton.getMartialArtPrice();
        String martialArtPriceFormatted = NumberFormat.getCurrencyInstance().format(totalMartialArtPrice);
        Toast.makeText(this, martialArtPriceFormatted, Toast.LENGTH_SHORT).show();
    }
}