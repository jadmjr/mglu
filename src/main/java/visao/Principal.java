package visao;

import controle.ScriptMagalu;

public class Principal {

	public static void main(String[] args) {

		ScriptMagalu sm = new ScriptMagalu();

		// VALIDA SE PRODUTO APARECE NA PRIMEIRA PAGINA QUANDO INFORMADO O NOME COMPLETO
		// DADOS VALIDOS
		int codigoProduto = 224223000;
		String nomeProduto = "Condicionador Ogx Biotin Collagen - 385ml";

		sm.validarBuscaProduto(codigoProduto, nomeProduto);

		// DADOS INVALIDOS
		codigoProduto = 924223000;// ESSE CODIGO NAO PERTENCE AO PRODUTO
		nomeProduto = "Condicionador Ogx Biotin Collagen - 385ml";
		sm.validarBuscaProduto(codigoProduto, nomeProduto);

	}

}
