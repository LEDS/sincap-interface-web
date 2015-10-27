package br.ifes.leds.sincap.web.test;


import java.util.concurrent.TimeUnit;


import br.ifes.leds.sincap.controleInterno.cln.cdp.AnalistaCNCDO;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.util.dataFactory.AnalistaCNCDOData;
import org.fluttercode.datafactory.impl.DataFactory;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;


import javax.xml.crypto.Data;

public class CadastroAnalista extends AbstractionTest {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();


    private AnalistaCNCDO analista;

    @Autowired
    private AnalistaCNCDOData analistaData;

    private DataFactory df;


    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://localhost:8080";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        DataFactory df = new DataFactory();
        df.randomize((int) System.currentTimeMillis());

        analista = analistaData.criaAnalistaCNDO(df);

    }

    @Test
    public void testCadastroAnalista() throws Exception {
        driver.get(baseUrl + "/sincap/");
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("333.333.333-33");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("abc123");
        driver.findElement(By.id("botaoLogin")).click();

        driver.findElement(By.cssSelector("li.settings.hidden-phone > a")).click();
        driver.findElement(By.xpath("//ul[@id='dashboard-menu']/li[2]/a/span")).click();

        driver.findElement(By.xpath("//div[@id='btnAdicionarOrigin-analista-table']/a/i")).click();

        driver.findElement(By.id("nome")).clear();
        driver.findElement(By.id("nome")).sendKeys(analista.getNome());
        driver.findElement(By.id("senha")).clear();
        driver.findElement(By.id("senha")).sendKeys("abc123");
        driver.findElement(By.id("confirmar-senha")).clear();
        driver.findElement(By.id("confirmar-senha")).sendKeys("abc123");
        driver.findElement(By.id("cpf")).clear();
        driver.findElement(By.id("cpf")).sendKeys(analista.getCpf());
        driver.findElement(By.id("documentoSocial-documento")).clear();
        driver.findElement(By.id("documentoSocial-documento")).sendKeys(analista.getDocumentoSocial().getDocumento());
        new Select(driver.findElement(By.id("documentoSocial-tipoDocumentoComFoto"))).selectByVisibleText(analista.getDocumentoSocial().getTipoDocumentoComFoto().name());
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys(analista.getEmail());
        new Select(driver.findElement(By.name("admin"))).selectByVisibleText("Não");
        new Select(driver.findElement(By.id("endereco-estado-id"))).selectByVisibleText("Espírito Santo");
        new Select(driver.findElement(By.id("endereco-cidade-id"))).selectByVisibleText("Serra");
        new Select(driver.findElement(By.id("endereco-bairro-id"))).selectByVisibleText("Chácara Parreiral");
        driver.findElement(By.id("endereco-logradouro")).clear();
        driver.findElement(By.id("endereco-logradouro")).sendKeys(analista.getEndereco().getLogradouro());
        driver.findElement(By.id("endereco-numero")).clear();
        driver.findElement(By.id("endereco-numero")).sendKeys(analista.getEndereco().getNumero());
        driver.findElement(By.id("endereco-complemento")).clear();
        driver.findElement(By.id("endereco-complemento")).sendKeys(analista.getEndereco().getComplemento());
        driver.findElement(By.id("endereco-cep")).clear();
        driver.findElement(By.id("endereco-cep")).sendKeys(analista.getEndereco().getCep());
        driver.findElement(By.id("telefone-numero")).sendKeys(analista.getTelefone().getNumero());
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
