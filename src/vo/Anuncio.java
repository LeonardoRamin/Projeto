package vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;

@Entity
public class Anuncio implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToMany
	@JoinTable(name="Fotos_do_Anuncio")
	private List<Foto> fotos;
	
	@Temporal(TemporalType.DATE)
	private Date data;
	private String tipoDeAnuncio;//Setar com: Peça(p) ou Veículo(v)
	@Column(length=1000)
	private String descricao;//v
	private String cor;//v
	private String combustivel;//v
	private String motor;//v
	private int portas;//v
	private int kmRodados;//v

	@ManyToOne
	@ForeignKey(name="id_veiculo")
	private Veiculo veiculo;//v
	@ManyToMany
	@JoinTable(name="Acessorios_do_Anuncio")
	private List<Acessorio> acessorios;//v
	@ManyToOne
	@ForeignKey(name="id_peca")
	private Peca peca;//p
	@ManyToOne
	@ForeignKey(name="id_anunciante")
	private Usuario anunciante;
	private Float valorFinal;

	private int curtidas;//
	private int visitas;//

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Foto> getFoto() {
		return fotos;
	}

	public void setFoto(List<Foto> fotos) {
		this.fotos = fotos;
	}
	
	public String getPrimeiraFoto(){
		return fotos.get(0).getNome();
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getTipoDeAnuncio() {
		return tipoDeAnuncio;
	}

	public void setTipoDeAnuncio(String tipoDeAnuncio) {
		this.tipoDeAnuncio = tipoDeAnuncio;
	}

	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getCombustivel() {
		return combustivel;
	}

	public void setCombustivel(String combustivel) {
		this.combustivel = combustivel;
	}

	public String getMotor() {
		return motor;
	}

	public void setMotor(String motor) {
		this.motor = motor;
	}

	public int getPortas() {
		return portas;
	}

	public void setPortas(int portas) {
		this.portas = portas;
	}

	public int getKmRodados() {
		return kmRodados;
	}

	public void setKmRodados(int kmRodados) {
		this.kmRodados = kmRodados;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public List<Acessorio> getAcessorios() {
		return acessorios;
	}

	public void setAcessorios(List<Acessorio> acessorios) {
		this.acessorios = acessorios;
	}

	public Peca getPeca() {
		return peca;
	}

	public void setPeca(Peca peca) {
		this.peca = peca;
	}

	public Usuario getAnunciante() {
		return anunciante;
	}

	public void setAnunciante(Usuario anunciante) {
		this.anunciante = anunciante;
	}

	public Float getValorFinal() {
		return valorFinal;
	}

	public void setValorFinal(Float valorFinal) {
		this.valorFinal = valorFinal;
	}

	public int getCurtidas() {
		return curtidas;
	}

	public void setCurtidas(int curtidas) {
		this.curtidas = curtidas;
	}

	public int getVisitas() {
		return visitas;
	}

	public void setVisitas(int vistas) {
		this.visitas = vistas;
	}

	
	public Anuncio() {
		portas = 2;
		visitas = 0;
		curtidas = 0;
	}
	
	public Anuncio(String nomeFoto){
		System.out.println("Construtor 2");
		Foto foto = new Foto(nomeFoto);
		List<Foto> imgs = new ArrayList<Foto>();
		imgs.add(foto);
		setFoto(imgs);
	}
	


	@Override
	public String toString() {
		return "Anuncio [id=" + id + ", fotos=" + fotos + ", data=" + data + ", tipoDeAnuncio=" + tipoDeAnuncio
				+ ", descricao=" + descricao + ", cor=" + cor + ", combustivel=" + combustivel + ", motor=" + motor
				+ ", portas=" + portas + ", kmRodados=" + kmRodados + ", veiculo=" + veiculo + ", acessorios="
				+ acessorios + ", peca=" + peca + ", anunciante=" + anunciante + ", valorFinal=" + valorFinal
				+ ", curtidas=" + curtidas + ", visitas=" + visitas + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acessorios == null) ? 0 : acessorios.hashCode());
		result = prime * result + ((anunciante == null) ? 0 : anunciante.hashCode());
		result = prime * result + ((combustivel == null) ? 0 : combustivel.hashCode());
		result = prime * result + ((cor == null) ? 0 : cor.hashCode());
		result = prime * result + curtidas;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((fotos == null) ? 0 : fotos.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + kmRodados;
		result = prime * result + ((motor == null) ? 0 : motor.hashCode());
		result = prime * result + ((peca == null) ? 0 : peca.hashCode());
		result = prime * result + portas;
		result = prime * result + ((tipoDeAnuncio == null) ? 0 : tipoDeAnuncio.hashCode());
		result = prime * result + Float.floatToIntBits(valorFinal);
		result = prime * result + ((veiculo == null) ? 0 : veiculo.hashCode());
		result = prime * result + visitas;
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
		Anuncio other = (Anuncio) obj;
		if (acessorios == null) {
			if (other.acessorios != null)
				return false;
		} else if (!acessorios.equals(other.acessorios))
			return false;
		if (anunciante == null) {
			if (other.anunciante != null)
				return false;
		} else if (!anunciante.equals(other.anunciante))
			return false;
		if (combustivel == null) {
			if (other.combustivel != null)
				return false;
		} else if (!combustivel.equals(other.combustivel))
			return false;
		if (cor == null) {
			if (other.cor != null)
				return false;
		} else if (!cor.equals(other.cor))
			return false;
		if (curtidas != other.curtidas)
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (fotos == null) {
			if (other.fotos != null)
				return false;
		} else if (!fotos.equals(other.fotos))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (kmRodados != other.kmRodados)
			return false;
		if (motor == null) {
			if (other.motor != null)
				return false;
		} else if (!motor.equals(other.motor))
			return false;
		if (peca == null) {
			if (other.peca != null)
				return false;
		} else if (!peca.equals(other.peca))
			return false;
		if (portas != other.portas)
			return false;
		if (tipoDeAnuncio == null) {
			if (other.tipoDeAnuncio != null)
				return false;
		} else if (!tipoDeAnuncio.equals(other.tipoDeAnuncio))
			return false;
		if (Float.floatToIntBits(valorFinal) != Float.floatToIntBits(other.valorFinal))
			return false;
		if (veiculo == null) {
			if (other.veiculo != null)
				return false;
		} else if (!veiculo.equals(other.veiculo))
			return false;
		if (visitas != other.visitas)
			return false;
		return true;
	}
}
