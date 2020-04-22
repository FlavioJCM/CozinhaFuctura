package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import entidade.Chef;
import exceptions.NaoCadastradoException;

public class ChefDAOImpl implements GenericoDAO<Chef>{
	
	private EntityManager ent;

	// Construtor vai receber a conexão para executar
	public ChefDAOImpl(EntityManager ent) {		
		this.ent = ent;
	}
	
	@Override
	public void inserir(Chef chef) throws Exception {
		EntityTransaction tx = ent.getTransaction();
		tx.begin();
		
		ent.persist(chef);
		tx.commit();
	}

	@Override
	public void alterar(Chef chef) throws Exception {
		EntityTransaction tx = ent.getTransaction();
		tx.begin();

		ent.merge(chef);
		tx.commit();	
	}

	@Override
	public void remover(Chef chef) throws Exception {
		Chef l = pesquisarPorID(chef);
		
		if(l == null) {
			throw new NaoCadastradoException("chef");
		}
		
		EntityTransaction tx = ent.getTransaction();
		tx.begin();
	
		ent.remove(l);
		tx.commit();
	}

	@Override
	public Chef pesquisarPorID(Chef chef) throws Exception {
		Chef p = ent.find(Chef.class, chef.getCpf());
		
		return p;
	}

	@Override
	public List<Chef> listarTodos() throws Exception {
		Query query = ent.createQuery("from Chef c");

		List<Chef> chefs = query.getResultList();

		return chefs;
	}

	@Override
	public List<Chef> listarPersonalizado(Chef chef) throws Exception {
		String[] especificos = new String[2];

		especificos[0] = "cpf = '" + chef.getCpf() + "'";
		especificos[1] = "nome LIKE '%" + chef.getNome() + "%'";				

		String and = " AND ";		

		String jpql = "FROM Chef c ";

		String onde = "WHERE ";
		
		boolean isNaoEhPrimeiro = false;

		if (!chef.getCpf().equals("")) {
			jpql = jpql + onde + especificos[0];
			isNaoEhPrimeiro = true;
		}

		if (!chef.getNome().equals("")) {
			if (isNaoEhPrimeiro) {
				jpql = jpql + and + especificos[1];
			} else {
				jpql = jpql + onde + especificos[1];
				isNaoEhPrimeiro = true;
			}
		}	
		
		Query query = ent.createQuery(jpql);
		List<Chef> chefs = query.getResultList();		
	
		return chefs;
	}

}
