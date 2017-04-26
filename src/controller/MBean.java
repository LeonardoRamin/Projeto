package controller;

import java.util.List;

public interface MBean<E> {

	public void salvar();
	public E getObject();
	public List<E> listar();
	public String deletar();
}
