package com.company;
import java.util.Scanner;

import java.util.ArrayList;

public class Banque {
    private final ArrayList<Client> clients;

    public Banque() {
        this.clients = new ArrayList<>();
    }

    public void interaction() {
        boolean open = true;
        switch (choixMenu(2,null)) {
            case 0 -> open = false;
            case 1 -> ajouterClient();
            case 2 -> operationClient();
            case 3 -> bilanBanque();
            default -> System.out.println("Selection inconnue, veuillez réessayer");
        }
        if(open) {
            System.out.println();
            interaction();
        }
    }

    private void bilanBanque() {
        int i = 1;
        float total = 0.0f;
        for (Client c : clients) {
            System.out.println(i + ") " + c.getNom());
            c.afficherSolde();
            total += c.getSolde();
            System.out.println();
        }
        System.out.println("Coffre de banque : " + total + " €");
    }

    private int choixMenu(int menuID, Client client) {
        menu(menuID,client);
        Scanner scan = new Scanner(System.in);
        if (scan.hasNextInt()) {
            return scan.nextInt();
        } else {
            System.out.println("Saisie incorrecte");
            System.out.print("- ");
            return choixMenu(menuID,client);
        }
    }

    private void menu(int id, Client client) {
        switch (id) {
            case 0 -> {
                System.out.println("1) Afficher un bilan");
                System.out.println("2) Faire un retrait");
                System.out.println("3) Faire un dépot");
                System.out.println("4) Faire un virement");
                System.out.println("5) Ajouter un compte");
                System.out.println("0) Retour");
            }
            case 1 -> {
                System.out.println("Quel Client?");
                System.out.println();
                int i = 1;
                for (Client c : clients) {
                    System.out.println(i + ") " + c.getNom());
                    i++;
                }
            }
            case 2 -> {
                System.out.println("Quelle opération voulez-vous effectuer ?");
                System.out.println("1) Ajouter un client");
                System.out.println("2) Effectuer une operation sur un client");
                System.out.println("3) Afficher un bilan général");
                System.out.println("0) Quitter");
            }
            case 3 -> {
                System.out.println("Quel Compte?");
                System.out.println();

                for(int i=0; i< client.getNbComptes(); i++) {
                    System.out.println((i+1) + ") " + client.getCompte(i).getNumCompte());
                }
            }
        }
        System.out.println();
        System.out.print("- ");
    }

    private void ajouterClient(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Entrez le nom du client:");
        System.out.println();

        System.out.print("- ");
        String rep = scan.next();
        System.out.println();

        clients.add(new Client(rep,clients.size()));
        System.out.println("Le Client "+ rep +" a été créé.");
        System.out.println();
        interaction();
    }

    private int selectionnerClient() {
        if(clients.size() == 0) {
            System.out.println("Pas de client disponible");
            interaction();
            System.exit(0);
        }
        int rep = choixMenu(1,null);
        System.out.println();
        if(rep > clients.size()) {
            System.out.println("Client inconnu, veuillez essayer à nouveau");
            return selectionnerClient();
        } else {
            return rep-1;
        }
    }

    private Compte selectionnerCompte(Client client) {
        if(client.getNbComptes() == 1)return client.getCompte(0);
        int rep = choixMenu(3,client);
        if(rep > client.getNbComptes()) {
            System.out.println("Compte Inconnu, veuillez essayer à nouveau");
            return selectionnerCompte(client);
        } else {
            return client.getCompte(rep-1);
        }
    }

    private void operationClient() {
        Client client = clients.get(selectionnerClient());

        switch (choixMenu(0,null)) {
            case 0 -> interaction();
            case 1 -> client.afficherSolde();
            case 2 -> selectionnerCompte(client).retrait(saisirMontant());
            case 3 -> selectionnerCompte(client).depot(saisirMontant());
            case 4 -> virement(selectionnerCompte(client));
            case 5 -> client.ajouterCompte();
            default -> {
                System.out.println("Selection inconnue, veuillez réessayer");
                operationClient();
            }
        }
    }

    private float saisirMontant(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Saisissez un montant (0 pour annuler):");
        System.out.println();
        System.out.print("- ");
        if (scan.hasNextFloat()) {
            return scan.nextFloat();
        } else {
            System.out.println("Montant incorrect");
            System.out.print("- ");
            return saisirMontant();
        }
    }

    private void virement(Compte emetteur) {
        System.out.println("Quel bénéficiaire ?");
        int rep = selectionnerClient();

        Compte beneficiaire = selectionnerCompte(clients.get(rep));

        if(beneficiaire.getNumCompte() != emetteur.getNumCompte()){
            System.out.println("Saisissez un montant:");
            System.out.println();

            emetteur.virer(saisirMontant(),beneficiaire);
        } else {
            System.out.println("Vous ne pouvez pas effectuer de virement sur le même compte");
            virement(emetteur);
        }
    }
}
