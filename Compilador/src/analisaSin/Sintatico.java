package analisaSin;

import analisaLex.CategoriaToken;
import analisaLex.Lexico;
import analisaLex.Token;

public class Sintatico {

	private Lexico analiseLex = new Lexico(); 
	private Token tokenAtual;

	public Sintatico(String path) {
		analiseLex.analise(path);
	}
	
	public void analise() {
		System.out.println(stateS());
	}

	private void nextToken() {
		tokenAtual = analiseLex.nextToken();
		System.out.println(tokenAtual);
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
										return true;
									}
								}
							}
						}
					}
				}
			}
		}
		throw new TokenInvalidoException (tokenAtual);
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
						nextToken();
						return;
					}
				}
			}
		}
		throw new TokenInvalidoException (tokenAtual);

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

				stateLInstruc();

				return;
			}
		}
		throw new TokenInvalidoException (tokenAtual);

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
				nextToken();
				return;
			}
			break;
		case tArray:
			nextToken();
			switch (tokenAtual.getCategoria()) {
			case tInt:
			case tFloat:
			case tBool:
			case tString:
			case tChar:
				nextToken();
				if (tokenAtual.getCategoria() == CategoriaToken.arrBegin) {
					nextToken();
					if (tokenAtual.getCategoria() == CategoriaToken.cInt) {
						nextToken();
						if (tokenAtual.getCategoria() == CategoriaToken.arrEnd) {
							nextToken();
							if (tokenAtual.getCategoria() == CategoriaToken.tkId) {
								nextToken();
								return;
							}
						}
					}
				}
			default:

			}
		default:
		}
		throw new TokenInvalidoException (tokenAtual);
	}

	private void stateLInstruc() {
		switch (tokenAtual.getCategoria()) {
		case tkId:
		case eWhile:
		case eFor:
		case tkReturn:
		case eIf:
			stateInstruc();
			stateLInstruc();
		default:
		}

	}

	private void stateInstruc() {
		switch (tokenAtual.getCategoria()) {
		case tkId:
			nextToken();
			stateAtrbFun();
			if (tokenAtual.getCategoria() == CategoriaToken.tkTerm) {
				nextToken();
				return;
			}
			break;
		case eWhile:
			nextToken();
			if (tokenAtual.getCategoria() == CategoriaToken.parBegin) {
				nextToken();
				stateEs();
				if (tokenAtual.getCategoria() == CategoriaToken.parEnd) {
					nextToken();
					if (tokenAtual.getCategoria() == CategoriaToken.blkBegin) {
						nextToken();
						stateLInstruc();
						if (tokenAtual.getCategoria() == CategoriaToken.blkEnd) {
							nextToken();
							return;
						}
					}
				}
			}
			break;
		case eFor:
			nextToken();
			if (tokenAtual.getCategoria() == CategoriaToken.parBegin) {
				nextToken();
				if (tokenAtual.getCategoria() == CategoriaToken.tkId) {
					nextToken();
					if (tokenAtual.getCategoria() == CategoriaToken.sepPon) {
						nextToken();
						stateEs();
						if (tokenAtual.getCategoria() == CategoriaToken.sepVir) {
							nextToken();
							stateEs();
							if (tokenAtual.getCategoria() == CategoriaToken.parEnd) {
								nextToken();
								if (tokenAtual.getCategoria() == CategoriaToken.blkBegin) {
									nextToken();
									stateLInstruc();
									if (tokenAtual.getCategoria() == CategoriaToken.blkEnd) {
										nextToken();
										return;
									}
								}
							}
						}
					}
				}
			}
			break;
		case tkReturn:
			nextToken();
			stateValue();
			if (tokenAtual.getCategoria() == CategoriaToken.tkTerm) {
				nextToken();
				return;
			}
			break;
		case eIf:
			nextToken();
			if (tokenAtual.getCategoria() == CategoriaToken.parBegin) {
				nextToken();
				stateEs();
				if (tokenAtual.getCategoria() == CategoriaToken.parEnd) {
					nextToken();
					if (tokenAtual.getCategoria() == CategoriaToken.blkBegin) {
						nextToken();
						stateLInstruc();
						if (tokenAtual.getCategoria() == CategoriaToken.blkEnd) {
							nextToken();
							stateCondElse();
							return;
						}
					}
				}
			}
			break;
		default:
		}
		throw new TokenInvalidoException (tokenAtual);
	}

	private void stateAtrbFun() {
		if (tokenAtual.getCategoria() == CategoriaToken.tkAtr) {
			nextToken();
			stateEs();
			return;
		}
		if (tokenAtual.getCategoria() == CategoriaToken.parBegin) {
			nextToken();
			stateLArgs();
			if (tokenAtual.getCategoria() == CategoriaToken.parEnd) {
				nextToken();
				return;
			}
		}
		throw new TokenInvalidoException (tokenAtual);
	}
	
	private void stateCondElse() {
		if (tokenAtual.getCategoria() == CategoriaToken.eElse) {
			nextToken();
			if (tokenAtual.getCategoria() == CategoriaToken.blkBegin) {
				nextToken();
				stateLInstruc();
				if (tokenAtual.getCategoria() == CategoriaToken.blkEnd) {
					nextToken();
					return;
				}
			}
		}
	}

	private void stateEs() {
		stateEb();
		stateEsr();
	}

	private void stateEsr() {
		if (tokenAtual.getCategoria() == CategoriaToken.opConc) {
			nextToken();
			stateEb();
			stateEsr();
		}
	}

	private void stateEb() {
		stateTb();
		stateEbr();
	}

	private void stateEbr() {
		if (tokenAtual.getCategoria() == CategoriaToken.opDisj) {
			nextToken();
			stateTb();
			stateEbr();
		}
	}

	private void stateTb() {
		stateFb();
		stateTbr();
	}

	private void stateTbr() {
		if (tokenAtual.getCategoria() == CategoriaToken.opConj) {
			nextToken();
			stateTb();
			stateEbr();
		}
	}

	private void stateFb() {
		if (tokenAtual.getCategoria() == CategoriaToken.opNeg) {
			nextToken();
			stateFb();
		}
		stateEc();
	}

	private void stateEc() {
		stateEa();
		if (tokenAtual.getCategoria() == CategoriaToken.opComp || tokenAtual.getCategoria() == CategoriaToken.opIgua) {
			nextToken();
			stateEa();
		}
	}

	private void stateEa() {
		stateEr();
		stateEar();
	}

	private void stateEar() {
		if (tokenAtual.getCategoria() == CategoriaToken.opAdi) {
			nextToken();
			stateEr();
			stateEar();
		}

	}
	
	private void stateEr() {
		stateTa();
		stateErr();
	}

	private void stateErr() {
		if (tokenAtual.getCategoria() == CategoriaToken.opRes) {
			nextToken();
			stateTa();
			stateErr();
		}
	}

	private void stateTa() {
		stateFa();
		stateTar();
	}

	private void stateTar() {
		if (tokenAtual.getCategoria() == CategoriaToken.opMul) {
			nextToken();
			stateFa();
			stateTar();
		}
	}

	private void stateFa() {
		switch (tokenAtual.getCategoria()) {
		case opMenUn:
			nextToken();
			stateFa();
			return;
		case parBegin:
			nextToken();
			stateEs();
			System.out.println("apareeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");

			if (tokenAtual.getCategoria() == CategoriaToken.parEnd) {
				nextToken();
				return;
			}
			break;
		default:
			stateValue();
		}
	}


	private void stateLArgs() {
		stateEs();
		stateLArgsT();
	}

	private void stateLArgsT() {
		if (tokenAtual.getCategoria() == CategoriaToken.sepVir) {
			stateEs();
			stateLArgsT();
		}
	}

	private void stateValue() {
		switch (tokenAtual.getCategoria()) {
		case cInt:
		case cFloat:
		case cBool:
		case cString:
		case cChar:
			nextToken();
			return;
		case tkId:
			nextToken();
			stateValuer();
			return;
		default:
			throw new TokenInvalidoException (tokenAtual);
		}
	}
	
	private void stateValuer() {
		switch (tokenAtual.getCategoria()) {
		case arrBegin:
			stateEs();
			System.out.println("parou aqui1");

			if (tokenAtual.getCategoria() == CategoriaToken.arrEnd) {
				nextToken();
				return;
			}
			break;
		case parBegin:
			stateLArgs();
			System.out.println("parou aqui2");

			if (tokenAtual.getCategoria() == CategoriaToken.parEnd) {
				nextToken();
				return;
			}
			break;
		default: //aceita vazio
			return;
		}
		throw new TokenInvalidoException (tokenAtual);
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
			throw new TokenInvalidoException (tokenAtual);
		}
	}
	
	private class TokenInvalidoException extends RuntimeException {

		  private TokenInvalidoException (Token token) {
		    super(token.toString());
		  }
		}
}
