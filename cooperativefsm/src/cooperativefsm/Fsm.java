package cooperativefsm;

/**
 *
 * @author Alessandro Ferrari, Carlo Svanera, Luca Cominardi
 */

import java.util.Vector;

public class Fsm {

    private String id;
    private int numStati;
    private Vector<Stato>       stati;
    private Vector<Transizione> transizioni;
    private int numRelazioniSincroneTransizioniUscenti;


    /**
     * Costruttore di una fsm
     */

    public Fsm (String _id)
    {
        id          = _id;
        stati       = new Vector<Stato> ();
        transizioni = new Vector<Transizione> ();
    }

    /** Costruttore (con argomenti in più...) di una macchina a stati finiti ... non so se servirà!?!
     *
     * @param _id
     * @param _stati
     * @param _transizioni
     */

    public Fsm (String _id, Vector<Stato> _stati,Vector<Transizione> _transizioni) {    

        id          = _id;
        stati       = _stati;
        transizioni = _transizioni;

     }

     public String getId(){
     return id;
     }

     public int getNumStati(){
        numStati = stati.size();
        return numStati;
     }

     public int getNumTr(){
     return transizioni.size();
     }
    public void addStato(Stato s) {         //aggiunge uno stato al vector listaStati
        stati.add(s);
    }

    public Vector<Stato> getStati(){
        return stati;
    }

    public Stato getStatoAt (int indice)  {
        
        Stato s = (Stato) stati.get(indice);
        return s;
    }




    public void addTrans (Transizione t)    {
        transizioni.add(t);
    }

    public Vector<Transizione> getTransizioni()    {
        return transizioni;
    }

    public Transizione getTransizioneAt (int indice)    {
        Transizione t = transizioni.get(indice);
        return t;
    }





     public void setTransizioniUscentiStati(){
         //TODO
     }

     public int getNumRelazioniSincroneTransizioniUscenti(){
         return numRelazioniSincroneTransizioniUscenti;
     }

     public void setNumRelazioniSincroneTransizioniUscenti(Simulazione.Relazione relazioni[][]){
         //TODO
     }

     public String ToString ()
     {
     String s = new String();
     s = ("\nNome della Fsm: " + id +
                        "\nNumero di stati: " + numStati +
                        "\nElenco delle transizioni: " );

     for (int i = 0; i<transizioni.size(); i++)
     {
        s.concat(transizioni.get(i).ToString());
     }
     return s;
     }

  }
