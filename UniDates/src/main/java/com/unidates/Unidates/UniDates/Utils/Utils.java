package com.unidates.Unidates.UniDates.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Timer;

public class Utils {

    public static byte[] downloadUrl(String stringDownload) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            URL toDownload = new URL(stringDownload);
            byte[] chunk = new byte[4096];
            int bytesRead;
            InputStream stream = toDownload.openStream();
            while ((bytesRead = stream.read(chunk)) > 0) {
                outputStream.write(chunk, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return outputStream.toByteArray();
    }
}
