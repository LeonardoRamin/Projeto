package facade;

import java.io.File;
import java.util.List;

import bo.AcessorioBO;
import bo.FotoBO;
import vo.Acessorio;

public class AcessorioFacade implements Facade<Acessorio> {
	
	private AcessorioBO acessoriobo;
	private FotoBO fotobo;

	@Override
	public void inserir(Acessorio vo) throws Exception {
		try {
			fotobo.salvar(vo.getFoto());
			acessoriobo.salvar(vo);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public List<Acessorio> carregarTudo() throws Exception {
		try {
			return acessoriobo.listar("Acessorio");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}

	public AcessorioFacade() {
		acessoriobo = new AcessorioBO();
		fotobo = new FotoBO();
	}

	@Override
	public Acessorio trazerUm(Long id, Acessorio classe) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void alterar(Acessorio vo) throws Exception  {
		try {
			fotobo.editar(vo.getFoto());
			acessoriobo.editar(vo);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
		
	}

	@Override
	public void excluir(Acessorio vo) throws Exception  {
		try {
			acessoriobo.deletar(vo);
			File f = new File("D:/workspace/PI_IV/WebContent/resources/Imagens/Cadastro/Acessorio/"+vo.getFoto().getNome());
			f.delete();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}
}
