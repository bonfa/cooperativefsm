/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cooperativefsm;

//import java.io.IOException;

import cooperativefsm.logic.Simulazione;
import cooperativefsm.io.Input;
import cooperativefsm.io.OutputXML;
import cooperativefsm.io.Servizio;
import cooperativefsm.io.InputTast;
import cooperativefsm.io.InputXML;
import cooperativefsm.io.MyMenu;

//import javax.xml.parsers.ParserConfigurationException;
//import org.xml.sax.SAXException;

/**
 *
 * Prova commit
 * Prova commit by carlo
 *
 * @author Cominardi Luca, Ferrari Alessandro, Svanera Carlo
 */

public class Main {     

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args)
    {
        final String TIPOINPUT = "TIPO DI INPUT";
        final String [] SCELTAINPUT  = {"Da tastiera, per salvataggio","Da tastiera, per l'esecuzione","Da file xml, per esecuzione"};
        final String MESS_FINALE = "CIAO!";
        final String XML_NOT = "-- File xml non formattato correttamente!!! --";
        final String XML_DEF = "fsm.xml";
        final String SALVA_SIM = "Vuoi salvare la simulazione? ";
        final String ESECUZIONE="ESECUZIONE SIMULAZIONE:";
       

        MyMenu menuInput = new MyMenu( TIPOINPUT , SCELTAINPUT );
        boolean continua = true;
        boolean fineProgramma=false;
        Simulazione s = null;
        Interazione i=new Interazione();
        int selezione = 0;
        
        while(continua)
        {
            Input in = null;
            selezione = menuInput.scegli();
        
            switch (selezione)
            {
                case 1: {   //Lettura da tastiera per salvataggio
                        in = new InputTast();
                        s = in.leggiSimulazione();          //leggiSimulazione è un metodo della classe Input,da cui ereditano le classi InputTast e InputXML
                        continua = false;
                        break;
                        }
                case 2: {   //Lettura da tastiera per esecuzione
                        in = new InputTast();
                        s = in.leggiSimulazione();
                        continua = false;
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
                                continua = false;
                            } catch (Exception ex) {
                                //System.out.println(XML_NOT + ex.toString());
                                System.out.println(XML_NOT);
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


                case 0: {
                        //System.out.println(MESS_FINALE);
                        //System.exit(0);
                        continua=false;
                        fineProgramma=true;
                        }
            }//switch
        
        
        }//while

       if (selezione!=1)
        System.out.println("\n\n"+ESECUZIONE);

       while (!fineProgramma && selezione != 1)     //l'esecuzione è fatta solo per le selezioni 2 e 3 del menu; se si è scelto 1 si passa subito al salvataggio
       {
        fineProgramma=i.selezioneTransizioneDaFarScattare(s);
       }
       if(s!=null)
        {
            boolean salva = Servizio.yesOrNo(SALVA_SIM);
            if(salva)
            {
                String url=Servizio.leggiString("Inserire il percorso dove salvare il file xml della simulazione (" + XML_DEF + ")> ");
                if(url.equals(""))
                    url=XML_DEF;
                //da cambiare
                OutputXML.salvaSimulazione(s,url);
            }
        }
        System.out.println(MESS_FINALE);
    }
    
}