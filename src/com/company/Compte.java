package com.company;

public class Compte {
    private final float numCompte;
    private float solde = 0.0f;

    public Compte(float numeroCompte) {
        numCompte = numeroCompte;
    }

    public void depot( float valeur) {
        if(valeur == 0) return;
        solde += valeur;
        System.out.println("Le solde du compte "+ numCompte +" est maintenant de : " + solde + "€");
    }

    public void retrait( float valeur) {
        if(valeur == 0) return;
        solde -= valeur;
        System.out.println("Le solde du compte "+ numCompte +" est maintenant de : " + solde + "€");
    }

    public void afficherSolde() {
        System.out.println("Le solde du compte "+ numCompte +" est de : " + solde + "€");
    }

    public void virer( float valeur, Compte destinataire) {
        retrait(valeur);
        destinataire.depot(valeur);
    }

    public float getSolde() {
        return solde;
    }

    public float getNumCompte() {
        return numCompte;
    }
}
