package com.example.contentprovider_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private String newId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.listview);
        //获取查询结果
        ArrayList<HashMap<String, Object>> listData = readContacts();
        //获取适配器
        SimpleAdapter adapter = fillAdapter(listData);
        //添加并且显示
        listView.setAdapter(adapter);

        EditText editText = findViewById(R.id.One);
        Button button = findViewById(R.id.select);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newId = editText.getText().toString();
                ArrayList<HashMap<String, Object>> listData = readContact();
                //获取适配器
                SimpleAdapter adapter = fillAdapter(listData);
                //添加并且显示
                listView.setAdapter(adapter);
            }
        });
        Button button2 = findViewById(R.id.selectAll);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<HashMap<String, Object>> listData = readContacts();
                //获取适配器
                SimpleAdapter adapter = fillAdapter(listData);
                //添加并且显示
                listView.setAdapter(adapter);
            }
        });

    }


    //查询所有联系人
    private ArrayList<HashMap<String, Object>> readContacts() {
        Uri uri = Uri.parse("content://com.example.contactsapplication.provider/contacts");
        //生成动态数组，并且转载数据
        ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();

        Cursor cursor = null;
        try {
            //查询联系人数据
            cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    //获取联系人姓名
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                    @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("phone"));
                    Log.d("MainActivity", "contacts name =" + name);
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("name", name);
                    map.put("phone", phone);
                    dataList.add(map);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return dataList;
    }

    //查询一个联系人
    private ArrayList<HashMap<String, Object>> readContact() {
        Uri uri = Uri.parse("content://com.example.contactsapplication.provider/contacts/"+newId);
        //生成动态数组，并且转载数据
        ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();

        Cursor cursor = null;
        try {
            //查询联系人数据
            cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    //获取联系人姓名
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                    @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("phone"));
                    Log.d("MainActivity", "contacts name =" + name);
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("name", name);
                    map.put("phone", phone);
                    dataList.add(map);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return dataList;
    }


    //适配器
    public SimpleAdapter fillAdapter(ArrayList<HashMap<String, Object>> listData) {

        //生成适配器，数组===》ListItem
        SimpleAdapter adapter = new SimpleAdapter(this,
                listData,//数据来源
                R.layout.phonelist,//ListItem的XML实现
                //动态数组与ListItem对应的子项
                new String[]{"name", "phone"},
                //ListItem的XML文件里面的3个TextView ID
                new int[]{R.id.name, R.id.phone});

        return adapter;
    }
}