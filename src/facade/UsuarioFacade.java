package facade;

import java.util.List;

import bo.TelefoneBO;
import bo.UsuarioBO;
import vo.Telefone;
import vo.Usuario;

public class UsuarioFacade implements Facade<Usuario> {

	private UsuarioBO usuariobo;
	private TelefoneBO telefonebo;
	

	@Override
	public void inserir(Usuario vo) throws Exception {	
		for (Telefone telefone : vo.getTelefones()) {
			try {
				telefonebo.salvar(telefone);
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}
		try {
			usuariobo.salvar(vo);
		} catch (Exception e) {
			for (Telefone telefone : vo.getTelefones()) {
				telefonebo.deletar(telefone);
			}
			throw new Exception(e.getMessage());	
		}
	}

	@Override
	public List<Usuario> carregarTudo() throws Exception {
		return usuariobo.listar("Usuario");
		
	}
	
	public UsuarioFacade() {
		usuariobo = new UsuarioBO();
		telefonebo = new TelefoneBO();
	}

	public boolean autenticar(Usuario u) throws Exception {
		UsuarioBO ubo = new UsuarioBO();
		return ubo.autenticar(u);
	}

	@Override
	public Usuario trazerUm(Long id, Usuario classe) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void alterar(Usuario vo) throws Exception {
		for (Telefone telefone : vo.getTelefones()) {
			try {
				telefonebo.salvar(telefone);
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}
		
		try {
			usuariobo.editar(vo);
		} catch (Exception e) {
			for (Telefone telefone : vo.getTelefones()) {
				telefonebo.deletar(telefone);
			}
			throw new Exception(e.getMessage());	
		}
	}

	@Override
	public void excluir(Usuario vo) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
