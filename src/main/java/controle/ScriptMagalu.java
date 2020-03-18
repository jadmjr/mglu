package controle;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;

import modelo.Magalu;

public class ScriptMagalu {
	String pathChromeServer = "resources\\chromedriver\\chromedriver.exe";

	public void validarBuscaProduto(int codigoProduto, String nomeProduto) {

		System.setProperty("webdriver.chrome.driver", pathChromeServer);

		WebDriver driver = new ChromeDriver();

		ChromeOptions options = new ChromeOptions();

		acessarEcommerce(driver);
		buscarProduto(driver, nomeProduto);
		validarRetorno(driver, codigoProduto, nomeProduto);
		adcionarProdutoSacola(driver);
	}

	private void acessarEcommerce(WebDriver driver) {

		driver.get("http://www.magazineluiza.com.br");
	}

	private void buscarProduto(WebDriver driver, String nomeProduto) {
		Magalu mglu = new Magalu();
		PageFactory.initElements(driver, mglu);
		mglu.buscaProduto(nomeProduto);
	}

	private void validarRetorno(WebDriver driver, int codigoProduto, String nomeProduto) {

		String idElemento = "nm-product-" + codigoProduto;

		Magalu mglu = new Magalu();
		PageFactory.initElements(driver, mglu);
		WebElement produto = mglu.getContainerResultado().findElement(By.id(idElemento)).findElement(By.tagName("a"));

		System.out.println(produto.getAttribute("title"));
		produto.click();

	}
	
	private void adcionarProdutoSacola(WebDriver driver) {
		Magalu mglu = new Magalu();
		PageFactory.initElements(driver, mglu);
		mglu.clicarBotaoComprar();
		mglu.clicarBotaoContinuar();
	}

}
