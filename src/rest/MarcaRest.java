package rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import facade.MarcaFacade;
import vo.Marca;

@Path("/marca")
public class MarcaRest {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Marca> listarMarcas() {
		List<Marca> marcas = null;
		try {
			marcas = new MarcaFacade().carregarTudo();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return marcas;
	}

}
