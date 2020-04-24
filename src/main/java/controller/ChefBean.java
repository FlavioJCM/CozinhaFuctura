package controller;

import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import dao.ChefDAOImpl;
import dao.GenericoDAO;
import entidade.Chef;
import entidade.Receita;
import exceptions.CampoInvalidoException;
import exceptions.JaCadastradoException;
import util.JpaUtil;

@ManagedBean(name = "ChefBean")
@SessionScoped
public class ChefBean {
	
	private Chef chef;
	private String senhaConfirmar;
	private GenericoDAO<Chef> daoChef = null;
	
	private static final String telaLogin = "/paginas/TelaLogin.xhtml";
	
	public static Chef chefLogado;
	
	public Chef getChef() {
		return chef;
	}
	
	public void setChef(Chef chef) {
		this.chef = chef;
	}
	
	public String getSenhaConfirmar() {
		return senhaConfirmar;
	}

	public void setSenhaConfirmar(String senhaConfirmar) {
		this.senhaConfirmar = senhaConfirmar;
	}

	public ChefBean() {		
		try {
			this.daoChef = new ChefDAOImpl(JpaUtil.getEntityManager());
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "", e.toString()));
			return;
		}	
		limparCampos();
	}
	
	//método para inserir o chef na tela cadastro
	public void inserir() {	
		
		try {
			if(this.daoChef.pesquisarPorID(chef) != null) {
				throw new JaCadastradoException("chef");
			}
			
			if(chef.getNome().trim().equals("")) {
				throw new CampoInvalidoException("nome do chef");
			}
			
			chef.setNome(chef.getNome().toUpperCase());
			
			if(chef.getSenha().trim().equals("")) {
				throw new CampoInvalidoException("senha");
			}
			
			if(!chef.getSenha().equals(senhaConfirmar)) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Senhas incompatíveis!"));
				return;
			}
			
			this.daoChef.inserir(chef);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Usuário salvo!"));
			limparCampos();
			voltarParaLogin();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "", e.toString()));
			return;
		}
	}
	
	public void limparCampos() {
		this.chef = new Chef();
		this.chef.setReceitas(new ArrayList<Receita>());		
	}
	
	//editar o chef
	public void editar() {
		
		this.chef = LoginBean.chefLogado;
		try {			
			
			if(chef.getNome().trim().equals("")) {
				throw new CampoInvalidoException("nome do chef");
			}
			chef.setNome(chef.getNome().toUpperCase());
			
			if(chef.getSenha().trim().equals("")) {
				throw new CampoInvalidoException("senha");
			}
			
			if(!chef.getSenha().equals(senhaConfirmar)) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Senhas incompatíveis!"));
				return;
			}
			this.daoChef.alterar(chef);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Usuário alterar!"));
			limparCampos();
			voltarParaLogin();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "", e.toString()));
			return;
		}
	}
	
	
	public String excluir() {		
		
		chef = LoginBean.chefLogado;
		
		try {
			this.daoChef.remover(chef);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "", e.toString()));	
			return null;
		}
		limparCampos();
		return telaLogin;
	}
	
	public String voltarParaLogin() {
		return telaLogin;
	}
	
}