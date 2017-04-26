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

import facade.PecaFacade;
import view.GrowlView;
import vo.Foto;
import vo.Peca;

@ManagedBean
@ViewScoped
public class MBeanPeca implements MBean<Peca>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Peca peca;
	private List<Peca> pecas;
	
	private File file;
	private byte[] conteudo;
	private boolean editar;
	
	public Peca getPeca() {
		return peca;
	}

	public void setPeca(Peca peca) {
		this.peca = peca;
	}

	public List<Peca> getPecas() {
		return pecas;
	}

	public void setPecas(List<Peca> pecas) {
		this.pecas = pecas;
	}
	
	public MBeanPeca() {
		peca = new Peca();
		pecas = new ArrayList<Peca>();
		editar = false;
	}

	@Override
	public void salvar() {
		try {
			if (peca.getId() == null) {
				new PecaFacade().inserir(peca);
				GrowlView.sucesso("Salvo");
			}
			else{
				new PecaFacade().inserir(peca);
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
		peca = new Peca();
		editar = false;
	}

	@Override
	public Peca getObject() {
		// TODO Auto-generated method stub
		return peca;
	}

	
	@Override
	public List<Peca> listar() {
		try {
			pecas = new PecaFacade().carregarTudo();
		} catch (Exception e) {
			GrowlView.saveMessage(e);
		}
		System.out.println("Listou peça");
		return pecas;
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
		file = new java.io.File(dir+"PI_IV/WebContent/resources/Imagens/Cadastro/Peca/"+nomeArquivo); // diretorio o qual vou salvar o arquivo do upload, equivale ao nome completamente qualificado  

		Foto foto = new Foto(file.getName());
		peca.setFoto(foto);
		System.out.println("Nome: "+file.getName()+" Path: "+file.getPath()+ " AbsolutPath: "+file.getAbsolutePath());


		conteudo = arquivo.getContents();  // daqui pra baixo é somente operações de IO. 
		System.out.println("Salvando na pasta ");
		editar = true;

	}
	
	private void salvarArquivo(File file, byte[] bits) throws IOException {
		
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

	@Override
	public String deletar() {
		try {
			new PecaFacade().excluir(peca);
			GrowlView.sucesso("Deletado");
		} catch (Exception e) {
			GrowlView.saveMessage(e);
			e.printStackTrace();
		}
		reset();
		return null;
	}
	
	@PostConstruct
	public void init(){
		try {
			pecas = new PecaFacade().carregarTudo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}
}
