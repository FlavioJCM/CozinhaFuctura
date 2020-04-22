package exceptions;

public class NaoCadastradoException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String retorno;
	
	public NaoCadastradoException(String retorno) {
		this.retorno = retorno;
	}
	
	@Override
	public String toString() {
		return "O " + retorno + " não está cadastrado";
	}

}
