package exceptions;

public class NaoConectouComOBanco extends Exception{

	private static final long serialVersionUID = 1L;
	
	@Override
	public String toString() {
		return "Não conectou com banco!";
	}
	
}
