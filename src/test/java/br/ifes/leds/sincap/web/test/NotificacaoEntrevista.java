package br.ifes.leds.sincap.web.test;



import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class NotificacaoEntrevista {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8080/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testNotificacaoEntrevista() throws Exception {
    driver.findElement(By.xpath("//div[@id='uniform-entrevistaRealizada:0']/span")).click();
    driver.findElement(By.id("entrevistaRealizada:0")).click();
    driver.findElement(By.xpath("//div[@id='uniform-doacaoAutorizada:0']/span")).click();
    driver.findElement(By.id("dataEntrevista")).sendKeys("13/08/2015");
    driver.findElement(By.id("horaEntrevista")).clear();
    driver.findElement(By.id("horaEntrevista")).sendKeys("09:12");
    driver.findElement(By.id("doacaoAutorizada:0")).click();
    driver.findElement(By.id("obito-paciente-profissao")).clear();
    driver.findElement(By.id("obito-paciente-profissao")).sendKeys("estudante");
    driver.findElement(By.id("obito-paciente-religiao")).clear();
    driver.findElement(By.id("obito-paciente-religiao")).sendKeys("catolico");
    driver.findElement(By.id("obito-paciente-nacionalidade")).clear();
    driver.findElement(By.id("obito-paciente-nacionalidade")).sendKeys("brasileiro");
    new Select(driver.findElement(By.id("obito-paciente-endereco-estado"))).selectByVisibleText("Espírito Santo");
    new Select(driver.findElement(By.id("obito-paciente-endereco-cidade"))).selectByVisibleText("Vitória");
    new Select(driver.findElement(By.id("obito-paciente-endereco-bairro"))).selectByVisibleText("São Pedro");
    driver.findElement(By.id("obito-paciente-endereco-cep")).sendKeys("43274-123");
    driver.findElement(By.id("obito-paciente-endereco-logradouro")).clear();
    driver.findElement(By.id("obito-paciente-endereco-logradouro")).sendKeys("rua");
    driver.findElement(By.id("obito-paciente-endereco-numero")).clear();
    driver.findElement(By.id("obito-paciente-endereco-numero")).sendKeys("14");
    new Select(driver.findElement(By.id("obito-paciente-estadoCivil"))).selectByVisibleText("SOLTEIRO");
    driver.findElement(By.cssSelector("option[value=\"SOLTEIRO\"]")).click();
    driver.findElement(By.id("entrevista-responsavel-nome")).clear();
    driver.findElement(By.id("entrevista-responsavel-nome")).sendKeys("responsavel legal");
    driver.findElement(By.id("entrevista-responsavel-dataNascimento")).clear();
    driver.findElement(By.id("entrevista-responsavel-dataNascimento")).sendKeys("13/08/1975");
    driver.findElement(By.id("entrevista-responsavel-documentoSocial-documento")).clear();
    driver.findElement(By.id("entrevista-responsavel-documentoSocial-documento")).sendKeys("2354523423");
    new Select(driver.findElement(By.id("entrevista-responsavel-documentoSocial-tipoDocumentoComFoto"))).selectByVisibleText("RG");
    new Select(driver.findElement(By.id("entrevista-responsavel-parentesco"))).selectByVisibleText("PAIS");
    new Select(driver.findElement(By.id("entrevista-responsavel-estadoCivil"))).selectByVisibleText("CASADO");
    driver.findElement(By.id("entrevista-responsavel-telefone-numero")).sendKeys("(27)3212-4231");
    driver.findElement(By.id("entrevista-responsavel-telefone2-numero")).sendKeys("(27)3729-8520");
    driver.findElement(By.xpath("//div[@id='uniform-entrevista-responsavel-sexo:1']/span")).click();
    driver.findElement(By.id("entrevista-responsavel-sexo:1")).click();
    driver.findElement(By.id("entrevista-responsavel-profissao")).clear();
    driver.findElement(By.id("entrevista-responsavel-profissao")).sendKeys("estudante");
    driver.findElement(By.id("entrevista-responsavel-religiao")).clear();
    driver.findElement(By.id("entrevista-responsavel-religiao")).sendKeys("catolico");
    new Select(driver.findElement(By.id("entrevista-responsavel-grauEscolaridade"))).selectByVisibleText("ENSINO_SUPERIOR_COMPLETO");
    driver.findElement(By.id("entrevista-responsavel-endereco-cep")).sendKeys("64224-930");
    driver.findElement(By.id("entrevista-responsavel-nacionalidade")).clear();
    driver.findElement(By.id("entrevista-responsavel-nacionalidade")).sendKeys("brasileiro");
    new Select(driver.findElement(By.id("entrevista-responsavel-endereco-estado"))).selectByVisibleText("Espírito Santo");
    new Select(driver.findElement(By.id("entrevista-responsavel-endereco-cidade"))).selectByVisibleText("Vitória");
    new Select(driver.findElement(By.id("entrevista-responsavel-endereco-bairro"))).selectByVisibleText("Jardim da Penha");
    driver.findElement(By.id("entrevista-responsavel-endereco-logradouro")).clear();
    driver.findElement(By.id("entrevista-responsavel-endereco-logradouro")).sendKeys("rua");
    driver.findElement(By.id("entrevista-responsavel-endereco-numero")).clear();
    driver.findElement(By.id("entrevista-responsavel-endereco-numero")).sendKeys("43");
    driver.findElement(By.id("btn-next")).click();
    driver.findElement(By.id("entrevista-testemunha1-nome")).clear();
    driver.findElement(By.id("entrevista-testemunha1-nome")).sendKeys("testemunha 1");
    new Select(driver.findElement(By.id("entrevista-testemunha1-documentoSocial-tipoDocumentoComFoto"))).selectByVisibleText("RG");
    driver.findElement(By.id("entrevista-testemunha1-documentoSocial-documento")).sendKeys("232452342");
    driver.findElement(By.id("entrevista-testemunha2-nome")).clear();
    driver.findElement(By.id("entrevista-testemunha2-nome")).sendKeys("testemunha 2");
    new Select(driver.findElement(By.id("entrevista-testemunha2-documentoSocial-tipoDocumentoComFoto"))).selectByVisibleText("RG");
    driver.findElement(By.id("entrevista-testemunha2-documentoSocial-documento")).sendKeys("34523422");
    driver.findElement(By.id("btn-finish")).click();
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
