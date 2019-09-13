package com.example.gallaryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Cell> allFilesPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1000 );

        }
        else{
            showImages();
        }

    }

    private void showImages() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Pictures/Walli Artworks/";
        allFilesPath = new ArrayList<>();
        allFilesPath = listAllFiles(path);
        RecyclerView recyclerView = findViewById(R.id.gallery_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<Cell> cells = prepareData();
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getApplicationContext(),cells);
        recyclerView.setAdapter(adapter);


    }

    private ArrayList<Cell> prepareData(){
        ArrayList<Cell> allImages = new ArrayList<>();
        for (Cell c : allFilesPath){
            Cell cell = new Cell();
            cell.setTitle(c.getTitle());
            cell.setPath(c.getPath());
            allImages.add(cell);
        }
        return allImages;
    }

    private ArrayList<Cell> listAllFiles(String pathName){
        ArrayList<Cell> allFiles = new ArrayList<>();
        File file = new File(pathName);
        File[] files = file.listFiles();
        if(files!=null){
            for(File f: files){
                Cell cell = new Cell();
                cell.setTitle(f.getName());
                cell.setPath(f.getAbsolutePath());
                allFiles.add(cell);
            }
        }
        return allFiles;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1000){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                showImages();
            }
            else{
                finish();
            }
        }
    }
}
