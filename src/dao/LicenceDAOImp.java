/**
 * 
 */
package dao;

import java.io.Serializable;

import models.Licences;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import util.HibernateUtil;

/**
 * @author Papangom
 *
 */
public class LicenceDAOImp implements LicencesDAO, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Session session = null;

	@Override
	public Licences getLicenceByCode(String code) {
		Licences lic = null;
		if (session == null)
			session = HibernateUtil.getSessionFactory();
		Query q = session.createQuery("from Licences a where a.code= :code")
				.setString("code", code);
		lic = (Licences) q.uniqueResult();
		return lic;
	}

	@Override
	public boolean addLicence(Licences lic) {
		boolean flag;
        if (session == null)
			session = HibernateUtil.getSessionFactory();
        // Debuter une transaction
        try {
        	Transaction t = session.beginTransaction();
	        // Sauvegarde du centre dans la base
			session.save(lic);
			flag = true;
			t.commit();
        }
        catch(Exception e){
        	flag = false;
            session.beginTransaction().rollback();
        }
		
		
        return flag;
	}

}
