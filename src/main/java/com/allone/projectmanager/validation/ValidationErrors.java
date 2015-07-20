/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.allone.projectmanager.validation;

import java.lang.annotation.Annotation;
import org.springframework.validation.annotation.Validated;
/**
 *
 * @author user1
 */
public class ValidationErrors implements Validated {

    @Override
    public Class<?>[] value() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
