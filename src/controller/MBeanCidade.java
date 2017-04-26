package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import facade.CidadeFacade;
import view.GrowlView;
import vo.Cidade;

@ManagedBean
@ViewScoped
public class MBeanCidade implements MBean<Cidade>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Cidade cidade;
	private List<Cidade> cidades;
	private CidadeFacade facade;

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	@Override
	public void salvar() {
		try {
			if (cidade.getId() == null) {
				facade.inserir(cidade);
				GrowlView.sucesso("Salvo");
			}
			else{
				facade.alterar(cidade);
				GrowlView.sucesso("Editado");
			}
			reset();
		} catch (Exception e) {
			GrowlView.saveMessage(e);
			e.printStackTrace();
		}
		
	}

	public void reset() {
		System.out.println("reset()");
		cidade = new Cidade();
		try {
			cidades = facade.carregarTudo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public Cidade getObject() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Cidade> listar() {
		try {
			cidades = facade.carregarTudo();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return cidades;
	}

	@Override
	public String deletar() {
		try {
			facade.excluir(cidade);
			GrowlView.sucesso("Deletado");
			
		} catch (Exception e) {
			GrowlView.saveMessage(e);
			e.printStackTrace();
		}
		reset();
		return null;
	}

	public MBeanCidade() {
		cidade = new Cidade();
		cidades = new ArrayList<Cidade>();
		facade = new CidadeFacade();
	}
	
	@PostConstruct
	public void init(){
		try {
			cidades = facade.carregarTudo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}
}
