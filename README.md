# Konzept: Schimmelrisiko und Taupunkt berechnen
---

## Definition

Mit Temperatur und relativer Feuchte ermitteln wir den Taupunkt und schätzen
darauf basierend das Risiko für Schimmelbildung in Klasse.

Je höher der Taupunkt in Innenräumen, desto eher schlägt sich Feuchte an kalten Wandflächen nieder.

Von _05_project die Temperatur mitels enpoint bekommen.

Von _06_project die Luftfeuchtigkeit mittels enpoint bekommen.

Mithilfe der Magnus-formel den Taupunkt berechnen.

Niedrig (< 10 °C Taupunkt) – gut
Mittel (10–14 °C) – mittel
Hoch (> 14 °C) – schlecht

---

## Architektur 

Spring boot backend

**Controller**
- 2 Controller (mold, dew)
  
**Repository**
- 2 Repositories (mold, dew)

**Service**
- Service mold - Führt Taupunkt-Berechnung durch (Magnus-Formel)
- Service dew - Klassifiziert Schimmelrisiko anhand Taupunkt Vergibt Labels ("niedrig", "mittel", "hoch")

**Datenverwaltung**
Gespeichert werden die Daten in `postgres`.

**Intervall** -> gesetztes intervall zum neu fetchen der temp. und luft Daten.

**Tables**
- dewpoint_data
- mold_risk_data
- mold_risk_labels

---
  
Dockerfile - um spring boot backend zu image zu machen.

Mit gradle oder maven das image in das container registry pushen.

Dazu ein lightweight `react frontend` mit `tailwind css` welches die daten simpel und einfach ausgeben kann.
