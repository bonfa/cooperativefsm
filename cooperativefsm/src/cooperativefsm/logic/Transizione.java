/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cooperativefsm.logic;


/**
 * Classe che rappresenta una transizioni tra stati di una fsm. Indica lo stato
 * sorgente e lo stato destinazione più una serie di altri attributi utili 
 * per la simulazione.
 *
 * @see Stato, Fsm, Simulazione
 * @author Alessandro
 */
public class Transizione {

    //@ public invariant /old(id)==id && /old(stato1)==stato1 && /old(stato2)==stato2

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

    //@ requires _stato1!=null && _stato2!=null
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

    /**
     * Setta il nome della transizione (utile per stampa)
     * @param _nome
     */
    public void setNome(String _nome)
    {
        nome = _nome;
    }

    /**
     * Ritorna il nome della Transizione.
     * @return nome della
     */
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

    //@ensures /return!=null && /return.getId()>0
    public Stato getStato1()
    {
        return stato1;
    }

    /**
     * Funzione che permette di accedere allo stato destinazione della
     * simulazione.
     * @return
     */

    //@ensures /return!=null && /return.getId()>0

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

    //@ requires numRelazioniSincroneStatoCorrente == 1 && t!=null
    public void setTransizioneSincronaCorrispondente(Transizione t){
        transizioneSincronaCorrispondente= t;
    }

     /**
     * Ritorna il valore transizioneSincronaCorrispondente nel caso in cui la
     * transizione sia uscente dallo stato corrente nella attuale iterazione
     * e abbia una e una sola relazione sincrona con una transizione uscente
     * dallo stato corrente della fsm complementare.
     * @return valore della transizione sincrona corrispodente nella corrente iterazione, ha senso solo
     * se getNumRelazioniSincroneStatoCorrente()==1.
     */

    //@ requires numRelazioniSincroneStatoCorrente == 1 && /return!=null
    public Transizione getTransizioneSincronaCorrispondente(){
        return transizioneSincronaCorrispondente;
    }

    /**
     * Serve per settare il numero di relazioni sincrone che la Transizione this,
     * che si suppone uscente dallo stato corrente, ha con Transizioni uscenti
     * dallo stato dell'altra Fsm contestualmente all'iterazione corrente.
     * @param n
     */
    //@ requires n=>0
    public void setNumRelazioniSincroneStatoCorrente( int n ){
        numRelazioniSincroneStatoCorrente=n;
    }

    /**
     * Ritorna il numero di relazioni sincrone che la Transizione this,
     * che si suppone uscente dallo stato corrente, ha con Transizioni uscenti
     * dallo stato dell'altra Fsm contestualmente all'iterazione corrente.
     */
    public int getNumRelazioniSincroneStatoCorrente()
    {

        return  numRelazioniSincroneStatoCorrente;
    }

    /**
     * Due transizioni sono uguali se hanno lo stesso stato sorgente e lo stesso stato destinazione
     * oppure lo stesso nome (id testuale)
     * @param t
     * @return
     */
    public boolean equals (Transizione t)
    {
        boolean stessoNome = this.getNome().equals(t.getNome());
        boolean stessoSorg = (this.stato1.getId() == t.getStato1().getId());
        boolean stessoDest = (this.stato2.getId() == t.getStato2().getId());
        return (stessoSorg && stessoDest) || stessoNome;
    }
}
