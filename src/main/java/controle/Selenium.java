package controle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Selenium {

	private String pathChromeServer = "resources\\chromedriver\\chromedriver.exe";
	public WebDriverWait dormir;
	public WebDriver navegador;
	public JavascriptExecutor JSexecutor;
	public Random randon;
	public GerarCpfCnpj geraCpf;

	public Selenium() {

	}

	public void inicializar() {

		System.setProperty("webdriver.chrome.driver", pathChromeServer);

		// Initialize browser
		WebDriver driver = new ChromeDriver();

		ChromeOptions options = new ChromeOptions();

		options.addArguments("start-maximized");

		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(ChromeOptions.CAPABILITY, options);

		/*
		 * LoggingPreferences logPrefs = new LoggingPreferences();
		 * logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
		 * cap.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
		 */

		System.setProperty("webdriver.chrome.driver", pathChromeServer);
		navegador = new ChromeDriver(cap);
		dormir = new WebDriverWait(navegador, 10);
		JSexecutor = (JavascriptExecutor) navegador;
		randon = new Random();
		geraCpf = new GerarCpfCnpj();
		// navegador.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

	}

	public void abrirURL(String url) {
		navegador.get(url);
	}

	public String gerarNomeAleatorio() {
		String letras = "ABCDEFGHIJKLMNOPQRSTUVYWXZ";

		Random random = new Random();

		String armazenaChaves = "";
		int index = -1;
		for (int i = 0; i < 9; i++) {
			index = random.nextInt(letras.length());
			armazenaChaves += letras.substring(index, index + 1);
		}
		return armazenaChaves;
	}

	public void selecionarPrimeiraOpcao(WebElement input, String texto) {
		input.clear();
		esperar(250);
		input.sendKeys(texto);
		esperar(500);
		input.sendKeys(Keys.ARROW_DOWN);
		esperar(500);
		input.sendKeys(Keys.ENTER);
	}

	public void esperar(int tempo) {
		try {
			Thread.sleep(tempo);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String pegarDataHora() {
		Date date = new Date(System.currentTimeMillis() - 3600 * 1000);
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		String dataHoras = formatter.format(date);
		return dataHoras;
	}

	public void esperarPorElemento(WebDriver navegador, WebElement elemento) {
		dormir = new WebDriverWait(navegador, 10);
		dormir.until(ExpectedConditions.visibilityOf(elemento));

	}

	public String carregarArquivo(String nomeArquivo) {
		File file = new File("resources\\imagem\\" + nomeArquivo);
		return file.getAbsolutePath();
	}

	public void salvarPaginaOFFLINE(String nomePagina) {
		String stored_report = navegador.getPageSource();

		File f = new File("resources\\pages-html\\" + nomePagina + ".html");
		FileWriter writer;
		try {
			writer = new FileWriter(f, true);
			writer.write(stored_report);
			System.out.println("Report Created is in Location : " + f.getAbsolutePath());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
