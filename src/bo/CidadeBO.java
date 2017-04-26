package bo;

import java.util.List;

import dao.DAOGenerico;
import vo.Cidade;

public class CidadeBO implements BO<Cidade> {

	@Override
	public void salvar(Cidade e) throws Exception {
		if (e.getNomeCidade() == null) {
			throw new Exception("Informe a cidade");
		}
		
		new DAOGenerico<Cidade>().salvar(e);
	}

	@Override
	public void editar(Cidade e) throws Exception {
		if (e.getNomeCidade() == null || e.getNomeCidade().equals("")) {
			throw new Exception("Informe a cidade");
		}
		
		new DAOGenerico<Cidade>().editar(e);
		
	}

	@Override
	public Cidade getObjectbyid(Long id, Cidade e) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cidade> listar(String o) throws Exception {
		return new DAOGenerico<Cidade>().listar(o);
	}

	@Override
	public void deletar(Cidade e) throws Exception {
		if (!e.getEstados().isEmpty()) {
			throw new Exception("Essa cidade está em algum estado");
		}
		new DAOGenerico<Cidade>().deletar(e, e.getId());
		
	}

}
