/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.result;

/**
 *
 * @author marcin
 * @param <E>
 */
public class Success<E> implements Result<E>{
    
    private final String message;
    private final E element;

    public Success(String message, E element) {
        this.message = message;
        this.element = element;
    }
    
    

    @Override
    public E element() {
        return element;
    }

    @Override
    public String message() {
       return message;
    }

    @Override
    public boolean success() {
        return true;
    }

    @Override
    public String view(String success, String failure) {
        return success;
    }

   
    
}
