package facade;

import java.util.List;

import bo.FotoBO;
import bo.PecaBO;
import vo.Peca;

public class PecaFacade implements Facade<Peca> {

	private PecaBO pecabo;
	private FotoBO fotobo;

	@Override
	public void inserir(Peca vo) throws Exception {
		try {
			fotobo.salvar(vo.getFoto());
			pecabo.salvar(vo);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}

	@Override
	public Peca trazerUm(Long id, Peca classe) throws Exception {
		return pecabo.getObjectbyid(id, classe);
	}
	
	@Override
	public List<Peca> carregarTudo() throws Exception {
		return pecabo.listar("Peca");
	}

	public PecaFacade() {
		pecabo = new PecaBO();
		fotobo = new FotoBO();
	}

	@Override
	public void alterar(Peca vo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void excluir(Peca vo) throws Exception {
		// TODO Auto-generated method stub
		
	}



	
}
