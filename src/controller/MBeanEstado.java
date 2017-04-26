package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.DualListModel;

import facade.CidadeFacade;
import facade.EstadoFacade;
import view.GrowlView;
import vo.Cidade;
import vo.Estado;

@ManagedBean
@ViewScoped
public class MBeanEstado implements MBean<Estado>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Estado estado;
	private List<Estado> estados;
	
	private DualListModel<Cidade> dualListModelCidade;
	private List<Cidade> cidadeSource;
	private List<Cidade> cidadeTarget;
	
	private EstadoFacade facade;
	
	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public DualListModel<Cidade> getDualListModelCidade() {
		return dualListModelCidade;
	}

	public void setDualListModelCidade(DualListModel<Cidade> dualListModelCidade) {
		this.dualListModelCidade = dualListModelCidade;
	}

		
	
	@Override
	public void salvar() {
		estado.setCidades(dualListModelCidade.getTarget());
		try {
			if (estado.getId() == null) {
			facade.inserir(estado);
			reset();
			GrowlView.sucesso("Salvo");
			}
			else{
				facade.alterar(estado);
				reset();
				GrowlView.sucesso("Editado");
			}
		} catch (Exception e) {
			GrowlView.saveMessage(e);
		}
		
	}

	private void reset() {
		estado = new Estado();
		init();
		cidadeTarget = new ArrayList<Cidade>();
		dualListModelCidade = new DualListModel<Cidade>(cidadeSource, cidadeTarget);
		
	}

	@Override
	public Estado getObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Estado> listar() {
		try {
			estados = facade.carregarTudo();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return estados;
	}

	@Override
	public String deletar() {
		try {
			facade.excluir(estado);
			GrowlView.sucesso("Deletado");
			reset();
		} catch (Exception e) {
			GrowlView.saveMessage(e);
		}
		return null;
	}

	public MBeanEstado() {
		estado = new Estado();
		estados = new ArrayList<Estado>();
		facade = new EstadoFacade();
		
		cidadeSource = new ArrayList<Cidade>();
		cidadeTarget = new ArrayList<Cidade>();
		dualListModelCidade = new DualListModel<Cidade>(cidadeSource, cidadeTarget);
	}
	
	@PostConstruct
	public void init(){
		try {
			estados = facade.carregarTudo();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		List<Cidade> cidades = null;
		try {
			cidades = new CidadeFacade().carregarTudo();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		cidadeSource = new ArrayList<Cidade>();
		if (cidades != null) {
			for (Cidade cidade : cidades) {
				if (cidade.getEstados().isEmpty()) {
					cidadeSource.add(cidade);
				}
			}
		}
		dualListModelCidade = new DualListModel<Cidade>(cidadeSource, cidadeTarget);
	}
	
	public void edicao(){
		dualListModelCidade.setTarget(estado.getCidades());
	}
	

}
