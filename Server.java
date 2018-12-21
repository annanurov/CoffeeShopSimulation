/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffeeshopsimulation;

/**
 *
 * @author Bayram
 */
public class Server {
    int ID;     //id of current server
    int cID;    //id of current customer on sevrer
    int cIn;    //time current customer arrived to the server
    int cOut;   //time current customer left the server
    //int free;   //1 if the server free, 0 otherwise
    public Server(int id){
        this.ID = id;
        //this.free = 1;
        this.cID = 0;
        this.cOut = 0;
    }
    
}
