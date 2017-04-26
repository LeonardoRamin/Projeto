package bo;

import java.util.List;

import dao.DAOGenerico;
import vo.Veiculo;

public class VeiculoBO implements BO<Veiculo> {

	@Override
	public void salvar(Veiculo e) throws Exception {
		if (e.getMarca() == null) {
			throw new Exception("O Veiculo deve ter uma marca");
		}
		
		if (e.getModelo() == null) {
			throw new Exception("Qual o modelo (nome) do veículo?");
		}
		
		if (e.getTipoCambio() == null) {
			throw new Exception("Selecione um tipo de cambio");
		}
		
		if (e.getFoto() == null) {
			throw new Exception("Insira uma imagem!");
		}
		
		new DAOGenerico<Veiculo>().salvar(e);
	}

	@Override
	public Veiculo getObjectbyid(Long id, Veiculo e) throws Exception {
		if (id == 0) {
			throw new Exception("Objeto não encontrado!");
		}

		return new DAOGenerico<Veiculo>().getObjetoById(id, e);
		
	}

	@Override
	public List<Veiculo> listar(String o) throws Exception {
		if (new DAOGenerico<Veiculo>().listar(o).isEmpty()) {
			throw new Exception("Nada consta no Banco");
		}

		return new DAOGenerico<Veiculo>().listar(o);
		
	}

	@Override
	public void editar(Veiculo e) throws Exception {
		new DAOGenerico<Veiculo>().editar(e);
		
	}

	@Override
	public void deletar(Veiculo e) throws Exception {
		if (!e.getAnuncios().isEmpty()) {
			throw new Exception("Esse veículo está sendo usado por um anuncio");
		}
		new DAOGenerico<Veiculo>().deletar(e, e.getId());
		
	}

}
