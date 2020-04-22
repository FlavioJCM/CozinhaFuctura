package entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "INGREDIENTE")
public class Ingrediente {
	@Id
	@Column(name="codigo")
	@GeneratedValue(generator = "S_INGREDIENTE")
	@SequenceGenerator(name = "S_INGREDIENTE", sequenceName = "S_INGREDIENTE", allocationSize = 1)
	private int codigo;
	
	@Column(name="descricao")
	private String descricao;	
	
	@ManyToOne
	@JoinColumn(name = "codigo_receita", referencedColumnName = "codigo", nullable = false)
	private Receita receita;
	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Receita getReceita() {
		return receita;
	}

	public void setReceita(Receita receita) {
		this.receita = receita;
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
		Ingrediente other = (Ingrediente) obj;
		if (codigo != other.codigo)
			return false;
		return true;
	}

	public Ingrediente(int codigo, String descricao, Receita receita) {
		super();
		this.codigo = codigo;
		this.descricao = descricao;
		this.receita = receita;
	}
	
	public Ingrediente(String descricao, Receita receita) {
		super();
		this.descricao = descricao;
		this.receita = receita;
	}
	
	public Ingrediente() {
		
	}

	@Override
	public String toString() {
		return "\n"+descricao;
	}
	
}
