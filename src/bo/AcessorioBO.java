package bo;

import java.util.ArrayList;
import java.util.List;

import dao.DAOGenerico;
import vo.Acessorio;


public class AcessorioBO implements BO<Acessorio> {

	@Override
	public void salvar(Acessorio e) throws Exception {
		if (e.getDepartamento() == null) {
			throw new Exception("Selecione um departamento (lugar do acess�rio)");
		}
		
		if (e.getNome().isEmpty()) {
			throw new Exception("O nome � obrigat�rio");
		}
		
		if (e.getDescricao().isEmpty()) {
			throw new Exception("O Acess�rio deve ter uma descri��o");
		}
		
		if (e.getDescricao().length() >= 1000) {
			throw new Exception("A Descri��o deve ter menos que 1000 (mil) caracteres");
		}
		
		if (e.getMaterial().isEmpty()) {
			throw new Exception("Especifique o material do acess�rio");
		}
		
		new DAOGenerico<Acessorio>().salvar(e);
	}

	@Override
	public Acessorio getObjectbyid(Long id, Acessorio e) throws Exception {
		if (id == 0) {
			throw new Exception("Objeto n�o encontrado!");
		}

		return (Acessorio) new DAOGenerico<Acessorio>().getObjetoById(id, e);
		
	}

	@Override
	public List<Acessorio> listar(String o) throws Exception {
		if (new DAOGenerico<Acessorio>().listar(o).isEmpty()) {
			throw new Exception("Nada consta no Banco");
		}

		return new DAOGenerico<Acessorio>().listar(o);
		
	}

	@Override
	public void editar(Acessorio e) throws Exception {
		if (e.getDepartamento() == null) {
			throw new Exception("Selecione um departamento (lugar do acess�rio)");
		}
		
		if (e.getNome().isEmpty()) {
			throw new Exception("O nome � obrigat�rio");
		}
		
		if (e.getDescricao().isEmpty()) {
			throw new Exception("O Acess�rio deve ter uma descri��o");
		}
		
		if (e.getDescricao().length() >= 1000) {
			throw new Exception("A Descri��o deve ter menos que 1000 (mil) caracteres");
		}
		
		if (e.getMaterial().isEmpty()) {
			throw new Exception("Especifique o material do acess�rio");
		}
		
		new DAOGenerico<Acessorio>().editar(e);
		
	}

	@Override
	public void deletar(Acessorio e) throws Exception {
		if (e.getAnuncios() instanceof ArrayList<?>) {
			throw new Exception("O acess�rio est� sendo utilizado em um ou mais anuncios");
		}
		new DAOGenerico<Acessorio>().deletar(e, e.getId());
	}

}
