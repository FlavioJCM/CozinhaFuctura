package testes;

import java.util.List;
import org.junit.Ignore;
import org.junit.Test;
import dao.ChefDAOImpl;
import entidade.Chef;
import exceptions.JaCadastradoException;
import exceptions.NaoCadastradoException;
import util.JpaUtil;

public class ChefTeste {
	
	@Test
	@Ignore
	public void inserir(){
		ChefDAOImpl dao = null;
		try {
			dao = new ChefDAOImpl(JpaUtil.getEntityManager());
		} catch (Exception e) {
			System.out.println(e.toString());
			return;
		}
		
		Chef chef = new Chef("101", "ANNY", "1");
		
		try {
			if(dao.pesquisarPorID(chef) != null) {
				throw new JaCadastradoException("chef");
			}
			dao.inserir(chef);
			System.out.println("Chef inserido!");
		} catch (Exception e) {
			System.out.println(e.toString());
			return;
		}
	}
	
	@Test
	@Ignore
	public void remover() {
		ChefDAOImpl dao = null;
		try {
			dao = new ChefDAOImpl(JpaUtil.getEntityManager());
		} catch (Exception e) {
			System.out.println(e.toString());
			return;
		}
		Chef chef = new Chef();
		chef.setCpf("999");
		
		try {
			if(dao.pesquisarPorID(chef) == null) {
				throw new NaoCadastradoException("chef");
			}
			dao.remover(chef);
			System.out.println("Chef removido!");
		} catch (Exception e) {
			System.out.println(e.toString());
			return;
		}
	}
	
	@Test
//	@Ignore
	public void alterar() {
		ChefDAOImpl dao = null;
		try {
			dao = new ChefDAOImpl(JpaUtil.getEntityManager());
		} catch (Exception e) {
			System.out.println(e.toString());
			return;
		}
		Chef chef = null;
		try {
			chef = dao.pesquisarPorID(new Chef("111"));
			if(chef == null) {
				throw new NaoCadastradoException("chef");
			}
		} catch (Exception e1) {
			System.out.println(e1.toString());
			return;
		}
		chef.setNome("ANNY ASSIS");
		
		try {
			if(dao.pesquisarPorID(chef) == null) {
				throw new NaoCadastradoException("chef");
			}
			dao.alterar(chef);
			System.out.println("Chef alterado!");
		} catch (Exception e) {
			System.out.println(e.toString());
			return;
		}
	}
	
	@Test
	@Ignore
	public void listarTodos() {
		ChefDAOImpl dao = null;
		try {
			dao = new ChefDAOImpl(JpaUtil.getEntityManager());
		} catch (Exception e) {
			System.out.println(e.toString());
			return;
		}
		try {
			List<Chef> chefs = dao.listarTodos();
			for (Chef chef : chefs) {
				System.out.println(chef.toString());
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			return;
		}
	}
	
	@Test
	public void pesquisarPersonalizado() {
		ChefDAOImpl dao = null;
		try {
			dao = new ChefDAOImpl(JpaUtil.getEntityManager());
		} catch (Exception e) {
			System.out.println(e.toString());
			return;
		}
		
		Chef c = new Chef("","");
		
		try {
			List<Chef> chefs = dao.listarPersonalizado(c);
			for (Chef chef : chefs) {
				System.out.println(chef.toString());
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			return;
		}
	}
}
