package JUnit;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import vo.Telefone;
import vo.Usuario;
import static org.junit.Assert.fail;

public class JUnitUsuario {

	@Test
	public void cadastrarCliente(){
		
		Usuario u = new Usuario();
		u.setNome("Jos� Carlos");
		u.setLogin("Z�");
		u.setEmail("Zecarlos@yahoo.com");
		Telefone telefone = new Telefone();
		telefone.setDDD(41);
		telefone.setNumero(33333333);
		List<Telefone> telefones = new ArrayList<Telefone>();
		telefones.add(telefone);
		u.setTelefones(telefones);
		u.setTipo("Pessoa F�sica");
		
		if (u.getId() == null) {
//			fail("Id n�o gerado");
		}
		
		Usuario u1 = new Usuario();
		u1.setLogin("Z�");
		u1.setEmail("Zecarlos@yahoo.com");
		Telefone telefone1 = new Telefone();
		telefone1.setDDD(41);
		telefone1.setNumero(33333333);
		List<Telefone> telefones1 = new ArrayList<Telefone>();
		telefones1.add(telefone1);
		u1.setTelefones(telefones1);
		u1.setTipo("Pessoa F�sica");
		
		if (u1.getNome() == null) {
//			fail("Nome precisa ser identificado");
		}
		
		Usuario u2 = new Usuario();
		u2.setLogin("Z�");
		u2.setEmail("Zecarlos@yahoo.com");
		List<Telefone> telefones2 = new ArrayList<Telefone>();
		u1.setTipo("Pessoa F�sica");
		
		if (telefones2.size() == 0) {
			fail("O cliente precisa ter pelo menos um telefone");
		}
	}
}
