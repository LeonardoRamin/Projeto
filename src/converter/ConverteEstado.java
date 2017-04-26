package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import vo.Estado;

@FacesConverter(value="converteEstado", forClass= Estado.class)
public class ConverteEstado implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		System.out.println("arg1 "+arg1);
		System.out.println("Convertor Estado "+arg2);
		if (arg2 != null && !arg2.isEmpty()) {
			System.out.println("Entrou");
			System.out.println("coverteEstado: "+(Estado) arg1.getAttributes().get(arg2)); 
			return (Estado) arg1.getAttributes().get(arg2);
		}
		return null;
	}

	
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 instanceof Estado) {
			System.out.println("String "+((Estado) arg2).getId());
			Estado entity= (Estado) arg2;
            if (entity != null  && entity.getId() != null) {
                arg1.getAttributes().put( entity.getId().toString(), entity);
                return entity.getId().toString();
            }
		}
		System.out.println("Vazio");
		return "";
	}

}
