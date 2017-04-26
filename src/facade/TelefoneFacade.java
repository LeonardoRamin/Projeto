package facade;

import java.util.List;

import bo.TelefoneBO;
import vo.Telefone;

public class TelefoneFacade implements Facade<Telefone> {
	
	private TelefoneBO telefonebo;

	@Override
	public void inserir(Telefone vo) throws Exception {
		telefonebo.salvar(vo);
		
	}

	@Override
	public List<Telefone> carregarTudo() throws Exception {
		return telefonebo.listar("Telefone");
	}

	public TelefoneFacade() {
		telefonebo = new TelefoneBO();
	}

	@Override
	public Telefone trazerUm(Long id, Telefone classe) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void alterar(Telefone vo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void excluir(Telefone vo) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
