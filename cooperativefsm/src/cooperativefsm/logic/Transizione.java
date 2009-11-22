/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cooperativefsm.logic;

import java.util.*;

/**
 *
 * @author Alessandro
 */
public class Transizione {

    private int id;         //è il numero progressivo assegnato alla transizione, riferito alla fsm cui appartiene
    //private String id;
    private String nome;    //identifica una certa transizione (attributo opzionale)
    //Id1 is the starting state, id2 is the outgoing state, and not viceversa
    private Stato stato1;
    private Stato stato2;
    private int numRelazioniSincroneStatoCorrente; //Indica quante relazioni sincrone ha con altre transizioni nello stato corrente
    
    /**
     *  Valore che viene aggiornato di iterazione in iterazione.
     *  indica la transizione con cui nello stato corrente this (eventualmente)
     *  una transizione sincrona. Il valore è da considerarsi utilizzabile solo nel caso in cui
     *  numRelazioniSincroneStatoCorrente sia uguale ad uno, altrimenti il suo contenuto
     *  è privo di qualsiasi senso
     */
    private Transizione transizioneSincronaCorrispondente;


    /**
     * Costruttore di una transizione a partire da due stati di una stessa fsm
     *
     * @param _id
     * @param _stato1
     * @param _stato2
     */

    public Transizione(int _id, Stato _stato1, Stato _stato2)
    {
        id = _id;
        stato1 = _stato1;
        stato2 = _stato2;
    }

    public String ToString()
    {
        String s = ("\nTransizione numero: " + id + "\tNome:\t" + nome +
                    "\nDallo stato:\t" + stato1.getId() +
                    "\tallo stato:  " + stato2.getId());
        return s;
    }

    public void setNome(String _nome)
    {
        nome = _nome;
    }

    public String getNome()
    {
        return nome;
    }

    public int getId()
    {
        return id;
    }

    /**
     * Funzione che permette di accedere allo stato sorgente della transizione
     * @return stato sorgente della transizione
     */

    public Stato getStato1()
    {
        return stato1;
    }

    /**
     * Funzione che permette di accedere allo stato destinazione della
     * simulazione.
     * @return
     */

    public Stato getStato2()
    {
        return stato2;
    }

    /**
     * Setta il valore transizioneSincronaCorrispondente nel caso in cui la
     * transizione sia uscente dallo stato corrente nella attuale iterazione
     * e abbia una e una sola relazione sincrona con una transizione uscente
     * dallo stato corrente della fsm complementare
     * @param t
     */

    public void setTransizioneSincronaCorrispondente(Transizione t){
        transizioneSincronaCorrispondente= t;
    }

    public Transizione getTransizioneSincronaCorrispondente(){
        return transizioneSincronaCorrispondente;
    }

    public void setNumRelazioniSincroneStatoCorrente( int n ){
        numRelazioniSincroneStatoCorrente=n;
    }

    public int getNumRelazioniSincroneStatoCorrente()
    {

        return  numRelazioniSincroneStatoCorrente;
    }

    /**
     * De transizioni sono uguali se hanno lo stesso stato sorgente e lo stesso stato destinazione
     * @param t
     * @return
     */
    public boolean equals (Transizione t)
    {
        boolean stessoSorg = (this.stato1.getId() == t.getStato1().getId());
        boolean stessoDest = (this.stato2.getId() == t.getStato2().getId());
        return stessoSorg && stessoDest;
    }
}
