/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.logic;

/**
 *
 * @author Francisco Valente
 */
public enum Categoria {
    REPARACAO_AUTOMOVEIS_MOTOCICLOS("Reparação de automóveis e motociclos", 0.15,0.15),
    ALOJAMENTO("Alojamento", 0.23,0.15),
    RESTAURACAO("Restauração", 0.13,0.15),
    CABELEIREIROS("Cabeleireiros", 0.23,0.15),
    ATIVIDADES_VETERINARIAS("Atividades veterinárias", 0.23,0.15),
    GINASIOS("Ginásios", 0.23,0.15),
    PASSES_TRANSPORTES_PUBLICOS("Passes mensais de transportes públicos", 0,1.0),
    MEDICAMENTOS_USO_VETERINARIO("Medicamentos de uso veterinário", 0.23,0.35);

    private String categoria;
    private double iva;
    private double irs;


    Categoria(String categoria, double iva,double irs) {
        this.categoria = categoria;
        this.iva = iva;
        this.irs = irs;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getIVA() {
        return iva;
    }
    
    public double getIRS() {
        return irs;
    }
}