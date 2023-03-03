import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.Duration;
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
//    GetLocation();
//    datePicker();
//    clickAndWaitFlightSearchLoading();
    listCheapestFlight();

  }

  public static void initializeWebDriver() throws InterruptedException {
    // initialize web driver with Chrome
    System.getProperty("webdriver.chrome.driver", "S:\\PERSONAL\\STUDY\\chromedriver.exe ");
    driver = new ChromeDriver();
//    driver.navigate().to("https://www.flighthub.com/");
    driver.navigate().to("https://www.flighthub.com/flight/search?num_adults=1&num_children=0&num_infants=0&num_infants_lap=0&seat_class=Economy&seg0_date=2023-03-12&seg0_from=YKA&seg0_to=YVR&seg1_date=2023-04-23&seg1_from=YVR&seg1_to=YKA&type=roundtrip&search_id=14a926cae2cc9723783ee2822a124abf");
    driver.manage().deleteAllCookies();
//    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    driver.manage().window().maximize();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class = 'container-progress-bar']")));
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

    yearCheck();
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
    yearCheck();
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

  public static void yearCheck() throws InterruptedException {
    WebElement getYear = driver.findElement(By.xpath("//div[@id = 'datepicker']//span[@class = 'ui-datepicker-year']"));
    String yeardate = dataDepartureDate.substring(6, 10);

    while (!yeardate.equalsIgnoreCase(getYear.getText())) {
      driver.findElement(By.xpath("//div[@class = 'ui-datepicker-inline ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all']//a[@data-handler = 'next']")).click();
      Thread.sleep(1000);
      try {
        getYear = driver.findElement(By.xpath("//div[@class = 'ui-datepicker-inline ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all']//span[@class = 'ui-datepicker-month']"));

      } catch (org.openqa.selenium.StaleElementReferenceException ex) {
        System.out.println(" Catch error !!!");
        getYear = driver.findElement(By.xpath("//div[@class = 'ui-datepicker-inline ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all']//span[@class = 'ui-datepicker-month']"));
      }
    }
  }

  public static void clickAndWaitFlightSearchLoading(){
    driver.findElement(By.id("btn-search-flight")).click();

    // Wait till the next page full load
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class = 'container-progress-bar']")));

    // WebElement selectbutton =
    driver.findElement(By.xpath("//a[@class = 'tab-btn cheapest']")).click();
  }

  public static void listCheapestFlight(){
    WebElement flightpackages = driver.findElement(By.xpath("//ul[@class = 'exp-container-flight-package']"));
    List<WebElement> flight = flightpackages.findElements(By.cssSelector("li"));

    WebElement totalprice;
    WebElement DepartureTime;
    WebElement ReturnArrivalTime;
    WebElement DepartureDate;
    WebElement ReturnArrivalDate;
    WebElement DeparturePlace;
    WebElement ReturnPlace;
    WebElement linkdetail;
    for (WebElement list: flight){
      //LOCATE variable
      totalprice = list.findElement(By.xpath("//span[@class = 'total-price']"));

      DepartureTime = list.findElement(By.xpath("//li[@class = 'flight-time'][1]"));
      ReturnArrivalTime = list.findElement(By.xpath("//div[@class = 'package-details-box flightDetails ']//div[@class = 'city-pair '][last()]//div[contains(@class, 'faredetails display-table')][last()]//li[@class = 'flight-time'][last()]"));

      DepartureDate = list.findElement(By.xpath("//li[@class = 'flight-date '][1]"));
      ReturnArrivalDate = list.findElement(By.xpath("//div[@class = 'package-details-box flightDetails ']//div[@class = 'city-pair '][last()]//div[contains(@class, 'faredetails display-table')][last()]//li[@class = 'flight-date'][last()]"));

      DeparturePlace = list.findElement(By.xpath("//span[@class = 'hide-on-mobile'][1]"));
      ReturnPlace = list.findElement(By.xpath("//div[@class = 'package-details-box flightDetails ']//div[@class = 'city-pair '][last()]//div[contains(@class, 'faredetails display-table')][last()]//ul[last()]//span[@class = 'hide-on-mobile']"));

      linkdetail = list.findElement(By.xpath("//a[@class = 'package-select']"));

      System.out.println("------------------------------------- ");
      System.out.println("DEPARTURE: ");
      System.out.println("Departure Location: " + DeparturePlace.getText());
      System.out.println("Departure Day: " + DepartureDate.getText());
      System.out.println("Departure Time: " + DepartureTime.getText());
      System.out.println("RETURN: ");
      System.out.println("Return Location: " + ReturnPlace.getText());
      System.out.println("Return Day: " + ReturnArrivalDate.getText());
      System.out.println("Return Time: " + ReturnArrivalTime.getText());
      System.out.println("OVERVIEW: ");
      System.out.println("Total Price: " + totalprice.getText());
      System.out.println("Link detail: " + linkdetail.getAttribute("href"));
//      System.out.println("Text: " + list.getText());

    }
  }

}
