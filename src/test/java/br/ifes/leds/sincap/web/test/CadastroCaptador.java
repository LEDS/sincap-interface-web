package br.ifes.leds.sincap.web.test;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class CadastroCaptador {
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
  public void testCadastroCaptador() throws Exception {
    driver.get(baseUrl + "/sincap/");
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("555.555.555-55");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("abc123");
    driver.findElement(By.id("botaoLogin")).click();
    driver.findElement(By.cssSelector("li.settings.hidden-phone > a")).click();
    driver.findElement(By.xpath("//ul[@id='dashboard-menu']/li[2]/a/span")).click();
    driver.findElement(By.xpath("//ul[@id='dashboard-menu']/li[2]/a/span")).click();
    driver.findElement(By.xpath("//ul[@id='tabs']/li[3]/a/h4")).click();
    driver.findElement(By.xpath("//div[@id='captador-table_wrapper']/div/div/div[3]/a/i")).click();
    driver.findElement(By.id("nome")).clear();
    driver.findElement(By.id("nome")).sendKeys("cadastro captador4");
    driver.findElement(By.id("senha")).clear();
    driver.findElement(By.id("senha")).sendKeys("abc123");
    driver.findElement(By.id("confirmar-senha")).clear();
    driver.findElement(By.id("confirmar-senha")).sendKeys("abc123");
    driver.findElement(By.id("cpf")).clear();
    driver.findElement(By.id("cpf")).sendKeys("182.180.980-12");
    driver.findElement(By.id("documentoSocial-documento")).clear();
    driver.findElement(By.id("documentoSocial-documento")).sendKeys("3248728342039");
    new Select(driver.findElement(By.id("documentoSocial-tipoDocumentoComFoto"))).selectByVisibleText("RG");
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("cadastro@captador.com");
    new Select(driver.findElement(By.id("endereco-estado-id"))).selectByVisibleText("Espírito Santo");
    new Select(driver.findElement(By.id("endereco-cidade-id"))).selectByVisibleText("Serra");
    new Select(driver.findElement(By.id("endereco-bairro-id"))).selectByVisibleText("Chácara Parreiral");
    driver.findElement(By.id("endereco-logradouro")).clear();
    driver.findElement(By.id("endereco-logradouro")).sendKeys("rua");
    driver.findElement(By.id("endereco-numero")).clear();
    driver.findElement(By.id("endereco-numero")).sendKeys("84");
    driver.findElement(By.id("endereco-complemento")).clear();
    driver.findElement(By.id("endereco-complemento")).sendKeys("casa");
    driver.findElement(By.id("endereco-cep")).clear();
    driver.findElement(By.id("endereco-cep")).sendKeys("32498-20");
    driver.findElement(By.id("telefone-numero")).sendKeys("(27)2717-8186");
    new Select(driver.findElement(By.id("bancoOlhos-id"))).selectByVisibleText("BANCO DE OLHOS 1");
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
