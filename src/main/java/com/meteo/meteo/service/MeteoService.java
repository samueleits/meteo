package com.meteo.meteo.service;

import java.util.ArrayList; // Import aggiunto
import java.util.List;      // Import aggiunto
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.meteo.meteo.dto.MeteoDto;
import com.meteo.meteo.dto.MeteoSettimanaleDto; // Nuova DTO per i dati settimanali

@Service
public class MeteoService {
    private static final Map<String, double[]> COORDINATE_CITTA = Map.of(
            "Roma", new double[]{41.9028, 12.4964},
            "Milano", new double[]{45.4642, 9.1900},
            "Napoli", new double[]{40.8518, 14.2681},
            "Torino", new double[]{45.0703, 7.6869},
            "Firenze", new double[]{43.7696, 11.2558},
            "Venezia", new double[]{45.4371, 12.3325},
            "Bologna", new double[]{44.4949, 11.3426},
            "Genova", new double[]{44.4056, 8.9463},
            "Palermo", new double[]{38.1157, 13.3615},
            "Bari", new double[]{41.1171, 16.8719}
    );

    private static final String OPEN_METEO_URL = "https://api.open-meteo.com/v1/forecast";

    private final RestTemplate restTemplate = new RestTemplate();

    public MeteoDto getMeteoPerCitta(String citta) {
        double[] coord = COORDINATE_CITTA.getOrDefault(citta, new double[]{41.9028, 12.4964});

        String url = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("api.open-meteo.com")
                .path("/v1/forecast")
                .queryParam("latitude", coord[0])
                .queryParam("longitude", coord[1])
                .queryParam("current_weather", true) // Richiede solo il meteo corrente
                .build()
                .toUriString();

        OpenMeteoResponseCurrent response = restTemplate.getForObject(url, OpenMeteoResponseCurrent.class);

        if (response != null && response.currentWeather != null) {
            Double temperatura = response.currentWeather.temperature;
            Double temperaturaPercepita = response.currentWeather.apparentTemperature;
            return new MeteoDto(temperatura, temperaturaPercepita);
        }
        return new MeteoDto(null, null);
    }

    // NUOVO METODO per ottenere i dati settimanali
    public MeteoSettimanaleDto getMeteoSettimanalePerCitta(String citta) {
        double[] coord = COORDINATE_CITTA.getOrDefault(citta, new double[]{41.9028, 12.4964});

        String url = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("api.open-meteo.com")
                .path("/v1/forecast")
                .queryParam("latitude", coord[0])
                .queryParam("longitude", coord[1])
                // Richiediamo la temperatura massima e minima giornaliera, più le date
                .queryParam("daily", "temperature_2m_max,temperature_2m_min")
                .queryParam("timezone", "auto") // Per avere le date corrette
                .build()
                .toUriString();

        OpenMeteoResponseDaily response = restTemplate.getForObject(url, OpenMeteoResponseDaily.class);

        if (response != null && response.daily != null) {
            return new MeteoSettimanaleDto(
                response.daily.time,
                response.daily.temperature2mMax,
                response.daily.temperature2mMin
            );
        }
        return new MeteoSettimanaleDto(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }


    // Classi interne per il parsing della risposta API (meteo corrente)
    private static class OpenMeteoResponseCurrent {
        @JsonProperty("current_weather")
        public CurrentWeather currentWeather;
    }

    private static class CurrentWeather {
        @JsonProperty("temperature")
        public Double temperature;

        @JsonProperty("apparent_temperature")
        public Double apparentTemperature;
    }

    // NUOVE Classi interne per il parsing della risposta API (meteo settimanale)
    private static class OpenMeteoResponseDaily {
        @JsonProperty("daily")
        public DailyData daily;
    }

    private static class DailyData {
        @JsonProperty("time")
        public List<String> time; // Le date dei giorni

        @JsonProperty("temperature_2m_max")
        public List<Double> temperature2mMax; // Temperature massime giornaliere

        @JsonProperty("temperature_2m_min")
        public List<Double> temperature2mMin; // Temperature minime giornaliere
    }
}
