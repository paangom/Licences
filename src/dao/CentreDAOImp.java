/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;

import models.Centres;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import util.HibernateUtil;

/**
 *
 * @author sambasow
 */
public class CentreDAOImp implements CentreDAO {

    private Session session = null;

    @Override
    public boolean createCenter(Centres c) {
        boolean flag;
        if (session == null)
			session = HibernateUtil.getSessionFactory();
        // Debuter une transaction
		Transaction t = session.beginTransaction();

		// Sauvegarde du centre dans la base
		session.save(c);
		flag=true;
		t.commit();
		
        return flag;
    }

    @Override
    public Centres getCenter(String code) {
    	if (session == null)
			session = HibernateUtil.getSessionFactory();
		Query q = session.createQuery("from Centres c where c.centerCode = :code")
				.setString("code", code);
		Centres c = (Centres) q.uniqueResult();
		return c;
    }

    @Override
    public Centres findById(int id) {
    	if (session == null)
			session = HibernateUtil.getSessionFactory();
		Query q = session.createQuery("from Centres c where c.id= :code")
				.setInteger("code", id);
		Centres c = (Centres) q.uniqueResult();
		return c;
    }

    @Override
    public boolean deleteCenter(String code) {
    	session = HibernateUtil.getSessionFactory();
		session.beginTransaction();
		Centres c = (Centres) session.get(Centres.class, code);
		session.delete(c);
		session.beginTransaction().commit();
		session.close();
		return true;
    }

    @Override
    public boolean modifyCenter(Centres c) {
    	boolean flag=false;
    	// Récupération d'une session Hibernate
    			if (session == null)
    				session = HibernateUtil.getSessionFactory();
    			// Début de la transaction Hibernate
    			Transaction tx = session.beginTransaction();

    			// Cherche s'il existe dans la BDD
    			Centres cc = this.findById(c.getCenterId());

    			// Si l'acte n'existe pas dans la BDD : sauvegarde
    			if (cc == null){
    				session.save(c);
    				flag=true;
    			}
    			// Sinon on est dans le cas d'une modification de l'acte : merge
    			else{
    				session.merge(c);
    				flag=true;
    			}

    			// Fin de transaction : synchronisation du contexte de persistance
    			tx.commit();
    			return flag;

    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Centres> getAllCentre() {
    	session = HibernateUtil.getSessionFactory();
		session.beginTransaction();
		List<Centres> l = session.createQuery("from Centres c").list();
		return l;
    }

    @Override
    public Centres getCentre() {
    	session = HibernateUtil.getSessionFactory();
		session.beginTransaction();
		Query q = session.createQuery("from Centres c");
		Centres c = (Centres) q.uniqueResult();
		return c;
    }

    @SuppressWarnings("unchecked")
	@Override
    public boolean verifyCentre() {
    	session = HibernateUtil.getSessionFactory();

		Query q = session.createQuery("from Centres c");
		List<Centres> l = q.list();
		if (l.size() != 0)
			return true;
		else
			return false;
    }

	@Override
	public boolean viderCentre() {
		
		boolean flag;
        if (session == null) {
            session = HibernateUtil.getSessionFactory();
        }
        try {
        	Transaction tx = session.beginTransaction();
    		String hqlDelete = "truncate table Centres";
    		session.createSQLQuery( hqlDelete )
                   .executeUpdate();
                    tx.commit();
            flag = true;
        } catch (Exception e) {
            flag = false;
            session.beginTransaction().rollback();
        }
        return flag;
	}



}
