package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import entidade.Ingrediente;
import entidade.Receita;
import exceptions.NaoCadastradoException;

public class ReceitaDAOImpl implements GenericoDAO<Receita> {

	private EntityManager ent;

	// Construtor vai receber a conexão para executar
	public ReceitaDAOImpl(EntityManager ent) {
		this.ent = ent;
	}

	@Override
	public void inserir(Receita receita) throws Exception {
		EntityTransaction tx = ent.getTransaction();
		tx.begin();

		ent.persist(receita);
		tx.commit();
	}

	@Override
	public void alterar(Receita receita) throws Exception {
		EntityTransaction tx = ent.getTransaction();
		tx.begin();

		ent.merge(receita);
		tx.commit();
	}

	@Override
	public void remover(Receita receita) throws Exception {
		Receita l = pesquisarPorID(receita);

		if (l == null) {
			throw new NaoCadastradoException("receita");
		}

		EntityTransaction tx = ent.getTransaction();
		tx.begin();

		ent.remove(l);
		tx.commit();
	}

	@Override
	public Receita pesquisarPorID(Receita receita) throws Exception {
		Receita r = ent.find(Receita.class, receita.getCodigo());

		return r;
	}

	@Override
	public List<Receita> listarTodos() throws Exception {
		Query query = ent.createQuery("from Receita r");

		List<Receita> receitas = query.getResultList();

		return receitas;
	}

	@Override
	public List<Receita> listarPersonalizado(Receita receita) throws Exception {
		Query query = ent.createQuery("select r from Receita r join r.chef c " + fazendoJoin(receita));
		List<Receita> receitas = query.getResultList();

		return receitas;
	}
	
	public String fazendoJoin(Receita receita) {
		String jpql = "";		
		String where = " where ";
		String and = " and ";		
		
		boolean isNaoEhPrimeiro = false;
		
		if (receita.getCodigo() != 0) {
			jpql += where + " r.codigo = " + receita.getCodigo();
			isNaoEhPrimeiro = true;
		}

		if (receita.getNome() != null && !receita.getNome().trim().equals("")) {
			if(isNaoEhPrimeiro) {
				jpql += and + "r.nome like '%" + receita.getNome().toUpperCase() + "%'";
			} else {
				jpql += where + "r.nome like '%" + receita.getNome().toUpperCase() + "%'";
				isNaoEhPrimeiro = true;
			}
		}

		if (receita.getPreco() != null && receita.getPreco() != 0.0){
			if(isNaoEhPrimeiro) {
				jpql += and + "r.preco = " + receita.getPreco();
			} else {
				jpql += where + "r.preco = " + receita.getPreco();
				isNaoEhPrimeiro = true;
			}			
		}
		
		if(receita.getChef().getNome() != null && !receita.getChef().getNome().trim().equals("")) {
			if(isNaoEhPrimeiro) {
				jpql += and + "c.nome like '%" + receita.getChef().getNome().toUpperCase() + "%'";
			} else {
				jpql += where + "c.nome like '%" + receita.getChef().getNome().toUpperCase() + "%'";
				isNaoEhPrimeiro = true;
			}			
		}
		
		if(receita.getChef().getCpf() != null && !receita.getChef().getCpf().equals("")) {
			if(isNaoEhPrimeiro) {
				jpql += and + "c.cpf = '" + receita.getChef().getCpf() + "'";
			} else {
				jpql += where + "c.cpf = '" + receita.getChef().getCpf() + "'";
				isNaoEhPrimeiro = true;
			}					
		}
		
		return jpql;
	}	
	
	public void removerIngrediente(List<Ingrediente> ingredientes) {		
		
		EntityTransaction tx = ent.getTransaction();
		tx.begin();
		
		for (int i = 0; i < ingredientes.size(); i++) {			
			ent.remove(ent.find(Ingrediente.class, ingredientes.get(i).getCodigo()));
		}		
		tx.commit();
	}

}
