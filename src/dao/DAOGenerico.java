package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class DAOGenerico<E> {

	public void salvar(E e) {
		EntityManager em = Conexao.getEntityManager();
		em.getTransaction().begin();

		em.persist(e);

		em.getTransaction().commit();

	}

	@SuppressWarnings("unchecked")
	public List<E> listar(String o) {
		EntityManager em = Conexao.getEntityManager();
		Query q = em.createQuery("Select o From " + o + " o");
		return q.getResultList();
	}

	
	@SuppressWarnings("unchecked")
	public E getObjetoById(Long id, E classe) {
		EntityManager em = Conexao.getEntityManager();
		return (E) em.find(classe.getClass(), id);
	}

	
	public void editar(E e) {
		EntityManager em = Conexao.getEntityManager();
		em.getTransaction().begin();
		em.merge(e);
		em.getTransaction().commit();
	}

	public void deletar(E classe, Long id){
		EntityManager em = Conexao.getEntityManager();
		em.getTransaction().begin();

		em.remove(em.getReference(classe.getClass(), id));

		em.getTransaction().commit();
	}
	
}
