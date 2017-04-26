package bo;

import java.io.Serializable;
import java.util.List;

import converter.ReflectionUtil;
import dao.DAOGenerico;
import facade.UsuarioFacade;
import vo.Usuario;


public class UsuarioBO implements BO<Usuario>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void salvar(Usuario e) throws Exception {
		List<Usuario> usuarios = new UsuarioFacade().carregarTudo();
		
		if (usuarios != null) {
			for (Usuario usuario : usuarios) {
				if (e.getNome().equals(usuario.getNome())) {
					throw  new Exception("Acho que você já está cadastrado!");
				}
				
				if (e.getLogin().equals(usuario.getLogin())) {
					throw new Exception("Esse login já está cadastrado. Tente usar outro");
				}
				
				if (e.getEmail().equals(usuario.getEmail())) {
					throw new Exception("Esse email já consta em nossos cadastros");
				}
				
				if (e.getCadastroPessoal().equals(usuario.getCadastroPessoal())) {
					throw new Exception("Esse cpf/cnpj já consta em nossos cadastros");
				}
			}
		}
		
		if (e.getNome() == null) {
			throw new Exception("O Nome deve ser preenchido");
		}
		
		if (e.getEmail() == null) {
			throw new Exception("O E-mail deve estar preenchido");
		}
		
		if (e.getLogin() == null) {
			throw new Exception("Precisa-se de um Apelido como seu login");
		}
		
		if (e.getCidade().getId() == null) {
			throw new Exception("Qual a sua cidade?");
		}
		
		if (e.getSenha() == null) {
			throw new Exception("Uma Senha é obrigatória para fazer login");
		}
		
		if (e.getTelefones().isEmpty()) {
			throw new Exception("Pelo menos um número de telefone deve ser informado");
		}
		
		new DAOGenerico<Usuario>().salvar(e);
	}

	@Override
	public Usuario getObjectbyid(Long id, Usuario e) throws Exception {
		 if (id == 0) {
			throw new Exception("Objeto não encontrado!");
		}
		
		 return new DAOGenerico<Usuario>().getObjetoById(id, e);
	}

	@Override
	public List<Usuario> listar(String o) throws Exception {
		
		if (new DAOGenerico<Usuario>().listar(o).isEmpty()) {
			throw new Exception("Nada consta no Banco");
		}
		
		return new DAOGenerico<Usuario>().listar(o);
	}

	public boolean autenticar(Usuario u) throws Exception{
		List<Usuario> usuarios = new UsuarioFacade().carregarTudo();
		for (Usuario usuario : usuarios) {
			if (u.getLogin().equals(usuario.getLogin()) && u.getSenha().equals(usuario.getSenha())) {
				ReflectionUtil.copyAttributesFromTo(u, usuario);
				return true;
			}
		}
		return false;
	}

	@Override
	public void editar(Usuario e) throws Exception {
		
		if (e.getNome() == null || e.getNome().equals("")) {
			throw new Exception("O Nome deve ser preenchido");
		}
		
		if (e.getEmail() == null || e.getEmail().equals("")) {
			throw new Exception("O E-mail deve estar preenchido");
		}
		
		if (e.getLogin() == null || e.getLogin().equals("")) {
			throw new Exception("Precisa-se de um Apelido como seu login");
		}
		
		if (e.getSenha() == null) {
			throw new Exception("Uma Senha é obrigatória para fazer login");
		}
		
		if (e.getTelefones().isEmpty()) {
			throw new Exception("Pelo menos um número de telefone deve ser informado");
		}

		new DAOGenerico<Usuario>().editar(e);
		
	}

	@Override
	public void deletar(Usuario e) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
