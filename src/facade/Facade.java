package facade;

import java.util.List;

public interface Facade<VO> {

	public void inserir(VO vo) throws Exception;
	public void alterar(VO vo) throws Exception;
	public VO trazerUm(Long id, VO classe) throws Exception;
	public List<VO> carregarTudo() throws Exception;
	public void excluir(VO vo) throws Exception;
}
