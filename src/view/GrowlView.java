package view;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


public abstract class GrowlView {


	public static void saveMessage(Exception e) {
		FacesContext context = FacesContext.getCurrentInstance();

		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro", "Aviso: "+ e.getMessage()));
	}
	
	public static void sucesso(String palavra){
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Informação", palavra+" com sucesso"));
	}
	
	public static void criarMensagem(javax.faces.application.FacesMessage.Severity icon, String titulo, String mensagem){
		FacesContext context = FacesContext.getCurrentInstance();

		context.addMessage(null, new FacesMessage(icon, titulo, mensagem));
	}
}