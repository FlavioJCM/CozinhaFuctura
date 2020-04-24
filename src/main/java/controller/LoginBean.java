package controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import dao.ChefDAOImpl;
import dao.GenericoDAO;
import entidade.Chef;
import exceptions.NaoCadastradoException;
import exceptions.SenhaException;
import util.JpaUtil;

@ManagedBean(name = "LoginBean")
@RequestScoped
public class LoginBean {

	private static boolean isLogado = false;
	private Chef chef;
	private GenericoDAO<Chef> chefDAO;

	private static final String telaPrincipal = "/templatePrincipal.xhtml";
	private static final String telaLogin = "/paginas/TelaLogin.xhtml";
	private static final String telaCadastroChef = "/paginas/TelaCadastroChef.xhtml";
	
	public static Chef chefLogado;

	public Chef getChef() {
		return this.chef;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	@PostConstruct
	public void init() {
		this.chef = new Chef();
		this.chef.setCpf("111.111.111-11");
		try {
			//cria o dao de chef	
			this.chefDAO = new ChefDAOImpl(JpaUtil.getEntityManager());
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro! " + e.toString()));
			return;
		}
	}

	public String logar() {
		isLogado = false;
		
		try {
			if (!chef.getCpf().isEmpty()) {

				Chef x = this.chefDAO.pesquisarPorID(chef);

				if (x == null) {
					throw new NaoCadastradoException("chef");
				}
 
				if (!x.getSenha().equals(chef.getSenha())) {
					throw new SenhaException("senhaInvalida");
				}

				isLogado = true;
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Informe o CPF!"));
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "", e.toString()));
		}
		//se passar nos crit�rios, entra aqui e vai pra tela principal
		if (isLogado) {
			try {
				chefLogado = this.chefDAO.pesquisarPorID(chef);
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Aten��o! " + e.toString()));
				e.printStackTrace();
			}
			return telaPrincipal;
		} else {
			return null;
		}

	}
	
	//serve pra levar a tela do cadastro de chef, caso ele ainda n�o tenha cadastro
	public String irParaCadastroChef() {
		isLogado = false;
		return telaCadastroChef;
	}
	
	public String sairLogin() {
		isLogado = false;
		return telaLogin;
	}

}
