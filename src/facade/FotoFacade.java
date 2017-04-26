package facade;

import java.util.List;

import bo.FotoBO;
import vo.Foto;

public class FotoFacade implements Facade<Foto> {
	
	private FotoBO fotobo;

	@Override
	public void inserir(Foto vo) throws Exception {
		fotobo.salvar(vo);
		
	}

	@Override
	public List<Foto> carregarTudo() throws Exception {
		return fotobo.listar("Foto");
	}

	public FotoFacade() {
		fotobo = new FotoBO();
	}

	@Override
	public Foto trazerUm(Long id, Foto classe) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void alterar(Foto vo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void excluir(Foto vo) throws Exception {
		fotobo.deletar(vo);
		
	}
}
