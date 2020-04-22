package entidade;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "RECEITA")
public class Receita {
	
	@Id
	@Column(name="codigo")
	@GeneratedValue(generator = "S_RECEITA")
	@SequenceGenerator(name = "S_RECEITA", sequenceName = "S_RECEITA", allocationSize = 1)
	private int codigo;
		
	@Column(name="nome")
	private String nome;	
	
	@OneToMany(mappedBy="receita", cascade= CascadeType.ALL)  
	private List<Ingrediente> ingredientes;
	
	@Column(name="modo_preparo")
	private String modoPreparo;
	
	@ManyToOne
	@JoinColumn(name = "cpf_chef", referencedColumnName = "cpf", nullable = false)	
	private Chef chef;

	@Column(name="preco")
	private Double preco;	
	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Ingrediente> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(List<Ingrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public String getModoPreparo() {
		return modoPreparo;
	}

	public void setModoPreparo(String modoPreparo) {
		this.modoPreparo = modoPreparo;
	}

	public Chef getChef() {
		return chef;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codigo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Receita other = (Receita) obj;
		if (codigo != other.codigo)
			return false;
		return true;
	}

	public Receita(int codigo, String nome, List<Ingrediente> ingredientes, String modoPreparo, Chef chef,
			Double preco) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.ingredientes = ingredientes;
		this.modoPreparo = modoPreparo;
		this.chef = chef;
		this.preco = preco;
	}
	
	public Receita(String nome, List<Ingrediente> ingredientes, String modoPreparo, Chef chef,
			Double preco) {
		super();
		this.nome = nome;
		this.ingredientes = ingredientes;
		this.modoPreparo = modoPreparo;
		this.chef = chef;
		this.preco = preco;
	}
	
	public Receita(String nome, String modoPreparo, Chef chef,
			Double preco) {
		super();
		this.nome = nome;
		this.modoPreparo = modoPreparo;
		this.chef = chef;
		this.preco = preco;
	}
	
	public Receita(int codigo) {
		super();
		this.codigo = codigo;
	}
	
	public Receita() {
		
	}

	@Override
	public String toString() {
		return chef + "\nReceita:\nCódigo: " + codigo + "\nNome: " + nome +"\nIngredientes: " + ingredientes + "\nModo de Preparo: "
				+ modoPreparo + "\nPreço: " + preco + "\n";
	}		
	
}
