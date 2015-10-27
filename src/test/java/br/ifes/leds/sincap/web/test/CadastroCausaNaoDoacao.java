package br.ifes.leds.sincap.web.test;

import java.util.concurrent.TimeUnit;

import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.CausaNaoDoacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.util.dataFactory.CausaNaoDoacaoData;
import org.fluttercode.datafactory.impl.DataFactory;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;

public class CadastroCausaNaoDoacao extends AbstractionTest{
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  private CausaNaoDoacao causaNaoDoacao;

  @Autowired
  private CausaNaoDoacaoData causaNaoDoacaoData;

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8080";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    DataFactory df = new DataFactory();
    df.randomize((int) System.currentTimeMillis());

    causaNaoDoacao = causaNaoDoacaoData.criaCausaNaoDoacao(df);
  }

  @Test
  public void testCadastroCausaNaoDoacao() throws Exception {
    driver.get(baseUrl + "/sincap/");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("abc123");
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("333.333.333-33");
    driver.findElement(By.id("botaoLogin")).click();
    driver.findElement(By.cssSelector("i.icon-cog")).click();
    driver.findElement(By.cssSelector("#dashboard-menu > li > a > span")).click();
    driver.findElement(By.xpath("//button[@type='button']")).click();
    driver.findElement(By.id("nome")).clear();
    driver.findElement(By.id("nome")).sendKeys(causaNaoDoacao.getNome());
    new Select(driver.findElement(By.id("tipoNaoDoacao"))).selectByVisibleText(causaNaoDoacao.getTipoNaoDoacao().name());
    driver.findElement(By.cssSelector("button.btn-flat.default")).click();
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
