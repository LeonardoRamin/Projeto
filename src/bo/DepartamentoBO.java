package bo;

import java.util.List;

import dao.DAOGenerico;
import vo.Departamento;

public class DepartamentoBO implements BO<Departamento> {

	@Override
	public void salvar(Departamento e) throws Exception {
		if (e.getNome() == null) {
			throw new Exception("O nome é obrigatório");
		}
		
		new DAOGenerico<Departamento>().salvar(e);
		
	}

	@Override
	public Departamento getObjectbyid(Long id, Departamento e) throws Exception {
		if (id == 0) {
			throw new Exception("Objeto não encontrado!");
		}

		return new DAOGenerico<Departamento>().getObjetoById(id, e);
		
	}

	@Override
	public List<Departamento> listar(String o) throws Exception {
		if (new DAOGenerico<Departamento>().listar(o).isEmpty()) {
			throw new Exception("Nada consta no Banco");
		}

		return new DAOGenerico<Departamento>().listar(o);
		
	}

	@Override
	public void editar(Departamento e) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletar(Departamento e) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
