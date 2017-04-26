package facade;

import java.util.List;

import bo.EstadoBO;
import vo.Estado;

public class EstadoFacade implements Facade<Estado> {

	private EstadoBO estadobo;
	
	@Override
	public void inserir(Estado vo) throws Exception {
		try {
			estadobo.salvar(vo);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
		
	}

	@Override
	public void alterar(Estado vo) throws Exception {
		try {
			estadobo.editar(vo);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}

	@Override
	public Estado trazerUm(Long id, Estado classe) throws Exception {
		// TODO Auto-generated method stub
		return estadobo.getObjectbyid(id, classe);
	}

	@Override
	public List<Estado> carregarTudo() throws Exception {
		List<Estado> estados = null;
		try {
			estados = estadobo.listar("Estado");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return estados;
	}

	@Override
	public void excluir(Estado vo) throws Exception {
		estadobo.deletar(vo);
		
	}

	public EstadoFacade() {
		estadobo = new EstadoBO();
	}
}
