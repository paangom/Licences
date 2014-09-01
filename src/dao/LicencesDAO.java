/**
 * 
 */
package dao;

import models.Licences;

/**
 * @author Papangom
 *
 */
public interface LicencesDAO {
	
	public Licences getLicenceByCode(String code);
	
	public boolean addLicence(Licences lic);
	
	

}
