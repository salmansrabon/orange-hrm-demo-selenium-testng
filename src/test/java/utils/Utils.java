package utils;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.github.javafaker.Faker;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void geneateRandomData(){
        Faker faker=new Faker();
        setFirstname(faker.name().firstName());
        setLastname(faker.name().lastName());
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
        List data= Utils.readJSONArray("./src/test/resources/Users.json");
        JSONObject obj= (JSONObject) data.get(1);
        System.out.println(obj.get("userName"));
    }

}
