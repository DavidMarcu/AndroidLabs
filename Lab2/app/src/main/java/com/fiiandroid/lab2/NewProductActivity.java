package com.fiiandroid.lab2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewProductActivity extends Activity {

    private SharedPreferences preferences;
    private boolean isDark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SettingsHelper.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_new_product);
        Button addProductButton = findViewById(R.id.new_product_save_button);
        final EditText newProductName = findViewById(R.id.new_product_name);
        final EditText newProductPrice = findViewById(R.id.new_product_price);
        final EditText newProductDescription = findViewById(R.id.new_product_description);
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).
                        setMessage(R.string.add_product_message)
                        .setTitle("Add product")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent();
                                intent.putExtra("product_name", newProductName.getText().toString());
                                intent.putExtra("product_price", Double.parseDouble(newProductPrice.getText().toString()));
                                intent.putExtra("product_description", newProductDescription.getText().toString());
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create();
                alertDialog.show();
            }
        });

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        isDark = preferences.getBoolean("theme", false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isDark != preferences.getBoolean("theme", false))
            recreate();
    }
}
