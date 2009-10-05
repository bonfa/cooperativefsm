package cooperativefsm;

/**
 *
 * @author Alessandro Ferrari, Carlo Svanera, Luca Cominardi
 */

import java.util.Vector;

public class Stato {

    private int idStato;
    private Vector<Transizione> transizioniUscenti;


    public Stato (int num_stato) {   //costruttore di uno stato

        idStato = num_stato;
        transizioniUscenti = new Vector<Transizione>();
    }

    public void addTransUscente(Transizione t){
        transizioniUscenti.add(t);
    }

    public int getId()
    {
        return idStato;
    }

    public void setId(int id)
    {
        idStato = id;
    }

}
