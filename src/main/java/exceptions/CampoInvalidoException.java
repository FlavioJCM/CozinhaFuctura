package exceptions;

public class CampoInvalidoException extends Exception{
	private static final long serialVersionUID = 1L;
	private String retorno;
	
	public CampoInvalidoException(String retorno) {
		this.retorno = retorno;
	}
	
	@Override
	public String toString() {
		return "Campo " + retorno + " inválido!";
	}
}
