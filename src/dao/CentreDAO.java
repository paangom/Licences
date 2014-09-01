/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;

import models.Centres;

/**
 *
 * @author sambasow
 */
public interface CentreDAO {

    public boolean createCenter(Centres c);

    public Centres getCenter(String code);

    public Centres findById(int id);

    public boolean deleteCenter(String code);

    public boolean modifyCenter(Centres c);

    public List<Centres> getAllCentre();

    public Centres getCentre();

    public boolean verifyCentre();
    
    public boolean viderCentre();
    
}
