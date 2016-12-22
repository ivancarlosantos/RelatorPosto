/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package relatorpostoeaton.business.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import relatorpostoeaton.business.jpa.exceptions.NonexistentEntityException;
import relatorpostoeaton.business.jpa.exceptions.PreexistingEntityException;
import relatorpostoeaton.entities.Colaborador;
import relatorpostoeaton.entities.ColaboradorPK;
import relatorpostoeaton.entities.Viagem;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

/**
 *
 * @author icarlos
 */
public class ColaboradorJpaController {

    public ColaboradorJpaController() {
        emf = Persistence.createEntityManagerFactory("RelatorPostoEatonPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Colaborador colaborador) throws PreexistingEntityException, Exception {
        /*if (colaborador.getColaboradorPK() == null) {
        colaborador.setColaboradorPK(new ColaboradorPK());
        }
        if (colaborador.getViagensMotoristaCollection() == null) {
        colaborador.setViagensMotoristaCollection(new ArrayList<Viagem>());
        }
        if (colaborador.getViagensPassageiroCollection() == null) {
        colaborador.setViagensPassageiroCollection(new ArrayList<Viagem>());
        }   */
        EntityManager em = null;
        try {
            System.out.println("===create===> -01");
            em = getEntityManager();
            System.out.println("===create===> 00");
            em.getTransaction().begin();
            /*List<Viagem> attachedViagensMotoristaCollection = new ArrayList<Viagem>();
            for (Viagem viagensMotoristaCollectionViagemToAttach : colaborador.getViagensMotoristaCollection()) {
            viagensMotoristaCollectionViagemToAttach = em.getReference(viagensMotoristaCollectionViagemToAttach.getClass(), viagensMotoristaCollectionViagemToAttach.getId());
            attachedViagensMotoristaCollection.add(viagensMotoristaCollectionViagemToAttach);
            }
            colaborador.setViagensMotoristaCollection(attachedViagensMotoristaCollection);
            List<Viagem> attachedViagensPassageiroCollection = new ArrayList<Viagem>();
            for (Viagem viagensPassageiroCollectionViagemToAttach : colaborador.getViagensPassageiroCollection()) {
            viagensPassageiroCollectionViagemToAttach = em.getReference(viagensPassageiroCollectionViagemToAttach.getClass(), viagensPassageiroCollectionViagemToAttach.getId());
            attachedViagensPassageiroCollection.add(viagensPassageiroCollectionViagemToAttach);
            }
            colaborador.setViagensPassageiroCollection(attachedViagensPassageiroCollection);   */
            System.out.println("===create===> 01");
            em.persist(colaborador);
            /*for (Viagem viagensMotoristaCollectionViagem : colaborador.getViagensMotoristaCollection()) {
            Colaborador oldMotoristaOfViagensMotoristaCollectionViagem = viagensMotoristaCollectionViagem.getMotorista();
            viagensMotoristaCollectionViagem.setMotorista(colaborador);
            viagensMotoristaCollectionViagem = em.merge(viagensMotoristaCollectionViagem);
            if (oldMotoristaOfViagensMotoristaCollectionViagem != null) {
            oldMotoristaOfViagensMotoristaCollectionViagem.getViagensMotoristaCollection().remove(viagensMotoristaCollectionViagem);
            oldMotoristaOfViagensMotoristaCollectionViagem = em.merge(oldMotoristaOfViagensMotoristaCollectionViagem);
            }
            }
            for (Viagem viagensPassageiroCollectionViagem : colaborador.getViagensPassageiroCollection()) {
            Colaborador oldMotoristaOfViagensPassageiroCollectionViagem = viagensPassageiroCollectionViagem.getMotorista();
            viagensPassageiroCollectionViagem.setMotorista(colaborador);
            viagensPassageiroCollectionViagem = em.merge(viagensPassageiroCollectionViagem);
            if (oldMotoristaOfViagensPassageiroCollectionViagem != null) {
            oldMotoristaOfViagensPassageiroCollectionViagem.getViagensPassageiroCollection().remove(viagensPassageiroCollectionViagem);
            oldMotoristaOfViagensPassageiroCollectionViagem = em.merge(oldMotoristaOfViagensPassageiroCollectionViagem);
            }
            } */
            System.out.println("===create===> 02");
            em.getTransaction().commit();
            System.out.println("===create===> 03");
        } catch (Exception ex) {
            if (findColaborador(colaborador.getColaboradorPK()) != null) {
                throw new PreexistingEntityException("Colaborador " + colaborador + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Colaborador colaborador) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Colaborador persistentColaborador = em.find(Colaborador.class, colaborador.getColaboradorPK());
            List<Viagem> viagensMotoristaCollectionOld = persistentColaborador.getViagensMotoristaCollection();
            List<Viagem> viagensMotoristaCollectionNew = colaborador.getViagensMotoristaCollection();
            List<Viagem> viagensPassageiroCollectionOld = persistentColaborador.getViagensPassageiroCollection();
            List<Viagem> viagensPassageiroCollectionNew = colaborador.getViagensPassageiroCollection();
            List<Viagem> attachedViagensMotoristaCollectionNew = new ArrayList<Viagem>();
            for (Viagem viagensMotoristaCollectionNewViagemToAttach : viagensMotoristaCollectionNew) {
                viagensMotoristaCollectionNewViagemToAttach = em.getReference(viagensMotoristaCollectionNewViagemToAttach.getClass(), viagensMotoristaCollectionNewViagemToAttach.getId());
                attachedViagensMotoristaCollectionNew.add(viagensMotoristaCollectionNewViagemToAttach);
            }
            viagensMotoristaCollectionNew = attachedViagensMotoristaCollectionNew;
            colaborador.setViagensMotoristaCollection(viagensMotoristaCollectionNew);
            List<Viagem> attachedViagensPassageiroCollectionNew = new ArrayList<Viagem>();
            for (Viagem viagensPassageiroCollectionNewViagemToAttach : viagensPassageiroCollectionNew) {
                viagensPassageiroCollectionNewViagemToAttach = em.getReference(viagensPassageiroCollectionNewViagemToAttach.getClass(), viagensPassageiroCollectionNewViagemToAttach.getId());
                attachedViagensPassageiroCollectionNew.add(viagensPassageiroCollectionNewViagemToAttach);
            }
            viagensPassageiroCollectionNew = attachedViagensPassageiroCollectionNew;
            colaborador.setViagensPassageiroCollection(viagensPassageiroCollectionNew);
            colaborador = em.merge(colaborador);
            for (Viagem viagensMotoristaCollectionOldViagem : viagensMotoristaCollectionOld) {
                if (!viagensMotoristaCollectionNew.contains(viagensMotoristaCollectionOldViagem)) {
                    viagensMotoristaCollectionOldViagem.setMotorista(null);
                    viagensMotoristaCollectionOldViagem = em.merge(viagensMotoristaCollectionOldViagem);
                }
            }
            for (Viagem viagensMotoristaCollectionNewViagem : viagensMotoristaCollectionNew) {
                if (!viagensMotoristaCollectionOld.contains(viagensMotoristaCollectionNewViagem)) {
                    Colaborador oldMotoristaOfViagensMotoristaCollectionNewViagem = viagensMotoristaCollectionNewViagem.getMotorista();
                    viagensMotoristaCollectionNewViagem.setMotorista(colaborador);
                    viagensMotoristaCollectionNewViagem = em.merge(viagensMotoristaCollectionNewViagem);
                    if (oldMotoristaOfViagensMotoristaCollectionNewViagem != null && !oldMotoristaOfViagensMotoristaCollectionNewViagem.equals(colaborador)) {
                        oldMotoristaOfViagensMotoristaCollectionNewViagem.getViagensMotoristaCollection().remove(viagensMotoristaCollectionNewViagem);
                        oldMotoristaOfViagensMotoristaCollectionNewViagem = em.merge(oldMotoristaOfViagensMotoristaCollectionNewViagem);
                    }
                }
            }
            for (Viagem viagensPassageiroCollectionOldViagem : viagensPassageiroCollectionOld) {
                if (!viagensPassageiroCollectionNew.contains(viagensPassageiroCollectionOldViagem)) {
                    viagensPassageiroCollectionOldViagem.setMotorista(null);
                    viagensPassageiroCollectionOldViagem = em.merge(viagensPassageiroCollectionOldViagem);
                }
            }
            for (Viagem viagensPassageiroCollectionNewViagem : viagensPassageiroCollectionNew) {
                if (!viagensPassageiroCollectionOld.contains(viagensPassageiroCollectionNewViagem)) {
                    Colaborador oldMotoristaOfViagensPassageiroCollectionNewViagem = viagensPassageiroCollectionNewViagem.getMotorista();
                    viagensPassageiroCollectionNewViagem.setMotorista(colaborador);
                    viagensPassageiroCollectionNewViagem = em.merge(viagensPassageiroCollectionNewViagem);
                    if (oldMotoristaOfViagensPassageiroCollectionNewViagem != null && !oldMotoristaOfViagensPassageiroCollectionNewViagem.equals(colaborador)) {
                        oldMotoristaOfViagensPassageiroCollectionNewViagem.getViagensPassageiroCollection().remove(viagensPassageiroCollectionNewViagem);
                        oldMotoristaOfViagensPassageiroCollectionNewViagem = em.merge(oldMotoristaOfViagensPassageiroCollectionNewViagem);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ColaboradorPK id = colaborador.getColaboradorPK();
                if (findColaborador(id) == null) {
                    throw new NonexistentEntityException("The colaborador with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ColaboradorPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Colaborador colaborador;
            try {
                colaborador = em.getReference(Colaborador.class, id);
                colaborador.getColaboradorPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The colaborador with id " + id + " no longer exists.", enfe);
            }
            List<Viagem> viagensMotoristaCollection = colaborador.getViagensMotoristaCollection();
            for (Viagem viagensMotoristaCollectionViagem : viagensMotoristaCollection) {
                viagensMotoristaCollectionViagem.setMotorista(null);
                viagensMotoristaCollectionViagem = em.merge(viagensMotoristaCollectionViagem);
            }
            List<Viagem> viagensPassageiroCollection = colaborador.getViagensPassageiroCollection();
            for (Viagem viagensPassageiroCollectionViagem : viagensPassageiroCollection) {
                viagensPassageiroCollectionViagem.setMotorista(null);
                viagensPassageiroCollectionViagem = em.merge(viagensPassageiroCollectionViagem);
            }
            em.remove(colaborador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Colaborador> findColaboradorEntities() {
        return findColaboradorEntities(true, -1, -1);
    }

    public Colaborador findColaboradorByTag(String tag) {
        Colaborador colab = null;
        try {
            EntityManager em = getEntityManager();
            System.out.println("<=====TagCardsColaborador====> \n" + tag);
            if (tag != null) {

            } else {
                System.out.println("Colaborador Não Encontrado...");
            }
            Query q = em.createQuery("select o from Colaborador as o where o.tag like ?1");
            q.setParameter(1, tag);
            colab = (Colaborador) q.getSingleResult();
        } catch (NoResultException nre) {
        }catch(NonUniqueResultException nure){

        }
        return colab;
    }

    public List<Colaborador> findColaboradorEntities(int maxResults, int firstResult) {
        return findColaboradorEntities(false, maxResults, firstResult);
    }

    private List<Colaborador> findColaboradorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Colaborador.class));
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

    public Colaborador findColaborador(ColaboradorPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Colaborador.class, id);
        } finally {
            em.close();
        }
    }

    public int getColaboradorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Colaborador> rt = cq.from(Colaborador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}

