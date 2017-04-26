package facade;

import java.util.List;

import bo.DepartamentoBO;
import bo.FotoBO;
import vo.Departamento;

public class DepartamentoFacade implements Facade<Departamento> {
	
	private DepartamentoBO departamentobo;
	private FotoBO fotobo;

	@Override
	public void inserir(Departamento vo) throws Exception  {
		try {
			fotobo.salvar(vo.getFoto());
			departamentobo.salvar(vo);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}

	@Override
	public List<Departamento> carregarTudo() throws Exception {
		return departamentobo.listar("Departamento");
	}

	public DepartamentoFacade() {
		departamentobo = new DepartamentoBO();
		fotobo = new FotoBO();
	}

	@Override
	public Departamento trazerUm(Long id, Departamento classe) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void alterar(Departamento vo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void excluir(Departamento vo) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
