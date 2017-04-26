package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import facade.AcessorioFacade;
import view.GrowlView;
import vo.Acessorio;
import vo.Foto;

@ManagedBean
@ViewScoped
public class MBeanAcessorio implements MBean<Acessorio>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Acessorio acessorio;
	private List<Acessorio> acessorios;
	private File file;
	private byte[] conteudo;
	private String antigo;
	private boolean editar;

	public Acessorio getAcessorio() {
		return acessorio;
	}

	public void setAcessorio(Acessorio acessorio) {
		this.acessorio = acessorio;
	}

	public List<Acessorio> getAcessorios() {
		return acessorios;
	}

	public void setAcessorios(List<Acessorio> acessorios) {
		this.acessorios = acessorios;
	}

	public String[] getMateriais(){
		String[] materiais = {"Aluminio", "Ferro", "Metal", "Mateira"};
		return materiais;
	}
	
	@Override
	public void salvar() {
		try {
			if(acessorio.getId() == null){
				new AcessorioFacade().inserir(acessorio);
				GrowlView.sucesso("Salvo");
			}
			else{
				new AcessorioFacade().alterar(acessorio);
				GrowlView.sucesso("Editado");
			}
			if (editar) {
				this.salvarArquivo(file, conteudo);
			}
			reset();
		} catch (Exception e) {
			GrowlView.saveMessage(e);
			e.printStackTrace();
		}
		
	}

	public void reset() {
		System.out.println("reset()");
		antigo = null;
		editar = false;
		acessorio = new Acessorio();
		try {
			acessorios = new AcessorioFacade().carregarTudo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public Acessorio getObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Acessorio> listar() {
		try {
			acessorios = new AcessorioFacade().carregarTudo();
		} catch (Exception e) {
			GrowlView.saveMessage(e);
		}
		return acessorios;
	}

	public MBeanAcessorio() {
		acessorio = new Acessorio();
		acessorios = new ArrayList<Acessorio>();
		antigo = null;
		editar = false;
	}

	@Override
	public String deletar() {
		try {
			new AcessorioFacade().excluir(acessorio);
			GrowlView.sucesso("Deletado");
		} catch (Exception e) {
			GrowlView.saveMessage(e);
			e.printStackTrace();
		}
		reset();
		return null;
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
        String nomeArquivo = data1 + "-" + hora1 + "." + nome;
        
		String dir = "D:/workspace/";//diretório fixo- mudar quando estiver com o projeto em outro computador
		file = new java.io.File(dir+"PI_IV/WebContent/resources/Imagens/Cadastro/Acessorio/"+nomeArquivo); // diretorio o qual vou salvar o arquivo do upload, equivale ao nome completamente qualificado  
		
		if (acessorio.getFoto() != null) {
			antigo = acessorio.getFoto().getNome();
			acessorio.getFoto().setNome(file.getName());
		}
		else{
			Foto foto = new Foto(file.getName());
			acessorio.setFoto(foto);
		}
		System.out.println("Nome: "+file.getName()+" Path: "+file.getPath()+ " AbsolutPath: "+file.getAbsolutePath());


		conteudo = arquivo.getContents();  // daqui pra baixo é somente operações de IO. 
		System.out.println("Salvando na pasta ");
		editar = true;

	}
	
	private void salvarArquivo(File file, byte[] bits) throws IOException {
			if (antigo != null) {
				File a = new File("D:/workspace/PI_IV/WebContent/resources/Imagens/Cadastro/Acessorio/"+antigo);
				a.delete();
			}
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file);
				fos.write(bits);
				fos.close();
			} catch (FileNotFoundException e) {
				GrowlView.saveMessage(e);
				e.printStackTrace();
			}
		
	}
	
	@PostConstruct
	public void init(){
		try {
			acessorios = new AcessorioFacade().carregarTudo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
