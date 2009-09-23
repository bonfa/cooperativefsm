/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cooperativefsm;

/**
 *
 * @author Renato
 */

import java.util.Vector;

public class Stato {

    int idStato;
    Vector transUscenti;


    public Stato (int num_stato) {   //costruttore di uno stato

        idStato = num_stato;
        transUscenti = new Vector ();
    }

    public void addTransUscente(Transizione t){
        transUscenti.add(t);
    }
}
