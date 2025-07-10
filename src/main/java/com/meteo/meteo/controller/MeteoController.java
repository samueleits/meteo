package com.meteo.meteo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.meteo.meteo.dto.MeteoDto;
import com.meteo.meteo.dto.MeteoSettimanaleDto; // Import aggiunto
import com.meteo.meteo.service.MeteoService;

@Controller
public class MeteoController {

    @Autowired
    private MeteoService meteoService;

    private static final List<String> CITTA = List.of("Roma", "Milano", "Napoli", "Torino", "Firenze", "Venezia", "Bologna", "Genova", "Palermo", "Bari");

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("cittaDisponibili", CITTA);
        return "index";
    }

    @GetMapping("/meteo")
    public String meteo(@RequestParam(name="citta", required=false, defaultValue="Roma") String citta,
                        Model model) {
        model.addAttribute("cittaDisponibili", CITTA);
        model.addAttribute("cittaSelezionata", citta);

        MeteoDto meteo = meteoService.getMeteoPerCitta(citta);
        model.addAttribute("temperatura", meteo.getTemperatura());
        model.addAttribute("percepita", meteo.getTemperaturaPercepita());

        // Recupera i dati settimanali
        MeteoSettimanaleDto meteoSettimanale = meteoService.getMeteoSettimanalePerCitta(citta);
        model.addAttribute("dateMeteo", meteoSettimanale.getDate());
        model.addAttribute("tempMaxMeteo", meteoSettimanale.getTemperatureMassime());
        model.addAttribute("tempMinMeteo", meteoSettimanale.getTemperatureMinime());

        return "meteo";
    }
}
