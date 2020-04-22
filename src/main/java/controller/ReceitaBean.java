package controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import dao.ReceitaDAOImpl;
import entidade.Chef;
import entidade.Ingrediente;
import entidade.Receita;
import exceptions.CampoInvalidoException;
import util.JpaUtil;

@ManagedBean(name = "ReceitaBean")
@SessionScoped
public class ReceitaBean {
	//essa receita serve para pesquisa
	private Receita receita;
	//essa receita serve para inserir
	private Receita receitaInserir;
	private Chef chef;
	private List<Receita> listaReceitas;
	private ReceitaDAOImpl receitaDAO;
	private Ingrediente ingrediente;
	private Ingrediente ingredienteEditar;
	private Receita receitaEditar;	
	private static int codigoNaoEditavel;
	private int codigoReceitaEditar;	
	
	private static final String telaReceita = "/paginas/TelaMinhasReceitas.xhtml";
	private static final String telaReceitaEditar = "/paginas/TelaMinhasReceitasEditar.xhtml";
	private static final String telaReceitaDetalhe = "/paginas/TelaDetalheReceita.xhtml";
	private static final String telaFeedReceitas = "/paginas/TelaFeedReceita.xhtml";
	
	private List<Ingrediente> ingredientesRemovidos;	
	
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
		this.chef = LoginBean.chefLogado;
		limparCampos();
		pesquisa();
	}
	
	//prepara cria o objeto receita para o editar na tela de receita
	public String prepararEditar() {	
		
		Receita r = new Receita();
		r.setCodigo(codigoReceitaEditar);
		
		try {
			codigoNaoEditavel = codigoReceitaEditar;
			receitaEditar = receitaDAO.pesquisarPorID(r);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", e.getMessage()));
		}
		
		return telaReceitaEditar;
	}
	
	//pesquisa personalizada das receitas de quem logou
	public void pesquisa() {
		chefLogado = LoginBean.chefLogado;
		if (receita.getNome() != null && !receita.getNome().trim().equals("")) {
			receita.setNome(receita.getNome().toUpperCase());
		}
		try {
			this.listaReceitas = receitaDAO.listarPersonalizado(receita);
			System.out.println();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro! " + e.toString()));
			return;
		}
	}
	
	//excluir receitas
	public void excluir(Receita receita) {
		try {
			receitaDAO.remover(receita);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro! " + e.toString()));
			return;
		}
		limparCampos();
		pesquisa();
	}
	
	//adicionar ingredientes ao criar uma receita
	public void adicionarIngredientes() {
		
		if (this.ingrediente.getDescricao().equals("")) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Informe o Ingrediente!"));
			return;
		}

		if (!this.existeIngrediente(ingrediente)) {

			Ingrediente ingredienteNovo = new Ingrediente();
			ingredienteNovo.setDescricao(this.ingrediente.getDescricao().toUpperCase());
			ingredienteNovo.setReceita(this.receitaInserir);

			this.receitaInserir.getIngredientes().add(ingredienteNovo);

			this.ingrediente = new Ingrediente();

		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Ingrediente já existe !!!"));
		}
	}
	
	//verifica se já existe o ingrediente na lista de ingredientes
	private boolean existeIngrediente(Ingrediente ingrediente) {
		boolean retorno = false;

		for (Ingrediente ingLista : this.receitaInserir.getIngredientes()) {
			if (ingLista.getDescricao().equals(ingrediente.getDescricao())) {
				retorno = true;
			}
		}

		return retorno;
	}
	
	//adiciona ingredientes na lista de receita que está sendo editada
	public void adicionarIngredienteEditar() {

		if (this.ingredienteEditar.getDescricao().equals("")) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Informe o Ingrediente!"));
			return;
		}

		if (!this.existeIngredienteEditar(ingredienteEditar)) {

			Ingrediente ingredienteNovo = new Ingrediente();
			ingredienteNovo.setDescricao(this.ingredienteEditar.getDescricao().toUpperCase());
			ingredienteNovo.setReceita(this.receitaEditar);

			this.receitaEditar.getIngredientes().add(ingredienteNovo);

			this.ingredienteEditar = new Ingrediente();

		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Ingrediente já existe !!!"));
		}

	}
	
	//verifica se existe o ingrediente na lista da receita que está sendo editada
	private boolean existeIngredienteEditar(Ingrediente ingrediente) {
		boolean retorno = false;

		for (Ingrediente ingLista : this.receitaEditar.getIngredientes()) {
			if (ingLista.getDescricao().equals(ingrediente.getDescricao().toUpperCase())) {
				retorno = true;
			}
		}

		return retorno;
	}
	
	//exclui o ingrediente da lista de ingredientes da receita que está sendo editada
	public void excluirIngredienteEditar(Ingrediente ingrediente) {
		try {
			for (int i = 0; i < receitaEditar.getIngredientes().size(); i++) {
				if (receitaEditar.getIngredientes().get(i).getDescricao().equals(ingrediente.getDescricao())) {
					ingredientesRemovidos.add(ingrediente);
					receitaEditar.getIngredientes().remove(i);					
				}
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro! " + e.toString()));
			return;
		}
	}
	
	//adiciona o ingrediente na lista de ingredientes da receita que está sendo editada
	public void adicionarIngredientesEditar() {

		if (this.ingredienteEditar.getDescricao().trim().equals("")) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Informe o Ingrediente!"));
			return;
		}

		if (!this.existeIngrediente(ingredienteEditar)) {

			Ingrediente ingredienteNovo = new Ingrediente();
			ingredienteNovo.setDescricao(this.ingredienteEditar.getDescricao().toUpperCase());
			ingredienteNovo.setReceita(this.receitaEditar);

			this.receitaEditar.getIngredientes().add(ingredienteNovo);
			this.ingredienteEditar = new Ingrediente();
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Ingrediente já existe !!!"));
			return;
		}

	}
	
	//edita a receita
	public void editar() {
		try {

			if (receitaEditar.getNome().trim().equals("")) {
				throw new CampoInvalidoException("nome da receita");
			}

			if (receitaEditar.getPreco() == null) {
				throw new CampoInvalidoException("preço");
			}

			if (receitaEditar.getModoPreparo().trim().equals("")) {
				throw new CampoInvalidoException("modo de preparo");
			}

			if (receitaEditar.getIngredientes().isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Insira ingredientes na lista!"));
				return;
			}

			receitaEditar.setChef(chef);
			receitaEditar.setNome(receitaEditar.getNome().toUpperCase());
			receitaEditar.setModoPreparo(receitaEditar.getModoPreparo().toUpperCase());
			receitaDAO.alterar(receitaEditar);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Receita alterada!"));
			receitaDAO.removerIngrediente(ingredientesRemovidos);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "", e.toString()));
			return;
		}
	}
	
	//cria a receita pra ser visualizada na tela de detalhe da receita
	public String prepararDetalhes() {	
		
		Receita r = new Receita();
		r.setCodigo(codigoReceitaEditar);
		
		try {
			codigoNaoEditavel = codigoReceitaEditar;
			receitaEditar = receitaDAO.pesquisarPorID(r);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", e.getMessage()));
		}
		
		return telaReceitaDetalhe;
	}
	
	public String voltarParaTelaMinhasReceitas() {
		limparCampos();		
		return telaReceita;
	}
	
	public String voltarParaTelaFeedReceitas() {
		limparCampos();		
		return telaFeedReceitas;
	}	

	public void limparCampos() {
		this.receita = new Receita();		
		this.receita.setChef(chef);
		this.receita.setIngredientes(new ArrayList<Ingrediente>());
		this.receitaInserir = new Receita();
		this.receitaInserir.setChef(chef);
		this.receitaInserir.setIngredientes(new ArrayList<Ingrediente>());
		this.receitaEditar = new Receita();
		this.receitaEditar.setChef(chef);
		this.receitaEditar.setIngredientes(new ArrayList<Ingrediente>());		
		this.listaReceitas = new ArrayList<Receita>();
		this.ingrediente = new Ingrediente();
		this.ingredienteEditar = new Ingrediente();
		this.ingredientesRemovidos = new ArrayList<Ingrediente>();
		pesquisa();
	}
	public int getCodigoReceitaEditar() {
		return codigoReceitaEditar;
	}

	public void setCodigoReceitaEditar(int codigoReceitaEditar) {
		this.codigoReceitaEditar = codigoReceitaEditar;
	}
	
	public Receita getReceita() {
		return receita;
	}

	public void setReceita(Receita receita) {
		this.receita = receita;
	}
	
	public Receita getReceitaInserir() {
		return receitaInserir;
	}

	public void setReceitaInserir(Receita receitaInserir) {
		this.receitaInserir = receitaInserir;
	}

	public Chef getChef() {
		return chef;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public List<Receita> getListaReceitas() {
		return listaReceitas;
	}

	public void setListaReceitas(List<Receita> listaReceitas) {
		this.listaReceitas = listaReceitas;
	}

	public Ingrediente getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
	}

	public Ingrediente getIngredienteEditar() {
		return ingredienteEditar;
	}

	public void setIngredienteEditar(Ingrediente ingredienteEditar) {
		this.ingredienteEditar = ingredienteEditar;
	}

	public Receita getReceitaEditar() {
		return receitaEditar;
	}

	public void setReceitaEditar(Receita receitaEditar) {
		this.receitaEditar = receitaEditar;
	}

	public static int getCodigoNaoEditavel() {
		return codigoNaoEditavel;
	}

	public static void setCodigoNaoEditavel(int codigoNaoEditavel) {
		ReceitaBean.codigoNaoEditavel = codigoNaoEditavel;
	}
	
	public List<Ingrediente> getIngredientesRemovidos() {
		return ingredientesRemovidos;
	}

	public void setIngredientesRemovidos(List<Ingrediente> ingredientesRemovidos) {
		this.ingredientesRemovidos = ingredientesRemovidos;
	}
}
