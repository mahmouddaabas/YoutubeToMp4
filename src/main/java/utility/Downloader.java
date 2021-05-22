package utility;

import view.GUI;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Downloader implements Runnable {

    private String dlink;
    private String path;
    private GUI gui;
    private Thread thread;

    /**
     * Constructs the class.
     * @param dlink
     * @param path
     */
    public Downloader(String dlink, String path, GUI gui) {
        this.dlink = dlink;
        this.path = path;
        this.gui = gui;
    }

    /**
     * Starts the thread.
     */
    public void start() {
        if(thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * Downloads the file with the url and path provided in the constructor.
     */
    @Override
    public void run() {
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
                                float percent = (totalDataRead * 100) / filesize;
                                System.out.println("Downloading Percent : " + percent + "%");
                                //Updates the progressbar.
                                gui.setProgress(percent/100);
                            }
                            System.out.println("Finished.");
                            //Sets the thread to null when finished.
                            thread = null;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }
}
