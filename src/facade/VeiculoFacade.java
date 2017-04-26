package facade;

import java.util.List;

import bo.FotoBO;
import bo.VeiculoBO;
import dao.DAOGenerico;
import vo.Veiculo;

public class VeiculoFacade implements Facade<Veiculo> {
	
	private VeiculoBO veiculobo;
	private FotoBO fotobo;

	@Override
	public void inserir(Veiculo vo) throws Exception {
		try {
			fotobo.salvar(vo.getFoto());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		try {
			veiculobo.salvar(vo);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}

	@Override
	public List<Veiculo> carregarTudo() throws Exception {
		return veiculobo.listar("Veiculo");
	}

	@Override
	public Veiculo trazerUm(Long id, Veiculo classe) throws Exception {
		// TODO Auto-generated method stub
		return veiculobo.getObjectbyid(id, classe);
	}
	
	public VeiculoFacade() {
		veiculobo = new VeiculoBO();
		fotobo = new FotoBO();
	}

	@Override
	public void alterar(Veiculo vo) throws Exception {
		Veiculo veiculo = null;
		veiculo = new DAOGenerico<Veiculo>().getObjetoById(vo.getId(), vo);
		if (!veiculo.getFoto().equals(vo.getFoto())) {
			try {
				fotobo.editar(vo.getFoto());
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}
		try {
			veiculobo.editar(vo);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}

	@Override
	public void excluir(Veiculo vo) throws Exception {
		try {
			veiculobo.deletar(vo);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
