package com.company;

import java.util.ArrayList;

public class Client {
    private final String nom;
    private final ArrayList<Compte>  comptes = new ArrayList<>();

    public Client(String nom_du_client, int numClient) {
        nom = nom_du_client;
        comptes.add(new Compte(numClient));
    }

    public void afficherSolde() {
        for (Compte compte : comptes) {
            compte.afficherSolde();
        }
        System.out.println("Votre solde est de : " + this.getSolde() + "€");
    }

    public void ajouterCompte() {
        float numCompte = comptes.get(0).getNumCompte() + (float)comptes.size()/100;
        comptes.add(new Compte(numCompte));
        System.out.println("Vous disposez de " + comptes.size() + " comptes.");
    }

    public String getNom() {
        return nom;
    }

    public float getSolde() {
        float totalSolde = 0;
        for (Compte compte : comptes) {
            totalSolde += compte.getSolde();
        }
        return totalSolde;
    }

    public int getNbComptes() {
        return comptes.size();
    }

    public Compte getCompte(int num) {
        return comptes.get(num);
    }
}
