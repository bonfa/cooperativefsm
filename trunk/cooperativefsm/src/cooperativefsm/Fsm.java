package cooperativefsm;

/**
 *
 * @author Alessandro Ferrari, Carlo Svanera, Luca Cominardi
 */

import java.util.*;

public class Fsm {

    private int id;
    private int numStati;
    private Vector<Stato>       stati;
    private Vector<Transizione> transizioni;
 

    /**
     * Costruttore di una fsm
     *
     * @param _id
     */

    public Fsm (int _id)
    {
        id          = _id;
        stati       = new Vector<Stato> ();
        transizioni = new Vector<Transizione> ();
    }

    /** Costruttore (con argomenti in più...) di una macchina a stati finiti ... non so se servirà!?!
     * (Serve per l'InputXML)
     *
     * @param _id
     * @param _stati
     * @param _transizioni
     */

    public Fsm (int _id, Vector<Stato> _stati,Vector<Transizione> _transizioni) {

        id          = _id;
        stati       = _stati;
        transizioni = _transizioni;
        numStati=_stati.size();

     }


     public void setId(int _id){
         id =_id;
     }

     public int getId(){
     return id;
     }

     public String getNome(){
     return Integer.toString(id);
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


    //Setto le transizioni uscenti da ogni stato della fsm
    public void setTransizioniUscentiStati(){
        
         ListIterator l_itr_st = stati.listIterator();
         ListIterator l_itr_tr;
         Stato stato;
         Transizione transizione;

         //Setto le transizioni uscenti per tutti gli stati della fsm
         while(l_itr_st.hasNext()){

            stato = (Stato) l_itr_st.next();
            l_itr_tr = transizioni.listIterator();

            //Scorro tutte le transizioni per vedere quelle che sono uscenti
            while(l_itr_tr.hasNext()){
                transizione = (Transizione) l_itr_tr.next();
                if( transizione.getStato1().equals(stato) )
                    stato.addTransUscente(transizione);
            }
            stato.transizioniUscentiIsSetted();
         }
    }


     public String ToString ()
     {
     String s = new String();
     s = ("\nNome della Fsm: " + id +
                        "\nNumero di stati: " + numStati +
                        "\n\nElenco delle transizioni: " );

     for (int i = 0; i < transizioni.size(); i++)
     {
        s = s + transizioni.get(i).ToString();
     }
     s += "\n-----------------------------------------------\n";
     return s;
     }

  }
