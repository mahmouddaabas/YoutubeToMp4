package utility;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Downloader {

    public static void download(String dlink, String path){
        if (dlink != null || path != null) {
            try {
                java.net.URL url = new URL(dlink);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int filesize = connection.getContentLength();
                float totalDataRead = 0;
                try (BufferedInputStream in = new BufferedInputStream(connection.getInputStream())) {
                    FileOutputStream fos = new FileOutputStream(path);
                    try (java.io.BufferedOutputStream bout = new BufferedOutputStream(fos, 1024)) {
                        byte[] data = new byte[1024];
                        int i;
                        while ((i = in.read(data, 0, 1024)) >= 0) {
                            totalDataRead = totalDataRead + i;
                            bout.write(data, 0, i);
                            float Percent = (totalDataRead * 100) / filesize;
                            System.out.println("Downloading Percent : " + Percent + "%");
                        }
                        System.out.println("Finished.");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
