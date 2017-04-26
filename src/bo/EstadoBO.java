package bo;

import java.util.List;


import dao.DAOGenerico;
import facade.EstadoFacade;
import vo.Estado;

public class EstadoBO implements BO<Estado> {

	@Override
	public void salvar(Estado e) throws Exception {
		List<Estado> estados = new EstadoFacade().carregarTudo();
		if (estados != null) {
			for (Estado estado : estados) {
				if (estado.getUf().equals(e.getUf())) {
					throw new Exception("Essa uf já consta em nossos arquivos");
				}
				
				if (estado.getNomeEstado().equals(e.getNomeEstado())) {
					throw new Exception("Esse nome do estado já está cadastrado");
				}
			}
		}

		
		if (e.getCidades().size() == 0) {
			throw new Exception("O estado deve ser cadastrao com pelo menos uma cidade");
		}
		
		if (e.getNomeEstado().isEmpty()) {
			throw new Exception("O nome deve ser preenchido");
		}
		
		if (e.getUf().isEmpty()) {
			throw new Exception("O estado deve conter a sua uf");
		}
		
		new DAOGenerico<Estado>().salvar(e);
	}

	@Override
	public void editar(Estado e) throws Exception {
		int cont = 0;
		List<Estado> estados = new EstadoFacade().carregarTudo();
		if (estados != null) {
			for (Estado estado : estados) {
				cont++;
				if(cont >= 2){
					if (estado.getUf().equals(e.getUf())) {
						throw new Exception("Essa uf já consta em nossos arquivos");
					}
					
					if (estado.getNomeEstado().equals(e.getNomeEstado())) {
						throw new Exception("Esse nome do estado já está cadastrado");
					}
				}
			}
		}
		
		

		
		if (e.getCidades().size() == 0) {
			throw new Exception("O estado deve ser cadastrao com pelo menos uma cidade");
		}
		
		if (e.getNomeEstado().isEmpty()) {
			throw new Exception("O nome deve ser preenchido");
		}
		
		if (e.getUf().isEmpty()) {
			throw new Exception("O estado deve conter a sua uf");
		}
		
		new DAOGenerico<Estado>().editar(e);
	}

	@Override
	public Estado getObjectbyid(Long id, Estado e) throws Exception {
		// TODO Auto-generated method stub
		return new DAOGenerico<Estado>().getObjetoById(id, e);
	}

	@Override
	public List<Estado> listar(String o) throws Exception {
		return new DAOGenerico<Estado>().listar(o);
	}

	@Override
	public void deletar(Estado e) throws Exception {
		new DAOGenerico<Estado>().deletar(e, e.getId());
		
	}

}
