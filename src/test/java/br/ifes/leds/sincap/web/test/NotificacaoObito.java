package br.ifes.leds.sincap.web.test;


import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class NotificacaoObito {
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
  public void testNotificacaoObito() throws Exception {
    driver.get(baseUrl + "/sincap/");
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("111.111.111-11");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("abc123");
    driver.findElement(By.id("botaoLogin")).click();
    driver.findElement(By.xpath("//ul[@id='dashboard-menu']/li[2]/a/span")).click();
    driver.findElement(By.id("obito-tipoObito")).click();
    new Select(driver.findElement(By.id("obito-tipoObito"))).selectByVisibleText("PCR");
    driver.findElement(By.cssSelector("option[value=\"PCR\"]")).click();
    new Select(driver.findElement(By.id("obito-paciente-documentoSocial-tipoDocumentoComFoto"))).selectByVisibleText("RG");
    driver.findElement(By.id("obito-paciente-documentoSocial-documento")).clear();
    driver.findElement(By.id("obito-paciente-documentoSocial-documento")).sendKeys("3546545333");
    driver.findElement(By.id("obito-paciente-nome")).clear();
    driver.findElement(By.id("obito-paciente-nome")).sendKeys("Notificacao de obito");
    driver.findElement(By.id("obito-paciente-dataNascimento")).clear();
    driver.findElement(By.id("obito-paciente-dataNascimento")).sendKeys("12/04/1985");
    driver.findElement(By.id("obito-paciente-dataInternacao")).clear();
    driver.findElement(By.id("obito-paciente-dataInternacao")).sendKeys("10/08/2015");
    driver.findElement(By.cssSelector("span.checked")).click();
    driver.findElement(By.id("obito-paciente-sexo:0")).click();
    driver.findElement(By.id("obito-paciente-nomeMae")).clear();
    driver.findElement(By.id("obito-paciente-nomeMae")).sendKeys("Mae do obito");
    driver.findElement(By.id("obito-paciente-numeroSUS")).clear();
    driver.findElement(By.id("obito-paciente-numeroSUS")).sendKeys("156234434345");
    driver.findElement(By.id("obito-paciente-numeroProntuario")).clear();
    driver.findElement(By.id("obito-paciente-numeroProntuario")).sendKeys("2564554643");
    driver.findElement(By.id("descricaoComentario")).clear();
    driver.findElement(By.id("descricaoComentario")).sendKeys("comentario 1");
    driver.findElement(By.id("btn-next")).click();
    driver.findElement(By.id("obito-dataObito")).clear();
    driver.findElement(By.id("obito-dataObito")).sendKeys("13/08/2015");
    driver.findElement(By.id("horarioObito")).clear();
    driver.findElement(By.id("horarioObito")).sendKeys("10:21");
    new Select(driver.findElement(By.id("obito-setor"))).selectByVisibleText("CIRURGIA GERAL");
    new Select(driver.findElement(By.id("obito-corpoEncaminhamento"))).selectByVisibleText("IML");
    driver.findElement(By.xpath("//div[@id='uniform-obito-aptoDoacao:0']/span")).click();
    driver.findElement(By.id("obito-aptoDoacao:0")).click();
    driver.findElement(By.id("obito-primeiraCausaMortis")).clear();
    driver.findElement(By.id("obito-primeiraCausaMortis")).sendKeys("causa 1");
    driver.findElement(By.id("obito-segundaCausaMortis")).clear();
    driver.findElement(By.id("obito-segundaCausaMortis")).sendKeys("causa 2");
    driver.findElement(By.id("obito-terceiraCausaMortis")).clear();
    driver.findElement(By.id("obito-terceiraCausaMortis")).sendKeys("causa 3");
    driver.findElement(By.id("obito-quartaCausaMortis")).clear();
    driver.findElement(By.id("obito-quartaCausaMortis")).sendKeys("causa 4");
    driver.findElement(By.xpath("(//button[@type='button'])[3]")).click();
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
