package testes;

import java.util.ArrayList;
import java.util.List;
import org.junit.Ignore;
import org.junit.Test;
import dao.ChefDAOImpl;
import dao.ReceitaDAOImpl;
import entidade.Chef;
import entidade.Ingrediente;
import entidade.Receita;
import util.JpaUtil;

public class ReceitaTeste {
	@Test
//	@Ignore
	public void inserir(){
		
		Chef chef = null;		
		try {
			ChefDAOImpl daoChef = new ChefDAOImpl(JpaUtil.getEntityManager());
			chef = daoChef.pesquisarPorID(new Chef("111.111.111-11"));
		} catch (Exception e) {
			System.out.println(e.toString());
			return;
		}	
		
		ReceitaDAOImpl daoReceita = null;
		try {
			daoReceita = new ReceitaDAOImpl(JpaUtil.getEntityManager());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		Receita receita = new Receita("TESTE RE 04", "COLOCAR O TESTE RE 04...", chef, 68.21);
		receita.setIngredientes(new ArrayList<Ingrediente>());
		
		Ingrediente ingredienteUm = new Ingrediente("1 pacotes de TESTE ING 04", receita);
		Ingrediente ingredienteDois = new Ingrediente("300 ml TESTE ING 04", receita);
		Ingrediente ingredienteTres = new Ingrediente("200 g TESTE ING 04", receita);
		
		receita.getIngredientes().add(ingredienteUm);
		receita.getIngredientes().add(ingredienteDois);
		receita.getIngredientes().add(ingredienteTres);
		
		try {
			daoReceita.inserir(receita);
			System.out.println("Receita Inserida!");
		} catch (Exception e) {
			System.out.println(e.toString());
		}		
	}
	
	@Test
	@Ignore
	public void listarTodos() {
		ReceitaDAOImpl daoReceita = null;
		try {
			daoReceita = new ReceitaDAOImpl(JpaUtil.getEntityManager());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		try {
			List<Receita> receitas = daoReceita.listarTodos();
			for (Receita receita : receitas) {
				System.out.println(receita.toString());
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	@Test
	@Ignore
	public void alterar() {
		
		Chef chef = null;		
		try {
			ChefDAOImpl daoChef = new ChefDAOImpl(JpaUtil.getEntityManager());
			chef = daoChef.pesquisarPorID(new Chef("555.555.555-55"));
		} catch (Exception e) {
			System.out.println(e.toString());
			return;
		}	
		
		ReceitaDAOImpl daoReceita = null;
		try {
			daoReceita = new ReceitaDAOImpl(JpaUtil.getEntityManager());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		try {
			Receita receita = daoReceita.pesquisarPorID(new Receita(2));
			receita.setChef(chef);
//			receita.setModoPreparo("COLOCAR O MACARRÃO INTEGRAL COM ÁGUA NO FOGO...");
			receita.setPreco(27.9);
			
			Ingrediente ingredienteUm = new Ingrediente("2 pacotes de macarrão", receita);
			Ingrediente ingredienteDois = new Ingrediente("1 caixa de extrato de tomate", receita);
			Ingrediente ingredienteTres = new Ingrediente("1 cebola ralada", receita);
			Ingrediente ingredienteQuatro = new Ingrediente("1 pacote de queijo ralado", receita);
			
			receita.getIngredientes().add(ingredienteUm);
			receita.getIngredientes().add(ingredienteDois);
			receita.getIngredientes().add(ingredienteTres);
			receita.getIngredientes().add(ingredienteQuatro);
			
			daoReceita.alterar(receita);
			
			System.out.println("Receita alterada!");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
	}
}
