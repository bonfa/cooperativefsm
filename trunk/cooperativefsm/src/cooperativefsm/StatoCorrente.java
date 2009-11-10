package cooperativefsm;

/**
 *
 * @author Renato
 * La classe stato corrente definisce la situazione della simulazione in un certo istante
 *
 */
public class StatoCorrente

{
    public Stato sCorrenteFsm1;
    public Stato sCorrenteFsm2;

    //Modify
    public StatoCorrente (/*Stato corrente1, Stato corrente2*/)
    {

        sCorrenteFsm1 = new Stato(0);
        sCorrenteFsm2 = new Stato(0);
        /*
        sCorrenteFsm1 = corrente1;
        sCorrenteFsm2 = corrente2;
        */
    }

    /**
     * Imposta gli stati correnti delle varie fsm
     * Per comodità si dà per scontato che le macchine di una simulazione siano 2
     * 
     * @param corrente1
     * @param corrente2
     */
    public void setStati ( Stato corrente1, Stato corrente2 )
    {
        //sCorrenteFsm1 = new Stato(corrente1.getId());
        sCorrenteFsm1 = corrente1;
        //sCorrenteFsm2 = new Stato(corrente2.getId());
        sCorrenteFsm2 = corrente2;
    }

    public Stato getStato(int s)
    {
        Stato p = null;
        if(s==0)
            p=sCorrenteFsm1;
        if(s==1)
            p=sCorrenteFsm2;
        return p;
    }
}

