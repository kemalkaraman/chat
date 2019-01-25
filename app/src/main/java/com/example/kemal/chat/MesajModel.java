package com.example.kemal.chat;

public class MesajModel {
    private String from, text;

    public MesajModel() {
    }

    public MesajModel(String from, String text) {
        this.from = from;
        this.text = text;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "MesajModel{" +
                "from='" + from + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
