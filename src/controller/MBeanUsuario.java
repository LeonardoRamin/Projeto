package controller;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;

import org.primefaces.component.inputmask.InputMask;

import facade.EstadoFacade;
import facade.UsuarioFacade;
import view.GrowlView;
import vo.Cidade;
import vo.Estado;
import vo.Telefone;
import vo.Usuario;

@ManagedBean(name="mBeanUsuario")
@ViewScoped
public class MBeanUsuario implements Serializable, MBean<Usuario>{

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	private List<Usuario> usuarios;
	private List<Telefone> telefones;
	private Telefone t;
	private List<Cidade> cidades;
	private Estado estado;
	
	private InputMask mask;
	private String label = "CPF";
	private boolean editar;
	
	private String senha;
	


	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios(){
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	public Telefone getT() {;
		return t;
	}

	public void setT(Telefone t) {
		this.t = t;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public InputMask getMask() {
		return mask;
	}

	public void setMask(InputMask mask) {
		this.mask = mask;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isEditar() {
		return editar;
	}

	public void setEditar(boolean editar) {
		this.editar = editar;
	}

	public MBeanUsuario() {
		usuario = new Usuario();
		usuarios = new ArrayList<Usuario>();
		telefones = new ArrayList<Telefone>();
		t = new Telefone();
		cidades = new ArrayList<Cidade>();
		estado = new Estado();
	}

	
	public void init(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if (session.getAttribute("USUARIO") != null){ 
			setUsuario((Usuario)session.getAttribute("USUARIO"));
			senha = usuario.getSenha();
		}

		if (usuario.getTipo().equals("Pessoa Juridica")) {
			label = "CNPJ";
		}
		else{
			label ="CPF";
		}
		System.out.println("usuario init() "+usuario);
	}
	
	@Override
	public void salvar() {	
		for (Telefone telefone : telefones) {
			telefone.setId(null);
		}
		usuario.setTelefones(telefones);
		System.out.println("Usuario mBeanUsuario "+usuario);
		try {
			if (usuario.getId() == null) {
				
				usuario.setAdm(false);
				new UsuarioFacade().inserir(usuario);
				GrowlView.sucesso("Cadastrado");
			}
			else{
				if (usuario.getSenha().equals("")) {
					usuario.setSenha(senha);
				}
				UsuarioFacade facade = new UsuarioFacade();
				System.out.println("Senha: "+usuario.getSenha());
				facade.alterar(usuario);
				GrowlView.sucesso("Editado");
			}
			reset();
		} catch (Exception e) {
			for (Telefone telefone : telefones) {
				telefone.setId(null);
			}
			GrowlView.saveMessage(e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

	private void reset() {
		usuario = new Usuario();
		t = new Telefone();
		telefones.clear();
		
	}

	@Override
	public Usuario getObject() {
		return usuario;
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Usuario> listar() {
		try {
			usuarios = new UsuarioFacade().carregarTudo();
		} catch (Exception e) {
			GrowlView.saveMessage(e);
			e.printStackTrace();
		}
		return usuarios;
		
	}

	public void addTelefone(){
		System.out.println("Um "+t.toString());
		telefones.add(t);
		System.out.println("Telefones "+Arrays.toString(telefones.toArray()));
		t = new Telefone();
	}
	
	public void remover(){
		if (telefones.remove(t)){
			System.out.println("Removeu "+ Arrays.toString(telefones.toArray()));
		}
		t = new Telefone();
	}
	
	public void removeTelefone(){
		telefones.clear();
		System.out.println("Apagados");
		GrowlView.criarMensagem(FacesMessage.SEVERITY_WARN, "Aviso", "Todos os telefones foram apagados");
	}
	
	

	public List<String> autoEmail(String txtemail){
		List<String> emails = new ArrayList<String>();
		if (txtemail.endsWith("@")) {
			emails.add(txtemail+"hotmail.com");
			emails.add(txtemail+"hotmail.com.br");
			emails.add(txtemail+"yahoo.com");
			emails.add(txtemail+"yahoo.com.br");
			emails.add(txtemail+"outlook.com");
			emails.add(txtemail+"outlook.com.br");
			emails.add(txtemail+"gmail.com");
			emails.add(txtemail+"gmail.com.br");
			
		}
		
		
		return emails;
	}
	
	public void maskara(ValueChangeEvent event){
		System.out.println(event.getNewValue());
		if (event.getNewValue().equals("Pessoa Juridica")) {
			mask.setMask("99.999.999/9999-99");
			mask.setPlaceholder("99.999.999/9999-99");
			label = "CNPJ";
		}
		else{
			mask.setMask("999.999.999.**");
			mask.setPlaceholder("999.999.999.**");
			label ="CPF";
		}
	}

	@Override
	public String deletar() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void edicao(){
		System.out.println("Editar perfil");
		telefones.addAll(usuario.getTelefones());
		setEditar(true);
	}
	
	public void valor(ValueChangeEvent event){
		System.out.println("classe "+event.getNewValue());
		if (!event.getNewValue().toString().equals("0")) {
			System.out.println("Entrou valor()");
			try {
				estado = new EstadoFacade().trazerUm(Long.valueOf((String) event.getNewValue()), estado);
				cidades = estado.getCidades();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			estado = new Estado();
			cidades = new ArrayList<Cidade>();
		}
		System.out.println("valorCidades() "+estado);
		
	}
}
