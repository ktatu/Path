## Mitä olen tehnyt
Sain JPS:n vihdoin toimimaan. Tein myös algoritmeja varten ArrayListiä vastaavan tietorakenteen. Koitin välttää tätä, sillä listaa ei sinänsä tarvitse yhdessäkään toteuttamassani algoritmissa, mutta se teki koodista siistimpää. 
Aloitin myös suorituskykytestauksen, mutta en halunnut vielä kirjoittaa mitään dokumentaatiota siitä, sillä käyn algoritmien koodia läpi vielä ensi viikolla jos ehdin. Ne saattavat vielä nopeutua.

## Mitä ensi viikolla
Dokumentaatio ja testit pitää viimeistellä. Refaktoroin vielä koodia jos ehdin. Graafista käyttöliittymää voisi myös parantaa, erittäin pienet ja isot kartat eivät sovi hyvin ruutuun ja niihin voisi lisätä scrollauksen / zoomauksen.

<b>Käytetty aika 20h</b>

## Kysymys
Käytän ArrayListiä suorituskykytestauksessa tiedostosta luettujen koordinaattien talletukseen (ei siis itse testeissä) ja IO-luokissa [FileGridMapReader](https://github.com/ktatu/Path/blob/master/path/src/main/java/tiralabra/path/data/FileGridMapReader.java) ja [FileIO](https://github.com/ktatu/Path/blob/master/path/src/main/java/tiralabra/path/data/FileIO.java). Yksikkötestauksessa on myös javan Random paikoin käytössä.

Tämä on ok, vai ymmärsinkö FAQ:n väärin?
