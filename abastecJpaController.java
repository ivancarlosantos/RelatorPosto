/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package relatorpostoeaton.business.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import relatorpostoeaton.business.jpa.exceptions.NonexistentEntityException;
import relatorpostoeaton.entities.Abastecimento;

/**
 *
 * @author icarlos
 */
public class AbastecimentoJpaController {

    public AbastecimentoJpaController() {
        emf = Persistence.createEntityManagerFactory("RelatorPostoEatonPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Abastecimento abastecimento) {
        EntityManager em = null;
        try {
           System.out.println("===create===> -01");
            em = getEntityManager();
             System.out.println("===create===> 00");
            em.getTransaction().begin();
             System.out.println("===create===> 01");
            em.persist(abastecimento);
             System.out.println("===create===> 02");
            em.getTransaction().commit();
             System.out.println("===create===> 03");
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Abastecimento abastecimento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            System.out.println("===create===> -01");
            em = getEntityManager();
            System.out.println("===create===> 00");
            em.getTransaction().begin();
            System.out.println("===create===> 01");
            abastecimento = em.merge(abastecimento);
            System.out.println("===create===> 02");
            em.getTransaction().commit();
            System.out.println("===create===> 03 ");
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = abastecimento.getId();
                if (findAbastecimento(id) == null) {
                    throw new NonexistentEntityException("The abastecimento with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Abastecimento abastecimento;
            try {
                abastecimento = em.getReference(Abastecimento.class, id);
                abastecimento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The abastecimento with id " + id + " no longer exists.", enfe);
            }
            em.remove(abastecimento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Abastecimento> findAbastecimentoEntities() {
        return findAbastecimentoEntities(true, -1, -1);
    }

    public List<Abastecimento> findAbastecimentoEntities(int maxResults, int firstResult) {
        return findAbastecimentoEntities(false, maxResults, firstResult);
    }

    @SuppressWarnings("unchecked")
    private List<Abastecimento> findAbastecimentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Abastecimento.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Abastecimento findAbastecimento(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Abastecimento.class, id);
        } finally {
            em.close();
        }
    }

    @SuppressWarnings("unchecked")
    public int getAbastecimentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            @SuppressWarnings("unchecked")
            Root<Abastecimento> rt = cq.from(Abastecimento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}

