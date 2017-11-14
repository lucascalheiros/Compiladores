package auxiliar;



/*
 Classe que salva uma string e marca sua posição inicial e final
 */
public class StringStruct {
	private String str;
	private int PosIni;
	private int PosFin;
	
	public StringStruct(String str, int PosIni){
		this.str = str;
		this.PosIni = PosIni;
		this.PosFin = PosIni + str.length();
	}
	
	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public int getPosIni() {
		return PosIni;
	}

	public void setPosIni(int posIni) {
		PosIni = posIni;
	}

	public int getPosFin() {
		return PosFin;
	}

	public void setPosFin(int posFin) {
		PosFin = posFin;
	}

	@Override
	public String toString() {
		return str+" Inicio:"+PosIni+" Final:"+PosFin;
	}
}
