package modelo;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import controle.Selenium;

public class Magalu {
	private Map<String, String> data;
	private WebDriver driver;
	private int timeout = 15;

	private final String pageUrl = "";

	@FindBy(id = "inpHeaderSearch")
	@CacheLookup
	private WebElement campoDeBusca;

	@FindBy(id = "btnHeaderSearch")
	@CacheLookup
	private WebElement botaoSeguir;

	@FindBy(className = "neemu-products-container")
	@CacheLookup
	private WebElement containerResultado;

	@FindBy(className = "js-main-add-cart-button")
	@CacheLookup
	private WebElement botaoComprar;

	@FindBy(className = "BasketContinue-button")
	@CacheLookup
	private WebElement botaoContinuar;

	public Magalu() {
	}

	public WebElement getContainerResultado() {
		return containerResultado;
	}

	public Magalu clicarBotaoComprar() {
		botaoComprar.click();
		return this;
	}

	public Magalu clicarBotaoContinuar() {
		botaoContinuar.click();
		return this;
	}

	public Magalu buscaProduto(String nomeProduto) {
		campoDeBusca.sendKeys(nomeProduto);
		botaoSeguir.click();
		return this;
	}

	public Magalu(WebDriver driver) {
		this();
		this.driver = driver;
	}

	public Magalu(WebDriver driver, Map<String, String> data) {
		this(driver);
		this.data = data;
	}

	public Magalu(WebDriver driver, Map<String, String> data, int timeout) {
		this(driver, data);
		this.timeout = timeout;
	}

	public Magalu verifyPageUrl() {
		(new WebDriverWait(driver, timeout)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getCurrentUrl().contains(pageUrl);
			}
		});
		return this;
	}

}
