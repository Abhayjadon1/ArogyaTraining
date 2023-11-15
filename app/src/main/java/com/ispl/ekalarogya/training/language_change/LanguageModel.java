package com.ispl.ekalarogya.training.language_change;

public class LanguageModel {
    private int ivLang;
    private String tvLang,lng;
    private boolean isSelected;

    public LanguageModel(int ivLang, String tvLang,String lng, boolean isSelected) {
        this.ivLang = ivLang;
        this.tvLang = tvLang;
        this.lng = lng;
        this.isSelected = isSelected;
    }

    public int getIvLang() {
        return ivLang;
    }

    public String getTvLang() {
        return tvLang;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public String getLng() {
        return lng;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
