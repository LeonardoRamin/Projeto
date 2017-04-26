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
import facade.MarcaFacade;
import view.GrowlView;
import vo.Foto;
import vo.Marca;

@ManagedBean
@ViewScoped
public class MBeanMarca implements Serializable, MBean<Marca> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Marca marca;
	private List<Marca> marcas;
	
	private File file;
	private byte[] conteudo;
	private boolean editar;
	
	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public List<Marca> getMarcas() throws Exception {
		return new MarcaFacade().carregarTudo();
	}

	public void setMarcas(List<Marca> marcas) {
		this.marcas = marcas;
	}
	
	public boolean isEditar() {
		return editar;
	}

	public void setEditar(boolean editar) {
		this.editar = editar;
	}

	public MBeanMarca() {
		marca = new Marca();
		marcas = new ArrayList<Marca>();
		editar = false;
	}

	@Override
	public void salvar() {
		try {
			if (marca.getId() == null) {
				new MarcaFacade().inserir(marca);
				GrowlView.sucesso("Salvo");
			}
			else{
				new MarcaFacade().alterar(marca);
				GrowlView.sucesso("Salvo");
			}
			if (editar) {
				this.salvarArquivo(file);
			}
			reset();
		} catch (Exception e) {
			GrowlView.saveMessage(e);
		}

	}

	private void reset() {
		marca = new Marca();
		file = null;
		conteudo = null;
		editar = false;
		
	}

	@Override
	public Marca getObject() {
		// TODO Auto-generated method stub
		return marca;
	}

	@Override
	public List<Marca> listar() {
		try {
			marcas = new MarcaFacade().carregarTudo();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return marcas;
	}

	@Override
	public String deletar(){
		try {
			new MarcaFacade().excluir(marca);
			GrowlView.sucesso("Deletado");
		} catch (Exception e) {
			GrowlView.saveMessage(e);
			e.printStackTrace();
		}
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
		file = new java.io.File(dir+"PI_IV/WebContent/resources/Imagens/Cadastro/Marca/"+nomeArquivo); // diretorio o qual vou salvar o arquivo do upload, equivale ao nome completamente qualificado  

		Foto foto = new Foto(file.getName());
		marca.setSimbolo(foto);
		System.out.println("Nome: "+file.getName()+" Path: "+file.getPath()+ " AbsolutPath: "+file.getAbsolutePath());

		conteudo = arquivo.getContents();  // daqui pra baixo é somente operações de IO. 
		System.out.println("Salvando na pasta ");
		setEditar(true);
	}  
	
	private void salvarArquivo(File file) throws IOException {
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			fos.write(conteudo);
			fos.close();
		} catch (FileNotFoundException e) {
			GrowlView.saveMessage(e);
			e.printStackTrace();
		}
	
}
	
	public String carregarImagem(){
		return marca.getSimbolo().toString();
	}
	
	@PostConstruct
	public void init(){
		try {
			marcas = new MarcaFacade().carregarTudo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
		}
	}
	
}