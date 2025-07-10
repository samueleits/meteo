package com.meteo.meteo.dto;

import java.util.List;

public class MeteoSettimanaleDto {
    private List<String> date;
    private List<Double> temperatureMassime;
    private List<Double> temperatureMinime;

    public MeteoSettimanaleDto(List<String> date, List<Double> temperatureMassime, List<Double> temperatureMinime) {
        this.date = date;
        this.temperatureMassime = temperatureMassime;
        this.temperatureMinime = temperatureMinime;
    }

    // Getters
    public List<String> getDate() {
        return date;
    }

    public List<Double> getTemperatureMassime() {
        return temperatureMassime;
    }

    public List<Double> getTemperatureMinime() {
        return temperatureMinime;
    }

    // Puoi aggiungere setters se necessario, ma per un DTO di solito non sono richiesti
}
