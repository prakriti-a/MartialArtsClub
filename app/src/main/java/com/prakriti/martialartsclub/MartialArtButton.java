package com.prakriti.martialartsclub;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.prakriti.martialartsclub.Model.MartialArt;

public class MartialArtButton extends AppCompatButton {

    private MartialArt martialArtObject;

    public MartialArtButton(@NonNull Context context, MartialArt martialArt) {
        super(context);
        martialArtObject = martialArt;
    }

    public String getMartialArtColor() {
        return martialArtObject.getMartialArtColor();
    }

    public double getMartialArtPrice() {
        return martialArtObject.getMartialArtPrice();
    }


}
