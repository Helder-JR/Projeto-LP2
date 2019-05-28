package Entidades;

import java.util.Objects;

public class Pessoa {

    private String nome;
    private String dni;
    private String estado;
    private String interesses;
    private String partido;
    private Funcao funcao;

    public Pessoa(String nome, String dni, String estado, String interesses, String partido) {
        this.nome = nome;
        this.dni = dni;
        this.interesses = interesses;
        this.estado = estado;
        this.partido = partido;
        this.funcao = new Civil(dni);
    }

    public Pessoa(String nome, String dni, String estado, String interesses) {
        this(nome, dni, estado, interesses,"");
    }

    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(nome, pessoa.nome) &&
                Objects.equals(dni, pessoa.dni) &&
                Objects.equals(estado, pessoa.estado) &&
                Objects.equals(interesses, pessoa.interesses) &&
                Objects.equals(partido, pessoa.partido) &&
                funcao.equals(pessoa.funcao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, dni, estado, interesses, partido, funcao);
    }

    public String toString() {
        return this.funcao.toString(this.nome, this.estado, this.partido, this.interesses);
    }
}
