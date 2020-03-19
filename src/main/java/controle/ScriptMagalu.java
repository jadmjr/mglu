package controle;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import modelo.Magalu;

public class ScriptMagalu {
	String pathChromeServer = "resources\\chromedriver\\chromedriver.exe";
	String pathFirefox = "resources\\firefox\\geckodriver.exe";
	boolean firefox = false;

	String url = "http://www.magazineluiza.com.br";
	// DRIVER
	WebDriver driver;
	// REPORT
	public ExtentReports extent;
	public ExtentTest logger;

	public void validarBuscaProduto(int codigoProduto, String nomeProduto) {

		try {
			if (firefox) {
				//System.setProperty("webdriver.firefox.marionette", pathFirefox);
				//driver = new FirefoxDriver();
				//
				System.setProperty("webdriver.gecko.driver", pathFirefox);
				WebDriver driver = new FirefoxDriver();       
			} else {
				System.setProperty("webdriver.chrome.driver", pathChromeServer);
				ChromeOptions options = new ChromeOptions();
				options.addArguments("start-maximized");
				driver = new ChromeDriver(options);
			}

			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

			// report
			String nomeRelatorio = url.substring(7, url.indexOf(".com"));
			inicializaReport(nomeRelatorio);

			acessarEcommerce(driver);
			buscarProduto(driver, nomeProduto);
			validarRetorno(driver, codigoProduto, nomeProduto);
			adcionarProdutoSacola(driver);
			validarSacola(driver, codigoProduto);
		} catch (Exception e) {
			finalizaTestes(driver);
		}

		finalizaTestes(driver);
	}

	private void acessarEcommerce(WebDriver driver) {
		driver.get(url);
	}

	private void buscarProduto(WebDriver driver, String nomeProduto) {
		Magalu mglu = new Magalu();
		PageFactory.initElements(driver, mglu);
		mglu.buscaProduto(nomeProduto);
	}

	private void validarRetorno(WebDriver driver, int codigoProduto, String nomeProduto) {
		try {
			logger = extent.createTest("Validar se produto apareceu na primeira pagina");
			logger.log(Status.INFO, "Realizando buscao pelo id do produto na primeira pagina");

			String idElemento = "nm-product-" + codigoProduto;

			Magalu mglu = new Magalu();
			PageFactory.initElements(driver, mglu);
			WebElement produto = mglu.getContainerResultado().findElement(By.id(idElemento))
					.findElement(By.tagName("a"));
			driver.get(produto.getAttribute("href"));
			logger.log(Status.PASS, "Produto localizado pelo product id!");
		} catch (Exception e) {
			logger.log(Status.PASS, "Produto nao localizado pelo product id!");
		}
	}

	private void adcionarProdutoSacola(WebDriver driver) {
		Magalu mglu = new Magalu();
		PageFactory.initElements(driver, mglu);
		mglu.clicarBotaoComprar();
		mglu.clicarBotaoContinuar();
	}

	private void validarSacola(WebDriver driver, int codigoProduto) {
		logger = extent.createTest("Validar se produto foi adicionado a sacola");
		logger.log(Status.INFO, "Busco nos itens da sacola e verifico se entre eles tem o codigo do produto.");
		try {
			driver.get("https://sacola.magazineluiza.com.br/#/");
			Magalu mglu = new Magalu();
			PageFactory.initElements(driver, mglu);
			if (mglu.validarProdutoSacola(codigoProduto))
				logger.log(Status.PASS, "Produto localizado !");
			else
				logger.log(Status.FAIL, "Produto nao localizado !");
		} catch (Exception e) {
			logger.log(Status.FAIL, "Produto nao localizado !");
		}

	}

	//
	public void inicializaReport(String nome) {
		ExtentHtmlReporter reporter = new ExtentHtmlReporter("./report/" + nome + ".html");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
	}

	public void finalizaTestes(WebDriver driver) {
		try {
			driver.close();
			extent.flush();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
