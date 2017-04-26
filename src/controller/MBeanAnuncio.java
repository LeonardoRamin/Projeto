package controller;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.UploadedFile;

import bo.EnviarEmail;
import converter.ConvertDate;
import facade.AcessorioFacade;
import facade.AnuncioFacade;
import facade.VeiculoFacade;
import view.GrowlView;
import vo.Acessorio;
import vo.Anuncio;
import vo.Foto;
import vo.Telefone;
import vo.Veiculo;

@ManagedBean
@ViewScoped
public class MBeanAnuncio implements MBean<Anuncio>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Bean
	private Anuncio anuncio;
	private List<Anuncio> anuncios;
	private DualListModel<Acessorio> dualListModelAcessorio;
	
	//Salvar arquivos
	private List<byte[]> conteudos;
	private List<Foto> imagens;
	private List<File> files;
	
	//Booleanos
	private boolean fechado;
	private boolean editar;
	
	//Email
	private String assunto;
	private String mensagem;
	
	@ManagedProperty(value="#{mBeanLogin}")
	private MBeanLogin mBeanLogin;
	private List<Acessorio> acessorioSource;
	private List<Acessorio> acessorioTarget;
	
	public Anuncio getAnuncio() {
		return anuncio;
	}
	public void setAnuncio(Anuncio anuncio) {
		this.anuncio = anuncio;
	}
	public List<Anuncio> getAnuncios(){
		return anuncios;
	}
	public void setAnuncios(List<Anuncio> anuncios) {
		this.anuncios = anuncios;
	}
	
	public DualListModel<Acessorio> getDualListModelAcessorio() {
		return dualListModelAcessorio;
	}
	public void setDualListModelAcessorio(DualListModel<Acessorio> dualListModelAcessorio) {
		this.dualListModelAcessorio = dualListModelAcessorio;
	}
	
	public MBeanLogin getmBeanLogin() {
		return mBeanLogin;
	}
	public void setmBeanLogin(MBeanLogin mBeanLogin) {
		this.mBeanLogin = mBeanLogin;
	}
	public boolean isFechado() {
		return fechado;
	}
	public void setFechado(boolean fechado) {
		this.fechado = fechado;
	}
	
	public boolean isEditar() {
		return editar;
	}
	public void setEditar(boolean editar) {
		this.editar = editar;
	}
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public MBeanAnuncio() {
		anuncio = new Anuncio();
		anuncios = new ArrayList<Anuncio>();
		
		acessorioSource = new ArrayList<Acessorio>();
		acessorioTarget = new ArrayList<Acessorio>();
		dualListModelAcessorio = new DualListModel<Acessorio>();
		fechado = true;
		editar = false;
		
		conteudos = new ArrayList<byte[]>();
		imagens = new ArrayList<Foto>();
		files = new ArrayList<File>();
	}
	
	@Override
	public void salvar() {
		String date = ConvertDate.DatetoString(new Date());
		
		
		System.out.println("data "+anuncio.getData());
		System.out.println("String data "+date);
		
		if (anuncio.getPeca() != null && anuncio.getVeiculo() == null) {
			anuncio.setTipoDeAnuncio("Peça");
		}
		else{
			anuncio.setTipoDeAnuncio("Veículo");
			System.out.println("v "+anuncio.getVeiculo().toString());
			if (dualListModelAcessorio.getTarget().size() >= 0) {
				anuncio.setAcessorios(dualListModelAcessorio.getTarget());
				System.out.println("acessorio target "+ Arrays.toString(anuncio.getAcessorios().toArray()));
			}
		}

		try {
			if (anuncio.getId() == null) {
				anuncio.setFoto(imagens);
				System.out.println("Entrou no inserir");
				anuncio.setData(ConvertDate.StringtoDate(date));
				new AnuncioFacade().inserir(anuncio);
				GrowlView.sucesso("Anunciado");
			}
			else{
				System.out.println("Entrou no alterar");
				AnuncioFacade af = new AnuncioFacade();
				af.setTamanho(anuncio.getFoto().size());
				anuncio.setFoto(imagens);
				af.alterar(anuncio);
				GrowlView.sucesso("Editado");
			}
			this.salvarArquivo(files, conteudos);
			reset();
			
		} catch (Exception e) {
			GrowlView.saveMessage(e);
			e.printStackTrace();
		}
		System.out.println("Cor "+anuncio.getCor());
	}
	
	
	@Override
	public Anuncio getObject() {
		try {
			anuncio = new AnuncioFacade().trazerUm(anuncio.getId(), anuncio);
		} catch (Exception e) {
			GrowlView.saveMessage(e);
			e.printStackTrace();
		}
		return anuncio;
		
	}
	
	
	@Override
	public List<Anuncio> listar() {
		System.out.println("Listar");
		try {
			anuncios = new AnuncioFacade().carregarTudo();
		} catch (Exception e) {
			GrowlView.saveMessage(e);
		}
		System.out.println(Arrays.toString(anuncios.toArray()));
		return anuncios;
		
	}
	
	@Override
	public String deletar() {
		System.out.println("deletado");
		try {
			new AnuncioFacade().excluir(anuncio);
			GrowlView.sucesso("Deletado");
			return "PAVeiculos.xhtml?faces-redirect=true";
		} catch (Exception e) {
			GrowlView.saveMessage(e);
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Veiculo> carregarVeiculos(){
		System.out.println("carregarVeiculos()");
		List<Veiculo> veiculos = new ArrayList<Veiculo>();
			for (Anuncio anuncio : anuncios) {
				veiculos.add(anuncio.getVeiculo());
			}
		return veiculos;
	}
	
	public String[] getCores(){
		String[] cores = new String[]{"Amarelo", "Azul", "Branco", "Cinza", "Prata", "Preto", "Roxo", "Verde", "Vermelho"};
		return cores;
	}
	
	public String[] getMotores(){
		String[] motores = new String[]{"Inline 4", "Inline 6", "Flat 4", "Flat 6", 
				"V-4", "V-6", "V-8", "V-10", "V-12", "V-14", "V-16",
				"straight eight" , "rotary/wankel"};
		return motores;
	}
	
	
	
	@PostConstruct
	public void init(){
		System.out.println("init "+anuncio.toString());
//		try {
//			acessorioSource = new AcessorioFacade().carregarTudo();
//		} catch (Exception e) {
//			GrowlView.saveMessage(e);
//		}
		dualListModelAcessorio = new DualListModel<Acessorio>(acessorioSource, acessorioTarget);
		
		
	}
	
	public void carregarArquivo(FileUploadEvent event) throws Exception{ 
		
		UploadedFile arquivo = event.getFile(); // pego o nome do arquivo
		System.out.println("arquivo "+arquivo.getFileName());
		String nome = arquivo.getFileName();
		//Data
		String data = "yyyy-MM-dd";
        String hora = "HH-mm-ss-SSS";
        String data1, hora1;
        Date agora = new Date();
        SimpleDateFormat formata = new SimpleDateFormat(data);
        data1 = formata.format(agora);
        formata = new SimpleDateFormat(hora);
        hora1 = formata.format(agora);
        //extensão
        int posicaoPonto = nome.lastIndexOf(".");
        int tamanhoString = nome.length();
        nome = nome.substring(posicaoPonto + 1, tamanhoString);  
        String nomeArquivo =data1+ "-" + hora1 + "." + nome;
        String pasta = null;
        if (anuncio.getVeiculo() != null) {
			pasta = "Veiculo";
		}
        else {
			pasta = "Peca";
		}
        
		String dir = "D:/workspace/";//diretório fixo- mudar quando estiver com o projeto em outro computador
		java.io.File file = new java.io.File(dir+"PI_IV/WebContent/resources/Imagens/Anuncio/"+pasta+"/"+nomeArquivo); // diretorio o qual vou salvar o arquivo do upload, equivale ao nome completamente qualificado  

//		InputStream is = arquivo.getInputstream(); // lê o arquivo
		//cria var com o arquivo
		Foto foto = new Foto(file.getName());
		files.add(file);
		imagens.add(foto);// add na list
		System.out.println("Nome: "+file.getName()+" Path: "+file.getPath()+ " AbsolutPath: "+file.getAbsolutePath());


		conteudos.add(arquivo.getContents());  // daqui pra baixo é somente operações de IO. 
		System.out.println("Salvando na pasta "+ Arrays.toString(imagens.toArray()));
		System.out.println("Conteudos "+Arrays.toString(conteudos.toArray()));


	}
	
	private void salvarArquivo(List<File> arquivos, List<byte[]> conteudos) throws IOException {
		System.out.println("salvarArquivo() "+arquivos.size()+", "+conteudos.size());
		if (arquivos.size() > 0) {
			FileOutputStream fos = null;
			for (int i = 0; i < arquivos.size(); i++) {
				try {
					fos = new FileOutputStream(arquivos.get(i));
					fos.write(conteudos.get(i));
					fos.close();
				} catch (FileNotFoundException e) {
					GrowlView.saveMessage(e);
					e.printStackTrace();
				}
			}		
		}

	}
	
	public void habilitar(ValueChangeEvent event){
		System.out.println("change "+event.getNewValue());
		System.out.println("varc "+fechado);
		fechado = false;
	}
	
	public void habilitarVeiculo(ValueChangeEvent event){
		System.out.println("change "+event.getNewValue());
			Veiculo v = new Veiculo();
			if (event != null && event.getNewValue() != null) {
				try {
					v = new VeiculoFacade().trazerUm(Long.valueOf((String) event.getNewValue()), new Veiculo());
					anuncio.setVeiculo(v);
					System.out.println("Veículo "+anuncio.getVeiculo());
					fechado = false;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else{
				anuncio.setVeiculo(null);
				fechado = true;
			}
			
		System.out.println("varc "+fechado);
		
	}
	
	private void reset() throws Exception {
		System.out.println("rest()");
		anuncio = new Anuncio();
		anuncio.setAnunciante(mBeanLogin.getUsuario());
		anuncios = new ArrayList<Anuncio>();
		
		acessorioTarget = new ArrayList<Acessorio>();
		dualListModelAcessorio = new DualListModel<Acessorio>();
		fechado = true;
		editar = false;
		
		conteudos = new ArrayList<byte[]>();
		imagens = new ArrayList<Foto>();
		files = new ArrayList<File>();
		acessorioSource = new AcessorioFacade().carregarTudo();
		if (acessorioSource == null) {
			acessorioSource = new ArrayList<Acessorio>();
		}
		dualListModelAcessorio = new DualListModel<Acessorio>(acessorioSource, acessorioTarget);
	}
	
	public void edicao(){
		System.out.println("edicao()");
		if (mBeanLogin.getAnuncio() != null) {
			System.out.println("Edição");
			imagens = new ArrayList<Foto>();
			imagens.addAll(mBeanLogin.getAnuncio().getFoto());
			fechado = false;
			editar = true;
			anuncio = mBeanLogin.getAnuncio();
			files = new ArrayList<File>();
			conteudos = new ArrayList<byte[]>();
			mBeanLogin.setAnuncio(null);
			if (anuncio.getPeca() == null) {
				try {
					pickList();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			System.out.println("Carregou "+anuncio);
		}
		else{
			List<Acessorio> ac = null;
			try {
				ac = new AcessorioFacade().carregarTudo();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			if (ac != null) {
				acessorioSource = ac;
			}
			anuncio.setAnunciante(mBeanLogin.getUsuario());
		}
		
		if (anuncio.getPeca() == null) {
			System.out.println("AC "+Arrays.toString(acessorioSource.toArray()));
			dualListModelAcessorio = new DualListModel<Acessorio>(acessorioSource, acessorioTarget);
		}
		
	}
	
	private void pickList() throws Exception {
		List<Acessorio> ac = new AcessorioFacade().carregarTudo();
		boolean igual;
		for (int i = 0; i < ac.size(); i++) {
			igual = false;
			for (int j = 0; j < anuncio.getAcessorios().size(); j++) {
				if (ac.get(i).getId().equals(anuncio.getAcessorios().get(j).getId())) {
					igual = true;
				}
			}
			if (!igual) {
				acessorioSource.add(ac.get(i));
			}
			
		}
		acessorioTarget = anuncio.getAcessorios();
	}
	
	public List<Anuncio> MelhoresAnunciosDeVeiculos(){
		List<Anuncio> ranking = new ArrayList<Anuncio>();
		List<Anuncio> tudo = null;
		boolean cheio = false;
		try {
			tudo = new AnuncioFacade().carregarTudo();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("DD :"+ranking);
		if (tudo != null) {
			for (Anuncio anuncio : tudo) {
				if (anuncio.getTipoDeAnuncio().equals("Veículo")) {
					ranking.add(anuncio);
					cheio = true;
				}
			}
		}
		
		if (!cheio) {
			ranking = null;
		}
		
		if (ranking != null) {
			Collections.sort(ranking, new Comparator<Anuncio>() {
				@Override
				public int compare(Anuncio o1, Anuncio o2) {
					if(o1.getCurtidas() > o2.getCurtidas()){
						return -1;
					}
					if(o1.getCurtidas() < o2.getCurtidas()){
						return +1;
					}
					return 0;
				}
			});
			mBeanLogin.setVazio(false);
		}
		else{
			ranking = new ArrayList<Anuncio>();
			ranking.add(new Anuncio("logo.png"));
			mBeanLogin.setVazio(true);
			System.out.println("Else "+ranking.get(0).getPrimeiraFoto()+" "+mBeanLogin.isVazio());
		}
		return ranking;
	}
	
	
	
//	public List<String> getImagensVeiculos(){
//		List<Anuncio> maior = MelhoresAnunciosDeVeiculos();
//		List<String> imagens = new ArrayList<String>();
//		if (maior != null) {
//			for (int i = 0; i < maior.size(); i++) {
//				if (maior.get(i).getTipoDeAnuncio().equals("Veículo")) {
//						imagens.add(maior.get(i).getFoto().get(0).getNome());
//				}
//			}
//		}
//		else{
//			imagens.add("logo.jpg");
//		}
//		return imagens;
//	
//	}
	
	public List<Anuncio> MelhoresAnunciosDePecas(){
		List<Anuncio> ranking = new ArrayList<Anuncio>();
		List<Anuncio> tudo = null;
		boolean cheio = false;
		try {
			tudo = new AnuncioFacade().carregarTudo();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("DD :"+ranking);
		if (tudo != null) {
			for (Anuncio anuncio : tudo) {
				if (anuncio.getTipoDeAnuncio().equals("Peça")) {
					ranking.add(anuncio);
					cheio = true;
				}
			}
		}
		
		if (!cheio) {
			ranking = null;
		}
		
		if (ranking != null) {
			Collections.sort(ranking, new Comparator<Anuncio>() {
				@Override
				public int compare(Anuncio o1, Anuncio o2) {
					if(o1.getCurtidas() > o2.getCurtidas()){
						return -1;
					}
					if(o1.getCurtidas() < o2.getCurtidas()){
						return +1;
					}
					return 0;
				}
			});
			mBeanLogin.setVaziop(false);
		}
		else{
			ranking = new ArrayList<Anuncio>();
			ranking.add(new Anuncio("logo.png"));
			mBeanLogin.setVaziop(true);
			System.out.println("Else "+ranking.get(0).getPrimeiraFoto()+" "+mBeanLogin.isVaziop());
		}
		return ranking;
	}
	
	public void getEmail(){
		EnviarEmail em = new EnviarEmail();
		System.out.println("Instânciou");
		em.setDestinatario(mBeanLogin.getAnuncio().getAnunciante().getEmail());
		System.out.println("Remetente e detinatário"+mBeanLogin.getUsuario()+" | "+mBeanLogin.getAnuncio().getAnunciante());
		em.setAssinatura("<br/>Enviado por: <b>"+mBeanLogin.getUsuario().getNome()+"</b> <br/>"
				+ "Email: <b>"+mBeanLogin.getUsuario().getEmail()+"</b> e telefones:<br/>");
		System.out.println("Assunto "+assunto);
		
		List<Telefone> telefones = mBeanLogin.getUsuario().getTelefones();
		List<String> tele = new ArrayList<String>();
		for (Telefone telefone : telefones) {
			tele.add(telefone.toString());
		}
		System.out.println("MSG "+mensagem);
		Date date = new Date();
		String data = formatarDataparaEmail(date);
		em.setAssunto(assunto+" "+data);
		em.setMensagem(mensagem+em.getAssinatura()+tele+" ");
		System.out.println("Entrou no método...");
		try {
			em.enviar();
			GrowlView.criarMensagem(FacesMessage.SEVERITY_INFO, "E-mail enviado", "Espere o anunciante te contactar.");
		} catch (Exception e) {
			GrowlView.saveMessage(e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	
	}
	
	private String formatarDataparaEmail(Date date) {
		String data1, hora;
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		data1 = formato.format(date);
		formato = new SimpleDateFormat("HH:mm");
		hora = formato.format(date);
		String data = data1+" "+hora;
		return data;
	}
	
}
