import org.openqa.selenium.WebElement;

import javax.xml.crypto.Data;
import java.util.Scanner;


public class DataHolder {

  static Scanner scan = new Scanner(System.in);
  static String departureLocation;
  static String arrivalLocation;
  static String departureDate;
  static String returnDate;

  public void DataHolder(String departureLocation, String arrivalLocation, String departureDate, String returnDate){
    DataHolder.departureLocation = departureLocation;
    DataHolder.arrivalLocation = arrivalLocation;
    DataHolder.departureDate = departureDate;
    DataHolder.returnDate = returnDate;

  }

  public String getDepatureLocation(){
    System.out.println("Enter the Departure location: ");
    departureLocation = scan.nextLine();
    return departureLocation;
  }

  public String getArrivalLocation(){
    System.out.println("Enter the Arrival location: ");
    arrivalLocation = scan.nextLine();
    return arrivalLocation;
  }

  public String getDepatureDate(){
    System.out.println("Enter the Arrival Date (DD/MM/YYY): ");
    departureDate = scan.nextLine();
    return departureDate;
  }

  public String getReturnDate(){
    System.out.println("Enter the Arrival Date (DD/MM/YYY): ");
    returnDate = scan.nextLine();
    return returnDate;
  }


}
