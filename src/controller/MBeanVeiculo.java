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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import facade.VeiculoFacade;
import view.GrowlView;
import vo.Foto;
import vo.Veiculo;

@ManagedBean
@ViewScoped
public class MBeanVeiculo implements MBean<Veiculo>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Veiculo veiculo;
	private List<Veiculo> veiculos;
	
	private File file;
	private byte[] conteudo;
	private boolean editar;
	private String antiga;
	
	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public List<Veiculo> getVeiculos() {
		return veiculos;
	}

	public void setVeiculos(List<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}

	@PostConstruct
	public void init(){
		try {
			veiculos = new VeiculoFacade().carregarTudo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void salvar() {
		try {
			if (veiculo.getId() == null) {
				new VeiculoFacade().inserir(veiculo);
				GrowlView.sucesso("Salvo");
			}
			else{
				new VeiculoFacade().alterar(veiculo);
				GrowlView.sucesso("Salvo");
			}
			if (editar) {
				System.out.println("Editar");
				this.salvarArquivo(file);
			}
			reset();
		} catch (Exception e) {
			GrowlView.saveMessage(e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		System.out.println("Veiculo "+veiculo.toString());
	}

	private void reset() {
		System.out.println("reset()");
		veiculo = new Veiculo();
		editar = false;
		try {
			veiculos = new VeiculoFacade().carregarTudo();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		antiga = null;
		
	}

	private void salvarArquivo(File file2) throws IOException {
		if (antiga != null) {
			System.out.println("Foto antiga "+antiga);
			File a = new File("D:/workspace/PI_IV/WebContent/resources/Imagens/Cadastro/Veiculo/"+antiga);
			a.delete();
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file2);
			fos.write(conteudo);
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fos.close();
		
	}

	@Override
	public Veiculo getObject() {
		return veiculo;
		// TODO Auto-generated method stub

	}

	@Override
	public List<Veiculo> listar() {
		try {
			veiculos = new VeiculoFacade().carregarTudo();
		} catch (Exception e) {
			GrowlView.saveMessage(e);
		}
		return veiculos;

	}

public void carregarArquivo(FileUploadEvent event) throws IOException { 
		
		UploadedFile arquivo = event.getFile(); // pego o nome do arquivo
		System.out.println("Nome do arquivo "+arquivo.getFileName());
		
		
		String nome = arquivo.getFileName();
		System.out.println("file "+nome);
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
		file = new java.io.File(dir+"PI_IV/WebContent/resources/Imagens/Cadastro/Veiculo/"+nomeArquivo); // diretorio o qual vou salvar o arquivo do upload, equivale ao nome completamente qualificado  
		GrowlView.criarMensagem(FacesMessage.SEVERITY_INFO, "Aviso", arquivo.getFileName()+" Adicionado!");

		if (veiculo.getId() == null) {
			
		}
		else{
			antiga = veiculo.getFoto().getNome();
		}
		
		System.out.println("nome antigo "+antiga);
		if (antiga != null) {
			veiculo.getFoto().setNome(file.getName());
		}
		else{
			Foto foto = new Foto(file.getName());
			veiculo.setFoto(foto);
		}
		conteudo = arquivo.getContents();  // daqui pra baixo é somente operações de IO. 
		System.out.println("Adicionada! "+file.getAbsolutePath());
		editar = true;
		
		
	}  

	public MBeanVeiculo() {
		veiculo = new Veiculo();
		veiculos = new ArrayList<Veiculo>();
		editar = false;
	}

	@Override
	public String deletar() {
		System.out.println("deletar()");
		try {
			new VeiculoFacade().excluir(veiculo);
			GrowlView.sucesso("Deletado");
		} catch (Exception e) {
			GrowlView.saveMessage(e);
		}
		return null;
	}
	

}
