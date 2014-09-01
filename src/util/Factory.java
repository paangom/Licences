/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import dao.CentreDAO;
import dao.CentreDAOImp;
import dao.LicenceDAOImp;
import dao.LicencesDAO;
import dao.UserDAO;
import dao.UserDAOImp;


/**
 *
 * @author sambasow
 */
public class Factory {
	
	
	public static CentreDAO getCentreDAO() {
		return new CentreDAOImp();
	}
	
	public static LicencesDAO getLicencesDAO(){
		return new LicenceDAOImp();
	}
	
	public static UserDAO getUserDAO(){
		return new UserDAOImp();
	}

}