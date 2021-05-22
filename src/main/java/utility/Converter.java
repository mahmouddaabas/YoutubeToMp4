package utility;

import javafx.application.Platform;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import view.GUI;

public class Converter implements Runnable {

    private String videoImage;
    private String videoTitle;
    private GUI gui;
    private Thread thread;
    private String url;

    /**
     * Constructs the class.
     * @param gui
     */
    public Converter(GUI gui) {
        this.gui = gui;
    }

    /**
     * Starts the thread if null.
     * Sets the url variable.
     * @param url
     */
    public void start(String url) {
        if(thread == null) {
            this.url = url;
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * Runs when the thread is started.
     */
    @Override
    public void run() {
        try {
            convertVideo();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts a webdriver and grabs all the neccessary elements before starting the conversion.
     * @throws InterruptedException
     */
    public void convertVideo() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/webdriver/chromedriver.exe");
        WebDriver driver = new ChromeDriver(settings());
        driver.manage().window().setSize(new Dimension(1920, 1080));

        driver.get("https://9convert.com/youtube-to-mp4/en");
        driver.findElement(By.className("search__input")).sendKeys(url + Keys.ENTER);

        Thread.sleep(2000);
        videoImage = driver.findElement(By.cssSelector("#content-wrapper > div > img")).getAttribute("src");

        driver.findElement(By.className("btn-action")).click();

        Thread.sleep(1000);
        //driver.findElement(By.id("asuccess")).click();

        String dlink = driver.findElement(By.id("asuccess")).getAttribute("href");
        System.out.println(dlink);

        videoTitle = driver.findElement(By.cssSelector("#modal-title > span")).getText();
        if(videoTitle.contains("/") || videoTitle.contains("|")){
            System.out.println("It does, replacing...");
            videoTitle = videoTitle.replace("/", "-");
            videoTitle = videoTitle.replace("|", "-");
        }
        //Sets the image and title on the GUI.
        setGUIAttributes();

        System.out.println(videoTitle);
        String path = "src/main/resources/downloads/" + videoTitle + ".mp4";

        driver.close();

        //Sets thread to null when finished.
        thread = null;

        //Sends the dlink, path and GUI object(For the progressbar) to Downloader.
        Downloader downloader = new Downloader(dlink, path, gui);
        downloader.start();
    }

    /**
     * Sets the video image and the title on the GUI.
     */
    public void setGUIAttributes() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gui.setVideoImage(videoImage);
                gui.setVideoTitle(videoTitle);
            }
        });
    }

    /**
     * Sets the settings for the webdriver.
     * @return
     */
    public static ChromeOptions settings() {
        final ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

        return options;
    }
}
