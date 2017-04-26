package bo;

import java.util.List;

import dao.DAOGenerico;
import vo.Foto;

public class FotoBO implements BO<Foto> {

	@Override
	public void salvar(Foto e) throws Exception {
		if (e.getNome() == null) {
			throw new Exception("Uma imagem é obrigatória");
		}
		
		new DAOGenerico<Foto>().salvar(e);
	}

	@Override
	public Foto getObjectbyid(Long id, Foto e) throws Exception {
		if (id == 0) {
			throw new Exception("Objeto não encontrado!");
		}

		return new DAOGenerico<Foto>().getObjetoById(id, e);
		
	}

	@Override
	public List<Foto> listar(String o) throws Exception {
			if (new DAOGenerico<Foto>().listar(o).isEmpty()) {
				throw new Exception("Nada consta no Banco");
			}

			return new DAOGenerico<Foto>().listar(o);
		
	}

	@Override
	public void deletar(Foto e) throws Exception {
		
		new DAOGenerico<Foto>().deletar(e, e.getId());
		
	}

	@Override
	public void editar(Foto e) throws Exception {
		new DAOGenerico<Foto>().editar(e);
		
	}

}
