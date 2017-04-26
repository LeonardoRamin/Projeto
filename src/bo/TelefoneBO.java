package bo;

import java.util.List;

import dao.DAOGenerico;
import vo.Telefone;

public class TelefoneBO implements BO<Telefone> {

	@Override
	public void salvar(Telefone e) throws Exception {
		if(e.getDDD() == 0) {
			throw new Exception("DDD não existe");
		}
		
		if (e.getNumero() == 0) {
			throw new Exception("Número não existe");
		}
		
		new DAOGenerico<Telefone>().salvar(e);
	}

	@Override
	public Telefone getObjectbyid(Long id, Telefone e) throws Exception {
		if (id == 0) {
			throw new Exception("Objeto não encontrado!");
		}

		return new DAOGenerico<Telefone>().getObjetoById(id, e);
		
	}

	@Override
	public List<Telefone> listar(String o) throws Exception {
		if (new DAOGenerico<Telefone>().listar(o).isEmpty()) {
			throw new Exception("Nada consta no Banco");
		}

		return new DAOGenerico<Telefone>().listar(o);
		
	}

	@Override
	public void editar(Telefone e) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletar(Telefone e) throws Exception {
		new DAOGenerico<Telefone>().deletar(e, e.getId());
		
	}

}
