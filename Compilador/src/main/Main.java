package main;
import analisaLex.Lexico;
import analisaSin.Sintatico;

public class Main {
	
	public static void main(String args[]){
	/*	Lexico analisador = new Lexico();
		analisador.analise("resources/hello");
		//*/
		Sintatico analisador = new Sintatico("resources/fib");
		analisador.analise();
	}

}
