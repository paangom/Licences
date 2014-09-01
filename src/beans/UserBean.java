/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import models.Centres;
import models.Users;

import org.apache.commons.codec.digest.DigestUtils;
import org.primefaces.context.RequestContext;

import services.CentreServices;
import services.UserServices;
import util.MyUtil;


public class UserBean implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Users userConnect = null;
    private Users userToCreate = null;
    private List<Users> usersCon ;
    private List<Users> users = null;
    private Centres c = null;
    private CentreServices cService = new CentreServices();
    private Users selectedUser = null;
    private UserServices uService = new UserServices();
    private boolean connected;
    private String ancienUserName;
    /**
     * Creates a new instance of userBean
     */

	public UserBean() {
        if(userToCreate == null){
            userToCreate = new Users();
        }
       
    }

    public Users getUserConnect() {
        userConnect = MyUtil.getUserLogged();
        return userConnect;
    }

    public List<Users> getUsers() {
        users = uService.getAllUser();
        return users;
    }
    
    
    

    public Centres getC() {
    	c = cService.getCentre();
		return c;
	}

	public void setC(Centres c) {
		this.c = c;
	}

	public Users getSelectedUser() {
        return selectedUser;
    }

    public Users getUserToCreate() {
        return userToCreate;
    }
    

    public void setSelectedUser(Users selectedUser) {
        this.selectedUser = selectedUser;
    }
    
    
    
    @SuppressWarnings({ "unused", "static-access" })
	public void updateProfil() {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String route = MyUtil.basePathLogin() + "login.xhtml";
        FacesMessage msg;
        boolean loggedIn;
        if(uService.findByUsername(this.ancienUserName) == null){
        	msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Le nom d'utilisateur "+this.getAncienUserName()+" n'est pas reconnu. Merci de saisir le bon nom.",null);
        	FacesContext context1 = FacesContext.getCurrentInstance();
            context1.getCurrentInstance().addMessage(null, msg);
        }
        else {
        	if(uService.findByUsername(this.userToCreate.getUserUserName()) == null){
        		this.userConnect.setUserUserName(this.userToCreate.getUserUserName());
        		this.userConnect.setUserPassword(DigestUtils.md5Hex(this.userToCreate.getUserPassword()));
        		if (this.uService.updateUser(this.userConnect)) {
                    loggedIn = false;
                    FacesContext contexts = FacesContext.getCurrentInstance();
                    HttpSession session = (HttpSession) contexts.getExternalContext().getSession(false);
                    session.invalidate();
                    context.addCallbackParam("isLogin", false);
                    //context.addCallbackParam("route", route);
                    String url = MyUtil.basePathLogin();
                    doRedirect(url);
                }
                else{
                    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erreur de de modification de votre profil",null);
                    FacesContext context1 = FacesContext.getCurrentInstance();
        	        context1.getCurrentInstance().addMessage(null, msg);
                 	
                 	context1.getExternalContext().getFlash().setKeepMessages(true);
                       
                }
        	}
        }
        
        
        
    }
    
    private void doRedirect(String url) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().redirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("static-access")
	public void btnCreateUser(){
        //UserDAO userDao = new UserDAOImp();
        String msg;
        
        this.userToCreate.setUserUserName(this.userToCreate.getUserPrenom().toLowerCase()+uService.lastUser());
        this.userToCreate.setUserPassword("passer");
        this.userToCreate.setEtat(true);
        String mdp=this.userToCreate.getUserPassword();
        this.userToCreate.setUserPassword(DigestUtils.md5Hex(this.userToCreate.getUserPassword()));
        if(uService.findByUsername(this.userToCreate.getUserUserName()) == null){
	        if(uService.addUser(this.userToCreate)){
	            msg = "Merci de bien noter les paramétres de connexion sont Login: "+this.userToCreate.getUserUserName()+" et Mot de passe: "+mdp;
	            this.userToCreate = new Users();
	            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO ,msg, null);
	            FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
	        	
	        	context.getExternalContext().getFlash().setKeepMessages(true);
	        }else{
	            msg = "Erreur de création de l'utilisateur. Certaines informations existent déjà (e-mail, cni)";
	             this.userToCreate = new Users();
	            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
	            FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
	        	
	        	context.getExternalContext().getFlash().setKeepMessages(true);
	        }
        }
        else{
        	 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ce nom d'utilisateur existe déja!", null);
	            FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
	        	
	        	context.getExternalContext().getFlash().setKeepMessages(true);
        }
        
    }
    
    @SuppressWarnings("static-access")
	public String saveFirstUser(){
    	String route = "";
    	//if(this.uService.viderUsers()){
    		this.userToCreate.setEtat(true);
    		this.userToCreate.setUserProfil("root");
    		String mdp = this.userToCreate.getUserPassword();
    		String log = this.userToCreate.getUserUserName();
    		this.userToCreate.setUserPassword(DigestUtils.md5Hex(this.userToCreate.getUserPassword()));
    		if(uService.addUser(this.userToCreate)){
    			this.userToCreate = new Users();
                route = "/login?faces-redirect=true";
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Le super utilisateur est crée. Nom d'utilisateur: "+log+" Mot de passe: "+mdp, null);
                FacesContext context = FacesContext.getCurrentInstance();
                context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);
            	FacesContext context1 = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) context1.getExternalContext().getSession(false);
                session.invalidate();
            }else{
            	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  "Impossible de créer cet utilisateur.", null);
                FacesContext context = FacesContext.getCurrentInstance();
                context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);
                
            }

    	return route;
    }
    
    @SuppressWarnings("static-access")
	public void btnUpdateUser(){
        
        String msg;
        String mdp=this.selectedUser.getUserPassword();
        this.selectedUser.setUserPassword(DigestUtils.md5Hex(this.selectedUser.getUserPassword()));
        if(uService.findByUsername(this.userToCreate.getUserUserName()) == null){
	        if(uService.updateUser(selectedUser)){
	            msg = "Votre nouveau login est: "+selectedUser.getUserUserName()+" et votre mot de passe est: "+mdp;
	            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,  msg, null);
	            FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
	        	
	        	context.getExternalContext().getFlash().setKeepMessages(true);
	        }else{
	            msg = "Impossible de faire la modification. Vérifier les informations saisies.";
	            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,  msg, null);
	            FacesContext context = FacesContext.getCurrentInstance();
	            context.getCurrentInstance().addMessage(null, message);
	        	
	        	context.getExternalContext().getFlash().setKeepMessages(true);
	        }
        }
        else{
        	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ce nom d'utilisateur existe déja!", null);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getCurrentInstance().addMessage(null, message);
        	
        	context.getExternalContext().getFlash().setKeepMessages(true);
        }
        
        
    }
    
    
    @SuppressWarnings("static-access")
	public void updateUser(){
        //this.selectedUser = (User) this.users.getRowData();
        String msg;
        if(uService.findByUsername(this.userConnect.getUserUserName()) != null) {
        	msg = "La modification a échoué, ce nom d'utilisateur existe déjà!";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,  msg, null);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getCurrentInstance().addMessage(null, message);
        }
        else {
        	this.userConnect.setUserPassword(DigestUtils.md5Hex(this.userConnect.getUserPassword()));
        	 if(uService.updateUser(this.userConnect)){
                 msg = "La modification de l'utilisateur s'est correctement passée.";
                 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,  msg, null);
                 FacesContext context = FacesContext.getCurrentInstance();
                 context.getCurrentInstance().addMessage(null, message);
             	
             	context.getExternalContext().getFlash().setKeepMessages(true);
             }else{
                 msg = "Erreur de modification de l'utilisateur";
                 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,  msg, null);
                 FacesContext context = FacesContext.getCurrentInstance();
                 context.getCurrentInstance().addMessage(null, message);
             	
             	context.getExternalContext().getFlash().setKeepMessages(true);
             }
        }
       
        
        
    }
    
    @SuppressWarnings("static-access")
	public void btnDeleteUser(){
    	String msg;
    	
        if(this.selectedUser.isEtat()){
        	this.selectedUser.setEtat(false);
        	
        	if(uService.deleteUser(this.selectedUser)){
        		msg = "L'utilisateur "+this.selectedUser.getUserPrenom()+" "+this.selectedUser.getUserNom()+" est désactivé avec succès.";
            }else{
                msg = "Erreur de désactivation de "+this.selectedUser.getUserPrenom()+" "+this.selectedUser.getUserNom();
            }
        }
        else{
        	this.selectedUser.setEtat(true);
        	this.selectedUser.setUserPassword(DigestUtils.md5Hex("passer"));
        	if(uService.deleteUser(this.selectedUser)){
                msg = "L'utilisateur "+this.selectedUser.getUserPrenom()+" "+this.selectedUser.getUserNom()+" est réactivé avec le login: "+this.selectedUser.getUserUserName()+" et mot de passe: passer.";
            }else{
                msg = "Erreur de réactivation de "+this.selectedUser.getUserPrenom()+" "+this.selectedUser.getUserNom();
            }
        }
        
        //this.selectedUser.setId(3);
        
        
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,  msg, null);
        FacesContext context = FacesContext.getCurrentInstance();
        context.getCurrentInstance().addMessage(null, message);
    	
    	context.getExternalContext().getFlash().setKeepMessages(true);
    }

	/**
	 * @return the usersCon
	 */
	@SuppressWarnings("rawtypes")
	public List<Users> getUsersCon() {
		 FacesContext facesContext = FacesContext.getCurrentInstance(); 
	        Map m = facesContext.getExternalContext().getSessionMap();
	        Iterator iterator = m.keySet().iterator();
	        List<Users> mnU = new ArrayList<Users>();
	        while(iterator.hasNext()){
	        	  Object key   = iterator.next();
	        	  Object value = m.get(key);
	        	  if(uService.findByUsername(value.toString()) != null)
	        		  mnU.add(uService.findByUsername((String) value));
	        	}
	        Set<Users> mySet = new HashSet<Users>(mnU);
	        usersCon = new ArrayList<Users>(mySet);
		return usersCon;
	}

	/**
	 * @param usersCon the usersCon to set
	 */
	public void setUsersCon(List<Users> usersCon) {
		this.usersCon = usersCon;
	}

	/**
	 * @return the connected
	 */
	public boolean isConnected() {
		if(connected == true)
			connected = false;
			else if(connected == false)
				connected = true;
			else
				connected = true;
		return connected;
	}

	/**
	 * @param connected the connected to set
	 */
	public void setConnected(boolean connected) {
		this.connected = connected;
	}


	/**
	 * @return the ancienUserName
	 */
	public String getAncienUserName() {
		return ancienUserName;
	}

	/**
	 * @param ancienUserName the ancienUserName to set
	 */
	public void setAncienUserName(String ancienUserName) {
		this.ancienUserName = ancienUserName;
	}

	
}
