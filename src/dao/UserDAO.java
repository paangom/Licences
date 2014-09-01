/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.List;
import models.Users;

/**
 *
 * @author sambasow
 */
public interface UserDAO {
    //public Users findByUser(Users u);
    public Users findByUserName(String uname);
    public Users login(Users u);
    public List<Users> findAll();
    public boolean create(Users u);
    public boolean update(Users u);
    public Users getUserById(int id);
    public boolean viderUsers();
    
    public boolean deleteUser(int id);
    
    public boolean reactiverUser(int id);
    
    public Users findUserDel(int id);
    
    public int lastUser();
}
