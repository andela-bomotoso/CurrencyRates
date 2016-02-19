package com.example.bukola_omotoso.jsonurlreader;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by bukola_omotoso on 19/02/16.
 */
public class ExchangeRateFetcher {

    public ExchangeRateFetcher() {

    }

    private String exchangeRates;

    public String getExchangeRates() {

        return exchangeRates;
    }

    public void loadRate()   {
        FetchExchangeRateTask exchangeRateTask = new FetchExchangeRateTask();
        exchangeRateTask.execute();
    }

    public class FetchExchangeRateTask extends AsyncTask<String,Void,String[]> {


        @Override
        protected String[] doInBackground(String...params)  {

            String str = "https://openexchangerates.org/api/latest.json?app_id=125d7e1c1f664d0488a4262f599038ae";

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;


            try{
                Uri builtUri = Uri.parse(str);
                URL url = new URL(builtUri.toString());
                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if(inputStream == null) {
                    return null;
                }
                reader = new BufferedReader((new InputStreamReader(inputStream)));
                String line="";

                while((line = reader.readLine()) != null)   {
                    buffer.append((line+"\n"));
                }

                if(buffer.length() == 0)    {
                    return null;
                }

                exchangeRates = buffer.toString();
                System.out.println(exchangeRates);

            }

            catch (IOException exception) {
                return null;
            }
            finally {

                if(urlConnection == null)   {
                    urlConnection.disconnect();
                }
                if(reader != null)  {
                    try {
                        reader.close();
                    }
                    catch (IOException exception)   {
                    }
                }
            }

            return  null;
        }
    }

}