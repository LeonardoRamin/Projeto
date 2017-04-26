package bo;

import java.util.List;

import dao.DAOGenerico;
import vo.Anuncio;

public class AnuncioBO implements BO<Anuncio> {

	@Override
	public void salvar(Anuncio e) throws Exception {
		if(e.getAnunciante() == null){
			throw new Exception("O Anunciante deve ser identificado!");
		}
		
		if(e.getPeca() == null && e.getVeiculo() == null ){
			throw new Exception("Voc� deve inserir uma pe�a ou um ve�culo");

		}
		
		if (e.getTipoDeAnuncio().equals("Veiculo")) {
			if (e.getCor().equals("")) {
				throw new Exception("Esscolha uma cor");
			}

			if (e.getKmRodados() < 0) {
				throw new Exception("KM rodados n�o podem ser menor que 0(zero)");
			}

			if (e.getCombustivel() == null) {
				throw new Exception("Selecione o combustivel utilizado pelo carro");
			}

			if (e.getMotor() == null) {
				throw new Exception("Identifique o motor do veiculo");
			}

			if (e.getPortas() < 2) {
				throw new Exception("O n�mero de portas tem que ser maior ou igual � dois (2)");
			}
			
		}
		

		if (e.getData() == null) {
			throw new Exception("H� um problema ao inserir a data para esse an�ncio");
		}

		if (e.getDescricao().length() < 20 ) {
			throw new Exception("Coloque uma descri��o mais completa");
		}

		if (e.getDescricao().length() > 1000 ) {
			throw new Exception("A descri��o n�o pode passar de 1000 (mil) caracteres");
		}

		if (e.getValorFinal() <= 0) {
			throw new Exception("O valor final n�o pode ser 0 (zero)");
		}

		if (e.getFoto().size() < 1) {
			throw new Exception("O anuncio n�o pode ser realizado sem foto(s) do produto");
		}
		
		if(e.getFoto().size() > 20){
			throw new Exception("O n�mero m�ximo de fotos por anuncio � vinte (20)");
		}
		new DAOGenerico<Anuncio>().salvar(e);
	}

	@Override
	public Anuncio getObjectbyid(Long id, Anuncio e) throws Exception {
		if (id == 0) {
			throw new Exception("Objeto n�o encontrado!");
		}

		return new DAOGenerico<Anuncio>().getObjetoById(id, e);
	}

	@Override
	public List<Anuncio> listar(String o) throws Exception {
		if (new DAOGenerico<Anuncio>().listar(o).isEmpty()) {
			throw new Exception("Nada consta no Banco");
		}

		return new DAOGenerico<Anuncio>().listar(o);
	}

	@Override
	public void deletar(Anuncio e) throws Exception {
		
		new DAOGenerico<Anuncio>().deletar(e, e.getId());
		
	}

	@Override
	public void editar(Anuncio e) throws Exception {
		if(e.getAnunciante() == null){
			throw new Exception("O Anunciante deve ser identificado!");
		}
		
		if(e.getPeca() == null && e.getVeiculo() == null ){
			throw new Exception("Voc� deve inserir uma pe�a ou um ve�culo");

		}
		
		if (e.getTipoDeAnuncio().equals("Veiculo")) {
			if (e.getCor().equals("")) {
				throw new Exception("Esscolha uma cor");
			}

			if (e.getKmRodados() < 0) {
				throw new Exception("KM rodados n�o podem ser menor que 0(zero)");
			}

			if (e.getCombustivel() == null) {
				throw new Exception("Selecione o combustivel utilizado pelo carro");
			}

			if (e.getMotor() == null) {
				throw new Exception("Identifique o motor do veiculo");
			}

			if (e.getPortas() < 2) {
				throw new Exception("O n�mero de portas tem que ser maior ou igual � dois (2)");
			}
			
		}
		

		if (e.getData() == null) {
			throw new Exception("H� um problema ao inserir a data para esse an�ncio");
		}

		if (e.getDescricao().length() < 20 ) {
			throw new Exception("Coloque uma descri��o mais completa");
		}

		if (e.getDescricao().length() > 1000 ) {
			throw new Exception("A descri��o n�o pode passar de 1000 (mil) caracteres");
		}

		if (e.getValorFinal() <= 0) {
			throw new Exception("O valor final n�o pode ser 0 (zero)");
		}

		if (e.getFoto().size() < 1) {
			throw new Exception("O anuncio n�o pode ser realizado sem foto(s) do produto");
		}
		
		if(e.getFoto().size() > 20){
			throw new Exception("O n�mero m�ximo de fotos por anuncio � vinte (20)");
		}
		
		new DAOGenerico<Anuncio>().editar(e);
		
	}

}
