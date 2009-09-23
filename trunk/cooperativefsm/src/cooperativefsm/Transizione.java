/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cooperativefsm;

/**
 *
 * @author Renato
 */
public class Transizione {

    int id;
    Stato sorgente;
    Stato destinazione;
    //int numRelazioniSincroneStatoCorrente //Indica quanti relazioni sincrone ha con altre transizioni nello stato corrente

    public Transizione(Stato s, Stato d) {
        sorgente=s;
        destinazione=d;
    }
}
