package controller;

import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import dao.ReceitaDAOImpl;
import entidade.Chef;
import entidade.Ingrediente;
import entidade.Receita;
import util.JpaUtil;

@ManagedBean(name = "FeedReceitaBean")
@RequestScoped
public class FeedReceitaBean {
	private Chef chef;
	private Receita receita;
	private List<Receita> receitas;
	private ReceitaDAOImpl receitaDAO;
	
	public Chef getChef() {
		return chef;
	}
	public void setChef(Chef chef) {
		this.chef = chef;
	}
	public Receita getReceita() {
		return receita;
	}
	public void setReceita(Receita receita) {
		this.receita = receita;
	}	
	public List<Receita> getReceitas() {
		return receitas;
	}
	public void setReceitas(List<Receita> receitas) {
		this.receitas = receitas;
	}
	public FeedReceitaBean() {
		this.receitas = new ArrayList<Receita>();
		try {
			this.receitaDAO = new ReceitaDAOImpl(JpaUtil.getEntityManager());
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro! " + e.toString()));
			return;
		}
		listarTodos();
		limparCampos();
	}	
	
	//pesquisa personalizada da lista de receitas do feed de receitas
	public void pesquisa() {		
		
		receita.getChef().setCpf(chef.getCpf());
		receita.getChef().setNome(chef.getNome());
		receita.setNome(receita.getNome().toUpperCase());
		try {
			this.receitas = receitaDAO.listarPersonalizado(receita);
			System.out.println();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro! " + e.toString()));
			return;
		} 
	}
	
	public void listarTodos() {
		try {
			this.receitas = receitaDAO.listarTodos();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Atenção! " + e.toString()));
			e.printStackTrace();
			return;
		}
	}
	
	public void limparCampos() {
		this.chef = new Chef();
		this.chef.setReceitas(new ArrayList<Receita>());
		this.receita = new Receita();
		this.receita.setChef(new Chef());
		this.receita.setIngredientes(new ArrayList<Ingrediente>());
	}
	
}
