package analisaLex;

import java.util.HashMap;

public class TokensMap {
	private static HashMap<String, CategoriaToken> map = new HashMap<String, CategoriaToken>();
	static {
		map.put("main", CategoriaToken.tkMain);
		map.put("//", CategoriaToken.tkComent);
		map.put(";", CategoriaToken.tkTerm);
		map.put(",", CategoriaToken.sepVir);
		map.put(":", CategoriaToken.sepPon);
		map.put("func", CategoriaToken.escFunc);
		map.put("decl", CategoriaToken.escDecl);
		map.put("end", CategoriaToken.escEnd);
		map.put("{", CategoriaToken.blkBegin);
		map.put("}", CategoriaToken.blkEnd);
		map.put("(", CategoriaToken.parBegin);
		map.put(")", CategoriaToken.parEnd);
		map.put("[", CategoriaToken.arrBegin);
		map.put("]", CategoriaToken.arrEnd);
		map.put("int", CategoriaToken.tInt);
		map.put("float", CategoriaToken.tFloat);
		map.put("char", CategoriaToken.tChar);
		map.put("bool", CategoriaToken.tBool);
		map.put("string", CategoriaToken.tString);
		map.put("array", CategoriaToken.tArray);
		map.put("if", CategoriaToken.eIf);
		map.put("else", CategoriaToken.eElse);
		map.put("for", CategoriaToken.eFor);
		map.put("while", CategoriaToken.eWhile);
		map.put("true", CategoriaToken.cBool);
		map.put("false", CategoriaToken.cBool);
		map.put("~", CategoriaToken.opMenUn);
		map.put("+", CategoriaToken.opAdi);
		map.put("-", CategoriaToken.opAdi);
		map.put("*", CategoriaToken.opMul);
		map.put("/", CategoriaToken.opMul);
		map.put("%", CategoriaToken.opRes);
		map.put("<", CategoriaToken.opComp);
		map.put(">", CategoriaToken.opComp);
		map.put("<=", CategoriaToken.opComp);
		map.put(">=", CategoriaToken.opComp);
		map.put("==", CategoriaToken.opIgua);
		map.put("!=", CategoriaToken.opIgua);
		map.put("!", CategoriaToken.opNeg);
		map.put("&", CategoriaToken.opConj);
		map.put("|", CategoriaToken.opDisj);
		map.put("#", CategoriaToken.opConc);
		map.put("=", CategoriaToken.tkAtr);
		map.put("return", CategoriaToken.tkReturn);


	}

	public static CategoriaToken getToken(String nome) {
		return map.get(nome);
	}

}
