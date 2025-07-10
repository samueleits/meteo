# Meteo App

Applicazione Java Spring Boot che recupera dati meteo (attuali e degli prossimi 7 giorni) per alcune città italiane tramite l'API di [open-meteo.com](https://open-meteo.com).

L’utente seleziona una città e visualizza le temperature massime e minime settimanali in un grafico a barre.

**Tecnologie usate**
Java 17
Spring Boot
Docker / Docker Compose
Open-Meteo API

**Città supportate**
Roma
Milano
Napoli
Torino
Firenze
Venezia
Bologna
Genova
Palermo
Bari

**Rimbocchiamoci le maniche e buildiamo questo fantastico progetto insieme.**

![Demo GIF](img/drip-vegeta-drip.gif)
   
## Installazione (3 step)

1. **Clona il progetto**
   git clone https://github.com/samueleits/meteo.git

2. **Avvia con Docker Compose**
   docker-compose up --build

3. **Apri nel browser**
  http://localhost:8080

## Arresto (2 step)

1. **Per interrompere l'app:**
Nel terminale: Ctrl + C

2. **Per rimuovere i container:**
docker-compose down
