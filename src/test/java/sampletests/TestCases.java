package sampletests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONObject;
import org.openqa.selenium.*;

@Listeners(ListenerTest.class)

public class TestCases implements ITestListener{

  public static void main(String[] args) throws Exception{

  }

  WebDriver driver = new ChromeDriver();
  APIClient client = new APIClient("https://esubonline.testrail.net/");
  int runId = 343;

  // Test to pass as to verify listeners .
  @Test
  public void Login() throws InterruptedException {
    driver.get("http://demo.guru99.com/V4/");
    driver.findElement(By.name("uid")).sendKeys("mngr34926");
    driver.findElement(By.name("password")).sendKeys("amUpenu");
    driver.findElement(By.name("btnLogin")).click();
  }

  // Forcefully failed this test as to verify listener.
  @Test
  public void TestToFail() {
    System.out.println("This method to test fail");
    Assert.assertTrue(false);
  }

  @Override
  public void onFinish(ITestContext Result) {
    // TODO Auto-generated method stub

  }

  @Override
  public void onStart(ITestContext Result) {
    // TODO Auto-generated method stub
    this.client.setUser("tonyd@esub.com");
    this.client.setPassword("Xxtest.test3!");
  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult Result) {
    // TODO Auto-generated method stub

  }

  @Override
  public void onTestFailure(ITestResult Result) {
    // TODO Auto-generated method stub
    System.out.println("The name of the testcase failed is :"+Result.getName());
  }
  @Override
  public void onTestSkipped(ITestResult Result) {
    // TODO Auto-generated method stub
    System.out.println("The name of the testcase Skipped is :"+Result.getName());
  }

  @Override
  public void onTestStart(ITestResult Result) {
    // TODO Auto-generated method stub
    System.out.println(Result.getName()+" test case started");

//    try {
//
//      JSONObject r = (JSONObject) client.sendPost("add_run/17", null);
//
//      if (r.get("id") != null) {
//        this.runId = (int) r.get("id");
//      }
//
//    } catch (IOException e) {
//      e.printStackTrace();
//    } catch (APIException e) {
//      e.printStackTrace();
//    }
  }

  // When Test case get Skipped, this method is called.
  @Override
  public void onTestSuccess(ITestResult Result) {
    // TODO Auto-generated method stub
    System.out.println("The name of the testcase passed is :"+Result.getName());

    Map data = new HashMap();
    data.put("status_id", new Integer(1));
    data.put("comment", "This test worked fine!");
    try {
      JSONObject r = (JSONObject) client.sendPost("add_result_for_case/" + this.runId + "/757", data);
      System.out.println(r);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (APIException e) {
      e.printStackTrace();
    }
  }
}
