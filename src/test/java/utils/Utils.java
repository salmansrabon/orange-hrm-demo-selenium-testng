package utils;

import com.github.javafaker.Faker;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Utils {
    public static void doScroll(WebDriver driver){
        JavascriptExecutor js= (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }
    public static int generateRandomNumber(int min, int max){
        int number= (int) Math.floor(Math.random()*(max-min)+min);
        return number;
    }
    private String firstname;
    private String lastname;
    private String username;

    public void geneateRandomData(){
        Faker faker=new Faker();
        setFirstname(faker.name().firstName());
        setLastname(faker.name().lastName());
        setUsername(faker.name().username());
    }

    public void saveJsonList(String username, String password) throws IOException, ParseException {
        String fileName="./src/test/resources/Users.json";
        JSONParser parser=new JSONParser();
        Object obj= parser.parse(new FileReader(fileName));
        JSONArray jsonArray= (JSONArray) obj;

        JSONObject userObject=new JSONObject();
        userObject.put("userName",username);
        userObject.put("password",password);

        jsonArray.add(userObject);

        FileWriter file=new FileWriter(fileName);
        file.write(jsonArray.toJSONString());
        file.flush();
        file.close();
        System.out.println("Saved data");

    }

    public static List readJSONArray(String filename) throws IOException, ParseException {
        JSONParser parser=new JSONParser();
        Object object= parser.parse(new FileReader(filename));
        JSONArray jsonArray= (JSONArray) object;
        return jsonArray;
    }
    public static void waitForElement(WebDriver driver, WebElement element, int TIME_UNIT_SECONDS){
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(TIME_UNIT_SECONDS));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void main(String[] args) throws IOException, ParseException {
        Utils utils=new Utils();
        utils.geneateRandomData();
        System.out.println(utils.getFirstname());
    }
    public void takeScreenShot(WebDriver driver) throws IOException {
        File screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String time = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss-aa").format(new Date());
        String fileWithPath = "./src/test/resources/screenshots/" + time + ".png";
        File DestFile = new File(fileWithPath);
        FileUtils.copyFile(screenshotFile, DestFile);
    }

}
