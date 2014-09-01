/**
 * 
 */
package services;

import models.Licences;
import util.Factory;
import dao.LicencesDAO;

/**
 * @author Papangom
 *
 */
public class LicencesServices {

	private LicencesDAO licDAO = Factory.getLicencesDAO();
	
	public boolean addLicence(Licences l){
		return licDAO.addLicence(l);
	}
	
	public Licences getLicenceByCode(String code){
		return licDAO.getLicenceByCode(code);
	}
}
