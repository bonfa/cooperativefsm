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

    private int id;
    private Stato stato1;
    private Stato stato2;
    private int numRelazioniSincroneStatoCorrente; //Indica quanti relazioni sincrone ha con altre transizioni nello stato corrente

    public Transizione(Stato _stato1, Stato _stato2) {
        stato1=_stato1;
        stato2=_stato2;
    }

    public void setNumRelazioniSincroneStatoCorrente( Simulazione.Relazione relazioni[][]){
        //TODO
    }

    public int getNumRelazioniSincroneStatoCorrente(){

        //TODO

        return  numRelazioniSincroneStatoCorrente;
    }
}
