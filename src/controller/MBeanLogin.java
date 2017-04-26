package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


import dao.Conexao;
import facade.AnuncioFacade;
import facade.UsuarioFacade;
import view.GrowlView;
import vo.Anuncio;
import vo.Usuario;

@ManagedBean
@SessionScoped
public class MBeanLogin implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Usuario usuario;
	private Anuncio anuncio;
	private List<Anuncio> filtroAnuncios;
	
	
	private boolean logado;
	private boolean admin;
	private boolean tipo;
	private boolean vazio;
	private boolean vaziop;
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Anuncio getAnuncio() {
		return anuncio;
	}
	public void setAnuncio(Anuncio anuncio) {
		this.anuncio = anuncio;
	}

	
	public List<Anuncio> getFiltroAnuncios() {
		return filtroAnuncios;
	}
	public void setFiltroAnuncios(List<Anuncio> filtroAnuncios) {
		this.filtroAnuncios = filtroAnuncios;
	}
	public boolean isLogado() {
		return logado;
	}
	public void setLogado(boolean logado) {
		this.logado = logado;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public boolean isTipo() {
		return tipo;
	}
	public void setTipo(boolean tipo) {
		this.tipo = tipo;
	}
	
	public boolean isVazio() {
		return vazio;
	}
	public void setVazio(boolean vazio) {
		this.vazio = vazio;
	}
	public boolean isVaziop() {
		return vaziop;
	}
	public void setVaziop(boolean vaziop) {
		this.vaziop = vaziop;
	}
	@PostConstruct
	public void init(){
		Conexao.getEntityManager();
	}
	
	public String validar() throws Exception{
		UsuarioFacade ff = new UsuarioFacade();
		FacesContext fc = FacesContext.getCurrentInstance();
		if (usuario.getLogin().equals("Admin") && usuario.getSenha().equals("admin")) {
			setUsuario(new Usuario("Administrador", null, usuario.getLogin(), null, "Pessoa Fisica", null));
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("USUARIO", this.usuario);
			System.out.println("Administrador "+session.getAttribute("USUARIO"));
			logado = true;
			admin = true;
			return "index.xhtml?faces-redirect=true";
		}
		else if (ff.autenticar(this.usuario)){
				HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
				session.setAttribute("USUARIO", this.usuario);
				System.out.println("sucesso "+session.getAttribute("USUARIO"));
				logado = true;
				return "index.xhtml?faces-redirect=true";
		}
		else{
			GrowlView.criarMensagem(FacesMessage.SEVERITY_ERROR, "Erro ao efetuar login", "Login ou senha incorreto!");
		}
		System.out.println("login");
		return "login";
	}

	public MBeanLogin() {
		usuario = new Usuario();
//		anuncio = new Anuncio();
		filtroAnuncios = new ArrayList<Anuncio>();
		
		logado = false;
		admin = false;
		tipo = false;
		vazio = false;
		vaziop = false;
	}
	
	public String logout(){
		System.out.println("Saiu");
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.getExternalContext().invalidateSession(); //Logout da sessão
		logado = false;
		admin = false;
		return "inicio";
	}
	

	
	public void tipoAnuncio(){
		System.out.println("tipoAnuncio() "+anuncio);
		
		if (anuncio.getTipoDeAnuncio().equals("Peça")) {
			tipo = true;
		}
		else{
			tipo = false;
		}
		System.out.println("Tipo "+tipo);
	}
	
	public void vazio(){
		anuncio = null;
		System.out.println("vazio() "+anuncio);
	}
	
	public void deletar(){
		try {
			new AnuncioFacade().excluir(anuncio);
			GrowlView.sucesso("Deletado");
		} catch (Exception e) {
			GrowlView.saveMessage(e);
			e.printStackTrace();
		}
		System.out.println("deletar()");
	}
	
	public String pagina(){
		if (anuncio.getTipoDeAnuncio().equals("Peça")) {
			return "AnunciarPeca.xhtml?faces-redirect=true";
		}
		else{
			return "AnunciarVeiculo.xhtml?faces-redirect=true";
		}
	}
	
	public String verAnuncios(){
		System.out.println("Anuncio selecionado "+anuncio);
		if (anuncio != null) {
			System.out.println("Entrou no if anuncio veiculo");
			if (anuncio.getTipoDeAnuncio().equals("Peça")) {
				return "vercarro";
			}
			else{
				return "verpeca";
			}
		}
		return null;
		
	}
	
	
}
