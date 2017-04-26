package vo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Usuario {

	/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String email;
	private String login;
	private String senha;
	private String cadastroPessoal;
	private String tipo;
	private boolean adm;
	
	@ManyToOne
	private Cidade cidade;
	
	@ManyToMany
	@JoinTable(name="Telefones_do_usuario")
	private List<Telefone> telefones;
	
	@OneToMany(mappedBy="anunciante")
	private List<Anuncio> anuncios;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCadastroPessoal() {
		return cadastroPessoal;
	}

	public void setCadastroPessoal(String cadastroPessoal) {
		this.cadastroPessoal = cadastroPessoal;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public boolean isAdm() {
		return adm;
	}

	public void setAdm(boolean adm) {
		this.adm = adm;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public List<Telefone> getTelefones(){
		return telefones;
	}
	
	public Telefone getTelefone(int i) {
		return telefones.get(i);
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	public List<Anuncio> getAnuncios() {
		return anuncios;
	}

	public void setAnuncios(List<Anuncio> anuncios) {
		this.anuncios = anuncios;
	}

	public Usuario() {
		tipo = "Pessoa Fisica";
		cidade = new Cidade();
	}
	
	public Usuario(String nome, String email, String login, String senha, String pessoaFisica, String cpf){
		this.nome = nome;
		this.email = email;
		this.login = login;
		this.senha = senha;
		this.tipo = pessoaFisica;
		this.cadastroPessoal = cpf;
	}

	

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", email=" + email + ", login=" + login + ", senha=" + senha
				+ ", cadastroPessoal=" + cadastroPessoal + ", tipo=" + tipo + ", telefones=" + telefones + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cadastroPessoal == null) ? 0 : cadastroPessoal.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		result = prime * result + ((telefones == null) ? 0 : telefones.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		Usuario other = (Usuario) obj;
		if (cadastroPessoal == null) {
			if (other.cadastroPessoal != null)
				return false;
		} else if (!cadastroPessoal.equals(other.cadastroPessoal))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		if (telefones == null) {
			if (other.telefones != null)
				return false;
		} else if (!telefones.equals(other.telefones))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}

}
