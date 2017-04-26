package facade;

import java.util.List;

import bo.FotoBO;
import bo.MarcaBO;
import vo.Marca;

public class MarcaFacade implements Facade<Marca> {

	private MarcaBO marcabo;
	private FotoBO fotobo;
	
	@Override
	public void inserir(Marca vo) throws Exception {
		try{
		fotobo.salvar(vo.getSimbolo());
		}
		catch (Exception e){
			throw new Exception(e.getMessage());
		}
		try {
			marcabo.salvar(vo);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}

	@Override
	public List<Marca> carregarTudo() throws Exception {
		try {
			return marcabo.listar("Marca");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public MarcaFacade() {
		marcabo = new MarcaBO();
		fotobo = new FotoBO();
	}

	@Override
	public Marca trazerUm(Long id, Marca classe) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void excluir(Marca vo) throws Exception {
		marcabo.deletar(vo);		
	}

	@Override
	public void alterar(Marca vo) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
