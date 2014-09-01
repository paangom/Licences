/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import dao.UserDAO;
import java.io.Serializable;
import java.util.List;
import models.Users;
import util.Factory;

/**
 *
 * @author sambasow
 */
public class UserServices implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserDAO uDAO = Factory.getUserDAO();
    

    
    public Users connectUser(Users u){
        return uDAO.login(u);
    }
    
    public List<Users> getAllUser(){
        return uDAO.findAll();
    }
    
    public boolean addUser(Users u){
        return uDAO.create(u);
    }
    
    public boolean updateUser(Users u){
        return uDAO.update(u);
    }
    
    public boolean deleteUser(Users u){
        return uDAO.deleteUser(u.getUserId());
    }
    
    public boolean reactiveUser(Users u){
        return uDAO.reactiverUser(u.getUserId());
    }
    
    public Users findByUsername(String uname){
        return uDAO.findByUserName(uname);
    }
    
    public boolean viderUsers(){
    	return uDAO.viderUsers();
    }
    
    public int lastUser(){
    	return uDAO.lastUser();
    }
}
