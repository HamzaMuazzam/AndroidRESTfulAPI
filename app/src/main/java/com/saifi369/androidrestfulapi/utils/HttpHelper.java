package com.saifi369.androidrestfulapi.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHelper {

    public static String downloadUrl(String address,String userName,String password) throws Exception {

        //Authorization: Basic {base64_encode(username:password)}

//        byte[] loginBytes=(userName+":"+password).getBytes();
//
//        StringBuilder stringBuilder=new StringBuilder()
//                .append("Basic ")
//                .append(Base64.encodeToString(loginBytes,Base64.DEFAULT));

        InputStream inputStream=null;

        try {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestProperty("Authorization", stringBuilder.toString());
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(10000);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");


            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new Exception("Error: Got response code: " + responseCode);
            }

            inputStream = connection.getInputStream();

            return readStream(inputStream);
        }
        finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    private static String readStream(InputStream stream) throws IOException {

        byte[] buffer = new byte[1024];
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        BufferedOutputStream out = null;
        try {
            int length = 0;
            out = new BufferedOutputStream(byteArray);
            while ((length = stream.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            out.flush();
            return byteArray.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (out != null) {
                out.close();
            }
        }

    }

}
