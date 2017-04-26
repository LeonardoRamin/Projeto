package facade;

import java.util.List;

import bo.CidadeBO;
import vo.Cidade;

public class CidadeFacade implements Facade<Cidade> {

	private CidadeBO cidadebo;
	
	@Override
	public void inserir(Cidade vo) throws Exception{
		try {
			cidadebo.salvar(vo);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}

	@Override
	public void alterar(Cidade vo) throws Exception {
		cidadebo.editar(vo);
	}

	@Override
	public Cidade trazerUm(Long id, Cidade classe) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cidade> carregarTudo() throws Exception {
		return cidadebo.listar("Cidade");
	}

	@Override
	public void excluir(Cidade vo) throws Exception{
		try {
			cidadebo.deletar(vo);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}

	public CidadeFacade() {
		cidadebo = new CidadeBO();
	}
}
