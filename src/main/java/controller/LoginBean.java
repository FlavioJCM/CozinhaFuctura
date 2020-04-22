package controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import dao.ChefDAOImpl;
import entidade.Chef;
import exceptions.NaoCadastradoException;
import exceptions.SenhaException;
import util.JpaUtil;

@ManagedBean(name = "LoginBean")
@RequestScoped
public class LoginBean {

	private static boolean isLogado = false;
	private Chef chef;

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
	}

	public String logar() {
		isLogado = false;
		
		//cria o dao de chef
		ChefDAOImpl chefDAO = null;
		try {
			chefDAO = new ChefDAOImpl(JpaUtil.getEntityManager());
		} catch (Exception e1) {
			System.out.println(e1.toString());
		}

		try {
			if (!chef.getCpf().isEmpty()) {

				Chef x = chefDAO.pesquisarPorID(chef);

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
		//se passar nos critérios, entra aqui e vai pra tela principal
		if (isLogado) {
			try {
				chefLogado = chefDAO.pesquisarPorID(chef);
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Atenção! " + e.toString()));
				e.printStackTrace();
			}
			return telaPrincipal;
		} else {
			return null;
		}

	}
	
	//serve pra levar a tela do cadastro de chef, caso ele ainda não tenha cadastro
	public String irParaCadastroChef() {
		isLogado = false;
		return telaCadastroChef;
	}
	
	public String sairLogin() {
		isLogado = false;
		return telaLogin;
	}

}
