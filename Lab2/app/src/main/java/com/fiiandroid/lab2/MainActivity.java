package com.fiiandroid.lab2;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class MainActivity extends ListActivity {

    private ArrayList<Product> products;
    private Product displayedProduct;
    private List<Map<String, Object>> theList;
    private ListAdapter listAdapter;
    private SharedPreferences preferences;
    private boolean isDark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SettingsHelper.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            products = loadProductList();
            if(products == null){
                products = new ArrayList<>();
            }
        } else {
            products = savedInstanceState.getParcelableArrayList("product_list");
            displayedProduct = (Product) savedInstanceState.get("product_details");
        }

        theList = new ArrayList<>();
        for (Product product : products) {
            Map<String, Object> listItem = new HashMap<>();
            listItem.put("name", product.getName());
            listItem.put("price", product.getPrice() + "$");
            listItem.put("product", product);
            theList.add(listItem);
        }
        if (displayedProduct != null)
            setProductDetailsTextView();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        isDark = preferences.getBoolean("theme", false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ACTIVITY_LIFECYCLE", "Activity destroyed!");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("ACTIVITY_LIFECYCLE", "Activity stopped!");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("ACTIVITY_LIFECYCLE", "Activity paused!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ACTIVITY_LIFECYCLE", "Activity resumed!");
        if(isDark != preferences.getBoolean("theme", false))
            recreate();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getActionBar().show();
        String[] from = {"name", "price"};
        int[] to = {R.id.product_name, R.id.product_price};

        listAdapter = new SimpleAdapter(this, theList, R.layout.item_layout, from, to);
        setListAdapter(listAdapter);
        Log.d("ACTIVITY_LIFECYCLE", "Activity started!");
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Object obj = getListView().getItemAtPosition(position);
        Map<String, Object> itemList = (Map<String, Object>) obj;
        displayedProduct = (Product) itemList.get("product");
        setProductDetailsTextView();
    }

    private void setProductDetailsTextView() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        Bitmap productImage = BitmapFactory.decodeResource(getResources(), displayedProduct.getImageResource(), options);

        TextView productDetails = findViewById(R.id.product_details);
        Drawable drawableProduct = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(productImage, 512, 500, false));
        productDetails.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableProduct, null, null, null);
        productDetails.setCompoundDrawablePadding(15);
        productDetails.setText(String.format(Locale.getDefault(), "%s\n%.2f$\n%s", displayedProduct.getName(), displayedProduct.getPrice(), displayedProduct.getDesciption()));
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstance) {
        savedInstance.putParcelableArrayList("product_list", products);
        savedInstance.putParcelable("product_details", displayedProduct);
        super.onSaveInstanceState(savedInstance);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("MENU", "Menu created");
        MenuInflater optionsMenu = getMenuInflater();
        optionsMenu.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                showAbout();
                return true;
            case R.id.new_product:
                showNewProduct();
                return true;
            case R.id.save_product_list:
                saveProductList();
                return true;
            case R.id.settings:
                showSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private ArrayList<Product> loadProductList(){
        File file = new File(this.getFilesDir(), "listfile");
        try(ObjectInputStream listStream = new ObjectInputStream(new FileInputStream(file))){
            return(ArrayList<Product>)listStream.readObject();
        }
        catch (IOException | ClassNotFoundException exception){
            Log.e("IOException", exception.toString());
        }
        return null;
    }

    private void saveProductList() {
        File file = new File(this.getFilesDir(), "listfile");
        try(ObjectOutputStream listStream = new ObjectOutputStream(new FileOutputStream(file))){
            listStream.writeObject(products);

        }
        catch (IOException exception){
            Log.e("IOException", exception.toString());
        }
    }

    public void showAbout(){
        final AlertDialog alertDialog = new AlertDialog.Builder(this).
                 setMessage(R.string.about_message)
                .setTitle("About")
                .create();
        alertDialog.show();
    }

    public void showSettings(){
        Intent intent = new Intent();
        intent.setClassName(this, SettingsActivity.class.getName());
        startActivity(intent);
    }

    public void showNewProduct(){
        Intent intent = new Intent();
        intent.setClassName(this, NewProductActivity.class.getName());
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                String newProductName = data.getStringExtra("product_name");
                double newProductPrice = data.getDoubleExtra("product_price", 0.00);
                String newProductDescription = data.getStringExtra("product_description");
                int photoId = data.getIntExtra("product_image", R.drawable.missing_image);
                Product newProduct = new Product(newProductName, newProductPrice, newProductDescription, photoId);
                products.add(newProduct);

                Map<String, Object> listItem = new HashMap<>();
                listItem.put("name", newProduct.getName());
                listItem.put("price", newProduct.getPrice() + "$");
                listItem.put("product", newProduct);
                theList.add(listItem);
            }
        }
    }
}
