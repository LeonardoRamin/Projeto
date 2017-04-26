package controller;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import facade.AnuncioFacade;
import vo.Anuncio;
 
@ManagedBean
@ViewScoped
public class PAV implements Serializable {
     
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Anuncio anuncio;

	private List<Anuncio> anuncios;
     
    private List<Anuncio> filteredAnuncios;
    
    
    @ManagedProperty(value="#{mBeanLogin}")
    private MBeanLogin mBeanLogin;

    
    public MBeanLogin getmBeanLogin() {
		return mBeanLogin;
	}

	public void setmBeanLogin(MBeanLogin mBeanLogin) {
		this.mBeanLogin = mBeanLogin;
	}


	
    public void init() {
    	List<Anuncio> dao = new ArrayList<Anuncio>();
    	boolean igual = false;
        try {
			dao = new AnuncioFacade().carregarTudo();
		} catch (Exception e) {
			System.out.println("Aviso: "+e.getMessage());
		}
        if (dao != null) {
        	for (Anuncio anuncio : dao) {
        		if (anuncio.getTipoDeAnuncio().equals("Veículo")) {
        			if (mBeanLogin.getUsuario().getAnuncios() != null) {
        				for (Anuncio au : mBeanLogin.getUsuario().getAnuncios()) {
    						if (anuncio.equals(au)) {
    							igual = true;
    						}
    					}
        				
            			if (!igual) {
            				anuncios.add(anuncio);
    					}
					}
        			else{
        				anuncios.add(anuncio);
        			}
        			
        			igual = false;
        		}
        	}
        }
        if(anuncio.getAcessorios() != null){
        	System.out.println("Anuncios "+Arrays.toString(anuncios.toArray()));
        }
        
    }
     
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean filterByPrice(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if(filterText == null||filterText.equals("")) {
            return true;
        }
         
        if(value == null) {
            return false;
        }
         
        return ((Comparable) value).compareTo(Integer.valueOf(filterText)) > 0;
    }
     
    public Set<String> getBrands() {
    	Set<String> marcas = new HashSet<String>();
    	for (Anuncio anuncio : anuncios) {
			marcas.add(anuncio.getVeiculo().getMarca().getNome());
		}
        return marcas;
    }
     
    public Set<String> getColors() {
    	Set<String> cores = new HashSet<String>();
    	for (Anuncio anuncio : anuncios) {
			cores.add(anuncio.getCor());
		}
        return cores;
    }
     
    public Anuncio getAnuncio() {
		return anuncio;
	}

	public void setAnuncio(Anuncio anuncio) {
		this.anuncio = anuncio;
	}

	public List<Anuncio> getAnuncios() {
        return anuncios;
    }
 
    public List<Anuncio> getFilteredAnuncios() {
        return filteredAnuncios;
    }
 
    public void setFilteredAnuncios(List<Anuncio> filteredAnuncios) {
        this.filteredAnuncios = filteredAnuncios;
    }
 
	public PAV() {
		anuncio = new Anuncio();
		anuncios = new ArrayList<Anuncio>();
		
	}
    
    public String abrir (){
    	mBeanLogin.setAnuncio(anuncio); 
    	return "vercarro";
    }
    
    public String editar(){
    	System.out.println("entrar metodo "+anuncio);
    	mBeanLogin.setAnuncio(anuncio); 
    	System.out.println("injetou no Login "+mBeanLogin.getAnuncio());
		return "AnunciarVeiculo.xhtml?faces-redirect=true";
    }
}