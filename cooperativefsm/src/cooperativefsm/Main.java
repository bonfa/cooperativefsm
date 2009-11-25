/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cooperativefsm;

//import java.io.IOException;

import cooperativefsm.io.*;
import cooperativefsm.logic.Simulazione;


//import javax.xml.parsers.ParserConfigurationException;
//import org.xml.sax.SAXException;

/**
 * @author Cominardi Luca, Ferrari Alessandro, Svanera Carlo, Francesco Bonfadelli
 */

public class Main {     

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args)
    {
        final String BENVENUTO = "Benvenuto! Questo programma ti permetterà di effettuare una simulazione\n di due macchine a stati" +
                " finiti cooperanti tra loro.\nPer prima cosa scegli l'opzione desiderata:\n";
        final String TIPOINPUT = "TIPO DI INPUT";
        final String [] SCELTAINPUT  = {"Da tastiera, per salvataggio","Da tastiera, per l'esecuzione","Da file xml, per esecuzione", "Esci"};
        final String MESS_FINALE = "CIAO E GRAZIE!";
        final String XML_NOT = "-- File xml inesistente o non formattato correttamente!!! --\nDescrizione dell'errore:\n";
        final String XML_DEF = "fsm.xml";
        final String SALVA_SIM = "Vuoi salvare la simulazione? ";
        final String ESECUZIONE="ESECUZIONE SIMULAZIONE:";
       
        
        MyMenu menuInput = new MyMenu( TIPOINPUT , SCELTAINPUT );
        Simulazione s = null;
        Interazione i= new Interazione();
        int selezione = 0;
        
        System.out.println(BENVENUTO);
        boolean continua = true;
        while(continua)
        {
            s = null;   //serve per azzerare le eventuali simulazioni relative a sessioni precedenti
            Input in = null;
            selezione = menuInput.scegli();
        
            switch (selezione)
            {
                case 1: {   //Lettura da tastiera per salvataggio
                        in = new InputTast();
                        s = in.leggiSimulazione();          //leggiSimulazione è un metodo della classe Input,da cui ereditano le classi InputTast e InputXML
                        break;
                        }
                case 2: {   //Lettura da tastiera per esecuzione
                        in = new InputTast();
                        s = in.leggiSimulazione();
                        break;
                        }
                case 3: {   //Lettura da XML per esecuzione
                            String url=Servizio.leggiString("Inserire il percorso del file xml da leggere (" + XML_DEF + ")> ");
                            if(url.equals(""))
                                url=XML_DEF;
                            try {
                                in = new InputXML(url); //l'uri del file xml dovrà essere passato da tastiera, invio per il default
                                System.out.println("-- Simulazione creata con successo!!! --");
                                s = in.leggiSimulazione();
                                //da cambiare
                                //System.out.println(s.ToString());
                                //continua = false;
                            } catch (Exception ex) {
                                //System.out.println(XML_NOT + ex.toString());
                                System.out.println(XML_NOT + ex.toString());
//                            } catch (IOException ex) {
//                                System.out.println("-- Il file specificato non esiste!!! --");
//                                //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//                            } catch (ParserConfigurationException ex) {
//                                System.out.println(XML_NOT);
//                                //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//                            } catch (NullPointerException q) {
//                                System.out.println(XML_NOT);
//                            } catch (ArrayIndexOutOfBoundsException t) {
//                                System.out.println(XML_NOT);
                            } break;
                        }


                case 4: {
                        continua=false;     //provoca l'uscita dal ciclo while del main menu e quindi la fine del programma
                        break;
                        }
            }//switch
        
        
        if (continua && s!=null)    //se la fase di input è andata a buon fine si procede
        {
               if (selezione!=1)
               System.out.println("\n\n"+ESECUZIONE);

               //esecuzione vera e propria
               boolean fineEsecuzione=false;
               while (!fineEsecuzione && selezione != 1)     //l'esecuzione è fatta solo per le selezioni 2 e 3 del menu; se si è scelto 1 si passa subito al salvataggio
                    fineEsecuzione=i.selezioneTransizioneDaFarScattare(s);

               //alla fine di un'esecuzione è sempre richiesto se si vuole salvare
               boolean salva = Servizio.yesOrNo(SALVA_SIM);
               if(salva)
               {
                    String url=Servizio.leggiString("Inserire il percorso dove salvare il file xml della simulazione (" + XML_DEF + ")> ");
                    if(url.equals(""))
                        url=XML_DEF;
                    OutputXML.salvaSimulazione(s,url);
               }
        }//if
        }//while

        System.out.println(MESS_FINALE);
    }
    
}