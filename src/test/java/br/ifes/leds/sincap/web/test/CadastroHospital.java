package br.ifes.leds.sincap.web.test;


import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class CadastroHospital {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8080";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testCadastroHospital() throws Exception {
    driver.get(baseUrl + "/sincap/");
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("555.555.555-55");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("abc123");
    driver.findElement(By.id("botaoLogin")).click();
    driver.findElement(By.cssSelector("i.icon-cog")).click();
    driver.findElement(By.xpath("//ul[@id='dashboard-menu']/li[4]/a/span")).click();
    driver.findElement(By.xpath("//div[@id='pad-wrapper']/div/div/a")).click();
    driver.findElement(By.id("nome")).clear();
    driver.findElement(By.id("nome")).sendKeys("cadastro de hospital");
    driver.findElement(By.id("sigla")).clear();
    driver.findElement(By.id("sigla")).sendKeys("cdh");
    driver.findElement(By.id("fantasia")).clear();
    driver.findElement(By.id("fantasia")).sendKeys("hospital teste");
    driver.findElement(By.id("cnes")).clear();
    driver.findElement(By.id("cnes")).sendKeys("564432");
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("hospital@teste.com");
    new Select(driver.findElement(By.id("bancoOlhos-id"))).selectByVisibleText("BANCO DE OLHOS 1");
    new Select(driver.findElement(By.id("endereco-estado-id"))).selectByVisibleText("Espírito Santo");
    new Select(driver.findElement(By.id("endereco-cidade-id"))).selectByVisibleText("Vitória");
    new Select(driver.findElement(By.id("endereco-bairro-id"))).selectByVisibleText("República");
    driver.findElement(By.id("endereco-logradrouro")).clear();
    driver.findElement(By.id("endereco-logradrouro")).sendKeys("rua");
    driver.findElement(By.id("endereco-numero")).clear();
    driver.findElement(By.id("endereco-numero")).sendKeys("45");
    driver.findElement(By.id("endereco-complemento")).clear();
    driver.findElement(By.id("endereco-complemento")).sendKeys("complemento");
    driver.findElement(By.id("endereco-cep")).clear();
    driver.findElement(By.id("endereco-cep")).sendKeys("38198188");
    driver.findElement(By.id("telefone-numero")).clear();
    driver.findElement(By.id("telefone-numero")).sendKeys("273217819");
    new Select(driver.findElement(By.id("lsetores"))).selectByVisibleText("CLINICA MEDICA");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
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
