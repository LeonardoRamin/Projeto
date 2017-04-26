package bo;

import java.util.List;

import dao.DAOGenerico;
import vo.Marca;

public class MarcaBO implements BO<Marca> {

	@Override
	public void salvar(Marca e) throws Exception {
		if (e.getNome() == null) {
			throw new Exception("Qual o nome da marca?");
		}
		
		if (e.getDescricao() == null) {
			throw new Exception("A descrição não pode ser vazia");
		}
		
		if (e.getDescricao().length() > 1000) {
			throw new Exception("A descrição deve conter menos que 1000 (mil) caracteres");
		}
		
		if (e.getSimbolo() == null) {
			throw new Exception("Deve-se conter uma imagem do simbolo da marca");
		}
		
		new DAOGenerico<Marca>().salvar(e);
	}

	@Override
	public Marca getObjectbyid(Long id, Marca e) throws Exception {
		if (id == 0) {
			throw new Exception("Objeto não encontrado!");
		}

		return new DAOGenerico<Marca>().getObjetoById(id, e);
		
	}

	@Override
	public List<Marca> listar(String o) throws Exception {
		if (new DAOGenerico<Marca>().listar(o) == null) {
			throw new Exception("Nada consta no Banco");
		}

		return new DAOGenerico<Marca>().listar(o);
		
	}

	@Override
	public void deletar(Marca e) throws Exception {
		
		new DAOGenerico<Marca>().deletar(e, e.getId());
	}

	@Override
	public void editar(Marca e) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
