package exceptions;

public class UsuarioNaoExisteException extends Exception{

	private static final long serialVersionUID = 1L;
			
	@Override
	public String toString() {
		return "Usu�rio N�o Existe!";
	}
	
	
}
