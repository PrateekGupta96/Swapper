package com.example.swapper;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import static java.lang.Boolean.TRUE;

public class Backgroundworker extends AsyncTask<String,Void,String> {
     Context context;
     AlertDialog alertDialog;

    Backgroundworker (Context ctx) {context = ctx; }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String register_url = "http://192.168.0.4/register.php";

        if(type.equals("Register")) {
            try {
                 String fname = params[1];
                 String lname = params[2];
                 String email = params[3];
                 URL url = new URL(register_url);

                //Connecting using https protocol to the server and opening output stream to send data

                HttpURLConnection httpURLConnection =  (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(TRUE);
                httpURLConnection.setDoOutput(TRUE);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                //Writing from the buffer
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                //Encoding string to send over the connection
                String post_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(fname,"UTF-8")+"&"
                        +URLEncoder.encode("surname","UTF-8")+"="+ URLEncoder.encode(lname,"UTF-8")+"&"
                        +URLEncoder.encode("mail","UTF-8")+"="+ URLEncoder.encode(email,"UTF-8");

                //Writing the data and flush it in the stream and close the stream
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                //Using the input stream to read the response from the server
                InputStream inputStream = httpURLConnection.getInputStream();

                //Reading from the buffer
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

                //strings to store the result abd readline from the buffer
                String result="";
                String line = "";

                //Checking whether the buffer is empty
                while((line = bufferedReader.readLine()) != null) {

                    result+=line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        return null;
    }

    @Override
    protected  void onPreExecute () {

        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Response");
    }

    @Override
    protected void onPostExecute(String result) {

        alertDialog.setMessage(result);
        alertDialog.show();

    }

    @Override
    protected void onProgressUpdate (Void... y) {

        super.onProgressUpdate(y);

    }
}
