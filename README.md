# Abschlussaufgabe

Herzlichen Glückwunsch, Sie haben es fast durch Programmierung 1 geschafft - hier kommt die Abschlussaufgabe!

## Abgabe

Sie können die Lösung auf drei Arten abgeben:

- Falls Sie schon Erfahrung mit git haben, können Sie das Repository forken, den fork auf private schalten, Ihre Lösung dort verwalten und mich zu dem repo einladen. Die Antwort auf die Analyse-Frage am Ende tragen Sie in diesem Fall bitte einfach am Ende der README.md ein. Ich werde dann für die Benotung den letzten commit mit Datum vor dem Ende des 1. Prüfungszeitraums verwenden.
- Sie können auch den *kompletten* src-Ordner packen (zip, tar, tar.gz - was Ihnen am liebsten ist) und mir vor Ende des 1. Prüfungszeitraums abgeben, indem Sie:
  - Die Datei in Moodle als Abgabe hochladen. Tragen Sie in diesem Fall Ihre Antwort auf die Analyse-Frage in das Freitextfeld der Abgabe ein.
  - Mir die Datei per Mail *von Ihrem HTW-Mail-Account aus* schicken, in dem Text der Mail soll dann die Antwort auf die Analyse-Frage stehen. Bitte nutzen Sie diese Möglichkeit nur als Backup für den Fall, dass aus irgendeinem Grund die Abgabe per Moodle bei Ihnen nicht funktioniert!

*VORSICHT* Das ist eine Prüfungsaufgabe, bei der es einige klare Regeln gibt, von denen ich nicht abweichen werde. Die Prüfung gilt auf *jeden Fall* als *nicht bestanden*, falls:
- Sie die Aufgabe auf eine andere Art abgeben, als die 3 oben genannten Möglichkeiten (dazu zählt auch, wenn Sie mir beispielsweise den Code einfach in die Mail copy-pasten - das würde als "nicht korrekt abgegeben, nicht bestanden" gelten)
- Die Tests nicht funktionieren. Es geht wie in fast jeder Übung besprochen nicht darum, dass alle Tests "grün" sein müssen, aber der Gradle-Task "verification->test" muss grundlegend funktionieren, die Tests müssen alle ausgeführt werden (egal, ob das Ergebnis "passed" oder "failed" ist, das hat dann nur auf die Zensur Einfluss)
- Sie die Testdateien im Verzeichnis src/test verändert haben

## Benotung

Für jede korrekt implementierte Methode gibt es pauschal 4.5 Punkte. Es sind insgesamt 21 Methoden zu implementieren, somit können mit einer vollständig korrekten Implementation 94.5 Punkte erreicht werden.

Für die Analyseaufgabe gibt es 6.5 Punkte.

Insgesamt können somit maximal 100 Punkte erreicht werden, die Umrechnung in eine Zensur erfolgt entsprechend der hochschulüblichen Skala.

## Hintergrund

In dieser Aufgabe arbeiten Sie mit Daten aus dem [OpenData-Portal des Deutschen Wetterdienstes](https://www.dwd.de/DE/leistungen/opendata/opendata.html), um zu sehen, wie sich mit der Zeit die Temperaturen an diversen Messstationen verändert haben.

Sie können die historischen Temperaturdaten der letzten Jahre [hier](https://opendata.dwd.de/climate_environment/CDC/observations_germany/climate/daily/kl/historical/) als zip-Dateien herunterladen.
Auf den Seiten des DWD finden Sie auch eine [Beschreibung des Formats der Datendateien](https://opendata.dwd.de/climate_environment/CDC/observations_germany/climate/daily/kl/BESCHREIBUNG_obsgermany-climate-daily-kl_de.pdf) sowie eine [Liste der Stationsnummern](https://opendata.dwd.de/climate_environment/CDC/observations_germany/climate/daily/kl/historical/KL_Tageswerte_Beschreibung_Stationen.txt). Zum Testen können Sie auch die Datei "testdata.txt" aus dem data-Ordner in diesem Repository verwenden.

Endziel dieser Aufgabe ist es, dass Sie aus der Datendatei die Mindest-, Maximal- sowie Durchschnittstemperaturen lesen und zwischen zwei Jahren einen Vergleich erstellen.



## TemperatureDataPoint

Implementieren Sie eine Klasse `TemperatureDataPoint`, die einen Temperaturdatenpunkt (repräsentiert durch eine Zeile in der Datendatei) mit den folgenden Informationen hält:

- Jahr, in dem der Datenpunkt aufgenommen wurde (aus MESS_DATUM)
- Minimaltemperatur an dem Tag (TNK)
- Maximaltemperatur an dem Tag (TXK)
- Durchschnittstemperatur an dem Tag (TMK)

Die Klasse soll folgende Funktionalitäten bieten:

### Konstruktoren und Grundfunktionen

* `public TemperatureDataPoint(double min, double avg, double max, int year)`: Constructor, der die 4 Werte des Datenpunktes explizit übergeben bekommt.
* `public TemperatureDataPoint(String line)`: Constructor, der eine Zeile aus der Textdatei bekommt. Zur Erinnerung: In dem vorherigen Abschnitt ist eine Beschreibung des Dateiformats verlinkt, in der steht, wo in jeder Zeile die nötigen Werte (MESS_DATUM, TNK, TXK, TMK) zu finden sind. Sie können auch zur Sicherheit in den Code der Tests schauen.
* `public double getMinTemperature()`: Gibt die Minimaltemperatur an dem Tag zurück.
* `public double getAvgTemperature()`: Gibt die Durchschnittstemperatur an dem Tag zurück.
* `public double getMaxTemperature()`: Gibt die Maximaltemperatur an dem Tag zurück.
* `public int getYear()`: Gibt das Jahr, in dem der Datenpunkt aufgenommen wurde, zurück.

### Analysemethoden

* `public double[] getTemperatures()`: Gibt die Temperaturen als double-Array zurück. Dabei soll an Position 0 die Mindesttemperatur, an Position 1 die Durchschnittstemperatur und an Position 2 die Maximaltemperatur stehen. Vorsicht: Die Reihenfolge ist hier anders, als in der Datei!
* `public TemperatureDataPoint getTemperatureDifference(TemperatureDataPoint other)`: Gibt einen neuen `TemperatureDataPoint` zurück, der die Absolutwerte der Differenzen der Temperaturen von diesem `TemperatureDataPoint` und `other` zurückgibt. Das Jahr soll dem Jahr des `TemperatureDataPoint` entsprechen, auf dem die Methode aufgerufen wurde (also nicht dem Jahr von `other`, falls die Jahre unterschiedlich sind).
* `public boolean isInYear(int year)`: Gibt `true` zurück, falls das Mess-Datum des Punktes im übergebenen Jahr liegt, sonst `false`.

### Ausgabe

* `public String toString()`: Gibt eine String-Repräsentation des Datenpunktes zurück, nach dem folgenden Format:
  "Temperatures on given day in [Jahr]: [Minimaltemperatur] - [Maximaltemperatur], average: [Durchschnittstemperatur].". Die Ausgabe wäre also für das Jahr 2023 mit TNK=5.0, TXK=7.5 und TMK=5.5: "Temperatures on given day in 2023: 5.0 - 7.5, average: 5.5.".

### Hinweise

* Achten Sie darauf, dass die Methode `toString()` einen String zurückgeben soll, ein reines Ausgeben reicht nicht!


## TemperatureTimeSeries

Implementieren Sie eine Klasse `TemperatureTimeSeries`, die eine Zeitreihe von Temperaturdaten verwaltet. Die Klasse soll folgende Funktionalitäten bieten:

### Konstruktoren und Grundfunktionen

* `public TemperatureTimeSeries()`: Erstellt eine leere Zeitreihe.
* `public TemperatureTimeSeries(String filename)`: Liest Temperaturdaten aus einer CSV-Datei ein. Das Format der einzelnen Zeilen ist wie in `public TemperatureDataPoint(String line)` beschrieben. Die erste Zeile wird als Header übersprungen.
* `public void addDataPoint(TemperatureDataPoint dataPoint)`: Fügt einen einzelnen Datenpunkt zur Zeitreihe hinzu.
* `public ArrayList<TemperatureDataPoint> getDataPoints()`: Gibt alle Datenpunkte zurück.
* `public int getNumDatapoints()`: Gibt die Anzahl der Datenpunkte zurück.

### Analysemethoden

* `public TemperatureTimeSeries filterByYear(int year)`: Erstellt eine neue Zeitreihe, die nur Datenpunkte aus dem angegebenen Jahr enthält. Es soll also eine neue `TemperatureTimeSeries` erstellt werden, die aber nur die `TemperatureDataPoint`s enthält, welche in dem Jahr `year` aufgenommen wurden.
* `public double getMinTemperature()`: Ermittelt die niedrigste Temperatur über alle Datenpunkte.
* `public double getMaxTemperature()`: Ermittelt die höchste Temperatur über alle Datenpunkte.
* `public double getOverallAvg()`: Berechnet den Durchschnitt der Durchschnittstemperaturen aller Datenpunkte.
* `public TemperatureDataPoint[] getAvgTemperatureDifferences()`: Berechnet die absoluten Temperaturdifferenzen zwischen aufeinanderfolgenden Datenpunkten und gibt diese als Array zurück. Hinweis: Sie können hier eine der Analysemethoden von `TemperatureDataPoint` verwenden, um sich das Leben sehr einfach zu machen.

### Ausgabe

* `public String toString()`: Gibt eine formatierte Übersicht der Zeitreihe zurück. Das Format soll wie folgt aussehen, wobei [Datenpunkt n] jeweils die Beschreibung ist, die der entsprechende `TemperatureDataPoint` mit seiner `toString()`-Methode zurückgibt:
  ```
  Temperature time series with [Anzahl] data points:
  * [Datenpunkt 1]
  * [Datenpunkt 2]
  ...
  ```

### Fehlerbehandlung

* Der Konstruktor mit Dateinamen soll eine Exception mit der Nachricht "Fehler beim Einlesen der Datei [Dateiname]" werfen, falls die Datei nicht gelesen werden kann.

### Hinweise

* Die Klasse arbeitet mit der bereits implementierten Klasse `TemperatureDataPoint` zusammen.
* Bei der Berechnung von Durchschnittswerten und Temperaturunterschieden müssen keine leeren Zeitreihen berücksichtigt werden.
* Die Datenpunkte sollen in `toString()` in der Reihenfolge aufgelistet werden, in der sie eingelesen oder hinzugefügt wurden.

## Analyseaufgabe

Implementieren Sie eine Klasse `Main` mit der gewohnten `main`-Methode, um den Code als eigenständiges Programm auszuführen. Führen Sie das Programm mit einer Datei von der DWD-Webseite für eine Station Ihrer Wahl aus. Filtern Sie datai mit der Methode `filterByYear()` zwei Jahre Ihrer Wahl heraus, die mindestens 10 Jahre auseinanderliegen. Lassen Sie sich dann für diese beiden Jahre jeweils die Gesamt-Durchschnittstemperatur (berechnet über die Methode `getOverallAvg()`) ausgeben und geben Sie die Ergebnisse (Stationsname, die 2 Jahreszahlen sowie die entsprechenden Temperaturen) als Antwort als die Analyseaufgabe an. Beschreiben Sie zudem kurz die Ergebnisse. 

Fällt Ihnen etwas Seltsames auf? Falls ja, woran könnte das liegen? Können Sie das Problem beheben, und wie ändert sich danach das Ergebnis (für die letzten 2 Fragen gibt es keine Punkte, es ist rein freiwillig)?
