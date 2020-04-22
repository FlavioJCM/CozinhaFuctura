package dao;

import java.util.List;

//Design patterns
public interface GenericoDAO <Entidade> {
	
	public void inserir(Entidade entidade) throws Exception;
	
	public void alterar(Entidade entidade) throws Exception;

	public void remover(Entidade entidade) throws Exception;

	public Entidade pesquisarPorID(Entidade entidade) throws Exception;

	public List<Entidade> listarTodos() throws Exception;
	
	public List<Entidade> listarPersonalizado(Entidade entidade) throws Exception;
}
