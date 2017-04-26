package bo;

import java.util.List;

import dao.DAOGenerico;
import vo.Peca;

public class PecaBO implements BO<Peca> {

	@Override
	public void salvar(Peca e) throws Exception {
		if (e.getDepartamento() == null) {
			throw new Exception("Selecione um departamento (lugar da peça)");
		}
		
		if (e.getNome() == null) {
			throw new Exception("O nome é obrigatório");
		}
		
		if (e.getDescricao() == null) {
			throw new Exception("A peça deve ter uma descrição");
		}
		
		if (e.getDescricao().length() >= 1000) {
			throw new Exception("A Descrição deve ter menos que 1000 (mil) caracteres");
		}
		
		
		new DAOGenerico<Peca>().salvar(e);
		
	}

	@Override
	public Peca getObjectbyid(Long id, Peca e) throws Exception {
		if (id == 0) {
			throw new Exception("Objeto não encontrado!");
		}

		return new DAOGenerico<Peca>().getObjetoById(id, e);
		
	}

	@Override
	public List<Peca> listar(String o) throws Exception {
		if (new DAOGenerico<Peca>().listar(o).isEmpty()) {
			throw new Exception("Nada consta no Banco");
		}

		return new DAOGenerico<Peca>().listar(o);
		
	}

	@Override
	public void editar(Peca e) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletar(Peca e) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
