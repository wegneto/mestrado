/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mt.exception;

/**
 *
 * @author wegneto
 */
public class AuthenticationException extends Exception {

    public AuthenticationException(String error) {
        super(error);
    }
    
}
