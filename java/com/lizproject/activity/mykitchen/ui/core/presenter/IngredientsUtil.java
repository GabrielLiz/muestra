package com.lizproject.activity.mykitchen.ui.core.presenter;

public class IngredientsUtil {
    public static final String KEY_CANTIDAD_EN_CUCHARITAS = "cuchartitas";
    public static final String KEY_CANTIDAD_EN_KYLOS = "kylogramos";
    public static final String KEY_CANTIDAD_EN_LITROS = "litros";
    public static final String KEY_CANTIDAD_EN_UNIDADES = "unidades";
    public static final String KEY_MEDIDAS_TAZAS = "tazas";

    private String name;
    private String status;
    private String quanty;
    private String classtype;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQuanty() {
        return quanty;
    }

    public void setQuanty(String quanty) {
        this.quanty = quanty;
    }

    public String getClasstype() {
        return classtype;
    }

    public void setClasstype(String classtype) {
        this.classtype = classtype;
    }
}
