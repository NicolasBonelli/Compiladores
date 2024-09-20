package Paquetecompi;

public class Pair {
	private String lexema;
	private int token;
	
	
	public Pair(String lexema, int token) {
		this.lexema = lexema;
		this.token = token;
	}
	public String getLexema() {
		return lexema;
	}
	public void setLexema(String lexema) {
		this.lexema = lexema;
	}
	public int getToken() {
		return token;
	}
	public void setToken(int token) {
		this.token = token;
	}
	
	@Override
	public String toString() {
		return lexema+"::"+token;
	}
}
