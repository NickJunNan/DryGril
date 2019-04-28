package com.example.drygirl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView showImage;
    private Button showButton;
    private ArrayList<String> urls;
    private int curPos = 0;
    private PictureLoader pictureLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pictureLoader = new PictureLoader();
        initData();
        initUI();
    }

    private void initUI() {
        showButton = findViewById(R.id.show_button);
        showImage = findViewById(R.id.show_image);
        showButton.setOnClickListener(this);
    }

    private void initData() {
        urls = new ArrayList<>();
        urls.add("http://ww4.sinaimg.cn/large/610dc034jw1f6ipaai7wgj20dw0kugp4.jpg");
        urls.add("http://ww3.sinaimg.cn/large/610dc034jw1f6gcxc1t7vj20hs0hsgo1.jpg");
        urls.add("http://ww4.sinaimg.cn/large/610dc034jw1f6f5ktcyk0j20u011hacg.jpg");
        urls.add("http://ww1.sinaimg.cn/large/610dc034jw1f6e1f1qmg3j20u00u0djp.jpg");
        urls.add("http://ww3.sinaimg.cn/large/610dc034jw1f6aipo68yvj20qo0qoaee.jpg");
        urls.add("http://ww3.sinaimg.cn/large/610dc034jw1f69c9e22xjj20u011hjuu.jpg");
        urls.add("http://ww3.sinaimg.cn/large/610dc034jw1f689lmaf7qj20u00u00v7.jpg");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show_button:
                if (curPos > 9){
                    curPos = 0;
                }
                pictureLoader.load(showImage,urls.get(curPos));
                curPos++;
                break;
        }
    }
}