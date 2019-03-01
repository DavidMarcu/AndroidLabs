package com.fiiandroid.lab2;

import android.app.ListActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        Product[] products = {product1, product2, product3, product4, product5, product6, product7, product8, product9, product10, product11
                , product12, product13, product14, product15};

        List<Map<String, Object>> theList = new ArrayList<>();
        for (Product product : products) {
            Map<String, Object> listItem = new HashMap<>();
            listItem.put("name", product.getName());
            listItem.put("price", product.getPrice() + "$");
            listItem.put("product", product);
            theList.add(listItem);
        }
        String[] from = {"name", "price"};
        int[] to = {R.id.product_name, R.id.product_price};

        ListAdapter listAdapter = new SimpleAdapter(this, theList, R.layout.item_layout, from, to);
        setListAdapter(listAdapter);

    }

    @SuppressWarnings(value = "unchecked")
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Object obj = getListView().getItemAtPosition(position);
        Map<String, Object> itemList = (Map<String, Object>) obj;

        Product product = (Product) itemList.get("product");

        TextView productDetails = findViewById(R.id.product_details);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        Bitmap productImage = BitmapFactory.decodeResource(getResources(), product.getImageResource(), options);

        Drawable drawableProduct = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(productImage, 512, 500, false));

        productDetails.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableProduct, null, null, null);
        productDetails.setCompoundDrawablePadding(15);
        productDetails.setText(String.format(Locale.getDefault(), "%s\n%.2f$\n%s", product.getName(), product.getPrice(), product.getDesciption()));
    }
}
