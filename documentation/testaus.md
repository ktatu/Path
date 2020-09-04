# Testausdokumentti

## Yksikkötestaus
<img src="https://github.com/ktatu/Path/blob/master/documentation/kuvat/testikattavuus.png">

### Testikattavuudesta
Ohjelman testikattavuus on 97 %, haaraumakattavuus 93 %. Suurin osa puuttuvasta kolmesta prosentista on gettereitä ja settereitä. Myös esimerkiksi InputData-luokan dataVerification() metodi näkyy testikattavuudessa testaamattomana, mutta metodi oikeastaan vain kutsuu luokkia tekemään toimintoja, jotka on jo niiden omissa testiluokissaan testattu.

### Algoritmien yksikkötestaus
Algoritmien testikattavuus on lähes 100 %, mutta yksikkötestit eivät todellisuudessa varmista algoritmien täydellistä toimimista. Testit tapahtuvat niitä varten generoiduissa pienissä kartoissa, jotka lähinnä varmistavat, että algoritmit ylipäätänsä toimivat ja liikkuvat odotetusti. Moving Ai:n tiedostoja olisi voinut käyttää yksikkötestauksessa varmistamaan, että algoritmit löytävät reitin kaikissa tilanteissa. Reitin pituuksien testauksessa tämä ei kuitenkaan olisi toiminut. Käytin sqrt(2):n sijaan arvoa 1.4 diagonaalisten liikkeiden kustannuksena ja en noudattanut Moving Ai:n liikkumisehtoja ohjelmassani - algoritmit löytävät lyhyempiä reittejä kuin mitä skenaariotiedostoissa on.

### Mitä jäi ulkopuolelle (excluded)
Testikattavuuden ulkopuolelle on jätetty ui-paketti, suorituskykytestaus-paketti ja AlgorithmImageWriter-luokka, joka piirtää kuvan algoritmista käyttöliittymää varten. Pidin kuvanpiirtoa lähinnä käyttöliittymään kuuluvana ominaisuutena. Sitä kuitenkin tulee testattua muun käyttöliittymätestauksen yhteydessä. Kuvan piirtämisen (ja tallettamisen) testaamatta jättäminen näkyy kuitenkin myös testikattavuudessa AlgorithmServicen puutteellisena kattavuutena.
