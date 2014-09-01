/**
 * 
 */
package beans;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import models.Centres;
import models.Licences;
import services.CentreServices;
import services.LicencesServices;
import util.MyUtil;
import util.Tools;

/**
 * @author Papangom
 *
 */
public class Generateur {
	
	private List<Centres> centre;
	private String date_deb;
	private String date_fin;
	
	private Licences lic = null;
	private Centres addCentre = null;
	private Centres selectCenter = null;
	private LicencesServices licService = new LicencesServices();
	private CentreServices cenService = new CentreServices();
	
	private Date datDeb;
	private Date dateFin;
	private Date dateInstall;
	
	private String code;
	
	private final String sa = "SYSCOWEB";
	private final String service = "SERVICE";
	private final String etat = "ETATCIVIL";
	
	public Generateur(){
		if (this.addCentre == null) {
            this.addCentre = new Centres();
        }
	}
	
	
	public String getDate_deb() {
		return date_deb;
	}
	
	public void setDate_deb(String date_deb) {
		this.date_deb = date_deb;
	}
	
	public String getDate_fin() {
		return date_fin;
	}
	
	public void setDate_fin(String date_fin) {
		this.date_fin = date_fin;
	}


	public List<Centres> getCentre() {
		centre = cenService.getAllCentre();
		return centre;
	}


	public void setCentre(List<Centres> centre) {
		this.centre = centre;
	}


	public Date getDateFin() {
		return dateFin;
	}


	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}


	public Date getDatDeb() {
		return datDeb;
	}


	public void setDatDeb(Date datDeb) {
		this.datDeb = datDeb;
	}
	
	@SuppressWarnings("static-access")
	public void generer(){
		if(!"".equals(this.getCode()) && !"".equals(this.getDatDeb()) && !"".equals(this.getDateFin())){
			String datedeb = Tools.formatDay(this.getDatDeb());
			String datefin = Tools.formatDay(this.getDateFin());
			String duree = Tools.nbJours(datedeb, datefin)+"";
			Centres cen = cenService.getCenter(this.getCode().trim());
			
			String age = Tools.nbJours(cen.getDate_Install(), Tools.getCurrentDateDDMMYYYY())+"";
			String dureeFormat = "";
			String ageFormat = "";
			if(duree.length() < 6 && age.length() < 9){
				for(int i = duree.length(); i<5; i++)
					dureeFormat = dureeFormat+"0";
				dureeFormat = dureeFormat+duree;
				
				for(int i = age.length(); i<8; i++)
					ageFormat = ageFormat+"0";
				ageFormat = ageFormat+age;
				
				String mot = sa+dureeFormat+service+datedeb.replaceAll("/", "")+etat+datefin.replaceAll("/", "")+service+ageFormat+"SYSCO"+this.getCode();
				
				if(mot.length() == 68){
					String code = Tools.Chiffertext(mot);
					lic = new Licences(cen, code, Tools.formatDay(this.getDatDeb()), duree, Tools.formatDay(this.getDateFin()), MyUtil.getUserLogged());
					if(licService.addLicence(lic)){
		                 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,  code, null);
		                 FacesContext context = FacesContext.getCurrentInstance();
		                 context.getCurrentInstance().addMessage(null, message);
		             	
		             	context.getExternalContext().getFlash().setKeepMessages(true);
					}
					else{
		                 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "Impossible de générer une licence, veuillez recommencer la procédure s'il vous plait.", null);
		                 FacesContext context = FacesContext.getCurrentInstance();
		                 context.getCurrentInstance().addMessage(null, message);
		             	
		             	context.getExternalContext().getFlash().setKeepMessages(true);
					}
				}
				else{
	                 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "Probléme de génération de code. Veuillez recommencer s'il vous plait.", null);
	                 FacesContext context = FacesContext.getCurrentInstance();
	                 context.getCurrentInstance().addMessage(null, message);
	             	
	             	context.getExternalContext().getFlash().setKeepMessages(true);
				}
				
				
			}
			else{
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "Veuillez diminuer la durée de validité de la licence.", null);
                FacesContext context = FacesContext.getCurrentInstance();
                context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);
			}
			
			
		}
		else{
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "Veuillez reprendre la procédure et remplir tous les champs.", null);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getCurrentInstance().addMessage(null, message);
        	
        	context.getExternalContext().getFlash().setKeepMessages(true);
		}
		
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public Centres getAddCentre() {
		return addCentre;
	}


	public void setAddCentre(Centres addCentre) {
		this.addCentre = addCentre;
	}


	public Centres getSelectCenter() {
		return selectCenter;
	}


	public void setSelectCenter(Centres selectCenter) {
		this.selectCenter = selectCenter;
	}
	
	public String createCentre(){
		return "/centres?faces-redirect=true";
	}
	
	public String createCode(){
		return "/home?faces-redirect=true";
	}
	
	public String createUsers(){
		return "/liste_utilisateur?faces-redirect=true";
	}
	
	@SuppressWarnings("static-access")
	public void createCenter(){
		
		this.addCentre.setDate_Install(Tools.formatDay(this.getDateInstall()));
		if(cenService.createCenter(this.addCentre)){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,  "Centre d'Etat Civil ajouté avec succès.", null);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getCurrentInstance().addMessage(null, message);
        	
        	context.getExternalContext().getFlash().setKeepMessages(true);
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "Le centre d'Etat Civil n'a pas été ajouté.", null);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getCurrentInstance().addMessage(null, message);
        	
        	context.getExternalContext().getFlash().setKeepMessages(true);
		}
	}
	
	@SuppressWarnings("static-access")
	public void updateCenter(){
		if(cenService.modifyCenter(this.selectCenter)){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,  "Les modifications du centre ont été apportées avec succès.", null);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getCurrentInstance().addMessage(null, message);
        	
        	context.getExternalContext().getFlash().setKeepMessages(true);
		}
		else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "Impossible d'apporter des modifications sur ce centre.", null);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getCurrentInstance().addMessage(null, message);
        	
        	context.getExternalContext().getFlash().setKeepMessages(true);
		}
	}


	public Date getDateInstall() {
		return dateInstall;
	}


	public void setDateInstall(Date dateInstall) {
		this.dateInstall = dateInstall;
	}

}
