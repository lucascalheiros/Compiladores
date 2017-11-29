package analisaLex;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Lexico{ 
	private ArrayList<Token> output = new ArrayList<Token>();
	private boolean palavraIniciada = false;
	private String strLinha = "";
	private String strPalavraAtual = "";
	private String charAtual = "";
	private String charInicial = "";
	private int colInicial = 0;
	private int colAtual = 0;
	private int linha = 0;
	private int tokenOut = 0;

	
	private void iniciaPalavra() {
		charAtual = String.valueOf(strLinha.charAt(colAtual));
		charInicial = charAtual;
		strPalavraAtual = ""+charInicial;
		palavraIniciada = true;
		colInicial = colAtual;
	}
	
	private void finalizaPalavra() {
		charInicial = "";
		strPalavraAtual = "";
		palavraIniciada = false;
		
	}
	
	private String proxChar() {
		colAtual++;
		if(colAtual >= strLinha.length())
			return "";
		charAtual = String.valueOf(strLinha.charAt(colAtual));
		return charAtual;
	}
	
	private boolean hasNextChar() {
		if(colAtual + 1 >= strLinha.length())
			return false;
		return true;		
	}
	
	public void lineAnalise(String strLin, int lin) {
		palavraIniciada = false;
		strLinha = strLin;
		strPalavraAtual = "";
		colInicial = 0;
		colAtual = 0;
		linha = lin;
		charAtual = String.valueOf(strLinha.charAt(colAtual));
		charInicial = "";
	
		while( colAtual < strLinha.length() ) {

			//pula caracteres brancos e tabulações
			if(charAtual.matches("(\\t| )*")) {

				proxChar();
				finalizaPalavra();//mesmo que não iniciar a palavra
				continue;
			}
			
			//inicialização da palavra
			if(!palavraIniciada) {

				iniciaPalavra();
			}
			
			if(charAtual.matches("[A-Z]|[a-z]")) {
				proxChar();

				while(charAtual.matches("[A-Z]|[a-z]|[0-9]") && colAtual < strLinha.length() ) {
					strPalavraAtual += charAtual;
					proxChar();
				}

				if(TokensMap.getToken(strPalavraAtual)!=null) {
					output.add(new Token(linha, colInicial, TokensMap.getToken(strPalavraAtual), strPalavraAtual));
				}
				else {
					output.add(new Token(linha, colInicial, CategoriaToken.tkId, strPalavraAtual));
				}

				finalizaPalavra();
				continue;
				
			}
			if(charAtual.matches("[0-9]")) {
				proxChar();
				
				while(charAtual.matches("[0-9|\\.]")) {
					strPalavraAtual += charAtual;
					proxChar();
				}
				
				if(strPalavraAtual.matches("[0-9]+")) {
					output.add(new Token(linha, colInicial, CategoriaToken.cInt, strPalavraAtual));
				}
				else if(strPalavraAtual.matches("[0-9]+\\.[0-9]+")) {
					output.add(new Token(linha, colInicial, CategoriaToken.cFloat, strPalavraAtual));
				} 
				else {
					System.err.println("linha=" +linha+"    coluna="+colInicial+"    dt"+strPalavraAtual);
				}
				
				finalizaPalavra();
				continue;
			}
			
			//caso caractere especial
			if(charAtual.matches("[^0-9A-Za-z]")) {
				strPalavraAtual += proxChar();
				//tokens com dois caracteres
				switch(strPalavraAtual) {
					case "==":
					case "!=":
					case ">=":
					case "<=":
						finalizaPalavra();
						output.add(new Token(linha, colInicial, TokensMap.getToken(strPalavraAtual), strPalavraAtual));
						proxChar();
						continue;
					case "//":
						output.add(new Token(linha, colInicial, TokensMap.getToken(strPalavraAtual), strPalavraAtual));
						return;
				}
				//tokens com um caractere, nesse momento a strPalavraAtual tem tamanho 2, charInicial e charAtual
				switch(charInicial) {
					case "\"":
						while(!charAtual.equals("\"") && hasNextChar()) {
							strPalavraAtual += proxChar();
						}
						if(charAtual.equals("\"")) {
							output.add(new Token(linha, colInicial, CategoriaToken.cString, strPalavraAtual));
						}
						else {
							System.err.println("provavel erro de literal string");
						}
						finalizaPalavra();
						proxChar();
						break;
					case "'":
						while(!charAtual.equals("'") && hasNextChar()) {
							strPalavraAtual += proxChar();
						}
						if(charAtual.equals("'")) {
							output.add(new Token(linha, colInicial, CategoriaToken.cChar, strPalavraAtual));
						}
						else {
							System.err.println("provavel erro de literal string");
						}
						finalizaPalavra();
						proxChar();
						break;
						
					default:
						if( TokensMap.getToken(charInicial) != null)
							output.add(new Token(linha, colInicial, TokensMap.getToken(charInicial), charInicial));
						else
							System.err.println("provavel erro de caractere não permitido["+charInicial+"]");
						finalizaPalavra();
				}	
			}			
		}
	}
	
	public void analise(String path) {
		
		try {
			FileReader arq = new FileReader(path);
			BufferedReader lerArq = new BufferedReader(arq);
			String linha = lerArq.readLine();
			int lin = 0;
			while(linha != null){
				lin++;
				this.lineAnalise( linha, lin);
				linha = lerArq.readLine();
			}
			lerArq.close();	
		} catch(FileNotFoundException e) {
			System.out.println("erro file not found");
			return;
		} catch(IOException e) {
			System.out.println("erro io");
			return;
		} 
		for(Token t: output) {
			System.out.println(t);
		}
	}
	
	public Token nextToken() {
		if(output.size() > tokenOut) {
			return output.get(tokenOut++);
		}
		return null;
	}
	
}

