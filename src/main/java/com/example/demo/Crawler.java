package com.example.demo;

import com.example.demo.dao.*;
import com.example.demo.repository.PECourseRepository;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.support.ui.Select;


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
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;



public class Crawler {
    static ChromeOptions options;
    static WebDriver driver;
    public static String loginMessage = "";
    @Autowired
    PECourseRepository peCourseRepository;
    public static void CrawlerHandle(String userAccount, String userPassword) throws IOException, TesseractException, InterruptedException {

        System.setProperty("javax.net.ssl.trustStore", "jssecacerts"); //解決SSL問題



//        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\chromedriver.exe");

        //System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");//白

        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");



        //System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");

        //C:\Program Files\Google\Chrome\Application\chromedriver.exe  //白
        //C:\\Program Files (x86)\\Google
        //C:\Program Files (x86)\Google\Chrome\Application
        //C:\Program Files\Google\Chrome\Application

        ChromeOptions options = new ChromeOptions();

        options.addArguments("–incognito"); //無痕
        options.addArguments("remote-allow-origins=*");
        options.addArguments("-headless");
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
                WebElement element = driver.findElement(By.xpath("//img[@id='importantImg']"));//驗證碼圖片

                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                BufferedImage image = ImageIO.read(screenshot);
                ImageIO.write(image, "png", screenshot);
                File screenshotLocation = new File("ttt.png");
                FileUtils.copyFile(screenshot, screenshotLocation);
                Point point = element.getLocation();
                int width = element.getSize().getWidth();
                int height = element.getSize().getHeight();
                BufferedImage subImage = image.getSubimage(point.getX(), point.getY(), 100, height + 4);//headless
                //BufferedImage subImage = image.getSubimage(point.getX()+350, point.getY()+132, width + 6, height + 4);//朱
                //BufferedImage subImage = image.getSubimage(point.getX()+205, point.getY()+69, width + 6, height + 4);//31
                //BufferedImage subImage = image.getSubimage(point.getX()+120, point.getY()+55, width + 6, height + 4);//白

                ImageIO.write(subImage, "png", screenshot);
                screenshotLocation = new File("test.png");
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
    //基本資料
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
        driver.quit();
        return personalInformation;
    }

    public static ArrayList<FinishedCourse> getFinishedCredict(ArrayList<FinishedCourse> ori, String sem) throws InterruptedException{
        //已完成課程
        ArrayList<FinishedCourse> fCourses = new ArrayList<FinishedCourse>();

        //driver.switchTo().defaultContent();
        driver.switchTo().frame("menuFrame");

        Thread.sleep(2000);
        driver.findElement(By.id("Menu_TreeViewt1")).click(); //教務系統
        Thread.sleep(1000);

        driver.findElement(By.linkText("成績系統")).click(); //成績系統
        Thread.sleep(2000);
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
            String now = nowYear + nowSemester;
            int ny = Integer.parseInt(nowYear);
            int ns = Integer.parseInt(nowSemester);

            String[] semester = fcList.get(i).getSemester().split("(?<=\\G.{3})");
            if(!fcList.get(i).getSemester().equals(now)){
                System.out.println("semester of course: " + fcList.get(i).getSemester());
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

    
    public static void detectCoureses(ArrayList<CourseToBeDetected> courses) throws InterruptedException{
        String tDate = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDateTime.now()); //today

        driver.switchTo().defaultContent();
        driver.switchTo().frame("menuFrame");
        Thread.sleep(1000);
        driver.findElement(By.id("Menu_TreeViewt1")).click(); //教務系統
        Thread.sleep(3000);
        driver.findElement(By.linkText("選課系統")).click(); //選課系統
        Thread.sleep(3000);
        driver.findElement(By.linkText("線上即時加退選")).click();
        driver.switchTo().defaultContent();
        driver.switchTo().frame("mainFrame");

        for(CourseToBeDetected c : courses){
            Thread.sleep(1000);
            driver.findElement(By.id("Q_COSID")).clear();
            driver.findElement(By.id("Q_COSID")).sendKeys(c.getNumber());
            driver.findElement(By.xpath("//*[@id=\"QUERY_COSID_BTN\"]")).click();
            Thread.sleep(1500);
            driver.findElement(By.xpath("//a[@href=\"javascript:__doPostBack('DataGrid1$ctl02$dolink','')\"]")).click();
            //switch iframe
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            WebElement iframe = driver.findElement(By.tagName("iframe"));
            driver.switchTo().frame(iframe);
            driver.switchTo().frame("mainFrame");
            Thread.sleep(1500);
            List<WebElement> trList2 = driver.findElements(By.cssSelector("#QTable2 > tbody > tr"));
            List<WebElement> tablelist = trList2.get(1).findElements(By.tagName("td")).get(1).findElements(By.tagName("table"));
            List<WebElement> tr = tablelist.get(0).findElements(By.tagName("tr"));
            String maxST = tr.get(9).findElement(By.id("M_MAX_ST")).getText();
            String nowST = tr.get(13).findElement(By.id("M_CHOICE_QTY")).getText();
            System.out.println("Maximum: " + maxST + ", now: " + nowST);
            if(Integer.parseInt(nowST) < Integer.parseInt(maxST)){
                System.out.println(c.getNumber() + " has place now!");
                c.setIsFull(false);
            }
            else{
                System.out.println(c.getNumber() + " is full now.");
                c.setIsFull(true);
            }
            driver.switchTo().defaultContent();
            driver.switchTo().frame("mainFrame");
            driver.findElement(By.xpath("//*[@title=\"Close\"]")).click();
            driver.switchTo().defaultContent();
            driver.switchTo().frame("mainFrame");
        }
        driver.switchTo().defaultContent();
        driver.switchTo().frame("menuFrame");
        driver.findElement(By.linkText("線上即時加退選")).click();
        Thread.sleep(1000);
        driver.findElement(By.linkText("選課系統")).click(); //選課系統
        Thread.sleep(1000);
        driver.findElement(By.id("Menu_TreeViewt1")).click(); //教務系統
    }

    public static List<TimeTableEntity.Info> getMyClass(String studentID, String password) throws InterruptedException{
        System.out.println("getMyClass");
        List<TimeTableEntity.Info> myClassList = new ArrayList<>();
        driver.switchTo().frame("menuFrame");
        driver.findElement(By.id("Menu_TreeViewt1")).click(); //教務系統
        System.out.println("教務系統");
        Thread.sleep(3000);
        driver.findElement(By.linkText("選課系統")).click(); //選課系統
        System.out.println("選課系統");
        Thread.sleep(3000);
        driver.findElement(By.linkText("學生個人選課清單課表列印")).click(); //學生個人選課清單課表列印
        System.out.println("學生個人選課清單課表列印");
        driver.switchTo().defaultContent();
        driver.switchTo().frame("mainFrame");
        String nowYear = driver.findElement(By.id("AYEAR")).getText();//取得現在學年
        String nowSemester= driver.findElement(By.id("SMS")).getText();//取得現在學期
        //select選單 : 調整為目前學期
        driver.findElement(By.id("Q_AYEAR")).findElement(By.xpath("//option[@value='"+nowYear+"']")).click();
        driver.findElement(By.id("Q_SMS")).findElement(By.xpath("//option[@value='"+nowSemester+"']")).click();

        driver.findElement(By.xpath("//*[@id=\"QUERY_BTN1\"]")).click(); //選課清單
        System.out.println("調整為目前學期");
        //顯示30筆
        Thread.sleep(3000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = '30';", driver.findElement(By.id("PC_PageSize")));
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"PC_ShowRows\"]")).click();
        Thread.sleep(3000);
        System.out.println("顯示30筆");
        //已選課程表格
        List<WebElement> trList = driver.findElements(By.cssSelector("#DataGrid > tbody > tr"));
        for(int i = 1;i< trList.size();i++){
            TimeTableEntity.Info myClass = new TimeTableEntity.Info();
            trList = driver.findElements(By.cssSelector("#DataGrid > tbody > tr"));
            WebElement row = trList.get(i);
            List<WebElement> cols= row.findElements(By.tagName("td"));
            Thread.sleep(1000);
            myClass.setName(cols.get(3).getText());
            myClass.setClassNum(cols.get(2).getText());
            myClass.setTeacher(cols.get(6).getText());
            myClass.setCategory(cols.get(9).getText());
            driver.findElement(By.linkText(cols.get(2).getText())).click();

            if(i<9) driver.findElement(By.cssSelector("a[href=\"javascript:__doPostBack('DataGrid$ctl0"+(i+1)+"$COSID','')\"]")).click();
            else driver.findElement(By.cssSelector("a[href=\"javascript:__doPostBack('DataGrid$ctl"+(i+1)+"$COSID','')\"]")).click();//click class number
            //switch iframe

            //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            WebElement iframe = driver.findElement(By.tagName("iframe"));
            driver.switchTo().frame(iframe);
            driver.switchTo().frame("mainFrame");
            List<WebElement> tr = driver.findElements(By.cssSelector("#QTable2 > tbody > tr")).get(1).findElements(By.tagName("td")).get(1).findElements(By.tagName("table")).get(0).findElements(By.tagName("tr"));
            String time = tr.get(11).findElement(By.id("M_SEG")).getText();
            String[] timeArray = time.split(",");
            myClass.setTime(timeArray);
            String classroom = tr.get(11).findElement(By.id("M_CLSSRM_ID")).getText();
            if(classroom.indexOf(',')!=-1)classroom = classroom.substring(0, classroom.indexOf(','));
            myClass.setClassroom(classroom);
            myClass.setOnline(tr.get(11).findElement(By.id("M_IS_LONGDIST_CURRI")).getText());
            myClass.setENname(tr.get(7).findElement(By.id("M_ENG_LESSON")).getText());
            myClass.setYearClass(tr.get(8).findElement(By.id("M_GRADE")).getText());
            myClass.setCredit(tr.get(8).findElement(By.id("M_CRD")).getText());
            myClass.setCredit(tr.get(8).findElement(By.id("M_CRD")).getText());
            myClass.setUpper(tr.get(9).findElement(By.id("M_MAX_ST")).getText());
            myClass.setLower(tr.get(9).findElement(By.id("M_MIN_ST")).getText());
            myClass.setDepartment(tr.get(4).findElement(By.id("M_FACULTY_NAME")).getText());
            myClass.setDuration(tr.get(10).findElement(By.id("M_COSTERM")).getText());
            // 課程大綱
            List<WebElement> tr2 = driver.findElements(By.cssSelector("#QTable2 > tbody > tr")).get(1).findElements(By.tagName("td")).get(1).findElements(By.tagName("table")).get(2).findElements(By.tagName("tr"));
            myClass.setTarget(tr2.get(1).findElement(By.id("M_CH_TARGET")).getText());
            myClass.setTargetE(tr2.get(2).findElement(By.id("M_ENG_TARGET")).getText());
            myClass.setPrerequisites(tr2.get(3).findElement(By.id("M_CH_PREOBJ")).getText());
            myClass.setPrerequisitesE(tr2.get(4).findElement(By.id("M_ENG_PREOBJ")).getText());
            myClass.setOutline(tr2.get(5).findElement(By.id("M_CH_OBJECT")).getText());
            myClass.setOutlineE(tr2.get(6).findElement(By.id("M_ENG_OBJECT")).getText());
            myClass.setTeachingMethod(tr2.get(7).findElement(By.id("M_CH_TEACH")).getText());
            myClass.setTeachingMethodE(tr2.get(8).findElement(By.id("M_ENG_TEACH")).getText());
            myClass.setReference(tr2.get(9).findElement(By.id("M_CH_REF")).getText());
            myClass.setReferenceE(tr2.get(10).findElement(By.id("M_ENG_REF")).getText());
            myClass.setSyllabus(tr2.get(11).findElement(By.id("M_CH_TEACHSCH")).getText());
            myClass.setSyllabusE(tr2.get(12).findElement(By.id("M_ENG_TEACHSCH")).getText());
            myClass.setEvaluation(tr2.get(13).findElement(By.id("M_CH_TYPE")).getText());
            myClass.setEvaluationE(tr2.get(14).findElement(By.id("M_ENG_TYPE")).getText());
            //back to main frame
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            driver.switchTo().defaultContent();
            driver.switchTo().frame("mainFrame");
            driver.findElement(By.xpath("//*[@title=\"Close\"]")).click();
            driver.switchTo().defaultContent();
            driver.switchTo().frame("mainFrame");
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            System.out.println(myClass.toString());
            myClassList.add(myClass);
        }

        return myClassList;
    }

    public static List<GeneralCourseEntity> getAllGeneralClass() throws InterruptedException{
        driver.switchTo().frame("menuFrame");
        driver.findElement(By.id("Menu_TreeViewt1")).click(); //教務系統
        Thread.sleep(1500);
        driver.findElement(By.linkText("選課系統")).click(); //選課系統
        Thread.sleep(1500);
        driver.findElement(By.linkText("課程課表查詢")).click(); //課程課表查詢
        Thread.sleep(1500);
        driver.switchTo().defaultContent();
        driver.switchTo().frame("mainFrame");
        //select
        driver.findElement(By.id("Q_FACULTY_CODE")).findElement(By.xpath("//option[@value='090M']")).click();
        //開課單位查詢
        driver.findElement(By.xpath("//*[@id=\"QUERY_BTN1\"]")).click();
        //顯示300筆
        Thread.sleep(3000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = '300';", driver.findElement(By.id("PC_PageSize")));
        Thread.sleep(1500);
        driver.findElement(By.xpath("//*[@id=\"PC_ShowRows\"]")).click();
        Thread.sleep(3000);

        List<GeneralCourseEntity> gCourses = new ArrayList<GeneralCourseEntity>();

        List<WebElement> trList = driver.findElements(By.cssSelector("#DataGrid > tbody > tr"));
        for(int i = 1; i < trList.size(); i++){
            GeneralCourseEntity gc = new GeneralCourseEntity();
            if(i<9) driver.findElement(By.cssSelector("a[href=\"javascript:__doPostBack('DataGrid$ctl0"+(i+1)+"$COSID','')\"]")).click();
            else driver.findElement(By.cssSelector("a[href=\"javascript:__doPostBack('DataGrid$ctl"+(i+1)+"$COSID','')\"]")).click();
            //switch iframe
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            WebElement iframe = driver.findElement(By.tagName("iframe"));
            driver.switchTo().frame(iframe);
            driver.switchTo().frame("mainFrame");
            Thread.sleep(1500);
            List<WebElement> trlist = driver.findElements(By.cssSelector("#QTable2 > tbody > tr"));
            List<WebElement> tablelist = trlist.get(1).findElements(By.tagName("td")).get(1).findElements(By.tagName("table"));
            List<WebElement> tr = tablelist.get(0).findElements(By.tagName("tr"));
            String semester = tr.get(0).findElement(By.id("M_AYEARSMS")).getText();
            String number = tr.get(4).findElement(By.id("M_COSID")).getText();
            String teacher = tr.get(5).findElement(By.id("M_LECTR_TCH_CH")).getText();
            String name = tr.get(6).findElement(By.id("CH_LESSON")).getText();
            String time = tr.get(11).findElement(By.id("M_SEG")).getText();
            String room = tr.get(11).findElement(By.id("M_CLSSRM_ID")).getText();
            String subfield = tr.get(12).findElement(By.id("M_CHILD_NAME")).getText();
            String category = tr.get(10).findElement(By.id("M_MUST")).getText();
            List<WebElement> tr2 = tablelist.get(2).findElements(By.tagName("tr"));
            String eva = tr2.get(13).findElement(By.id("M_CH_TYPE")).getText();
            String target = tr2.get(1).findElement(By.id("M_CH_TARGET")).getText();
            String syllabus = tr2.get(11).findElement(By.id("M_CH_TEACHSCH")).getText();
            System.out.println("///course number: " + number);
            System.out.println("///subfield: " + subfield);
            gc.setSemester(semester);
            gc.setClassNum(number);
            gc.setName(name);
            gc.setTeacher(teacher);
            gc.setTime(time);
            gc.setClassroom(room);
            gc.setSubfield(subfield);
            gc.setEvaluation(eva);
            gc.setCategory(category);
            gc.setTarget(target);
            gc.setSyllabus(syllabus);
            gCourses.add(gc);
            driver.switchTo().defaultContent();
            driver.switchTo().frame("mainFrame");
            driver.findElement(By.xpath("//*[@title=\"Close\"]")).click();
            driver.switchTo().defaultContent();
            driver.switchTo().frame("mainFrame");
        }
        return gCourses;
    }

    public static List<RequiredCourseEntity> findRCourse(String takingMajor, String takingCategory, String takingGrade) throws InterruptedException{
        //Convert major code
        if(takingMajor.equals("商船")) {
            takingMajor = "0701";
        }
        else if (takingMajor.equals("航管")) {
            takingMajor = "0703";
        }
        else if (takingMajor.equals("運輸")) {
            takingMajor = "0608";
        }
        else if (takingMajor.equals("輪機")) {
            takingMajor = "060F";
        }
        else if (takingMajor.equals("食科")) {
            takingMajor = "0302";
        }
        else if (takingMajor.equals("養殖")) {
            takingMajor = "0303";
        }
        else if (takingMajor.equals("生科")) {
            takingMajor = "030B";
        }
        else if (takingMajor.equals("環漁")) {
            takingMajor = "0301";
        }
        else if (takingMajor.equals("機械")) {
            takingMajor = "0702";
        }
        else if (takingMajor.equals("系工")) {
            takingMajor = "0501";
        }
        else if (takingMajor.equals("河工")) {
            takingMajor = "0502";
        }
        else if (takingMajor.equals("電機")) {
            takingMajor = "0503";
        }
        else if (takingMajor.equals("資工")) {
            takingMajor = "0507";
        }
        else if (takingMajor.equals("通訊")) {
            takingMajor = "060C";
        }
        else if (takingMajor.equals("光電")) {
            takingMajor = "0809";
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

        driver.findElement(By.id("Q_FACULTY_CODE")).findElement(By.xpath("//option[@value='"+ takingMajor +"']")).click();
        System.out.println(takingMajor);
        WebElement selectedOption2 = driver.findElement(By.id("Q_FACULTY_CODE")).findElement(By.xpath("//option[@value='"+ takingMajor +"']"));
        String selectedOptionText2 = selectedOption2.getText();
        System.out.println("->"+ selectedOptionText2);
        
        driver.findElement(By.id("Q_GRADE")).findElement(By.xpath("//option[. ='"+ takingGrade +"']")).click();
        System.out.println(takingGrade);
        WebElement selectedOption3 = driver.findElement(By.id("Q_GRADE")).findElement(By.xpath("//option[. ='"+ takingGrade +"']"));
        String selectedOptionText3 = selectedOption3.getText();
        System.out.println("->"+ selectedOptionText3);        

        driver.findElement(By.xpath("//*[@id=\"QUERY_BTN1\"]")).click();  //開課單位查詢
        Thread.sleep(3000);
        //show_50
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = '50';", driver.findElement(By.id("PC_PageSize"))); //商船有86筆(Max)
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"PC_ShowRows\"]")).click();
        Thread.sleep(3000);
        List<WebElement> rcReault = driver.findElements(By.cssSelector("#DataGrid > tbody > tr"));
        System.out.println(takingCategory);
        for(int i = 1;i < rcReault.size();i++){
            System.out.println(i);
            WebElement itemCourse = driver.findElements(By.cssSelector("#DataGrid > tbody > tr")).get(i);
            List<WebElement> item = itemCourse.findElements(By.tagName("td"));
            Thread.sleep(3000);
            System.out.println(item.get(10).getText());
            //System.out.println(takingCategory);
            if(item.get(10).getText().equals(takingCategory))
            {
                RequiredCourseEntityMajorCSE rcDetail = new RequiredCourseEntityMajorCSE();
                System.out.println("課名 : " + item.get(3).getText());
                System.out.println("年班級 : " + item.get(5).getText());

                RequiredCourseEntity courseEntity = new RequiredCourseEntity();
                courseEntity.setCNumber(item.get(2).getText());
                courseEntity.setCName(item.get(3).getText());
                courseEntity.setCGrade(item.get(5).getText());
                courseEntity.setCTeacher(item.get(6).getText());
                courseEntity.setCCredit(item.get(9).getText());
                courseEntity.setCCategory(item.get(10).getText());

                Integer index= 1;
                index += i;
                if(i<9){
                    driver.findElement(By.cssSelector("a[href=\"javascript:__doPostBack('DataGrid$ctl0"+index+"$COSID','')\"]")).click();
                }
                else{
                    driver.findElement(By.cssSelector("a[href=\"javascript:__doPostBack('DataGrid$ctl"+index+"$COSID','')\"]")).click();
                }
                Thread.sleep(3000);
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                //switch iframe
                WebElement iframe = driver.findElement(By.tagName("iframe"));
                driver.switchTo().frame(iframe);
                driver.switchTo().frame("mainFrame");
                Thread.sleep(1500);

                WebElement time = driver.findElement(By.id("M_SEG"));
                System.out.println("上課時間 : " + time.getText());
                courseEntity.setCTime(time.getText());

                WebElement location = driver.findElement(By.id("M_CLSSRM_ID"));
                System.out.println("上課地點 : " + location.getText());
                courseEntity.setCLocation(location.getText());

                WebElement people = driver.findElement(By.id("M_CHOICE_QTY"));
                System.out.println("選課人數 : " + people.getText());
                courseEntity.setCPeople(people.getText());

                WebElement objective = driver.findElement(By.id("M_CH_TARGET"));
                System.out.println("教學目標 : " + objective.getText());
                courseEntity.setCObjective(objective.getText());

                WebElement pre_course = driver.findElement(By.id("M_CH_PREOBJ"));
                System.out.println("先修科目 : " + pre_course.getText());
                courseEntity.setCPrecourse(pre_course.getText());

                WebElement outline = driver.findElement(By.id("M_CH_OBJECT"));
                System.out.println("教材內容 : " + outline.getText());
                courseEntity.setCOutline(outline.getText());

                WebElement teaching_method = driver.findElement(By.id("M_CH_TEACH"));
                System.out.println("教學方式 : " + teaching_method.getText());
                courseEntity.setCTmethod(teaching_method.getText());

                WebElement reference = driver.findElement(By.id("M_CH_REF"));
                System.out.println("參考書目 : " + reference.getText());
                courseEntity.setCReference(reference.getText());

                WebElement syllabus = driver.findElement(By.id("M_CH_TEACHSCH"));
                System.out.println("教學進度 : " + syllabus.getText());
                courseEntity.setCSyllabus(syllabus.getText());

                WebElement evaluation = driver.findElement(By.id("M_CH_TYPE"));
                System.out.println("評量方式 : " + evaluation.getText());
                courseEntity.setCEvaluation(evaluation.getText());

                RCourseList.add(courseEntity);

                driver.switchTo().defaultContent();
                driver.switchTo().frame("mainFrame");
                driver.findElement(By.xpath("//*[@title=\"Close\"]")).click();
                //driver.switchTo().defaultContent();
                //driver.switchTo().frame("mainFrame");
            }
        }
        return RCourseList;
    }
    public static List<CourseEntity> getCourses() throws InterruptedException{
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

        //driver.findElement(By.id("Q_SMS")).findElement(By.xpath("//option[@value='全部']")).click();
        Select dropdown = new Select(driver.findElement(By.id("Q_SMS")));
        dropdown.selectByVisibleText("全部");
        Thread.sleep(500);
        driver.findElement(By.id("Q_DEGREE_CODE")).findElement(By.xpath("//option[@value='0']")).click();
        Thread.sleep(500);
        driver.findElement(By.id("Q_FACULTY_CODE")).findElement(By.xpath("//option[@value='0507']")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//*[@id=\"QUERY_BTN1\"]")).click();  //開課單位查詢
        Thread.sleep(3000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = '200';", driver.findElement(By.id("PC_PageSize")));
        Thread.sleep(1500);
        driver.findElement(By.xpath("//*[@id=\"PC_ShowRows\"]")).click();
        Thread.sleep(5000);

        List<CourseEntity> courseList = new ArrayList<CourseEntity>();

        List<WebElement> trList = driver.findElements(By.cssSelector("#DataGrid > tbody > tr"));
        for(int i = 1; i < trList.size(); i++){
            Thread.sleep(1000);
            if(i<9) driver.findElement(By.cssSelector("a[href=\"javascript:__doPostBack('DataGrid$ctl0"+(i+1)+"$COSID','')\"]")).click();
            else driver.findElement(By.cssSelector("a[href=\"javascript:__doPostBack('DataGrid$ctl"+(i+1)+"$COSID','')\"]")).click();
            //switch iframe
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            WebElement iframe = driver.findElement(By.tagName("iframe"));
            driver.switchTo().frame(iframe);
            driver.switchTo().frame("mainFrame");
            Thread.sleep(1500);
            List<WebElement> trlist = driver.findElements(By.cssSelector("#QTable2 > tbody > tr"));
            List<WebElement> tablelist = trlist.get(1).findElements(By.tagName("td")).get(1).findElements(By.tagName("table"));
            List<WebElement> tr = tablelist.get(0).findElements(By.tagName("tr"));
            List<WebElement> tr2 = tablelist.get(2).findElements(By.tagName("tr"));
            String semester = tr.get(0).findElement(By.id("M_AYEARSMS")).getText();
            String number = tr.get(4).findElement(By.id("M_COSID")).getText();
            String dept = tr.get(4).findElement(By.id("M_FACULTY_NAME")).getText();
            String teacher = tr.get(5).findElement(By.id("M_LECTR_TCH_CH")).getText();
            String name = tr.get(6).findElement(By.id("CH_LESSON")).getText();
            System.out.println("///course name: " + name);
            String grade = tr.get(8).findElement(By.id("M_GRADE")).getText();
            String credit = tr.get(8).findElement(By.id("M_CRD")).getText();
            String people = tr.get(9).findElement(By.id("M_MAX_ST")).getText();
            String category = tr.get(10).findElement(By.id("M_MUST")).getText();
            String time = tr.get(11).findElement(By.id("M_SEG")).getText();
            String room = tr.get(11).findElement(By.id("M_CLSSRM_ID")).getText();
            System.out.println("///course number: " + number);
            String objective = tr2.get(1).findElement(By.id("M_CH_TARGET")).getText();
            String precourse = tr2.get(3).findElement(By.id("M_CH_PREOBJ")).getText();
            String outline = tr2.get(5).findElement(By.id("M_CH_OBJECT")).getText();
            String method = tr2.get(7).findElement(By.id("M_CH_TEACH")).getText();
            String reference = tr2.get(9).findElement(By.id("M_CH_REF")).getText();
            String syllabus = tr2.get(11).findElement(By.id("M_CH_TEACHSCH")).getText();
            String evaluation = tr2.get(13).findElement(By.id("M_CH_TYPE")).getText();
            CourseEntity ce = new CourseEntity(semester, name, category, number, time, room, teacher, grade, people, dept, credit, objective, precourse, outline, method, reference, syllabus, evaluation);
            courseList.add(ce);
            driver.switchTo().defaultContent();
            driver.switchTo().frame("mainFrame");
            driver.findElement(By.xpath("//*[@title=\"Close\"]")).click();
            driver.switchTo().defaultContent();
            driver.switchTo().frame("mainFrame");
        }
        return courseList;
    }

    public static List<PECourseEntity> getPE() throws InterruptedException{
        System.out.println("getPE class");
        driver.switchTo().frame("menuFrame");
        driver.findElement(By.id("Menu_TreeViewt1")).click(); //教務系統
        System.out.println("教務系統");
        Thread.sleep(2000);
        driver.findElement(By.linkText("選課系統")).click(); //選課系統
        System.out.println("選課系統");
        Thread.sleep(2000);
        driver.findElement(By.linkText("課程課表查詢")).click(); //課程課表查詢
        System.out.println("課程課表查詢");
        driver.switchTo().defaultContent();
        driver.switchTo().frame("mainFrame");
        System.out.println("mainframe");
        driver.findElement(By.id("Q_FACULTY_CODE")).findElement(By.xpath("//option[@value='0902']")).click();
        System.out.println("體育室");
        driver.findElement(By.id("QUERY_BTN1")).click();
        System.out.println("開課單位查詢");
        //顯示150筆
        Thread.sleep(3000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = '150';", driver.findElement(By.id("PC_PageSize")));
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"PC_ShowRows\"]")).click();
        Thread.sleep(3000);
        System.out.println("顯示150筆");
        List<PECourseEntity> peCourseEntityList = new ArrayList<>();
        List<WebElement> PE = driver.findElements(By.cssSelector("#DataGrid > tbody > tr"));
        for(int i=1;i<PE.size();i++){
            PECourseEntity peCourse = new PECourseEntity();
            if(i+1<10)driver.findElement(By.cssSelector("a[href=\"javascript:__doPostBack('DataGrid$ctl0"+(i+1)+"$COSID','')\"]")).click();
            else driver.findElement(By.cssSelector("a[href=\"javascript:__doPostBack('DataGrid$ctl"+(i+1)+"$COSID','')\"]")).click();
            //switch iframe
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            WebElement iframe = driver.findElement(By.tagName("iframe"));
            driver.switchTo().frame(iframe);
            driver.switchTo().frame("mainFrame");
            List<WebElement> tr = driver.findElements(By.cssSelector("#QTable2 > tbody > tr")).get(1).findElements(By.tagName("td")).get(1).findElements(By.tagName("table")).get(0).findElements(By.tagName("tr"));
            peCourse.setClassNum(tr.get(4).findElement(By.id("M_COSID")).getText());
            peCourse.setDepartment(tr.get(4).findElement(By.id("M_FACULTY_NAME")).getText());
            String temp = tr.get(5).findElement(By.id("M_LECTR_TCH_CH")).getText();
            peCourse.setTeacher(temp.substring(0, 3));
            peCourse.setName(tr.get(6).findElement(By.id("CH_LESSON")).getText());
            peCourse.setENname(tr.get(7).findElement(By.id("M_ENG_LESSON")).getText());
            peCourse.setYearClass(tr.get(8).findElement(By.id("M_GRADE")).getText());
            peCourse.setCredit(tr.get(8).findElement(By.id("M_CRD")).getText());
            peCourse.setUpper(tr.get(9).findElement(By.id("M_MAX_ST")).getText());
            peCourse.setLower(tr.get(9).findElement(By.id("M_MIN_ST")).getText());
            temp = tr.get(10).findElement(By.id("M_MUST")).getText();//選課類別
            peCourse.setCategory(temp.substring(0, temp.indexOf('(')));
            peCourse.setDuration(tr.get(10).findElement(By.id("M_COSTERM")).getText());
            temp = tr.get(11).findElement(By.id("M_SEG")).getText();//上課時間
            String[] timeArray = temp.split(",");
            peCourse.setTime(timeArray);
            temp = tr.get(11).findElement(By.id("M_CLSSRM_ID")).getText();//上課地點
            if(temp.indexOf(',')!=-1)temp = temp.substring(0, temp.indexOf(','));
            peCourse.setClassroom(temp);
            peCourse.setOnline(tr.get(11).findElement(By.id("M_IS_LONGDIST_CURRI")).getText());
            peCourse.setMainField(tr.get(12).findElement(By.id("M_MAIN_NAME")).getText());
            peCourse.setNote(tr.get(14).findElement(By.id("M_DESCRIPTION")).getText());
            List<WebElement> tr2 = driver.findElements(By.cssSelector("#QTable2 > tbody > tr")).get(1).findElements(By.tagName("td")).get(1).findElements(By.tagName("table")).get(2).findElements(By.tagName("tr"));
            peCourse.setTarget(tr2.get(1).findElement(By.id("M_CH_TARGET")).getText());
            peCourse.setTargetE(tr2.get(2).findElement(By.id("M_ENG_TARGET")).getText());
            peCourse.setPrerequisites(tr2.get(3).findElement(By.id("M_CH_PREOBJ")).getText());
            peCourse.setPrerequisitesE(tr2.get(4).findElement(By.id("M_ENG_PREOBJ")).getText());
            peCourse.setOutline(tr2.get(5).findElement(By.id("M_CH_OBJECT")).getText());
            peCourse.setOutlineE(tr2.get(6).findElement(By.id("M_ENG_OBJECT")).getText());
            peCourse.setTeachingMethod(tr2.get(7).findElement(By.id("M_CH_TEACH")).getText());
            peCourse.setTeachingMethodE(tr2.get(8).findElement(By.id("M_ENG_TEACH")).getText());
            peCourse.setReference(tr2.get(9).findElement(By.id("M_CH_REF")).getText());
            peCourse.setReferenceE(tr2.get(10).findElement(By.id("M_ENG_REF")).getText());
            peCourse.setSyllabus(tr2.get(11).findElement(By.id("M_CH_TEACHSCH")).getText());
            peCourse.setSyllabusE(tr2.get(12).findElement(By.id("M_ENG_TEACHSCH")).getText());
            peCourse.setEvaluation(tr2.get(13).findElement(By.id("M_CH_TYPE")).getText());
            peCourse.setEvaluationE(tr2.get(14).findElement(By.id("M_ENG_TYPE")).getText());
            peCourseEntityList.add(peCourse);
            //back to main frame
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            driver.switchTo().defaultContent();
            driver.switchTo().frame("mainFrame");
            driver.findElement(By.xpath("//*[@title=\"Close\"]")).click();
            driver.switchTo().defaultContent();
            driver.switchTo().frame("mainFrame");
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            System.out.println(peCourse);
        }
        System.out.println(peCourseEntityList.size());
        return peCourseEntityList;
    }
    public static List<ForeignLanguageEntity> getForeignLanguageClass() throws InterruptedException{
        System.out.println("getForeignLanguage class");
        driver.switchTo().frame("menuFrame");
        driver.findElement(By.id("Menu_TreeViewt1")).click(); //教務系統
        System.out.println("教務系統");
        Thread.sleep(2000);
        driver.findElement(By.linkText("選課系統")).click(); //選課系統
        System.out.println("選課系統");
        Thread.sleep(2000);
        driver.findElement(By.linkText("課程課表查詢")).click(); //課程課表查詢
        System.out.println("課程課表查詢");
        driver.switchTo().defaultContent();
        driver.switchTo().frame("mainFrame");
        System.out.println("mainframe");
        driver.findElement(By.id("Q_FACULTY_CODE")).findElement(By.xpath("//option[@value='090K']")).click();
        System.out.println("共同教育中心語文教育組");
        driver.findElement(By.id("QUERY_BTN1")).click();
        System.out.println("開課單位查詢");
        //顯示100筆
        Thread.sleep(3000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = '100';", driver.findElement(By.id("PC_PageSize")));
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"PC_ShowRows\"]")).click();
        Thread.sleep(3000);
        System.out.println("顯示100筆");
        List<ForeignLanguageEntity> fCourseEntityList = new ArrayList<>();
        List<WebElement> PE = driver.findElements(By.cssSelector("#DataGrid > tbody > tr"));
        for(int i=1;i<PE.size();i++){
            ForeignLanguageEntity fCourse = new ForeignLanguageEntity();
            if(i+1<10)driver.findElement(By.cssSelector("a[href=\"javascript:__doPostBack('DataGrid$ctl0"+(i+1)+"$COSID','')\"]")).click();
            else driver.findElement(By.cssSelector("a[href=\"javascript:__doPostBack('DataGrid$ctl"+(i+1)+"$COSID','')\"]")).click();
            //switch iframe
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            WebElement iframe = driver.findElement(By.tagName("iframe"));
            driver.switchTo().frame(iframe);
            driver.switchTo().frame("mainFrame");
            List<WebElement> tr = driver.findElements(By.cssSelector("#QTable2 > tbody > tr")).get(1).findElements(By.tagName("td")).get(1).findElements(By.tagName("table")).get(0).findElements(By.tagName("tr"));
            fCourse.setClassNum(tr.get(4).findElement(By.id("M_COSID")).getText());
            fCourse.setDepartment(tr.get(4).findElement(By.id("M_FACULTY_NAME")).getText());
            String temp = tr.get(5).findElement(By.id("M_LECTR_TCH_CH")).getText();
            fCourse.setTeacher(temp.substring(0, 3));
            fCourse.setName(tr.get(6).findElement(By.id("CH_LESSON")).getText());
            fCourse.setENname(tr.get(7).findElement(By.id("M_ENG_LESSON")).getText());
            fCourse.setYearClass(tr.get(8).findElement(By.id("M_GRADE")).getText());
            fCourse.setCredit(tr.get(8).findElement(By.id("M_CRD")).getText());
            fCourse.setUpper(tr.get(9).findElement(By.id("M_MAX_ST")).getText());
            fCourse.setLower(tr.get(9).findElement(By.id("M_MIN_ST")).getText());
            temp = tr.get(10).findElement(By.id("M_MUST")).getText();//選課類別
            fCourse.setCategory(temp.substring(0, temp.indexOf('(')));
            fCourse.setDuration(tr.get(10).findElement(By.id("M_COSTERM")).getText());
            temp = tr.get(11).findElement(By.id("M_SEG")).getText();//上課時間
            String[] timeArray = temp.split(",");
            fCourse.setTime(timeArray);
            temp = tr.get(11).findElement(By.id("M_CLSSRM_ID")).getText();//上課地點
            if(temp.indexOf(',')!=-1)temp = temp.substring(0, temp.indexOf(','));
            fCourse.setClassroom(temp);
            fCourse.setOnline(tr.get(11).findElement(By.id("M_IS_LONGDIST_CURRI")).getText());
            fCourse.setMainField(tr.get(12).findElement(By.id("M_MAIN_NAME")).getText());
            fCourse.setNote(tr.get(14).findElement(By.id("M_DESCRIPTION")).getText());
            List<WebElement> tr2 = driver.findElements(By.cssSelector("#QTable2 > tbody > tr")).get(1).findElements(By.tagName("td")).get(1).findElements(By.tagName("table")).get(2).findElements(By.tagName("tr"));
            fCourse.setTarget(tr2.get(1).findElement(By.id("M_CH_TARGET")).getText());
            fCourse.setTargetE(tr2.get(2).findElement(By.id("M_ENG_TARGET")).getText());
            fCourse.setPrerequisites(tr2.get(3).findElement(By.id("M_CH_PREOBJ")).getText());
            fCourse.setPrerequisitesE(tr2.get(4).findElement(By.id("M_ENG_PREOBJ")).getText());
            fCourse.setOutline(tr2.get(5).findElement(By.id("M_CH_OBJECT")).getText());
            fCourse.setOutlineE(tr2.get(6).findElement(By.id("M_ENG_OBJECT")).getText());
            fCourse.setTeachingMethod(tr2.get(7).findElement(By.id("M_CH_TEACH")).getText());
            fCourse.setTeachingMethodE(tr2.get(8).findElement(By.id("M_ENG_TEACH")).getText());
            fCourse.setReference(tr2.get(9).findElement(By.id("M_CH_REF")).getText());
            fCourse.setReferenceE(tr2.get(10).findElement(By.id("M_ENG_REF")).getText());
            fCourse.setSyllabus(tr2.get(11).findElement(By.id("M_CH_TEACHSCH")).getText());
            fCourse.setSyllabusE(tr2.get(12).findElement(By.id("M_ENG_TEACHSCH")).getText());
            fCourse.setEvaluation(tr2.get(13).findElement(By.id("M_CH_TYPE")).getText());
            fCourse.setEvaluationE(tr2.get(14).findElement(By.id("M_ENG_TYPE")).getText());
            fCourseEntityList.add(fCourse);
            //back to main frame
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            driver.switchTo().defaultContent();
            driver.switchTo().frame("mainFrame");
            driver.findElement(By.xpath("//*[@title=\"Close\"]")).click();
            driver.switchTo().defaultContent();
            driver.switchTo().frame("mainFrame");
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            System.out.println(fCourse);
        }
        System.out.println(fCourseEntityList.size());
        return fCourseEntityList;
    }
    public static List<EnglishCourseEntity> getEnglishClass() throws InterruptedException{
        System.out.println("getEnglish class");
        driver.switchTo().frame("menuFrame");
        driver.findElement(By.id("Menu_TreeViewt1")).click(); //教務系統
        System.out.println("教務系統");
        Thread.sleep(2000);
        driver.findElement(By.linkText("選課系統")).click(); //選課系統
        System.out.println("選課系統");
        Thread.sleep(2000);
        driver.findElement(By.linkText("課程課表查詢")).click(); //課程課表查詢
        System.out.println("課程課表查詢");
        driver.switchTo().defaultContent();
        driver.switchTo().frame("mainFrame");
        System.out.println("mainframe");
        driver.findElement(By.id("Q_FACULTY_CODE")).findElement(By.xpath("//option[@value='090D']")).click();
        System.out.println("大學部英語課程(原外語中心)");
        driver.findElement(By.id("QUERY_BTN1")).click();
        System.out.println("開課單位查詢");
        //顯示100筆
        Thread.sleep(3000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = '100';", driver.findElement(By.id("PC_PageSize")));
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"PC_ShowRows\"]")).click();
        Thread.sleep(3000);
        System.out.println("顯示100筆");
        List<EnglishCourseEntity> eCourseEntityList = new ArrayList<>();
        List<WebElement> PE = driver.findElements(By.cssSelector("#DataGrid > tbody > tr"));
        for(int i=1;i<PE.size();i++){
            EnglishCourseEntity eCourse = new EnglishCourseEntity();
            if(i+1<10)driver.findElement(By.cssSelector("a[href=\"javascript:__doPostBack('DataGrid$ctl0"+(i+1)+"$COSID','')\"]")).click();
            else driver.findElement(By.cssSelector("a[href=\"javascript:__doPostBack('DataGrid$ctl"+(i+1)+"$COSID','')\"]")).click();
            //switch iframe
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            WebElement iframe = driver.findElement(By.tagName("iframe"));
            driver.switchTo().frame(iframe);
            driver.switchTo().frame("mainFrame");
            List<WebElement> tr = driver.findElements(By.cssSelector("#QTable2 > tbody > tr")).get(1).findElements(By.tagName("td")).get(1).findElements(By.tagName("table")).get(0).findElements(By.tagName("tr"));
            eCourse.setClassNum(tr.get(4).findElement(By.id("M_COSID")).getText());
            eCourse.setDepartment(tr.get(4).findElement(By.id("M_FACULTY_NAME")).getText());
            String temp = tr.get(5).findElement(By.id("M_LECTR_TCH_CH")).getText();
            eCourse.setTeacher(temp.substring(0, 3));
            eCourse.setName(tr.get(6).findElement(By.id("CH_LESSON")).getText());
            eCourse.setENname(tr.get(7).findElement(By.id("M_ENG_LESSON")).getText());
            eCourse.setYearClass(tr.get(8).findElement(By.id("M_GRADE")).getText());
            eCourse.setCredit(tr.get(8).findElement(By.id("M_CRD")).getText());
            eCourse.setUpper(tr.get(9).findElement(By.id("M_MAX_ST")).getText());
            eCourse.setLower(tr.get(9).findElement(By.id("M_MIN_ST")).getText());
            temp = tr.get(10).findElement(By.id("M_MUST")).getText();//選課類別
            eCourse.setCategory(temp.substring(0, temp.indexOf('(')));
            eCourse.setDuration(tr.get(10).findElement(By.id("M_COSTERM")).getText());
            temp = tr.get(11).findElement(By.id("M_SEG")).getText();//上課時間
            String[] timeArray = temp.split(",");
            eCourse.setTime(timeArray);
            temp = tr.get(11).findElement(By.id("M_CLSSRM_ID")).getText();//上課地點
            if(temp.indexOf(',')!=-1)temp = temp.substring(0, temp.indexOf(','));
            eCourse.setClassroom(temp);
            eCourse.setOnline(tr.get(11).findElement(By.id("M_IS_LONGDIST_CURRI")).getText());
            eCourse.setMainField(tr.get(12).findElement(By.id("M_MAIN_NAME")).getText());
            eCourse.setNote(tr.get(14).findElement(By.id("M_DESCRIPTION")).getText());
            List<WebElement> tr2 = driver.findElements(By.cssSelector("#QTable2 > tbody > tr")).get(1).findElements(By.tagName("td")).get(1).findElements(By.tagName("table")).get(2).findElements(By.tagName("tr"));
            eCourse.setTarget(tr2.get(1).findElement(By.id("M_CH_TARGET")).getText());
            eCourse.setTargetE(tr2.get(2).findElement(By.id("M_ENG_TARGET")).getText());
            eCourse.setPrerequisites(tr2.get(3).findElement(By.id("M_CH_PREOBJ")).getText());
            eCourse.setPrerequisitesE(tr2.get(4).findElement(By.id("M_ENG_PREOBJ")).getText());
            eCourse.setOutline(tr2.get(5).findElement(By.id("M_CH_OBJECT")).getText());
            eCourse.setOutlineE(tr2.get(6).findElement(By.id("M_ENG_OBJECT")).getText());
            eCourse.setTeachingMethod(tr2.get(7).findElement(By.id("M_CH_TEACH")).getText());
            eCourse.setTeachingMethodE(tr2.get(8).findElement(By.id("M_ENG_TEACH")).getText());
            eCourse.setReference(tr2.get(9).findElement(By.id("M_CH_REF")).getText());
            eCourse.setReferenceE(tr2.get(10).findElement(By.id("M_ENG_REF")).getText());
            eCourse.setSyllabus(tr2.get(11).findElement(By.id("M_CH_TEACHSCH")).getText());
            eCourse.setSyllabusE(tr2.get(12).findElement(By.id("M_ENG_TEACHSCH")).getText());
            eCourse.setEvaluation(tr2.get(13).findElement(By.id("M_CH_TYPE")).getText());
            eCourse.setEvaluationE(tr2.get(14).findElement(By.id("M_ENG_TYPE")).getText());
            eCourseEntityList.add(eCourse);
            //back to main frame
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            driver.switchTo().defaultContent();
            driver.switchTo().frame("mainFrame");
            driver.findElement(By.xpath("//*[@title=\"Close\"]")).click();
            driver.switchTo().defaultContent();
            driver.switchTo().frame("mainFrame");
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            System.out.println(eCourse);
        }
        System.out.println(eCourseEntityList.size());
        return eCourseEntityList;
    }
    public static void main(String[] args) throws Exception {


        String account = "";
        String password = "";

/*
        String account = "00957030";
        String password = "0baf254b";
        CrawlerHandle(account,password);
*/


        //getBasicData(account,password);
        CrawlerHandle(account,password);
        getForeignLanguageClass();
        //getPE();
        //getMyClass(account,password);

        //getAllGeneralClass();
        //getFinishedCredict();
        //findRCourse("必修","3");
        //detectCoureses();
    }
}

