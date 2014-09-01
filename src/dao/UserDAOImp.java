/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.util.List;

import models.Users;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import util.HibernateUtil;

/**
 *
 * @author sambasow
 */
@SuppressWarnings("serial")
public class UserDAOImp implements UserDAO, Serializable {

    private Session session = null;



    @Override
    public Users login(Users u) {

        Users model = this.findByUserName(u.getUserUserName());
        if (model != null) {
            if (!model.getUserPassword().equals(u.getUserPassword())) {
                model = null;
            }
        }
        return model;
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Users> findAll() {
    	if (session == null)
			session = HibernateUtil.getSessionFactory();

		Query q = session.createQuery("from Users ");

		List<Users> list = q.list();
		return list;
    }

    @Override
    public boolean create(Users u) {
        boolean flag;
        if (session == null) {
            session = HibernateUtil.getSessionFactory();
        }
        try {
            session.beginTransaction();
            session.save(u);
            session.beginTransaction().commit();
            flag = true;
        } catch (Exception e) {
            flag = false;
            session.beginTransaction().rollback();
        }
        return flag;
    }

    @Override
    public boolean update(Users u) {
        boolean flag;
        if (session == null)
			session = HibernateUtil.getSessionFactory();
		// D�but de la transaction Hibernate
		Transaction tx = session.beginTransaction();
		// Cherche s'il existe d�j� dans la BDD
		Users a = this.getUserById(u.getUserId());
		
		// Si l'acte n'existe pas dans la BDD : sauvegarde
		if (a == null){
			session.save(u);
			flag=true;
		}
		// Sinon on est dans le cas d'une modification de l'acte : merge
		else{
			session.merge(u);
			flag=true;
		}
		tx.commit();
        return flag;
    }


    @Override
    public Users findByUserName(String uname) {
    	// Recup�ration d'une session
    			if (session == null)
    				session = HibernateUtil.getSessionFactory();

    			// Requete de recuperation du User correspondant au userName donn�
    			Query q = session.createQuery("from Users u where u.userUserName = :userName")
    					.setString("userName", uname);

    			// Recuperation et renvoi du resultat unique
    			Users u = (Users) q.uniqueResult();

    			return u;
    }

    @Override
    public Users getUserById(int id) {
    	if (session == null)
			session = HibernateUtil.getSessionFactory();

		// Requete de recuperation du User correspondant au userName donn�
		Query q = session.createQuery("from Users u where u.id = :id ")
				.setInteger("id", id);

		// Recuperation et renvoi du resultat unique
		Users u = (Users) q.uniqueResult();

		return u;
    }

	@Override
	public boolean viderUsers() {
		
		 boolean flag;
	        if (session == null) {
	            session = HibernateUtil.getSessionFactory();
	        }
	        try {
	        	Transaction tx = session.beginTransaction();
	    		String hqlDelete = "truncate table Users";
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

	@Override
	public boolean deleteUser(int id) {
		
		boolean flag;
        if (session == null) {
            session = HibernateUtil.getSessionFactory();
        }
        Users user = getUserById(id);
        try {
            session.beginTransaction();
            session.merge(user);
            session.beginTransaction().commit();
            flag = true;
        } catch (Exception e) {
            flag = false;
            session.beginTransaction().rollback();
        }
        return flag;
	}

	@Override
	public boolean reactiverUser(int id) {
		
		boolean flag;
        if (session == null) {
            session = HibernateUtil.getSessionFactory();
        }
        Users user = findUserDel(id);
        user.setEtat(true);
        try {
            session.beginTransaction();
            session.merge(user);
            session.beginTransaction().commit();
            flag = true;
        } catch (Exception e) {
            flag = false;
            session.beginTransaction().rollback();
        }
        return flag;
	}

	@Override
	public Users findUserDel(int id) {
		
		if (session == null)
			session = HibernateUtil.getSessionFactory();

		// Requete de recuperation du User correspondant au userName donn�
		Query q = session.createQuery("from Users u where u.id = :id and u.etat = :etat")
				.setInteger("id", id).setBoolean("etat", false);

		// Recuperation et renvoi du resultat unique
		Users u = (Users) q.uniqueResult();

		return u;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int lastUser() {
		
		if (session == null)
			session = HibernateUtil.getSessionFactory();

		Query q = session.createQuery("from Users");

		List<Users> list = q.list();
		int x = list.size()-1;
		return x;
	}

}
