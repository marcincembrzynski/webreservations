/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.result;

/**
 *
 * @author marcin
 */
public interface Result<E> {
    
    E element();
    String message();
    boolean success();
    String view(String success, String failure);
}
