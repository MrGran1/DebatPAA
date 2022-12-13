import java.io.File;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Tigran WATTRELOS
 * @author Ilan' DAUMONT-OUK
 * @authour Terry TRAN
 */


public class Main {

    public static void main (String[]args){
        ArrayList <Argument> listeArgument = new ArrayList<Argument>();
        boolean boucle=true;
        Scanner sc = new Scanner(System.in);
        ArrayList <Argument> ensembleE = new ArrayList<Argument>();
        ArrayList <ArrayList<Argument>> listeSolution= new ArrayList<ArrayList<Argument>>(); //Pour fichier
        ArrayList <ArrayList<Argument>> dejaVu= new ArrayList<ArrayList<Argument>>();
        ArrayList <ArrayList<Argument>> dejaVuPref= new ArrayList<ArrayList<Argument>>();
        int autoManuel= -1;
        

        while (boucle){
            try{
                System.out.println("Voulez vous remplir les arguments et contradictions : \n 1) Manuellement \n 2) A l'aide d'un fichier");
                autoManuel = sc.nextInt();
                if (autoManuel== 1 || autoManuel== 2){
                    boucle = false;
                }
            }catch (InputMismatchException e){
                System.out.println("Veuillez entrer un nombre");
                sc.next();
            }
        }
        
        boucle = true;
        if (autoManuel==1){ // Manuel
            listeArgument = UtilDebat.creationArgument(sc); // On demande le nombre d'argument et on crée les arguments
            while (boucle){
                try{
                    System.out.println(listeArgument);
                    System.out.println("1) Ajouter une contradiction \n2) Fin");
                    int choix = sc.nextInt(); // On sticke ici le choix de l'utilistateur
                    if (choix == 1){ //Ajouter une contradiction

                    UtilDebat.ajoutContradictionSc(sc,listeArgument);
                    }

                    else if (choix == 2 ){ //FIN
                        boucle = false;
                    }

                    else{
                        System.out.println("Entrez un chiffre entre 1 et 4");
                    }
                }
                catch(InputMismatchException e){
                    System.out.println("Veuillez entrer un entier");
                    sc.nextLine();

                }
            }
            boucle=true;
            while(boucle){

                try{
                    System.out.println("1) Ajouter un argument \n2) Supprimer un argument \n3) Vérifier la solution \n4) Fin");
                    int choix = sc.nextInt();

                    if( choix==1){
                        
                        UtilDebat.ajouterArgument(listeArgument, ensembleE, sc);
                    }

                    else if (choix ==2){
                        UtilDebat.supprimerArgument(listeArgument, ensembleE, sc);
                    }

                    else if (choix ==3){// Vérifier la solution
                        String solution = UtilDebat.verifSolution(ensembleE);
                        if( solution.equals("Admissible") ){
                            System.out.println(ensembleE+" est une solution admissible");
                        }
                        else{
                            System.out.println(solution);
                        }
                        
                    }
                    

                    else if (choix ==4){ //Fin
                        boucle = false;
                    }
                    else{
                        System.out.println("Entrez un chiffre entre 1 et 4");
                    }
                }
                catch(InputMismatchException e){
                    System.out.println("Veuillez entrer un entier");
                    sc.nextLine();
                }
            }sc.close();
        }
        else if (autoManuel == 2){ //Fichier
            sc.nextLine();
            File fichier = UtilDebat.getFichier(sc);
            UtilDebat.lireFichier(fichier, listeArgument);
            boucle = true;
            boolean solDejaDemande = false;
            ArrayList <Argument> ensembleESauvegarde = new ArrayList<Argument>();
            while(boucle){

                try{
                    listeSolution = UtilDebat.recupListeSolution(listeArgument, listeSolution);
                    ArrayList <Argument> solAdmissible;                    
                    System.out.println("1) Chercher une solution admissible \n2) Chercher une solution préférée \n3) Sauvegarder la solution \n4) Fin");
                    int choix = sc.nextInt();

                    if( choix==1){
                        solDejaDemande = true;
                        solAdmissible =UtilDebat.solutionAdmissible(dejaVu, listeSolution);
                        if (solAdmissible==null){
                            dejaVu.clear();
                            solAdmissible =UtilDebat.solutionAdmissible(dejaVu,listeSolution);
                        }
                        ensembleESauvegarde=solAdmissible;
                        String chaine = "";
                        for (int i=0; i<solAdmissible.size();i++){
                            chaine+=solAdmissible.get(i);
                            if(solAdmissible.size()-1!=i){
                                chaine += ",";
                             }

                            
                        }
                        if (solAdmissible.isEmpty()){
                            chaine = "Ensemble vide";
                        }
                        System.out.println("solution admissible : "+chaine);
                    }

                    else if (choix ==2){
                        solDejaDemande = true;
                         ensembleE =UtilDebat.solutionPref(dejaVuPref,listeSolution);
                         if(ensembleE==null){
                            dejaVuPref.clear();
                            ensembleE =UtilDebat.solutionPref(dejaVuPref,listeSolution);
                         }
                         ensembleESauvegarde = ensembleE;
                         String chaine="";
                         for (int i=0; i<ensembleE.size();i++){
                             chaine+=ensembleE.get(i);
                             if(ensembleE.size()-1!=i){
                                chaine += ",";
                             }
                             
                             
                         }
                         if (ensembleE.isEmpty()){
                            chaine = "Ensemble vide";
                        }
                         System.out.println("solution préférée : "+chaine);   
                    }

                    else if (choix ==3){// Sauvegarder la solution
                        if (solDejaDemande){
                            ensembleE =UtilDebat.solutionAdmissible(dejaVuPref, listeSolution);
                            System.out.println("Veuiller entrer le chemin du fichier ");
                            sc.nextLine(); //on vide le buffer
                            String lien =sc.nextLine();
                            UtilDebat.sauvegarde(ensembleESauvegarde, lien);
                        }
                        else{
                            System.out.println("Veuillez séléctionner d'abord le choix 1 ou 2");
                        }
                        
                        
                    }
                    
                    else if (choix ==4){ //Fin
                        boucle = false;
                    }

                    else{
                        System.out.println("Entrez un chiffre entre 1 et 4");
                    }
                }
                catch(InputMismatchException e){
                    System.out.println("Veuillez entrer un entier");
                    sc.nextLine();
                }
            }sc.close();
            
        }
    }
}