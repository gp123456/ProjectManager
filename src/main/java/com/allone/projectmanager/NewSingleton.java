package com.allone.projectmanager;


import com.allone.projectmanager.controller.common.Common;
import com.allone.projectmanager.model.User;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gpatitakis
 */
public class NewSingleton {
    
    private static final Logger logger = Logger.getLogger(Common.class.getName());
    
    private static final Map<String, User> user = new HashMap<>();
    
    private NewSingleton() {
    }
    
    public static NewSingleton getInstance() {
        return NewSingletonHolder.INSTANCE;
    }
    
    private static class NewSingletonHolder {

        private static final NewSingleton INSTANCE = new NewSingleton();
    }
    
    public void setUser(String session, User user) {
        NewSingleton.user.put(session, user);
    }

    public User getUser(String session) {
        User u = user.get(session);

        return u;
    }

    public User setUserProjectId(String session, Long projectId) {
        User u = user.get(session);

        if (u != null) {
            u.setProjectId(projectId);
        }

        return u;
    }

    public Long isLockProject(Long userId, Long projectId) {
        for (User u : user.values()) {
            Long pId = u.getProjectId();

            if (!u.getId().equals(userId) && pId != null && pId.equals(projectId)) {
                return u.getId();
            }
        }

        return null;
    }
    
    public String lockUser(Long projectId) {
        logger.log(Level.INFO, "lockUser={0}, size={1}", new Object[]{projectId, user.size()});
        
        for (User u : user.values()) {
            Long pId = u.getProjectId();

            if (pId != null && pId.equals(projectId)) {
                logger.log(Level.INFO, "lockUser={0}", u.getUsername());
                
                return u.getUsername();
            }
        }

        return null;
    }

    public User removeUser(String session) {
        return user.remove(session);
    }
}
