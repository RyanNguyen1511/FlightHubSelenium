import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class FlightHubDriven {
  static WebDriver driver;
  static DataHolder dataHolder = new DataHolder();
  static Robot robot;

  static {
    try {
      robot = new Robot();
    } catch (AWTException e) {
      throw new RuntimeException(e);
    }
  }

  static WebElement departure;
  static WebElement arrival;
  static WebElement departureDate;
  static WebElement returnDate;
  static WebElement searchFlightButton;

  static String dataDepartureLocation;
  static String dataArrivalLocation;
  static String dataDepartureDate;
  static String dataReturnDate;


  public static void main(String[] args) throws InterruptedException {
    dataDepartureLocation = dataHolder.getDepatureLocation();;
    dataArrivalLocation = dataHolder.getArrivalLocation();
    dataDepartureDate = dataHolder.getDepatureDate();
    dataReturnDate = dataHolder.getReturnDate();

    initializeWebDriver();
    GetLocation();
    datePicker();

    driver.findElement(By.id("btn-search-flight")).click();

  }

  public static void initializeWebDriver() throws InterruptedException {
    // initialize web driver with Chrome
    System.getProperty("webdriver.chrome.driver", "S:\\PERSONAL\\STUDY\\chromedriver.exe ");
    driver = new ChromeDriver();
    driver.navigate().to("https://www.flighthub.com/");
    driver.manage().deleteAllCookies();
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    driver.manage().window().maximize();
    Thread.sleep(3000);
  }

  public static void GetLocation() throws InterruptedException {

    // Locate the Webelement
    departure = driver.findElement(By.xpath("//input[@name ='seg0_from']"));
    arrival = driver.findElement(By.xpath("//input[@name ='seg0_to']"));
    // Input data into search bar
    departure.clear();
    departure.sendKeys(dataDepartureLocation);
    Thread.sleep(2000);
    departure.sendKeys(Keys.TAB);

    arrival.clear();
    arrival.sendKeys(dataArrivalLocation);
    Thread.sleep(2000);
    arrival.sendKeys(Keys.TAB);


  }

  public static void datePicker() throws InterruptedException {
    // create a new HashMap object
    HashMap<String, String> monthMap = new HashMap<String, String>();
    // add the month names to the HashMap with their corresponding number
    monthMap.put("01", "January");
    monthMap.put("02", "February");
    monthMap.put("03", "March");
    monthMap.put("04", "April");
    monthMap.put("05", "May");
    monthMap.put("06", "June");
    monthMap.put("07", "July");
    monthMap.put("08", "August");
    monthMap.put("09", "September");
    monthMap.put("10", "October");
    monthMap.put("11", "November");
    monthMap.put("12", "December");

    // Departure Date
    departureDate= driver.findElement(By.xpath("//input[@placeholder ='Departure Date']"));
    departureDate.click();
    Thread.sleep(2000);

    WebElement getMonth = driver.findElement(By.xpath("//div[@class = 'ui-datepicker-inline ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all']//span[@class = 'ui-datepicker-month']"));

    while (!monthMap.get(dataDepartureDate.substring(3, 5)).equalsIgnoreCase(getMonth.getText())) {
      driver.findElement(By.xpath("//div[@class = 'ui-datepicker-inline ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all']//a[@data-handler = 'next']")).click();
      Thread.sleep(1000);
      try {
        getMonth = driver.findElement(By.xpath("//div[@class = 'ui-datepicker-inline ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all']//span[@class = 'ui-datepicker-month']"));

      } catch (org.openqa.selenium.StaleElementReferenceException ex) {
        getMonth = driver.findElement(By.xpath("//div[@class = 'ui-datepicker-inline ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all']//span[@class = 'ui-datepicker-month']"));
      }

    }

    int dayDepart = Integer.parseInt(dataDepartureDate.substring(0, 2));
    String XpathAddress = "//div[@class = 'ui-datepicker-inline ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all']//a[contains(text(),'" +dayDepart+ "')]";
    driver.findElement(By.xpath(XpathAddress)).click();


    // Return date
    returnDate = driver.findElement(By.xpath("//input[@name ='seg1_date']"));
    returnDate.click();
    Thread.sleep(4000);

    getMonth = driver.findElement(By.xpath("//div[@class = 'ui-datepicker-inline ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all']//span[@class = 'ui-datepicker-month']"));

    while (!monthMap.get(dataReturnDate.substring(3, 5)).equalsIgnoreCase(getMonth.getText())) {
      driver.findElement(By.xpath("//div[@class = 'ui-datepicker-inline ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all']//a[@data-handler = 'next']")).click();
      Thread.sleep(1000);
      try {
        getMonth = driver.findElement(By.xpath("//div[@class = 'ui-datepicker-inline ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all']//span[@class = 'ui-datepicker-month']"));

      } catch (org.openqa.selenium.StaleElementReferenceException ex) {
        System.out.println(" Catch error !!!");
        getMonth = driver.findElement(By.xpath("//div[@class = 'ui-datepicker-inline ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all']//span[@class = 'ui-datepicker-month']"));
      }

    }

    int dayReturn = Integer.parseInt(dataReturnDate.substring(0, 2));
    XpathAddress = "//div[@class = 'ui-datepicker-inline ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all']//a[contains(text(),'" +dayReturn+ "')]";
    driver.findElement(By.xpath(XpathAddress)).click();

  }

}
