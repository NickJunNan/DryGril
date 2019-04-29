package com.example.drygirl;

import android.util.Log;

import com.example.drygirl.bean.Girl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GirlApi {
    private static final String TAG = "NETWORK";
    private static final String BASE_URL = "http://gank.io/api/data/福利/";

    public ArrayList<Girl> fetchGirl(int count,int page){
        String fetchUrl = BASE_URL + count + "/" + page;
        ArrayList<Girl> girls = new ArrayList<>();
        try {
            URL url = new URL(fetchUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            int code = connection.getResponseCode();
            Log.v(TAG,"Server Response:" + code);
            if (code == 200){
                InputStream in = connection.getInputStream();
                byte[] data = readFromStream(in);
                String result = new String(data,"UTF-8");
                girls = parseGirl(result);
            }else {
                Log.e(TAG,"请求失败:"+code);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return girls;
    }

    private ArrayList<Girl> parseGirl(String content) throws JSONException {
        ArrayList<Girl> girls = new ArrayList<>();
        JSONObject object = new JSONObject(content);
        JSONArray array = object.getJSONArray("results");
        for (int i = 0;i < array.length(); i++){
            JSONObject results = (JSONObject) array.get(i);
            Girl girl = new Girl();
            girl.set_id(results.getString("_id"));
            girl.setCreateAt(results.getString("createdAt"));
            girl.setDesc(results.getString("desc"));
            girl.setPublishedAt(results.getString("publishedAt"));
            girl.setSource(results.getString("source"));
            girl.setType(results.getString("type"));
            girl.setUrl(results.getString("url"));
            girl.setUsed(results.getBoolean("used"));
            girl.setWho(results.getString("who"));
            girls.add(girl);
        }
        return girls;
    }

    private byte[] readFromStream(InputStream stream) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = stream.read(buffer)) != -1){
            out.write(buffer,0,len);
        }
        stream.close();
        return out.toByteArray();
    }
}