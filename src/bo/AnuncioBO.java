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
			throw new Exception("Você deve inserir uma peça ou um veículo");

		}
		
		if (e.getTipoDeAnuncio().equals("Veiculo")) {
			if (e.getCor().equals("")) {
				throw new Exception("Esscolha uma cor");
			}

			if (e.getKmRodados() < 0) {
				throw new Exception("KM rodados não podem ser menor que 0(zero)");
			}

			if (e.getCombustivel() == null) {
				throw new Exception("Selecione o combustivel utilizado pelo carro");
			}

			if (e.getMotor() == null) {
				throw new Exception("Identifique o motor do veiculo");
			}

			if (e.getPortas() < 2) {
				throw new Exception("O número de portas tem que ser maior ou igual à dois (2)");
			}
			
		}
		

		if (e.getData() == null) {
			throw new Exception("Há um problema ao inserir a data para esse anúncio");
		}

		if (e.getDescricao().length() < 20 ) {
			throw new Exception("Coloque uma descrição mais completa");
		}

		if (e.getDescricao().length() > 1000 ) {
			throw new Exception("A descrição não pode passar de 1000 (mil) caracteres");
		}

		if (e.getValorFinal() <= 0) {
			throw new Exception("O valor final não pode ser 0 (zero)");
		}

		if (e.getFoto().size() < 1) {
			throw new Exception("O anuncio não pode ser realizado sem foto(s) do produto");
		}
		
		if(e.getFoto().size() > 20){
			throw new Exception("O número máximo de fotos por anuncio é vinte (20)");
		}
		new DAOGenerico<Anuncio>().salvar(e);
	}

	@Override
	public Anuncio getObjectbyid(Long id, Anuncio e) throws Exception {
		if (id == 0) {
			throw new Exception("Objeto não encontrado!");
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
			throw new Exception("Você deve inserir uma peça ou um veículo");

		}
		
		if (e.getTipoDeAnuncio().equals("Veiculo")) {
			if (e.getCor().equals("")) {
				throw new Exception("Esscolha uma cor");
			}

			if (e.getKmRodados() < 0) {
				throw new Exception("KM rodados não podem ser menor que 0(zero)");
			}

			if (e.getCombustivel() == null) {
				throw new Exception("Selecione o combustivel utilizado pelo carro");
			}

			if (e.getMotor() == null) {
				throw new Exception("Identifique o motor do veiculo");
			}

			if (e.getPortas() < 2) {
				throw new Exception("O número de portas tem que ser maior ou igual à dois (2)");
			}
			
		}
		

		if (e.getData() == null) {
			throw new Exception("Há um problema ao inserir a data para esse anúncio");
		}

		if (e.getDescricao().length() < 20 ) {
			throw new Exception("Coloque uma descrição mais completa");
		}

		if (e.getDescricao().length() > 1000 ) {
			throw new Exception("A descrição não pode passar de 1000 (mil) caracteres");
		}

		if (e.getValorFinal() <= 0) {
			throw new Exception("O valor final não pode ser 0 (zero)");
		}

		if (e.getFoto().size() < 1) {
			throw new Exception("O anuncio não pode ser realizado sem foto(s) do produto");
		}
		
		if(e.getFoto().size() > 20){
			throw new Exception("O número máximo de fotos por anuncio é vinte (20)");
		}
		
		new DAOGenerico<Anuncio>().editar(e);
		
	}

}
