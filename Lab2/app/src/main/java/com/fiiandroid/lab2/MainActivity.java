package com.fiiandroid.lab2;

import android.app.ListActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends ListActivity {

    private ArrayList<Product> products;
    private Product displayedProduct;
    private List<Map<String, Object>> theList;
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            Product product1 = new Product("Bananas", 2.49, "Some bananas", R.drawable.bananas);
            Product product2 = new Product("Apples", 1.99, "Some apples", R.drawable.apples);
            Product product3 = new Product("Tomatoes", 4.49, "Some tomatoes", R.drawable.tomatoes);
            Product product4 = new Product("Pears", 5.99, "Some pears", R.drawable.pears);
            Product product5 = new Product("Pineapples", 9.99, "Some pineapples", R.drawable.pineapples);
            Product product6 = new Product("Bananas", 2.49, "Some bananas", R.drawable.bananas);
            Product product7 = new Product("Apples", 1.99, "Some apples", R.drawable.apples);
            Product product8 = new Product("Tomatoes", 4.49, "Some tomatoes", R.drawable.tomatoes);
            Product product9 = new Product("Pears", 5.99, "Some pears", R.drawable.pears);
            Product product10 = new Product("Pineapples", 9.99, "Some pineapples", R.drawable.pineapples);
            Product product11 = new Product("Bananas", 2.49, "Some bananas", R.drawable.bananas);
            Product product12 = new Product("Apples", 1.99, "Some apples", R.drawable.apples);
            Product product13 = new Product("Tomatoes", 4.49, "Some tomatoes", R.drawable.tomatoes);
            Product product14 = new Product("Pears", 5.99, "Some pears", R.drawable.pears);
            Product product15 = new Product("Pineapples", 9.99, "Some pineapples", R.drawable.pineapples);
            products = new ArrayList<>(Arrays.asList(product1, product2, product3, product4, product5, product6, product7, product8, product9, product10, product11
                    , product12, product13, product14, product15));
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
        if(displayedProduct != null)
            setProductDetailsTextView();
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
}
