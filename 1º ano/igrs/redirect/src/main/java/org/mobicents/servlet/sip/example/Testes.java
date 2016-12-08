package org.mobicents.servlet.sip.example;

public class Testes {

	public static void main(String[] args) {
		String ativar = "ativar  sala";
		String desativar = "desativar";

		System.out.println(ativar.matches("^ativar [\\w]*"));
		System.out.println(desativar.matches("^desativar"));

		System.out.println(ativar.split("\\s+")[0] + " - " + ativar.split("\\s+")[1]);

	}

}
