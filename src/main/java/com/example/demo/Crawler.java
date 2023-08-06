package com.example.demo;
import com.example.demo.FinishedCourseList;

import com.example.demo.dao.BasicEntity;
import com.example.demo.dao.RequiredCourseEntity;
import com.example.demo.dao.TimeTableEntity;
import com.google.common.base.Splitter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import net.sourceforge.tess4j.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@Component
public class Crawler {
    static ChromeOptions options;
    static WebDriver driver;
    public static String loginMessage = "";
    @Autowired
    TimeTableRepository timeTableRepository;
    public static void CrawlerHandle(String userAccount, String userPassword) throws IOException, TesseractException, InterruptedException {

        System.setProperty("javax.net.ssl.trustStore", "jssecacerts"); //解決SSL問題
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");
        //C:\Program Files\Google\Chrome\Application

        ChromeOptions options = new ChromeOptions();

        //options = new ChromeOptions();

        options.addArguments("–incognito");
        options.addArguments("remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://ais.ntou.edu.tw/Default.aspx");
        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);

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

                //BufferedImage subImage = image.getSubimage(point.getX()+350, point.getY()+132, width + 6, height + 4);//朱
                //BufferedImage subImage = image.getSubimage(point.getX()+205, point.getY()+69, width + 6, height + 4);
                BufferedImage subImage = image.getSubimage(point.getX()+120, point.getY()+55, width + 6, height + 4);
                ImageIO.write(subImage, "png", screenshot);
                File screenshotLocation = new File("test.png");
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
                new WebDriverWait(driver, Duration.ofSeconds(1)).until(ExpectedConditions.alertIsPresent());
                alert = driver.switchTo().alert();
                if (alert != null) {
                    System.out.println(alert.getText() + "1");
                    if (Objects.equals(alert.getText(), "帳號或密碼錯誤，請查明後再登入，若您不確定密碼，請執行忘記密碼，取得新密碼後再登入!")) {
                        System.out.println(alert.getText() + "2");
                        loginMessage = "帳號或密碼錯誤";
                        //driver.close();
                        break;
                    }
                    alert.accept();
                }
                System.out.println("驗證碼錯誤，重新登入\n");
            } catch (Exception e) {
                System.out.println("登入成功!!!\n");
                loginMessage = "登入成功";
                break;
            }
        }


        /*
        //登入後
        driver.switchTo().frame("menuFrame");
        driver.findElement(By.id("Menu_TreeViewt1")).click(); //教務系統
        Thread.sleep(3000);
        driver.findElement(By.linkText("成績系統")).click(); //成績系統
        Thread.sleep(3000);
        driver.findElement(By.linkText("查詢各式成績")).click(); //查詢各式成績
        driver.switchTo().defaultContent();
        driver.switchTo().frame("mainFrame");
        driver.findElement(By.xpath("//*[@id=\"RB_TYPE_3\"]")).click(); //歷年成績
        driver.findElement(By.xpath("//*[@id=\"QUERY_BTN1\"]")).click(); //查詢


        driver.switchTo().defaultContent();
        driver.switchTo().frame("viewFrame");
        Thread.sleep(3000);
//        System.out.println(driver.getPageSource());

        // 添加等待
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //獲取scoretable
        List<WebElement> trList = driver.findElements(By.cssSelector("#DataGrid > tbody > tr"));
        for(WebElement row:trList){
            List<WebElement> cols= row.findElements(By.tagName("td"));
            System.out.println("=======================");
            for(int i = 0; i < cols.size(); i++){
                if(i == 0)  System.out.println(cols.get(i).getText());
                else if(i == 1)  System.out.println(cols.get(i).getText());
                else if(i == 3)  System.out.println(cols.get(i).getText());
                else if(i == 4)  System.out.println(cols.get(i).getText());
                else if(i == 5)  System.out.println(cols.get(i).getText());
                else if(i == 6)  System.out.println(cols.get(i).getText());
                else if(i == 7)  System.out.println(cols.get(i).getText());
            }
            System.out.println("=======================");
        }
        driver.close();*/
    }
    public static BasicEntity getBasicData(String studentID, String password) throws InterruptedException {//基本資料
        BasicEntity personalInformation = new BasicEntity();
        driver.switchTo().frame("menuFrame");
        driver.findElement(By.id("Menu_TreeViewt1")).click(); //教務系統
        Thread.sleep(3000);
        driver.findElement(By.linkText("學生基本資料維護作業")).click(); //學生基本資料維護作業
        Thread.sleep(3000);
        driver.findElement(By.linkText("維護舊生資料")).click(); //維護舊生資料
        driver.switchTo().defaultContent();
        driver.switchTo().frame("mainFrame");
        String name = driver.findElement(By.id("M_CH_NAME")).getText();
        String department = driver.findElement(By.id("M_TEACH_GRP_NAME")).getText();
        String grade = driver.findElement(By.id("M_GRADE_NAME")).getText();
        String team = driver.findElement(By.id("M_CLASSNO_NAME")).getText();//class
        String birth = driver.findElement(By.id("M_BIRTH_DATE")).getText();
        System.out.println("姓名 : "+name);
        System.out.println("系所 : "+department);
        System.out.println("年級 : "+grade);
        System.out.println("班級 : "+team);
        System.out.println("生日 : "+birth);
        personalInformation.setStudentID(studentID);
        personalInformation.setPassword(password);
        personalInformation.setName(name);
        personalInformation.setDepartment(department.replaceAll("[^\\u4E00-\\u9FA5]",""));
        personalInformation.setGrade(grade);
        personalInformation.setTeam(team);
        personalInformation.setBirth(birth);
        return personalInformation;
    }

    public static ArrayList<FinishedCourse> getFinishedCredict(ArrayList<FinishedCourse> ori, String sem) throws InterruptedException{
        //已完成課程
        ArrayList<FinishedCourse> fCourses = new ArrayList<FinishedCourse>();

        driver.switchTo().defaultContent();
        driver.switchTo().frame("menuFrame");
        Thread.sleep(1000);
        driver.findElement(By.linkText("成績系統")).click(); //成績系統
        Thread.sleep(1000);
        driver.findElement(By.linkText("查詢各式成績")).click(); //查詢各式成績
        driver.switchTo().defaultContent();
        driver.switchTo().frame("mainFrame");
        driver.findElement(By.xpath("//*[@id=\"RB_TYPE_3\"]")).click(); //歷年成績
        driver.findElement(By.xpath("//*[@id=\"QUERY_BTN1\"]")).click(); //查詢

        driver.switchTo().defaultContent();
        driver.switchTo().frame("viewFrame");
        Thread.sleep(3000);

        // 添加等待
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //獲取scoretable
        List<WebElement> trList = driver.findElements(By.cssSelector("#DataGrid > tbody > tr"));
        //ArrayList<String> cTime = new ArrayList<String>();   //學期
        ArrayList<String> cID = new ArrayList<String>();     //課號
        ArrayList<FinishedCourse> fcList = new ArrayList<FinishedCourse>();
        int j = 0;
        for(WebElement row:trList){
                List<WebElement> cols= row.findElements(By.tagName("td"));
                if(j > 0){
                    FinishedCourse fc = new FinishedCourse();
                    fc.setSemester(cols.get(0).getText());
                    //cTime.add(cols.get(0).getText());
                    System.out.println("**********Time: " + cols.get(0).getText());
                    cID.add(cols.get(1).getText());
                    System.out.println("**********ID: " + cols.get(1).getText());
                    fc.setCredit(cols.get(3).getText());
                    fc.setCategory(cols.get(4).getText());
                    fc.setName(cols.get(5).getText());
                    fc.setTeacher(cols.get(6).getText());
                    fcList.add(fc);
                }
                else j++;
        }
        
        System.out.println("*********cID size: " + cID.size());
        System.out.println("c sme: " + fcList.get(cID.size()-1).getSemester());
        if(sem.equals(fcList.get(cID.size()-1).getSemester())){
            System.out.println("already update: " + fcList.get(cID.size()-1).getSemester());
            return ori;
        }

        //取得開課單位
        driver.switchTo().defaultContent();
        driver.switchTo().frame("menuFrame");
        //driver.findElement(By.id("Menu_TreeViewt1")).click(); //教務系統
        //Thread.sleep(3000);
        driver.findElement(By.linkText("選課系統")).click(); //選課系統
        Thread.sleep(3000);
        driver.findElement(By.linkText("歷年課程課表查詢")).click();
        driver.switchTo().defaultContent();
        driver.switchTo().frame("mainFrame");
        for(int i = 0; i < cID.size(); i++){
            Thread.sleep(3000);

            String nowYear = driver.findElement(By.id("AYEAR")).getText();  //取得現在學年
            String nowSemester= driver.findElement(By.id("SMS")).getText(); //取得現在學期
            int ny = Integer.parseInt(nowYear);
            int ns = Integer.parseInt(nowSemester);

            String[] semester = fcList.get(i).getSemester().split("(?<=\\G.{3})");
            if(Integer.parseInt(semester[0]) <= ny && Integer.parseInt(semester[1]) <= ns){
                driver.findElement(By.id("Q_AYEAR")).findElement(By.xpath("//option[@value='" + semester[0] + "']")).click();
                driver.findElement(By.id("Q_SMS")).findElement(By.xpath("//option[@value='" + semester[1] + "']")).click();
                //driver.findElement(By.id("radioButtonClass_0")).click();
                driver.findElement(By.id("Q_CH_LESSON")).clear();
                driver.findElement(By.id("Q_CH_LESSON")).sendKeys(cID.get(i));
                driver.findElement(By.xpath("//*[@id=\"QUERY_BTN7\"]")).click(); //關鍵字查詢

                Thread.sleep(800);
                List<WebElement> trList2 = driver.findElements(By.cssSelector("#DataGrid > tbody > tr"));
                if(trList2.size() > 1){
                    List<WebElement> col = trList2.get(1).findElements(By.tagName("td"));
                    fcList.get(i).setDepartment(col.get(4).getText());
                    System.out.println("**********course ID(" + i + ") " + cID.get(i));
                    System.out.println("**********department(" + i + ") " + fcList.get(i).getDepartment());
                    fCourses.add(fcList.get(i));
                }
                else{
                    fcList.get(i).setDepartment("");
                    fCourses.add(fcList.get(i));
                    System.out.println("**********course ID(" + i + ") " + cID.get(i));
                    System.out.println("**********department: " + fcList.get(i).getDepartment());
                }
            }

        }
        return fCourses;
    }

    //@Scheduled(fixedDelay = 5000)    //間隔5秒
    public static void detectCoureses(ArrayList<CourseToBeDetected> courses) throws InterruptedException{
        String tDate = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDateTime.now()); //today


        driver.switchTo().defaultContent();
        Thread.sleep(1000);
        driver.switchTo().frame("menuFrame");
        Thread.sleep(1000);
        driver.findElement(By.id("Menu_TreeViewt1")).click(); //教務系統
        Thread.sleep(1000);
        driver.findElement(By.linkText("選課系統")).click(); //選課系統
        Thread.sleep(3000);
        driver.findElement(By.linkText("歷年課程課表查詢")).click();
        driver.switchTo().defaultContent();
        driver.switchTo().frame("mainFrame");
        while (courses.size() > 0) {
            for (int i = 0; i < courses.size(); i++) {
                Thread.sleep(3000);
                String[] semester = courses.get(i).getSemester().split("(?<=\\G.{3})");

                System.out.println("semester:" + semester[0]);

                driver.findElement(By.id("Q_AYEAR")).findElement(By.xpath("//option[@value='" + semester[0] + "']")).click();
                driver.findElement(By.id("Q_SMS")).findElement(By.xpath("//option[@value='" + semester[1] + "']")).click();
                //driver.findElement(By.id("radioButtonClass_0")).click();
                driver.findElement(By.id("Q_CH_LESSON")).clear();
                driver.findElement(By.id("Q_CH_LESSON")).sendKeys(courses.get(i).getNumber());
                driver.findElement(By.xpath("//*[@id=\"QUERY_BTN7\"]")).click(); //關鍵字查詢

                Thread.sleep(500);
                List<WebElement> trList2 = driver.findElements(By.cssSelector("#DataGrid > tbody > tr"));
                //            List<WebElement> col = trList2.get(1).findElements(By.tagName("td"));
                //            System.out.println("***" + col.get(2) + "***");}
            }
        }
    }

    public static List<TimeTableEntity.Info> getMyClass(String studentID, String password) throws InterruptedException{
        List<TimeTableEntity.Info> myClassList = new ArrayList<>();
        driver.switchTo().frame("menuFrame");
        driver.findElement(By.id("Menu_TreeViewt1")).click(); //教務系統
        Thread.sleep(3000);
        driver.findElement(By.linkText("選課系統")).click(); //選課系統
        Thread.sleep(3000);
        driver.findElement(By.linkText("學生個人選課清單課表列印")).click(); //學生個人選課清單課表列印
        driver.switchTo().defaultContent();
        driver.switchTo().frame("mainFrame");
        String nowYear = driver.findElement(By.id("AYEAR")).getText();//取得現在學年
        String nowSemester= driver.findElement(By.id("SMS")).getText();//取得現在學期
        //select選單 : 調整為目前學期
        driver.findElement(By.id("Q_AYEAR")).findElement(By.xpath("//option[@value='"+nowYear+"']")).click();
        driver.findElement(By.id("Q_SMS")).findElement(By.xpath("//option[@value='"+nowSemester+"']")).click();

        driver.findElement(By.xpath("//*[@id=\"QUERY_BTN1\"]")).click(); //選課清單
        //顯示20筆
        Thread.sleep(3000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = '20';", driver.findElement(By.id("PC_PageSize")));
        //driver.findElement(By.id("PC_PageSize")).sendKeys("20");
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"PC_ShowRows\"]")).click();
        Thread.sleep(3000);
        //已選課程表格
        List<WebElement> trList = driver.findElements(By.cssSelector("#DataGrid > tbody > tr"));
        for(int i = 1;i< trList.size();i++){
            TimeTableEntity.Info myClass = new TimeTableEntity.Info();
            trList = driver.findElements(By.cssSelector("#DataGrid > tbody > tr"));
            WebElement row = trList.get(i);
            List<WebElement> cols= row.findElements(By.tagName("td"));
            Thread.sleep(1000);
            System.out.println("課名 : " + cols.get(3).getText());
            myClass.setName(cols.get(3).getText());
            System.out.println("課號 : " + cols.get(2).getText());
            myClass.setClassNum(cols.get(2).getText());
            System.out.println("授課老師 : " + cols.get(6).getText());
            myClass.setTeacher(cols.get(6).getText());
            driver.findElement(By.linkText(cols.get(2).getText())).click();

            if(i<9) driver.findElement(By.cssSelector("a[href=\"javascript:__doPostBack('DataGrid$ctl0"+(i+1)+"$COSID','')\"]")).click();
            else driver.findElement(By.cssSelector("a[href=\"javascript:__doPostBack('DataGrid$ctl"+(i+1)+"$COSID','')\"]")).click();//click class number
            //switch iframe
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            WebElement iframe = driver.findElement(By.tagName("iframe"));
            driver.switchTo().frame(iframe);
            driver.switchTo().frame("mainFrame");
            List<WebElement> tr = driver.findElements(By.cssSelector("#QTable2 > tbody > tr"));
            String time = tr.get(1).findElements(By.tagName("td")).get(1).findElement(By.tagName("table")).findElements(By.tagName("tr")).get(11).findElement(By.id("M_SEG")).getText();
            System.out.println("時間 : " + time);
            String[] timeArray = time.split(",");
            myClass.setTime(timeArray);
            String classroom = tr.get(1).findElements(By.tagName("td")).get(1).findElement(By.tagName("table")).findElements(By.tagName("tr")).get(11).findElement(By.id("M_CLSSRM_ID")).getText();
            System.out.println("教室 : " + classroom);
            if(classroom.indexOf(',')!=-1)classroom = classroom.substring(0, classroom.indexOf(','));
            myClass.setClassroom(classroom);
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            driver.switchTo().defaultContent();
            driver.switchTo().frame("mainFrame");
            driver.findElement(By.xpath("//*[@title=\"Close\"]")).click();
            driver.switchTo().defaultContent();
            driver.switchTo().frame("mainFrame");
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            myClassList.add(myClass);
        }

        return myClassList;
    }
    public static void getAllGeneralClass() throws InterruptedException{
        driver.switchTo().frame("menuFrame");
        driver.findElement(By.id("Menu_TreeViewt1")).click(); //教務系統
        Thread.sleep(3000);
        driver.findElement(By.linkText("選課系統")).click(); //選課系統
        Thread.sleep(3000);
        driver.findElement(By.linkText("課程課表查詢")).click(); //課程課表查詢
        Thread.sleep(3000);
        driver.switchTo().defaultContent();
        driver.switchTo().frame("mainFrame");
        //select
        driver.findElement(By.id("Q_FACULTY_CODE")).findElement(By.xpath("//option[@value='090M-共同教育中心博雅教育組']")).click();
        //開課單位查詢
        driver.findElement(By.xpath("//*[@id=\"QUERY_BTN1\"]")).click();
        //顯示300筆
        Thread.sleep(3000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = '300';", driver.findElement(By.id("PC_PageSize")));
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"PC_ShowRows\"]")).click();
        Thread.sleep(5000);
    }

    public static List<RequiredCourseEntity> findRCourse(String takingCategory, String takingGrade) throws InterruptedException{
        if(takingGrade.equals("大一")) {
            takingGrade = "1";
        }
        else if(takingGrade.equals("大二")) {
            takingGrade = "2";
        }
        else if(takingGrade.equals("大三")) {
            takingGrade = "3";
        }
        else if(takingGrade.equals("大四")) {
            takingGrade = "4";
        }
        List<RequiredCourseEntity> RCourseList = new ArrayList<>();
        driver.switchTo().frame("menuFrame");
        driver.findElement(By.id("Menu_TreeViewt1")).click(); //教務系統
        Thread.sleep(3000);
        driver.findElement(By.linkText("選課系統")).click(); //選課系統
        Thread.sleep(3000);
        driver.findElement(By.linkText("課程課表查詢")).click(); //課程課表查詢
        Thread.sleep(3000);
        driver.switchTo().defaultContent();
        driver.switchTo().frame("mainFrame");
        //select
        driver.findElement(By.id("Q_DEGREE_CODE")).findElement(By.xpath("//option[@value='0']")).click();
        System.out.println("0-大學");
        WebElement selectedOption1 = driver.findElement(By.id("Q_DEGREE_CODE")).findElement(By.xpath("//option[@value='0']"));
        String selectedOptionText1 = selectedOption1.getText();
        System.out.println("->"+ selectedOptionText1);

        driver.findElement(By.id("Q_FACULTY_CODE")).findElement(By.xpath("//option[@value='0507']")).click();
        System.out.println("資工系");
        WebElement selectedOption2 = driver.findElement(By.id("Q_FACULTY_CODE")).findElement(By.xpath("//option[@value='0507']"));
        String selectedOptionText2 = selectedOption2.getText();
        System.out.println("->"+ selectedOptionText2);
        /*
        driver.findElement(By.id("Q_GRADE")).click();
        {
            WebElement dropdown = driver.findElement(By.id("Q_GRADE"));
            dropdown.findElement(By.xpath("//option[. = '2']")).click();
        }
        */
        driver.findElement(By.id("Q_GRADE")).findElement(By.xpath("//option[. ='"+ takingGrade +"']")).click();
        System.out.println(takingGrade);
        WebElement selectedOption3 = driver.findElement(By.id("Q_GRADE")).findElement(By.xpath("//option[. ='"+ takingGrade +"']"));
        String selectedOptionText3 = selectedOption3.getText();
        System.out.println("->"+ selectedOptionText3);


        //driver.findElement(By.xpath("//*[@id='Q_GRADE']")).findElement(By.xpath("//option[@value='2']")).click();
        

        driver.findElement(By.xpath("//*[@id=\"QUERY_BTN1\"]")).click();  //開課單位查詢
        Thread.sleep(3000);
        //show_25
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = '25';", driver.findElement(By.id("PC_PageSize")));
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"PC_ShowRows\"]")).click();
        Thread.sleep(3000);
        List<WebElement> rcReault = driver.findElements(By.cssSelector("#DataGrid > tbody > tr"));
        System.out.println(takingCategory);
        for(int i = 1;i < rcReault.size();i++){
            System.out.println(i);
            WebElement itemCourse = rcReault.get(i);
            List<WebElement> item = itemCourse.findElements(By.tagName("td"));
            Thread.sleep(3000);
            System.out.println(item.get(10).getText());
            System.out.println(takingCategory);
            if(item.get(10).getText().equals(takingCategory))
            {
                System.out.println("課號 : " + item.get(2).getText());
                System.out.println("課名 : " + item.get(3).getText());
                System.out.println("老師 : " + item.get(6).getText());
                System.out.println("學分 : " + item.get(9).getText());

                RequiredCourseEntity courseEntity = new RequiredCourseEntity();
                courseEntity.setCNumber(item.get(2).getText());
                courseEntity.setCName(item.get(3).getText());
                courseEntity.setCGrade(item.get(5).getText());
                courseEntity.setCTeacher(item.get(6).getText());
                courseEntity.setCCredit(item.get(9).getText());
                courseEntity.setCCategory(item.get(10).getText());

                RCourseList.add(courseEntity);

            }
        }
        return RCourseList;
    }

    public static void main(String[] args) throws Exception {

        String account = "00957039";
        String password = "kl29nhpo";
        CrawlerHandle(account,password);

        //getBasicData(account,password);
        //getMyClass(account,password);
        //getAllGeneralClass();
        //getFinishedCredict();
        //findRCourse("必修","3");
        //detectCoureses();

    }
}

