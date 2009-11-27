package cooperativefsm.io;

import java.io.*;

/**
 * Classe di supporto per le operazioni di Input da tastiera
 * @author Carlo
 */

public class Servizio {

	  private final static String ERRORE_FORMATO = "Attenzione: il dato inserito non e' nel formato corretto";
	  private final static String ERRORE_MINIMO= "Attenzione: e' richiesto un valore maggiore o uguale a ";
	  private final static String ERRORE_STRINGA_VUOTA= "Attenzione: non hai inserito alcun carattere";
	  private final static String ERRORE_MASSIMO= "Attenzione: e' richiesto un valore minore o uguale a ";
	  private final static String MESSAGGIO_AMMISSIBILI= "Attenzione: i caratteri ammissibili sono: ";

	  private final static char RISPOSTA_SI='S';
	  private final static char RISPOSTA_NO='N';

          private static InputStreamReader reader = new InputStreamReader (System.in);
          private static BufferedReader myInput = new BufferedReader (reader);

          /**
           * Metodo per leggere una stringa
           * @param messaggio da visualizzare a video
           * @return una stringa generica
           */
          public static String leggiString(String messaggio)
                {
                System.out.println (messaggio);
                String str= new String();
                    try 
                    {
                    str = myInput.readLine();
                    } 
                    catch (IOException e) 
                    {
                    System.out.println ("Si è verificato un errore: " + e);
                    System.exit(-1);
                    }
                return str;
                }
          /**
           * Metodo per leggere un intero
           * @param messaggio da visualizzare a video
           * @return un intero
           */
            public static int leggiInt(String messaggio)
                {
                
                int i=0;
                String a = leggiString(messaggio);
                    try 
                    {
                    i = Integer.parseInt(a);
                    }
                    catch (Exception e)
                    {
                    System.out.println ("Devi inserire un intero! ");
                    return leggiInt(messaggio);
                    }
                return i;
                }

            /**
             * Metodo per leggere un astringa non vuota
             * @param messaggio
             * @return una stringa non vuota
             * @ensures lettura!= "";
             */
	  public static String leggiStringaNonVuota(String messaggio)
	  {
	   boolean finito=false;
	   String lettura = null;
	   do
	   {
		 lettura = leggiString(messaggio);
		 lettura = lettura.trim();
		 if (lettura.length() > 0)
		  finito=true;
		 else
		  System.out.println(ERRORE_STRINGA_VUOTA);
	   } while (!finito);

	   return lettura;
	  }

/**
 * Metodo per leggere un singolo char
 * @param messaggio
 * @return una varibile char
 */
          public static char leggiCaratt (String messaggio)
	  {
	   boolean finito = false;
	   char valoreLetto = '\0';
	   do
	    {
	     String lettura = leggiString(messaggio);
	     if (lettura.length() > 0)
	      {
	       valoreLetto = lettura.charAt(0);
	       finito = true;
	      }
	     else
	      {
	       System.out.println(ERRORE_STRINGA_VUOTA);
	      }
	    } while (!finito);

	   return valoreLetto;
	  }


          /**
           * Metodo che chiese all'utente una conferma
           * @param messaggio
           * @return una variabile boolean
           */
	  public static boolean yesOrNo(String messaggio)
	  {
		  String mioMessaggio = messaggio + "("+RISPOSTA_SI+"/"+RISPOSTA_NO+")";
		  char valoreLetto = leggiUpperChar(mioMessaggio,String.valueOf(RISPOSTA_SI)+String.valueOf(RISPOSTA_NO));

		  if (valoreLetto == RISPOSTA_SI)
			return true;
		  else
			return false;
	  }

	  public static char leggiUpperChar (String messaggio, String ammissibili)
	  {
	   boolean finito = false;
	   char valoreLetto = '\0';
	   do
	   {
	    valoreLetto = leggiCaratt(messaggio);
	    valoreLetto = Character.toUpperCase(valoreLetto);
	    if (ammissibili.indexOf(valoreLetto) != -1)
		 finito  = true;
	    else
	     System.out.println(MESSAGGIO_AMMISSIBILI + ammissibili);
	   } while (!finito);
	   return valoreLetto;
	  }

	  public static int leggiIntConMinimo(String messaggio, int minimo)
	  {
	   boolean finito = false;
	   int valoreLetto = 0;
	   do
	    {
	     valoreLetto = leggiInt(messaggio);
	     if (valoreLetto >= minimo)
	      finito = true;
	     else
	      System.out.println(ERRORE_MINIMO + minimo);
	    } while (!finito);

	   return valoreLetto;
	  }

          /**
           * Overload del metodo leggiInt()
           * @param messaggio
           * @param minimo
           * @param massimo
           * @return un intero compreso nel range
           * @ensures valoreLetto <= massimo && valoreLetto >= minimo;
           */
	  public static int leggiInt(String messaggio, int minimo, int massimo)
	  {
	   boolean finito = false;
	   int valoreLetto = 0;
           String range = "";
           if (minimo == massimo)
               range = " (unico valore ammesso: " + minimo +"): ";
           else
               range = " (n° compreso tra " + minimo + " e " + massimo + "): ";
	   do
	    {
	     valoreLetto = leggiInt(messaggio + range);
	     if (valoreLetto >= minimo && valoreLetto<= massimo)
	      finito = true;
	     else
	      if (valoreLetto < minimo)
	         System.out.println(ERRORE_MINIMO + minimo);
	      else
	    	 System.out.println(ERRORE_MASSIMO + massimo);
	    } while (!finito);

	   return valoreLetto;
	  }
}
