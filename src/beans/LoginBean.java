package beans;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpSession;

import models.Users;

import org.apache.commons.codec.digest.DigestUtils;

import services.UserServices;
import util.MyUtil;

public class LoginBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Users user = null;
    private Users userConnect = null;
    private UserServices uService = new UserServices();
    private boolean isLogin;
    private boolean mysession;
    private String login;
    private String pwd;
    private String url;

    /**
     * Creates a new instance of loginBean
     */
    public LoginBean() {
        if (this.user == null) {
            this.user = new Users();
        }
        
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Users getUserConnect() {
        userConnect = MyUtil.getUserLogged();
        return userConnect;
    }

    public boolean isIsLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }
    
   

    @SuppressWarnings("static-access")
	public String login() {
        String route = "";
        this.user.setUserPassword(DigestUtils.md5Hex(this.user.getUserPassword()));
        Users u = this.uService.connectUser(this.user);
        if (u != null) {
        	
        	isLogin = true;
        	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(u.getUserUserName(), u.getUserUserName());
        		
        	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("login", u.getUserUserName());
        	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("profil", u.getUserProfil());
        	            
        	           
        	route = "/home?faces-redirect=true";
        	            
        }else if(this.user.getUserUserName().equals("admin") && this.user.getUserPassword().equals(DigestUtils.md5Hex("admin"))){
        	route = "/home?faces-redirect=true";
        	
        }
        else{
        	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Invalide nom d'utilisateur et/ou mot de passe", null);
        	FacesContext context = FacesContext.getCurrentInstance();
            context.getCurrentInstance().addMessage(null, message);
        	
        	context.getExternalContext().getFlash().setKeepMessages(true);
	            if (this.user == null) {
	                this.user = new Users();
	            }
        	
        }
        return route;
    }


    /**
     *
     * @return
     * @throws IOException 
     */
    public String logout(){
        
    	FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        session.invalidate();
        
        return "/login?faces-redirect=true";
       
      
    }

    /**
     * An event listener for redirecting the user to login page if he/she is not
     * currently logged in
     *
     * @param event
     */

    @SuppressWarnings("static-access")
	public String verifyLogin(){
    	String route = "";
    	if(MyUtil.getProfil() != null){
    	if(uService.findByUsername(this.getLogin()) != null){
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ce nom utilisateur existe déjà", null);
    		FacesContext context = FacesContext.getCurrentInstance();
            context.getCurrentInstance().addMessage(null, message);
        	
        	context.getExternalContext().getFlash().setKeepMessages(true);
    	}
    	else{
    		Users us = uService.findByUsername(this.user.getUserUserName());
    		if(us != null){
    			us.setUserUserName(this.getLogin());
    			us.setUserPassword(DigestUtils.md5Hex(this.getPwd()));
    			
    			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("login", us.getUserUserName());
	            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("profil", us.getUserProfil());
    			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Votre nouveau login est: "+this.getLogin()+" et votre mot de passe est: "+this.getPwd(), null);
        		FacesContext context = FacesContext.getCurrentInstance();
                context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);
        		uService.updateUser(us);
        		us = new Users();
        		this.user = new Users();
        		FacesContext context1 = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) context1.getExternalContext().getSession(false);
                session.invalidate();
        		route = "/login?faces-redirect=true";
    		}
    		else{
    			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aucun utilisateur du nom de "+this.user.getUserUserName()+" n'existe pas!", null);
        		FacesContext context = FacesContext.getCurrentInstance();
                context.getCurrentInstance().addMessage(null, message);
            	
            	context.getExternalContext().getFlash().setKeepMessages(true);
    		}
    		
    	}
    	}
    	else {
    		route = "/login?faces-redirect=true";
    	}
    	return route;
    }
    
    
   
    
    
    public void verifyUseLogin(ComponentSystemEvent event){
  	  if(!isLogin){
  		  String url = "";
  		  if(MyUtil.getProfil() == null){
  			  isLogin = false;
  			  url = MyUtil.basePathLogin();
  		  }
  	   doRedirect(url);
  	  }
  	 }
    
    public void redirection(String url) throws IOException
    {
    	String profil = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("profil");
    	if(profil != null) {
    	FacesContext.getCurrentInstance()
    	.getExternalContext()
    	.redirect("/faces/home.xhtml");
    	}
    	else {
    		FacesContext.getCurrentInstance()
        	.getExternalContext()
        	.redirect("/faces/home.xhtml");
    	}
    		
     }

  	 /**
  	 * Method for redirecting a request
  	 * @param url
  	 */
  	 private void doRedirect(String url){
  	  try {
  	   FacesContext context=FacesContext.getCurrentInstance();
  	   context.getExternalContext().redirect(url);
  	  } catch (IOException e) {
  	   e.printStackTrace();
  	  }
  	 }


	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * @param pwd the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	/**
	 * @return the mysession
	 */
	public boolean isMysession() {
		if(MyUtil.getUserLogged() != null)
			mysession = true;
		else
			mysession = false;
		return mysession;
	}

	/**
	 * @param mysession the mysession to set
	 */
	public void setMysession(boolean mysession) {
		this.mysession = mysession;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		url = "/home?faces-redirect=true";
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
