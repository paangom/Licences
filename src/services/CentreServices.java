/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import java.util.List;

import models.Centres;
import util.Factory;
import dao.CentreDAO;

/**
 *
 * @author sambasow
 */
public class CentreServices {
    private CentreDAO cenDAO = Factory.getCentreDAO();
    
    public boolean createCenter(Centres c){
        return cenDAO.createCenter(c);
    }

    public Centres getCenter(String code){
        return cenDAO.getCenter(code);
    }

    public Centres findById(int id){
        return cenDAO.findById(id);
    }

    public boolean modifyCenter(Centres c){
        return cenDAO.modifyCenter(c);
    }

    public List<Centres> getAllCentre(){
        return cenDAO.getAllCentre();
    }

    public Centres getCentre(){
        return cenDAO.getCentre();
    }

    public boolean verifyCentre(){
        return cenDAO.verifyCentre();
    }
    
    public boolean viderCentre(){
    	return cenDAO.viderCentre();
    }
    

}
