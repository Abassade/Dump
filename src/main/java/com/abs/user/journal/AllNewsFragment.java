package com.abs.user.journal;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abs.user.journal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AllNewsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.allnewsfragment, container, false);
        
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new NewsTask().execute("https://newsapi.org/v2/top-headlines?sources=bbc-news&apiKey=9b017b36c1b74854aeb704e2b893a1b9");

    }

    public class NewsTask extends AsyncTask<String, Void, String>{


        @Override
        protected String doInBackground(String... strings) {

            URL url=null;
            HttpURLConnection httpURLConnection=null;
            String result="";

            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);

                int data = reader.read();
                while (data!=-1){

                    char present = (char) data;
                    result+=present;
                    data=reader.read();

                }
             //   Log.i("News Api...", result);

                JSONObject object = new JSONObject(result);
                String articles = object.getString("articles");
                JSONArray articleArray = new JSONArray(articles);
                for (int i=0; i<articleArray.length(); i++){

                 //   Log.i("article..."+i, articleArray.getString(i));
                    String article = articleArray.getString(i);
                    JSONObject jsonObject = new JSONObject(article);
                    String title = jsonObject.getString("title");
                    Log.i("Title "+i, title);

                }

                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


        }
    }
}
