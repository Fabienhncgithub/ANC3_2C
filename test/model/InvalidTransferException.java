/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author GV
 */
class InvalidTransferException extends RuntimeException {

    public InvalidTransferException() {
        super("InvalidTE error!");
    }
}
