package facade;

import java.io.File;
import java.util.List;

import bo.AnuncioBO;
import bo.FotoBO;
import vo.Anuncio;
import vo.Foto;

public class AnuncioFacade implements Facade<Anuncio> {

	private AnuncioBO anunciobo;
	private FotoBO fotobo;
	private int tamanho;
	
	public int getTamanho() {
		return tamanho;
	}

	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}

	public void inserir(Anuncio anuncio) throws Exception{
		for (Foto img : anuncio.getFoto()) {
			try {
				fotobo.salvar(img);
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}
		try {
			System.out.println("Tipo - tudo  "+anuncio);
			anunciobo.salvar(anuncio);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public List<Anuncio> carregarTudo() throws Exception {
		List<Anuncio> al = null;
		try {
			al = anunciobo.listar("Anuncio");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return al;
	}
	
	public AnuncioFacade() {
		anunciobo = new AnuncioBO();
		fotobo = new FotoBO();
	}

	@Override
	public Anuncio trazerUm(Long id, Anuncio classe) throws Exception  {
		Anuncio anuncio = null;
		try {
			anuncio = anunciobo.getObjectbyid(id, classe);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return anuncio;
	}

	@Override
	public void excluir(Anuncio vo) throws Exception {
		String pasta;
		if (vo.getTipoDeAnuncio().equals("Veículo")) {
			pasta = "Veiculo";
		}
		else {
			pasta = "Peca";
		}
		File file;
		anunciobo.deletar(vo);
		for (Foto foto : vo.getFoto()) {
			String dir = "D:/workspace/";//diretório fixo- mudar quando estiver com o projeto em outro computador
			file = new java.io.File(dir+"PI_IV/WebContent/resources/Imagens/Anuncio/"+pasta+"/"+foto.getNome());
			fotobo.deletar(foto);
			file.delete();
		}
		
		
	}

	@Override
	public void alterar(Anuncio vo) throws Exception {
		if (vo.getFoto().size() > tamanho) {
			for (int i = tamanho; i < vo.getFoto().size(); i++) {
				try {
					System.out.println("alterando foto "+vo.getFoto().get(i));
					fotobo.salvar(vo.getFoto().get(i));
				} catch (Exception e) {
					throw new Exception(e.getMessage());
				}

			}
		}
		anunciobo.editar(vo);
	}
}
