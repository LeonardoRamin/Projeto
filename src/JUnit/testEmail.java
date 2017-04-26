package JUnit;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;

import bo.EnviarEmail;

public class testEmail {

	public static void main(String[] args) throws MessagingException {
		EnviarEmail em = new EnviarEmail();
		em.setDestinatario("leonardoramin@outlook.com");
		Date date = new Date();
		String data1, hora;
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		data1 = formato.format(date);
		formato = new SimpleDateFormat("HH:mm");
		hora = formato.format(date);
		String data = data1+" "+hora;
		em.setAssunto("Testando "+data);
		em.setAssinatura("<br/><b>Leonardo</b>");
		em.setMensagem("Sucesso ao utilizar javaMail para enviar email"+em.getAssinatura());
		System.out.println("Entrou no método");
		try {
			em.enviar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
