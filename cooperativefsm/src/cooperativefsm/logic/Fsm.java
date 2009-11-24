package cooperativefsm.logic;

/**
 *
 * Classe che rappresenta una fsm, contiene gli stati e le transizioni di questa
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
     * @deprecated
     */

    //@ requires _id==0 || _id==1
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

    /*@ requires _id == 0 || _id==1
      @ requires _stati!=null && _transizioni!=null
      @ requires (/ forall Stato s; _stati.contains(s) ; s.getId()<numStati )
      @ requires (/ forall Stato s1, Stato s2; _stati.contains(s1) && _stati.contains(s2) && s1 != s2; s1.getId()!=s2.getId())
      @ requires (/ forall Transizione t; transizioni.contains(t); stati.contains(t.getStato1()) && stati.contains(t.getStato2()))
      @*/
    public Fsm (int _id, Vector<Stato> _stati,Vector<Transizione> _transizioni) {

        id          = _id;
        stati       = _stati;
        transizioni = _transizioni;
        numStati=_stati.size();

     }

    /**
     * @deprecated
     */

     //@ requires _id==0 || _id==1
     public void setId(int _id){
         id =_id;
     }

     //@ ensures /return==0 || /return==1
     public int getId(){
     return id;
     }

     public String getNome(){
        return Integer.toString(id);
     }

     //@ ensures /return>0
     public /*@ pure @*/ int getNumStati(){
        numStati = stati.size();
        return numStati;
     }

     public /*@ pure @*/ int getNumTr(){
     return transizioni.size();
     }

    /**
     * @deprecated
     */
    public void addStato(Stato s) {  //aggiunge uno stato al vector listaStati
        stati.add(s);
    }

    //@ ensures
    public Vector<Stato> getStati(){
        return stati;
    }

    public Stato getStatoAt (int indice)  {
        
        Stato s = (Stato) stati.get(indice);
        return s;
    }

    /**
     * @deprecated
     */

    public void addTrans (Transizione t)    {
        transizioni.add(t);
    }

    //@ ensures /return!=null
    public Vector<Transizione> getTransizioni()    {
        return transizioni;
    }

    //@ requires indice>=0 && indice<=transizioni.size()
    public Transizione getTransizioneAt (int indice)    {
        Transizione t = transizioni.get(indice);
        return t;
    }


    /**
     * Setta le transizioni uscenti da ogni stato della fsm.
     */
    //@ ensures (/ forall Stato  stato; stati.contains( stato ) ; stato.getTransizioniUscenti != null )
    //@ ensures (/ forall Transizione transizione; stato.getTransizioniUscenti().contains(transizione) ; this == transizione.getStato1() )
    //@ ensures (/ forall Transizione transizione; transizioni.contains( transizione ) && this == transizione.getStato1() ; transizioniUscenti.contains(this))
    //@ ensures (/ forall Stato stato; stati.contains( stato ) ; stato.getTransizioniUscentiIsSetted())
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
            stato.setTransizioniUscentiIsSetted();
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
