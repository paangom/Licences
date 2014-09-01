/**
 * 
 */
package models;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Papangom
 *
 */

@Entity
@Table(name="licences")
public class Licences  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private Integer licenceId;
	
	@Column(name = "code", columnDefinition = "varchar(150) default '' ", nullable = false, unique = true)
    private String code;
	
	@Column(name = "Date_Activation", columnDefinition = "varchar(30) ", nullable = false)
    private String date_Activation;
	
	@Column(name = "Duree", columnDefinition = "varchar(30)", nullable = false)
    private String duree;
	
	@Column(name = "Date_expiration", columnDefinition = "varchar(30)", nullable = false)
    private String date_Expiration;
	
	@ManyToOne
	(
			cascade={CascadeType.PERSIST, CascadeType.MERGE}
	)
	@JoinColumn(name="centre")
    private Centres centre;
	
	
	@ManyToOne
	(
			cascade={CascadeType.PERSIST, CascadeType.MERGE}
	)
	@JoinColumn(name="createlicence")
    private Users createlicence;

	/**
	 * 
	 */
	public Licences() {
	}

	/**
	 * @param code
	 * @param date_Activation
	 * @param duree
	 * @param centerCode
	 */
	public Licences(Centres centre, String code, String date_Activation, String duree,
			String date_Expiration, Users createlicence) {
		this.code = code;
		this.date_Activation = date_Activation;
		this.duree = duree;
		this.date_Expiration = date_Expiration;
		this.createlicence = createlicence;
	}

	public Integer getLicenceId() {
		return licenceId;
	}

	public void setLicenceId(Integer licenceId) {
		this.licenceId = licenceId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	

	public String getDate_Activation() {
		return date_Activation;
	}

	public void setDate_Activation(String date_Activation) {
		this.date_Activation = date_Activation;
	}

	public String getDuree() {
		return duree;
	}

	public void setDuree(String duree) {
		this.duree = duree;
	}

	public String getDate_Expiration() {
		return date_Expiration;
	}

	public void setDate_Expiration(String date_Expiration) {
		this.date_Expiration = date_Expiration;
	}

	public Centres getCentre() {
		return centre;
	}

	public void setCentre(Centres centre) {
		this.centre = centre;
	}

	public Users getCreatelicence() {
		return createlicence;
	}

	public void setCreatelicence(Users createlicence) {
		this.createlicence = createlicence;
	}

	
}
