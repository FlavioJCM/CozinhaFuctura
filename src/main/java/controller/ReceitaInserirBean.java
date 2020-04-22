package controller;

import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import dao.ReceitaDAOImpl;
import entidade.Chef;
import entidade.Ingrediente;
import entidade.Receita;
import exceptions.CampoInvalidoException;
import util.JpaUtil;

@ManagedBean(name = "ReceitaInserirBean")
@SessionScoped
public class ReceitaInserirBean {
	private Receita receita;
	private Chef chef;
	private ReceitaDAOImpl receitaDAO;
	private Ingrediente ingrediente;	
	
	public static Chef chefLogado;

	@PostConstruct
	public void init() {
		try {
			this.receitaDAO = new ReceitaDAOImpl(JpaUtil.getEntityManager());
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro! " + e.toString()));
			return;
		}
		
		limparCampos();
	}
	
	//cria uma receita
	public void salvar() {
		
		try {

			if (receita.getNome().trim().equals("")) {
				throw new CampoInvalidoException("nome da receita");
			}

			if (receita.getPreco() == null) {
				throw new CampoInvalidoException("preço");
			}

			if (receita.getModoPreparo().trim().equals("")) {
				throw new CampoInvalidoException("modo de preparo");
			}

			if (receita.getIngredientes().isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Insira ingredientes na lista!"));
				return;
			}

			receita.setChef(chef);
			receita.setNome(receita.getNome().toUpperCase());
			receita.setModoPreparo(receita.getModoPreparo().toUpperCase());
			receitaDAO.inserir(receita);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Receita inserida!"));
			limparCampos();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "", e.toString()));
			return;
		}
	}	
	
	//exclui um ingrediente da lista da receita que está sendo criada
	public void excluirIngrediente(Ingrediente ingrediente) {
		try {
			for (int i = 0; i < receita.getIngredientes().size(); i++) {
				if (receita.getIngredientes().get(i).getDescricao().equals(ingrediente.getDescricao())) {
					receita.getIngredientes().remove(i);
				}
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro! " + e.toString()));
			return;
		}
	}
	
	//adiciona ingrediente na lista de ingredientes da receita que será criada
	public void adicionarIngredientes() {

		if (this.ingrediente.getDescricao().trim().equals("")) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Informe o Ingrediente!"));
			return;
		}

		if (!this.existeIngrediente(ingrediente)) {

			Ingrediente ingredienteNovo = new Ingrediente();
			ingredienteNovo.setDescricao(this.ingrediente.getDescricao().toUpperCase());
			ingredienteNovo.setReceita(this.receita);

			this.receita.getIngredientes().add(ingredienteNovo);
			this.ingrediente = new Ingrediente();
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Ingrediente já existe !!!"));
			return;
		}

	}
	
	//verifica se o ingrediente existe na lista de ingredientes da receita que será criada
	private boolean existeIngrediente(Ingrediente ingrediente) {
		boolean retorno = false;

		for (Ingrediente ingLista : this.receita.getIngredientes()) {
			if (ingLista.getDescricao().equals(ingrediente.getDescricao().toUpperCase())) {
				retorno = true;
			}
		}

		return retorno;
	}

	public void limparCampos() {
		this.receita = new Receita();
		this.chef = LoginBean.chefLogado;
		this.receita.setChef(chef);
		this.receita.setIngredientes(new ArrayList<Ingrediente>());
		this.ingrediente = new Ingrediente();
	}

	public Receita getReceita() {
		return receita;
	}

	public void setReceita(Receita receita) {
		this.receita = receita;
	}

	public Chef getChef() {
		return chef;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public Ingrediente getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
	}
	

}
