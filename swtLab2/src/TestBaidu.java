
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
@RunWith(Parameterized.class)
public class TestBaidu {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  private String username;
  private String passwd;
  private String git;
  
  private static String path = "E:\\乱七八糟资料文件夹\\软件测试\\lab2\\软件测试名单.csv";
  
  public TestBaidu(String uname, String pass, String git) {
	  this.username = uname;
	  this.passwd = pass;
	  this.git = git;
  }
  @Parameters
  public static Collection<String[]>importCsv() throws IOException{
	  Collection<String[]> list = new ArrayList<>();
	  BufferedReader br = new BufferedReader(new FileReader(path));
	  String temp = br.readLine();
	  temp = br.readLine();
	  while(temp != null) {
		  String [] split = new String[3];
		  String[] result = temp.split(",");
		  
		  split[0] = result[1];
		  split[1] = result[1].substring(4);
		  split[2] = result.length == 4 ? result[3] : "";
		  
		  list.add(split);
		  temp = br.readLine();
	  }
	  return list;
  }
  @Before
  public void setUp() throws Exception {
	  String driverPath = System.getProperty("user.dir") + "/src/resources/driver/geckodriver.exe";
	  System.setProperty("webdriver.gecko.driver", driverPath);
	  System.setProperty("webdriver.firefox.bin", "C:\\Program Files\\Mozilla Firefox\\firefox.exe");

	  driver = new FirefoxDriver();
	  baseUrl = "http://121.193.130.195:8800";
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testBaidu() throws Exception {
    driver.get(baseUrl + "/");
    //WebElement we = driver.findElement(By.id("id"));
    //we.click();
//    driver.findElement(By.id("kw")).click();
//    driver.findElement(By.id("kw")).clear();
    driver.findElement(By.name("id")).sendKeys(username);
    
    driver.findElement(By.name("password")).sendKeys(passwd);
    driver.findElement(By.id("btn_login")).click();
    assertEquals(git, driver.findElement(By.id("student-git")).getText());
    driver.findElement(By.id("btn_logout")).click();
    driver.findElement(By.id("btn_return")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}

