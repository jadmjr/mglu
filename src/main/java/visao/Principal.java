package visao;

import controle.ScriptMagalu;

public class Principal {

	public static void main(String[] args) {

		int codigoProduto = 224223000;// nm-product-155556000
		String nomeProduto = "Condicionador Ogx Biotin Collagen - 385ml";

		ScriptMagalu sm = new ScriptMagalu();
		sm.validarBuscaProduto(codigoProduto, nomeProduto);
	}

}

