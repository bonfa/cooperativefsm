package cooperativefsm;

/**
 * Classe che eredita  dalla classe padre Input, e ne sovrascrive alcuni metodi in modo  da
 * specializzarli per la lettura da tastiera
 *
 * @author Renato
 */

import java.util.Vector;

public class InputTast extends Input
{
    
     private final String RICH_STATI = "Inserisci il numero di stati: ";
     private final String RICH_TRANS = "Vuoi inserire una nuova transizione? ";
     private final String INS_STATO   = "Inserire il numero dello stato ";
     private final String INS_RELAZ = "Vuoi inserire una nuova relazione tra transizioni? ";
     private final String TIPO_RELAZ = "TIPO DI RELAZIONE TRA LE TRANSIZIONI";
     private final String [] SCELTA_RELAZ  = {"Relazione sincrona","Relazione mutuamente esclusiva"};

     private final int min_stati = 2;


     /**
      * Costruttore specializzato per l'input da tastiera.
      */
     public InputTast ()
     {

     }


     public @Override Stato leggiStato (String a)
     {
         Stato s = new Stato (Servizio.leggiInteroConMinimo(INS_STATO + a + ": ", 0));
         return s;
     }


     public @Override int leggiNumStati ()
     {
        int numStati = Servizio.leggiInteroConMinimo(RICH_STATI, min_stati);
        return numStati;
     }


     /**
      *
      * @return
      */
     public @Override boolean ciSonoTrans ()
     {
        boolean ciSono = Servizio.yesOrNo(RICH_TRANS);
        return ciSono;
     }


     /**
      *
      * @param lista: la lista contenente le fsm
      * @return lo stato corrente
      *
      */
     public @Override StatoCorrente leggiStatoIniziale (Vector<Fsm> lista)
     {
        StatoCorrente s = new StatoCorrente ();

        Stato c1 = leggiStato("corrente della fsm " + lista.get(0).getId());
        Stato c2 = leggiStato("corrente della fsm " + lista.get(1).getId());

        //TODO controllo di appartenenza

        s.setStati( c1, c2 );
        return s;
     }

     public @Override boolean ciSonoRelaz()
     {
        return Servizio.yesOrNo(INS_RELAZ);
     }

     /**
      * imposta una relazione tra due transizioni € a due fsm diverse
      * @param relaz
      */

     public @Override void imposta(Simulazione.Relazione[][] relaz)
     {
        int t1 = Servizio.leggiInteroConMinimo("Transizione di riferimento della fsm 1", 0);
            //controllo di appartenenza
        int t2 = Servizio.leggiInteroConMinimo("Transizione di riferimento della fsm 2", 0);
            //controllo di appartenenza

        MyMenu sceltaTipo = new MyMenu ( TIPO_RELAZ, SCELTA_RELAZ);
        int sel = sceltaTipo.scegli();

        switch (sel)
            {
                case 1: (relaz[t1][t2]) = (relaz[t1][t2]).SINCRONA;
                case 2: (relaz[t1][t2]) = (relaz[t1][t2]).M_EX;
                break;
            }
     }
}
