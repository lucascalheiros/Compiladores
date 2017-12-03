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
		stateS();
	}

	private void nextToken() {
		tokenAtual = analiseLex.nextToken();
	}

	private void stateS() {
		System.out.println("S = 'func' LDeclFunc 'end' 'int' 'main' '(' LDeclArgs ')' '{' FuncBody '}'");
		nextToken();
		
		if (tokenAtual.getCategoria() == CategoriaToken.escFunc) {
			nextToken();
			stateLDeclFunc();

			if (tokenAtual.getCategoria() == CategoriaToken.escEnd) {
				nextToken();

				if (tokenAtual.getCategoria() == CategoriaToken.tInt) {
					nextToken();

					if (tokenAtual.getCategoria() == CategoriaToken.tkMain) {
						nextToken();

						if (tokenAtual.getCategoria() == CategoriaToken.parBegin) {
							nextToken();
							stateLDeclArgs();

							if (tokenAtual.getCategoria() == CategoriaToken.parEnd) {
								nextToken();

								if (tokenAtual.getCategoria() == CategoriaToken.blkBegin) {
									nextToken();
									stateFuncBody();

									if (tokenAtual.getCategoria() == CategoriaToken.blkEnd) {
										return;
									}
								}
							}
						}
					}
				}
			}
		}
		throw new TokenInvalidoException(tokenAtual);
	}

	private void stateLDeclFunc() {
		switch (tokenAtual.getCategoria()) {
		case tInt:
		case tFloat:
		case tBool:
		case tString:
		case tChar:
		case tArray:
			System.out.println("LDeclFunc = DeclFunc LDeclFunc");
			stateDeclFunc();
			stateLDeclFunc();
			break;
		default:
			System.out.println("LDeclFun = epsilon");
		}

	}

	private void stateDeclFunc() {
		System.out.println("DeclFunc = Decl '(' LDeclArgs ')' '{' FuncBody '}'");
		stateDecl();
		
		if (tokenAtual.getCategoria() == CategoriaToken.parBegin) {
			nextToken();
			stateLDeclArgs();

			if (tokenAtual.getCategoria() == CategoriaToken.parEnd) {
				nextToken();

				if (tokenAtual.getCategoria() == CategoriaToken.blkBegin) {
					nextToken();
					stateFuncBody();

					if (tokenAtual.getCategoria() == CategoriaToken.blkEnd) {
						nextToken();
						return;
					}
				}
			}
		}
		throw new TokenInvalidoException(tokenAtual);
	}

	private void stateLDeclArgs() {
		switch (tokenAtual.getCategoria()) {
		case tInt:
		case tFloat:
		case tBool:
		case tString:
		case tChar:
		case tArray:
			System.out.println("LDeclArgs = Decl LDeclArgsT");
			stateDecl();
			stateLDeclArgsT();
			break;
		default:
			System.out.println("LDeclArgs = epsilon");
		}

	}

	private void stateLDeclArgsT() {
		
		if (tokenAtual.getCategoria() == CategoriaToken.sepVir) {
			System.out.println("LDeclArgsT = ',' Decl LDeclArgsT");
			nextToken();
			stateDecl();
			stateLDeclArgsT();
			return;
		}
		System.out.println("LDeclArgsT = epsilon");

	}

	private void stateFuncBody() {
		System.out.println("FuncBody = LDecl LInstruc");
		
		if (tokenAtual.getCategoria() == CategoriaToken.escDecl) {
			nextToken();
			stateLDecl();

			if (tokenAtual.getCategoria() == CategoriaToken.escEnd) {
				nextToken();
				stateLInstruc();
				return;
			}
		}
		throw new TokenInvalidoException(tokenAtual);

	}

	private void stateLDecl() {
		switch (tokenAtual.getCategoria()) {
		case tInt:
		case tFloat:
		case tBool:
		case tString:
		case tChar:
		case tArray:
			System.out.println("LDecl = Decl ';' LDecl");
			stateDecl();
			
			if (tokenAtual.getCategoria() == CategoriaToken.tkTerm) {
				nextToken();
			} 
			else {
				throw new TokenInvalidoException(tokenAtual);
			}
			stateLDecl();
			return;
		default:
			System.out.println("LDecl = epsilon");
		}
	}

	private void stateDecl() {
		switch (tokenAtual.getCategoria()) {
		case tInt:
		case tFloat:
		case tBool:
		case tString:
		case tChar:
			System.out.println("Decl = Type tkId");
			stateType();
			
			if (tokenAtual.getCategoria() == CategoriaToken.tkId) {
				System.out.println("tkId = " + tokenAtual);
				nextToken();
				return;
			}
			break;
		case tArray:
			System.out.println("Decl = 'array' Type '[' ArrDeclNum ']' tkId");
			nextToken();
			switch (tokenAtual.getCategoria()) {
			case tInt:
			case tFloat:
			case tBool:
			case tString:
			case tChar:
				stateType();

				if (tokenAtual.getCategoria() == CategoriaToken.arrBegin) {
					nextToken();
					stateArrDeclNum();

					if (tokenAtual.getCategoria() == CategoriaToken.arrEnd) {
						nextToken();

						if (tokenAtual.getCategoria() == CategoriaToken.tkId) {
							System.out.println("tkId = " + tokenAtual);
							nextToken();
							return;
						}
					}

				}
			default:
			}
		default:
		}
		throw new TokenInvalidoException(tokenAtual);
	}

	private void stateArrDeclNum() {
		
		if (tokenAtual.getCategoria() == CategoriaToken.cInt) {
			System.out.println("ArrDeclNum = " + tokenAtual);
			nextToken();
			return;
		}
		System.out.println("ArrDeclNum = epsilon");
	}

	private void stateLInstruc() {
		switch (tokenAtual.getCategoria()) {
		case tkId:
		case eWhile:
		case eFor:
		case tkReturn:
		case eIf:
			System.out.println("LInstruc = Instruc LInstruc");
			stateInstruc();
			stateLInstruc();
			return;
		default:
			System.out.println("LInstruc = epsilon");
		}

	}

	private void stateInstruc() {
		switch (tokenAtual.getCategoria()) {
		case tkId:
			System.out.println("Instruc = tkId Arr AtrbFun ';'");
			System.out.println("tkId = "+tokenAtual);
			nextToken();
			stateArr();
			stateAtrbFun();
			
			if (tokenAtual.getCategoria() == CategoriaToken.tkTerm) {
				nextToken();
				return;
			}
			break;
		case eWhile:
			System.out.println("Instruc = 'while' '(' Es ')' '{' LInstruc '}'");
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
			System.out.println("Instruc = 'for' '(' tkId ':' Es ',' Es ')' '{' LInstruc '}'");
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
			System.out.println("Instruc = 'return' Value ';'");
			nextToken();
			stateValue();
			if (tokenAtual.getCategoria() == CategoriaToken.tkTerm) {
				nextToken();
				return;
			}
			break;
		case eIf:
			System.out.println("Instruc = 'if' '(' Es ')' '{' LInstruc '}' CondElse");
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
		throw new TokenInvalidoException(tokenAtual);
	}
	
	
	private void stateArr() {
		if(tokenAtual.getCategoria() == CategoriaToken.arrBegin) {
			System.out.println("Arr = '[' Es ']'");
			nextToken();
			stateEs();
			if (tokenAtual.getCategoria() == CategoriaToken.arrEnd) {
				nextToken();
				return;
			}
		}
		System.out.println("Arr = epsilon");
	}

	private void stateAtrbFun() {
		if (tokenAtual.getCategoria() == CategoriaToken.tkAtr) {
			System.out.println("AtrbFun = '=' Es");
			nextToken();
			stateEs();
			return;
		}
		if (tokenAtual.getCategoria() == CategoriaToken.parBegin) {
			System.out.println("AtrbFun = ‘(‘ LArgs ’)’");
			nextToken();
			stateLArgs();
			
			if (tokenAtual.getCategoria() == CategoriaToken.parEnd) {
				nextToken();
				return;
			}
		}
		throw new TokenInvalidoException(tokenAtual);
	}

	private void stateCondElse() {
		if (tokenAtual.getCategoria() == CategoriaToken.eElse) {
			System.out.println("CondElse = 'else' '{' LInstruc }");
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
		else {
			System.out.println("CondElse = epsilon");
		}
		throw new TokenInvalidoException(tokenAtual);
	}

	private void stateEs() {
		System.out.println("Es = Eb Esr");
		stateEb();
		stateEsr();
	}

	private void stateEsr() {
		if (tokenAtual.getCategoria() == CategoriaToken.opConc) {
			System.out.println("Esr = '#' Eb Esr");
			nextToken();
			stateEb();
			stateEsr();
			return;
		}
		System.out.println("Esr = epsilon");
	}

	private void stateEb() {
		System.out.println("Eb = Tb Ebr");
		stateTb();
		stateEbr();
	}

	private void stateEbr() {
		if (tokenAtual.getCategoria() == CategoriaToken.opDisj) {
			System.out.println("Ebr = '|' Tb Ebr");
			nextToken();
			stateTb();
			stateEbr();
			return;
		}
		System.out.println("Ebr = epsilon");
	}

	private void stateTb() {
		System.out.println("Tb = Fb Tbr");
		stateFb();
		stateTbr();
	}

	private void stateTbr() {
		if (tokenAtual.getCategoria() == CategoriaToken.opConj) {
			System.out.println("Tbr = '&' Tb Ebr");
			nextToken();
			stateTb();
			stateEbr();
			return;
		}
		System.out.println("Tbr = epsilon");
	}

	private void stateFb() {
		if (tokenAtual.getCategoria() == CategoriaToken.opNeg) {
			System.out.println("Fb = '!' Fb");
			nextToken();
			stateFb();
			return;
		}
		System.out.println("Fb = Ei");
		stateEi();
	}

	private void stateEi() {
		System.out.println("Ei = Ec Eir");
		stateEc();
		stateEir();
	}

	private void stateEir() {
		if (tokenAtual.getCategoria() == CategoriaToken.opIgua) {
			System.out.println("Eir = opIgua Ec");
			System.out.println("opIgua = " + tokenAtual);
			nextToken();
			stateEc();
			return;
		}
		System.out.println("Eir = epsilon");
	}

	private void stateEc() {
		System.out.println("Ec = Ea Ecr");
		stateEa();
		stateEcr();
	}

	private void stateEcr() {
		if (tokenAtual.getCategoria() == CategoriaToken.opComp) {
			System.out.println("Ecr = opComp Ea");
			System.out.println("opComp = " + tokenAtual);
			nextToken();
			stateEa();
			return;
		}
		System.out.println("Ecr = epsilon");
	}

	private void stateEa() {
		System.out.println("Ea = Er Ear");
		stateEr();
		stateEar();
	}

	private void stateEar() {
		if (tokenAtual.getCategoria() == CategoriaToken.opAdi) {
			System.out.println("Ear = opAdi Er Ear");
			System.out.println("opAdi = " + tokenAtual);
			nextToken();
			stateEr();
			stateEar();
			return;
		}
		System.out.println("Ear = epsilon");
	}

	private void stateEr() {
		System.out.println("Ta = Ta Err");
		stateTa();
		stateErr();
	}

	private void stateErr() {
		if (tokenAtual.getCategoria() == CategoriaToken.opRes) {
			System.out.println("Err = '%' Ta Err");
			nextToken();
			stateTa();
			stateErr();
			return;
		}
		System.out.println("Err = epsilon");
	}

	private void stateTa() {
		System.out.println("Ta = Fa Tar");
		stateFa();
		stateTar();
	}

	private void stateTar() {
		if (tokenAtual.getCategoria() == CategoriaToken.opMul) {
			System.out.println("Tar = opMul Fa Tar");
			System.out.println("opmul = " + tokenAtual);
			nextToken();
			stateFa();
			stateTar();
			return;
		}
		System.out.println("Tar = epsilon");
	}

	private void stateFa() {
		switch (tokenAtual.getCategoria()) {
		case opMenUn:
			System.out.println("Fa = '~' Fa");
			nextToken();
			stateFa();
			return;
		case parBegin:
			System.out.println("Fa = '(' Fa ')'");
			nextToken();
			stateEs();
			
			if (tokenAtual.getCategoria() == CategoriaToken.parEnd) {
				nextToken();
				return;
			}
			break;
		default:
			System.out.println("Fa = Value");
			stateValue();
		}
	}

	private void stateLArgs() {
		if (tokenAtual.getCategoria() == CategoriaToken.parEnd) {
			System.out.println("LArgs = epsilon");
			return;
		}
		System.out.println("LArgs = Es LArgsT");
		stateEs();
		stateLArgsT();
	}

	private void stateLArgsT() {
		if (tokenAtual.getCategoria() == CategoriaToken.sepVir) {
			System.out.println("LArgsT = ',' Es LArgsT");
			nextToken();
			stateEs();
			stateLArgsT();
			return;
		}
		System.out.println("LArgsT = epsilon");

	}

	private void stateValue() {
		switch (tokenAtual.getCategoria()) {
		case cInt:
		case cFloat:
		case cBool:
		case cString:
		case cChar:
			System.out.println("Value = " + tokenAtual);
			nextToken();
			return;
		case tkId:
			System.out.println("Value = tkId Valuer");
			System.out.println("tkId = " + tokenAtual);
			nextToken();
			stateValuer();
			return;
		default:
			throw new TokenInvalidoException(tokenAtual);
		}
	}

	private void stateValuer() {
		switch (tokenAtual.getCategoria()) {
		case arrBegin:
			System.out.println("Valuer = '[' Es ']'");
			nextToken();
			stateEs();
			if (tokenAtual.getCategoria() == CategoriaToken.arrEnd) {
				nextToken();
				return;
			}
			break;
		case parBegin:
			System.out.println("Valuer = ‘(‘ LArgs ’)’");
			nextToken();
			stateLArgs();
			if (tokenAtual.getCategoria() == CategoriaToken.parEnd) {
				nextToken();
				return;
			}
			break;
		default: // aceita vazio
			return;
		}
		throw new TokenInvalidoException(tokenAtual);
	}

	private void stateType() {
		switch (tokenAtual.getCategoria()) {
		case tInt:
		case tFloat:
		case tBool:
		case tString:
		case tChar:
		case tArray:
			System.out.println("Type = " + tokenAtual);
			nextToken();
			return;
		default:
			throw new TokenInvalidoException(tokenAtual);
		}
	}

	private class TokenInvalidoException extends RuntimeException {
		private TokenInvalidoException(Token token) {
			super(token.toString());
		}
	}
}
