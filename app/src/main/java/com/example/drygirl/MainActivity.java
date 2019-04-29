package com.example.drygirl;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.drygirl.bean.Girl;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView showImage;
    private Button showButton;
    private Button refreshButton;
    private ArrayList<Girl> data;
    private int curPos = 0;
    private int page = 1;
    private PictureLoader pictureLoader;
    private GirlApi girlApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        girlApi = new GirlApi();
        pictureLoader = new PictureLoader();
        initData();
        initUI();
    }

    private void initUI() {
        showButton = findViewById(R.id.show_button);
        showImage = findViewById(R.id.show_image);
        refreshButton = findViewById(R.id.refresh_btn);
        showButton.setOnClickListener(this);
        refreshButton.setOnClickListener(this);
    }

    private void initData() {
        data = new ArrayList<>();
        new GirlTask(page).execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show_button:
                if (data != null && !data.isEmpty()){
                    if (curPos > 9){
                        curPos = 0;
                    }
                    pictureLoader.load(showImage,data.get(curPos).getUrl());
                    curPos++;
                }
                break;
            case R.id.refresh_btn:
                page++;
                new GirlTask(page).execute();
                curPos = 0;
                break;
        }
    }

    private class GirlTask extends AsyncTask<Void,Void,ArrayList<Girl>>{
        private int page;
        public GirlTask(int page) {
            this.page = page;
        }

        @Override
        protected ArrayList<Girl> doInBackground(Void... voids) {
            return girlApi.fetchGirl(10,page);
        }

        @Override
        protected void onPostExecute(ArrayList<Girl> girls) {
            super.onPostExecute(girls);
            data.clear();
            data.addAll(girls);
        }
    }
}