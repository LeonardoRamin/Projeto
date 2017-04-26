package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import vo.Marca;

@FacesConverter(value="converteMarca", forClass= Marca.class)
public class ConverteMarca implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		System.out.println("Convertor "+arg2);
		if (arg2 != null && !arg2.isEmpty()) {
			System.out.println("Entrou");
			System.out.println((Marca) arg1.getAttributes().get(arg2)); 
			return (Marca) arg1.getAttributes().get(arg2);
		}
		return null;
	}

	
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 instanceof Marca) {
			System.out.println("String "+((Marca) arg2).getId());
			Marca entity= (Marca) arg2;
            if (entity != null  && entity.getId() != null) {
                arg1.getAttributes().put( entity.getId().toString(), entity);
                return entity.getId().toString();
            }
		}
		System.out.println("Vazio");
		return "";
	}

}
