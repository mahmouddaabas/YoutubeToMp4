package utility;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Converter {

    private WebDriver driver;

    public void convertVideo(String url) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/webdriver/chromedriver.exe");
        WebDriver driver = new ChromeDriver(settings());
        driver.manage().window().setSize(new Dimension(1920, 1080));

        driver.get("https://9convert.com/youtube-to-mp4/en");
        driver.findElement(By.className("search__input")).sendKeys(url + Keys.ENTER);

        Thread.sleep(2000);
        driver.findElement(By.className("btn-action")).click();

        Thread.sleep(1000);
        //driver.findElement(By.id("asuccess")).click();

        String dlink = driver.findElement(By.id("asuccess")).getAttribute("href");
        System.out.println(dlink);

        String videoTitle = driver.findElement(By.cssSelector("#modal-title > span")).getText();
        if(videoTitle.contains("/") || videoTitle.contains("|")){
            System.out.println("It does, replacing...");
            videoTitle = videoTitle.replace("/", "-");
            videoTitle = videoTitle.replace("|", "-");
        }
        System.out.println(videoTitle);
        String path = "src/downloads/" + videoTitle + ".mp4";

        driver.close();
    }

    public static ChromeOptions settings() {
        final ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");

        return options;
    }
}
