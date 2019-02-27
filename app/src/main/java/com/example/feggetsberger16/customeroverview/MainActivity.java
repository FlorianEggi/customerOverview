package com.example.feggetsberger16.customeroverview;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Customer> customers = new ArrayList<>();
    String[] custArr = listToStringArray();
    ArrayAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readAssets();
        printAssets();
        ListView lv = findViewById(R.id.listView);
        lv.setAdapter(mAdapter);
        bindAdapterToListView(lv);
        SearchView sv = findViewById(R.id.searchView);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(customers.contains(query))
                {
                    mAdapter.getFilter().filter(query);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Not found", Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void bindAdapterToListView(ListView lv) {
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, custArr);
        lv.setAdapter(mAdapter);
    }

    private void onClickButton(View view)
    {

    }

    private String[] listToStringArray()
    {
        String[] result = new String[customers.size()];
        for (int i = 0; i < customers.size(); i++) {
            result[i] = customers.get(i).toString();
        }
        return result;
    }

    private void readAssets() {
        InputStream in = getInputStreamForAsset("customers_data.csv");
        BufferedReader bin = new BufferedReader(new InputStreamReader(in));
        String line;
        try {
            while((line = bin.readLine())!=null) {
                String[] arr = line.split(",");
                int id = Integer.parseInt(arr[0]);
                String vorname = arr[1];
                String nachname = arr[2];
                Customer c = new Customer(id, vorname, nachname);
                customers.add(c);
                custArr = listToStringArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private InputStream getInputStreamForAsset(String filename) {
        AssetManager assets = getAssets();
        try {
            return assets.open(filename);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void printAssets()
    {
        for (int i = 0; i < customers.size(); i++) {
            System.out.println(customers.get(i).toString());
        }
    }
}
