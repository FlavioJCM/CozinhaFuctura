package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import entidade.Ingrediente;

public class IngredienteDAOImpl implements IngredienteDAO {
	private EntityManager ent;

	// Construtor vai receber a conexão para executar
	public IngredienteDAOImpl(EntityManager ent) {
		this.ent = ent;
	}

	@Override
	public void removerIngrediente(List<Ingrediente> ingredientes) {
		EntityTransaction tx = ent.getTransaction();
		tx.begin();

		for (int i = 0; i < ingredientes.size(); i++) {
			ent.remove(ent.find(Ingrediente.class, ingredientes.get(i).getCodigo()));
		}
		tx.commit();
	}

}
