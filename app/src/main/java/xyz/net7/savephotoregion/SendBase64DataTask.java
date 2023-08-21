package xyz.net7.savephotoregion;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendBase64DataTask extends AsyncTask<Void, Void, String> {

    private final String serverUrl = "http://192.168.1.50:8000"; // Use 10.0.2.2 on emulator
    private final String base64Data;

    public SendBase64DataTask(String base64Data) {
        this.base64Data = base64Data;
    }

    @Override
    protected String doInBackground(Void... voids) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(serverUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Convert the data to Base64
            String encodedData = Base64.encodeToString(base64Data.getBytes(), Base64.DEFAULT);

            // Set the request headers
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", Integer.toString(encodedData.length()));

            // Write the Base64 data to the request body
            OutputStream os = new BufferedOutputStream(connection.getOutputStream());
            os.write(encodedData.getBytes());
            os.flush();

            // Get the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result != null) {
            Log.i("this is ",result);
        } else {
            // Handle error
        }
    }
}
