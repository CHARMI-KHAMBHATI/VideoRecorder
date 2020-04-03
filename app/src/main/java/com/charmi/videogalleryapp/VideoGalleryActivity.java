package com.charmi.videogalleryapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class VideoGalleryActivity extends AppCompatActivity {

    ArrayList<String> videos;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;
    CheckBox checkbox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_gallery);

        videos = FetchFileNames();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        checkbox = findViewById(R.id.chkBox);

        mAdapter = new GalleryAdapter(getApplicationContext(), videos);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

         recyclerView.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getApplicationContext(), recyclerView, new GalleryAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                /*Bundle bundle = new Bundle();
                bundle.putSerializable("videos", videos);
                bundle.putInt("position", position);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");*/
                Intent i = new Intent(getApplicationContext(),ExoPlayerActivity.class);
                i.putExtra("filepath",videos.get(position));
                startActivity(i);



            }

            @Override
            public void onLongClick(View view, int position) {

                view.findViewById(R.id.chkBox).setVisibility(View.VISIBLE);
                Toast.makeText(VideoGalleryActivity.this, videos.get(position), Toast.LENGTH_SHORT).show();

            }
        }));


    }

    private ArrayList<String> FetchFileNames() {

        ArrayList<String> filenames = new ArrayList<String>();
        String path =  Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+ "/" + MainActivity.GALLERY_DIRECTORY_NAME;
        Log.d("Files", "Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles();
        Log.d("Files", "Size: " + files.length);
        for (int i = 0; i < files.length; i++) {
            Log.d("Files", "FileName:" + files[i].getName());
            String file_name = files[i].getName();
            // you can store name to arraylist and use it later
            filenames.add(path+"/"+file_name);
        }
        return filenames;
    }
}
