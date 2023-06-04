package com.example.demo;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import net.sourceforge.tess4j.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CrawlerMust {
    public static void CrawlerMust(String userAccount, String userPassword) throws IOException, TesseractException, InterruptedException {
        System.setProperty("javax.net.ssl.trustStore", "jssecacerts"); //解決SSL問題
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("–incognito");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://ais.ntou.edu.tw/Default.aspx");
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

        //登入
        while (true){
            //下載驗證碼圖片
            boolean flag = false;
            String captchaText = "";
            while (!flag) {
                WebElement element = driver.findElement(By.xpath("//img[@id='importantImg']"));
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                BufferedImage image = ImageIO.read(screenshot);
                Point point = element.getLocation();
                int width = element.getSize().getWidth();
                int height = element.getSize().getHeight();
                BufferedImage subImage = image.getSubimage(point.getX() + 205, point.getY() + 69, width + 5, height + 3);
                ImageIO.write(subImage, "png", screenshot);
                File screenshotLocation = new File("C:\\Users\\elain\\1112\\senior_project\\Seleniumtest\\test.png");
                FileUtils.copyFile(screenshot, screenshotLocation);
                System.out.println("驗證碼圖片已下載!!");
                //圖片辨識
                Tess tess = new Tess();
                tess.tessHandler();
                if (tess.text.length() == 4 && tess.text.matches("[a-zA-Z0-9]+")) {
                    flag = true;
                    captchaText = tess.text;
                } else {
                    element.click();
                    flag = false;
                    captchaText = "";
                    System.out.println("驗證碼字數不符合，換一張圖片重新辨識\n");
                }
            }
            //輸入帳號
            WebElement account = driver.findElement(By.name("M_PORTAL_LOGIN_ACNT"));
            account.clear();
            account.sendKeys(userAccount);

            //輸入密碼
            WebElement password = driver.findElement(By.name("M_PW"));
            password.sendKeys(userPassword);

            //輸入驗證碼
            WebElement check = driver.findElement(By.name("M_PW2"));
            check.sendKeys(captchaText);
            driver.findElement(By.name("LGOIN_BTN")).click();
            Alert alert = null;
            try {
                new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.alertIsPresent());
                alert = driver.switchTo().alert();
                if (alert != null)
                    alert.accept();
                System.out.println("驗證碼錯誤，重新登入\n");
            } catch (Exception e) {
                System.out.println("登入成功!!!\n");
                break;
            }
        }

        //登入後
        driver.switchTo().frame("menuFrame");
        driver.findElement(By.id("Menu_TreeViewt1")).click(); //教務系統
        Thread.sleep(2000);
        driver.findElement(By.linkText("選課系統")).click(); //選課系統
        Thread.sleep(2000);
        driver.findElement(By.linkText("查詢必修科目表")).click(); //查詢必修科目表
        driver.switchTo().defaultContent();
        driver.switchTo().frame("mainFrame");
        WebElement dropdown = driver.findElement(By.id("Q_ENROLL_AYEAR"));
        dropdown.findElement(By.xpath("//option[. = '109']")).click();
        WebElement dropdown1 = driver.findElement(By.id("Q_FACULTY_CODE"));
        dropdown1.findElement(By.xpath("//option[. = '0507-資訊工程學系']")).click();
        driver.findElement(By.id("QUERY_BTN1")).click();

        // 添加等待
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        List<WebElement> trList = driver.findElements(By.cssSelector(".sortable1 > tbody > tr"));
        System.out.println(trList.size());
        for(int i = 2; i < trList.size(); i++){
            List<WebElement> cols= trList.get(i).findElements(By.tagName("td"));
            if(i == 2 || i == 13) System.out.println(cols.get(1).getText());
            else if(i == 12) System.out.println(cols.get(0).getText() + " : " + cols.get(1).getText());
            else if(i < 31) System.out.println(cols.get(0).getText());
            else if(i > 30 && i < 36) System.out.println(cols.get(0).getText() + " : " + cols.get(1).getText());
        }
        driver.close();
    }

    public static void main(String[] args) throws Exception {

        String account = "00957123";
        String password = "14105097";
        CrawlerMust(account,password);

    }

}
