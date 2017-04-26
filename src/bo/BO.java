package bo;

import java.util.List;


public interface BO<E> {
	public void salvar(E e) throws Exception;
	public void editar(E e) throws Exception;
	public E getObjectbyid(Long id, E e) throws Exception;
	public List<E> listar(String o) throws Exception;
	public void deletar(E e) throws Exception;
}
