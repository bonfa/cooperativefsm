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
    //private String id;
    private String nome;    //identifica una certa transizione (attributo opzionale)
    private Stato stato1;
    private Stato stato2;
    private int numRelazioniSincroneStatoCorrente; //Indica quante relazioni sincrone ha con altre transizioni nello stato corrente

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
