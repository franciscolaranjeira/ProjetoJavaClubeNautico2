package model.entities;

import java.util.ArrayList;
import java.util.List;

public class EncarregadoEducacao {

    private Integer idEncarregadoEducacao;
    private String nome;
    private int telefone;

    private List<String> nomesMenores = new ArrayList<>();
    private int totalMenoresACargo;

    public EncarregadoEducacao(String nome, int telefone) {
        this.nome = nome;
        this.telefone = telefone;
    }

    public EncarregadoEducacao() {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdEncarregadoEducacao() {
        return idEncarregadoEducacao;
    }

    public void setIdEncarregadoEducacao(Integer idEncarregadoEducacao) {
        this.idEncarregadoEducacao = idEncarregadoEducacao;
    }

    public List<String> getNomesMenores() {
        return nomesMenores;
    }

    public void setNomesMenores(List<String> nomesMenores) {
        this.nomesMenores = nomesMenores;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public int getTotalMenoresACargo() {
        return totalMenoresACargo;
    }

    public void setTotalMenoresACargo(int totalMenoresACargo) {
        this.totalMenoresACargo = totalMenoresACargo;
    }

    public void listaEncarregadosEducacao() {
        System.out.println("\n|==================================|");
        System.out.println("| Encarregado Educacao: " + nome
                + "\n| Numero menores a cargo: " + totalMenoresACargo
                + "\n| Nome Menores: " + nomesMenores
                + "\n|==================================|");
    }
    
    @Override
    public String toString() {
        return "\n| Nome Encarregado Educacao: " + nome
                + "\n| Telefone: " + telefone;
    }
}
