package exceptions;

public class JaCadastradoException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private String retorno;
	
	public JaCadastradoException(String retorno) {
		this.retorno = retorno;
	}
	
	@Override
	public String toString() {
		return "O " + retorno + " já está cadastrado";
	}
}
