package main;

public class LackOfCohesionOfTestSmellPresent {
    private String nome;
    private int eta;
    private double reddito;

    public LackOfCohesionOfTestSmellPresent() {
    }

    public LackOfCohesionOfTestSmellPresent(String nome, int eta, double reddito) {
        this.nome = nome;
        this.eta = eta;
        this.reddito = reddito;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getEta() {
        return eta;
    }

    public void setEta(int eta) {
        this.eta = eta;
    }

    public double getReddito() {
        return reddito;
    }

    public void setReddito(double reddito) {
        this.reddito = reddito;
    }
}
