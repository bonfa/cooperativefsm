/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cooperativefsm;

import java.util.*;

/**
 *
 * @author Renato
 */
public class Transizione {

    private int id;         //è il numero progressivo assegnato alla transizione, riferito alla fsm cui appartiene
    //private String id;
    private String nome;    //identifica una certa transizione (attributo opzionale)
    //Id1 is the starting state, id2 is the outgoing state, and not viceversa
    private Stato stato1;
    private Stato stato2;
    private int numRelazioniSincroneStatoCorrente; //Indica quante relazioni sincrone ha con altre transizioni nello stato corrente
    /*
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

    public Stato getStato1()
    {
        return stato1;
    }

    public Stato getStato2()
    {
        return stato2;
    }

    public Transizione getTransizioneSincronaCorrispondente(){
        return transizioneSincronaCorrispondente;
    }

    public void setNumRelazioniSincroneStatoCorrente( Simulazione.Relazione relazioni[][], Vector<Transizione> transUscFsmCorrispondente){
        int count=0;
        Transizione tr_corr;
        ListIterator l_itr_tr = transUscFsmCorrispondente.listIterator();

        //Scorro la lista di transizioni uscenti per verificare quelle con cui ho una relazione sincrona
        while(l_itr_tr.hasNext()){
            tr_corr = (Transizione) l_itr_tr.next();
            if(relazioni[id][tr_corr.getId()] == Simulazione.Relazione.SINCRONA){
                count++;
                //Il valore sarà sensato solo se al termine count sarà uguale ad 1
                transizioneSincronaCorrispondente = tr_corr;
            }
        }

        numRelazioniSincroneStatoCorrente = count;
    }

    public int getNumRelazioniSincroneStatoCorrente()
    {

        //TODO

        return  numRelazioniSincroneStatoCorrente;
    }
}
