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

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import facade.DepartamentoFacade;
import view.GrowlView;
import vo.Departamento;
import vo.Foto;

@ManagedBean
@ViewScoped
public class MBeanDepartamento implements MBean<Departamento>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Departamento departamento;
	private List<Departamento> departamentos;
	
	private File file;
	private byte[] conteudo;

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public List<Departamento> getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(List<Departamento> departamentos) {
		this.departamentos = departamentos;
	}

	public MBeanDepartamento() {
		departamento = new Departamento();
		departamentos = new ArrayList<Departamento>();
	}
	
	@Override
	public void salvar() {
		System.out.println("Departamento "+departamento);
		try {
			this.salvarArquivo(file, conteudo);
			new DepartamentoFacade().inserir(departamento);
			GrowlView.sucesso("Salvo");
		} catch (Exception e) {
			GrowlView.saveMessage(e);
			e.printStackTrace();
		}
		
	}

	@Override
	public Departamento getObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Departamento> listar() {
		try {
			departamentos = new DepartamentoFacade().carregarTudo();
		} catch (Exception e) {
			GrowlView.saveMessage(e);
		}
		return departamentos;
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
        
        System.out.println("Criar arquivo");
		String dir = "D:/workspace/";//diretório fixo- mudar quando estiver com o projeto em outro computador
		file = new File(dir+"PI_IV/WebContent/resources/Imagens/Cadastro/Departamento/"+nomeArquivo); // diretorio o qual vou salvar o arquivo do upload, equivale ao nome completamente qualificado  
		
		System.out.println("setar foto");
		System.out.println("Nome: "+file.getName()+" Path: "+file.getPath()+ " AbsolutPath: "+file.getAbsolutePath());
		Foto foto = new Foto(file.getName());
		departamento.setFoto(foto);
		

		System.out.println("pegar bytes");
		conteudo = arquivo.getContents();  // daqui pra baixo é somente operações de IO. 
		System.out.println("Salvando na pasta... ");


	}
	
	private void salvarArquivo(File file, byte[] bits) throws IOException {
		System.out.println("salvarArquivo()");
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file);
				fos.write(bits);
				fos.close();
			} catch (FileNotFoundException e) {
				GrowlView.saveMessage(e);
				e.printStackTrace();
			}
		System.out.println("salvar");
	}

	@Override
	public String deletar() {
		// TODO Auto-generated method stub
		return null;
	}
}
