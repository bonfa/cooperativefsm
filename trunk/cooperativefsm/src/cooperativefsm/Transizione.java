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

    private int id;         //è il numero progressivo assegnato alla transizione, riferito alla fsm cui appartiene
    private String nome;    //identifica una certa transizione (attributo opzionale)
    private Stato stato1;
    private Stato stato2;
    private int numRelazioniSincroneStatoCorrente; //Indica quanti relazioni sincrone ha con altre transizioni nello stato corrente

    /**
     * Costruttore di una transizione a partire da due stati di una stessa fsm
     *
     * @param _stato1
     * @param _stato2
     */

    public Transizione(Stato _stato1, Stato _stato2)
    {
        stato1 = _stato1;
        stato2 = _stato2;
    }


    public void setNumRelazioniSincroneStatoCorrente( Simulazione.Relazione relazioni[][])
    {
        //TODO
    }

    public int getNumRelazioniSincroneStatoCorrente()
    {

        //TODO

        return  numRelazioniSincroneStatoCorrente;
    }
}
