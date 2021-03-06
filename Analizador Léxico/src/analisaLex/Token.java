package analisaLex;


public class Token {
	private int linha;
	private int coluna;
	private String nome;
	private CategoriaToken categoria;

	public Token(int linha, int coluna,CategoriaToken categoria, String nome) {
		this.linha = linha;
		this.coluna = coluna;
		this.categoria = categoria;
		this.nome = nome;
	}

	@Override
	public String toString(){
		return "("+linha+","+coluna+")"+"<"+categoria+","+nome+">";
	}
}