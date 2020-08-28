# Käyttöohje

## Ohjelman käynnistys
<b>Huom! Ohjelma toimii testatusti vain Java 11:llä, eikä ainakaan toiminut Java 8:ssa</b>

### Kloonatun / ladatun projektin suoritus NetBeansista
Avaa projekti NetBeansissa. Oikea klikkaa projektia ja valitse "Build". Nyt ohjelman pitäisi käynnistyä oikea klikkaamalla projektia ja valitsemalla Run Maven -> javafx run.

JavaFX:n takia ohjelman suoritus "Run Project"-napista ei toimi.

### .jar
Graafinen käyttöliittymä aukeaa suorittamalla .jar komennolla 
```
java -jar path-1.0-SNAPSHOT.jar
```
Suorituskykytestejä voi suorittaa .jarista komennolla
```
java -jar path-1.0-SNAPSHOT.jar testityyppi karttatiedosto.map skenaariotiedosto.map.scen suoritukset_per_skenaario
```
Lisäohjeistusta löytyy testausdokumentista

## Käyttöliittymä
Graafisen käyttöliittymän komponentit:
- Choose a map-nappi
  - Avaa hakemiston, josta syötetään karttatiedosto. Valmiita karttatiedostoja (.map) löytyy [täältä](https://movingai.com/benchmarks/grids.html) 
  - Lisää tietoa kartoista löytyy [määrittelydokumentista](https://github.com/ktatu/Path/blob/master/documentation/maarittely.md)
- Save image?-checkbox
  - Jos checkbox on valittu, niin algoritmin onnistuneen suorituksen jälkeen algoritmista kartalla tallettuu kuva projektin juurihakemistoon
- Start coordinates ja Goal coordinates
  - Algoritmille alku- ja loppukoordinaatit. Ohjelma ilmoittaa virheestä, jos reittiä kyseisten koordinaattien kautta ei voi suorittaa
  - Ohjelmassa koordinaatit muodostavat "Skenaarioita". Valmiita skenaarioita löytyy myös [täältä](https://movingai.com/benchmarks/grids.html) 
- Algoritmin valinta
  - Valitse suoritettava algoritmi
  
## Ohjelman suoritus
Ohjelma suorittaa valitun algoritmin Run algorithm-napista. Mahdollisista käyttäjäsyötteestä johtuvista virhetilanteista ilmestyy näytölle virheilmoitus. Onnistuneesti suoritetusta algoritmista ilmestyy kuva käyttöliittymään.
  
  
