package analisaSin;

import analisaLex.CategoriaToken;
import analisaLex.Lexico;
import analisaLex.Token;

public class Sintatico {

	private Lexico analiseLex;
	private Token tokenAtual;

	Sintatico(String path) {
		analiseLex.analise(path);
	}

	private void nextToken() {
		tokenAtual = analiseLex.nextToken();

	}

	private boolean stateS() {
		nextToken();
		if (tokenAtual.getCategoria() == CategoriaToken.escFunc) {
			nextToken();
			//
			stateLDeclFunc();
			//
			if (tokenAtual.getCategoria() == CategoriaToken.escEnd) {
				nextToken();
				if (tokenAtual.getCategoria() == CategoriaToken.tInt) {
					nextToken();
					if (tokenAtual.getCategoria() == CategoriaToken.tkMain) {
						nextToken();
						if (tokenAtual.getCategoria() == CategoriaToken.parBegin) {
							//
							stateLDeclArgs();
							//
							nextToken();
							if (tokenAtual.getCategoria() == CategoriaToken.parEnd) {
								nextToken();
								if (tokenAtual.getCategoria() == CategoriaToken.blkBegin) {
									//
									stateFuncBody();
									//
									nextToken();
									if (tokenAtual.getCategoria() == CategoriaToken.blkEnd) {
										return true;
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	private void stateLDeclFunc() {
		switch (tokenAtual.getCategoria()) {
		case tInt:
		case tFloat:
		case tBool:
		case tString:
		case tChar:
		case tArray:
			stateDeclFunc();
			stateLDeclFunc();
			break;
		default:
		}

	}

	private void stateDeclFunc() {
		stateDecl();
		if (tokenAtual.getCategoria() == CategoriaToken.parBegin) {
			nextToken();
			//
			stateLDeclArgs();
			//
			if (tokenAtual.getCategoria() == CategoriaToken.parEnd) {
				nextToken();
				if (tokenAtual.getCategoria() == CategoriaToken.blkBegin) {
					nextToken();
					//
					stateFuncBody();
					//
					if (tokenAtual.getCategoria() == CategoriaToken.blkEnd) {
						return;
					}
				}
			}
		}
		// throws new Exception =

	}

	private void stateLDeclArgs() {
		switch (tokenAtual.getCategoria()) {
		case tInt:
		case tFloat:
		case tBool:
		case tString:
		case tChar:
		case tArray:
			stateDecl();
			stateLDeclArgsT();
			break;
		default:
		}

	}

	private void stateLDeclArgsT() {
		if (tokenAtual.getCategoria() == CategoriaToken.sepVir) {
			stateDecl();
			stateLDeclArgsT();
		}
	}

	private void stateFuncBody() {
		if (tokenAtual.getCategoria() == CategoriaToken.escDecl) {
			nextToken();
			//
			stateLDecl();
			//
			if (tokenAtual.getCategoria() == CategoriaToken.escEnd) {
				nextToken();
			}
		}

	}

	private void stateLDecl() {
		switch (tokenAtual.getCategoria()) {
		case tInt:
		case tFloat:
		case tBool:
		case tString:
		case tChar:
		case tArray:
			stateDecl();
			if (tokenAtual.getCategoria() == CategoriaToken.tkTerm) {
				nextToken();
			}
			stateLDecl();
			break;
		default:
		}
	}

	private void stateDecl() {
		switch (tokenAtual.getCategoria()) {
		case tInt:
		case tFloat:
		case tBool:
		case tString:
		case tChar:
			nextToken();
			if (tokenAtual.getCategoria() == CategoriaToken.tkId) {
				return;
			}
			break;
		case tArray:
			if (tokenAtual.getCategoria() == CategoriaToken.tkTerm) {
				nextToken();
			}
			stateLDecl();
			break;
		default:
		}
	}

	private void stateLInstruc(Token token) {

	}

	private void stateInstruc(Token token) {

	}

	private void stateCondElse(Token token) {

	}

	private void stateEs(Token token) {

	}

	private void stateEsr(Token token) {

	}

	private void stateEb(Token token) {

	}

	private void stateEbr(Token token) {

	}

	private void stateTb(Token token) {

	}

	private void stateTbr(Token token) {

	}

	private void stateFb(Token token) {

	}

	private void stateEc(Token token) {

	}

	private void stateEa(Token token) {

	}

	private void stateEar(Token token) {

	}

	private void stateTa(Token token) {

	}

	private void stateTar(Token token) {

	}

	private void stateFa(Token token) {

	}

	private void stateFunc(Token token) {

	}

	private void stateLArgs(Token token) {

	}

	private void stateLArgsT(Token token) {

	}

	private void stateValue(Token token) {

	}

	private void stateType() {
		switch (tokenAtual.getCategoria()) {
		case tInt:
		case tFloat:
		case tBool:
		case tString:
		case tChar:
		case tArray:
			nextToken();
			return;
		default:
			//throw exception
		}
	}
}
