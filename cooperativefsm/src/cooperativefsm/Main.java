/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cooperativefsm;

//import java.io.IOException;
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
        final String [] SCELTAINPUT  = {"da tastiera","da file xml"};
        final String MESS_FINALE = "CIAO!";
        final String XML_NOT = "-- File xml non formattato correttamente!!! --";
        final String XML_DEF = "fsm.xml";

        MyMenu menuInput = new MyMenu( TIPOINPUT , SCELTAINPUT );
        boolean continua = true;
        Simulazione s = null;

        while(continua)
        {
            Input in = null;
            int selezione = menuInput.scegli();
        
            switch (selezione)
            {
                case 1: {
                        in = new InputTast();
                        s = in.leggiSimulazione();          //leggiSimulazione è un metodo della classe Input,
                        System.out.println(s.ToString());   //da cui ereditano le classi InputTast e InputXML
                        continua = false;
                        break;
                        }
                case 2: {
                            String url=Servizio.leggiString("Inserire il percorso del file xml da leggere (" + XML_DEF + ")> ");
                            if(url.equals(""))
                                url=XML_DEF;
                            try {
                                in = new InputXML(url); //l'uri del file xml dovrà essere passato da tastiera, invio per il default
                                System.out.println("-- Simulazione creata con successo!!! --");
                                s = in.leggiSimulazione();
                                System.out.println(s.ToString());
                            } catch (Exception ex) {
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


                case 0: {
                        System.out.println(MESS_FINALE);
                        //System.exit(0);
                        continua=false;
                        }
            }//switch
        
        
        }//while
                                                
        //while (!fineProgramma)
           // {
            //fineProgramma = s.eseguiIterazione();
           // }

        //System.out.println(s.getListaFsm().get(0).ToString());
        //System.out.println(s.getListaFsm().get(1).ToString());

        System.out.println(MESS_FINALE);
    }
    
}