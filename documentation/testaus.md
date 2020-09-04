# Testausdokumentti

## Yksikkötestaus
<img src="https://github.com/ktatu/Path/blob/master/documentation/kuvat/testikattavuus.png">

### Testikattavuudesta
Ohjelman testikattavuus on 97 %, haaraumakattavuus 93 %. Suurin osa puuttuvasta kolmesta prosentista on gettereitä ja settereitä. Myös esimerkiksi InputData-luokan dataVerification() metodi näkyy testikattavuudessa testaamattomana, mutta metodi oikeastaan vain kutsuu luokkia tekemään toimintoja, jotka on jo niiden omissa testiluokissaan testattu.

### Algoritmien yksikkötestaus
Algoritmien testikattavuus on lähes 100 %, mutta yksikkötestit eivät todellisuudessa varmista algoritmien täydellistä toimimista. Testit tapahtuvat niitä varten generoiduissa pienissä kartoissa, jotka lähinnä varmistavat, että algoritmit ylipäätänsä toimivat ja liikkuvat kartoissa odotetusti. Moving Ai:n tiedostoja olisi voinut käyttää yksikkötestauksessa varmistamaan, että algoritmit löytävät reitin kaikissa tilanteissa. Reitin pituuksien testauksessa tämä ei kuitenkaan olisi toiminut. Käytin sqrt(2):n sijaan arvoa 1.4 diagonaalisten liikkeiden kustannuksena ja en noudattanut Moving Ai:n liikkumisehtoja ohjelmassani - algoritmit löytävät lyhyempiä reittejä kuin mitä skenaariotiedostoissa on.

### Mitä jäi ulkopuolelle (excluded)
Testikattavuuden ulkopuolelle on jätetty ui-paketti, suorituskykytestaus-paketti ja AlgorithmImageWriter-luokka, joka piirtää kuvan algoritmista käyttöliittymää varten. Pidin kuvanpiirtoa lähinnä käyttöliittymään kuuluvana ominaisuutena. Sitä kuitenkin tulee testattua muun käyttöliittymätestauksen yhteydessä. Kuvan piirtämisen (ja tallettamisen) testaamatta jättäminen näkyy kuitenkin myös testikattavuudessa AlgorithmServicen puutteellisena kattavuutena.

### Yksikkötestien suorituksesta
Yksikkötestit kloonatusta ohjelmasta suorittaessa konsoliin ilmestyy ilmoituksia poikkeuksista, vaikka testit menevät läpi. Tämä johtuu nimenomaan kyseisiä poikkeuksia testaavista testeistä. Tämä ei siis tarkoita, että jossakin testissä olisi tapahtunut odottamaton poikkeus, siinä tapauksessa kyseiset testit eivät menisi läpi.

## Suorituskykytestaus

### Toteutus
Suorituskykytestauksessa on käytetty Moving Ai:n kartta- ja skenaariotiedostoja. Releasesta löytyy ne tiedostot, joita olen itse käyttänyt, mutta testit voi pyörittää millä tahansa Moving Ai-formaatin tiedostoilla. Testien tuloksena syntyy graafeja, joista voi tulkita algoritmien eroja. Testejä on kolme ja ne voi suorittaa .jarista komentorivillä:
```
java -jar path-SNAPSHOT.jar no_bfs_runtime kartta.map 10
```
Testi vertaa Dijkstran, A*:n ja JPS:n suorituskykyä suorittamalla joka kymmenennen skenaarion tiedostosta parametrina annetulla kartalla. kartta.map on Moving Ai-kartta, jolla testi halutaan suorittaa ja viimeisenä argumenttina annettava luku kertoo testille kuinka monta kertaa kukin skenaario suoritetaan per algoritmi, luku voi siis olla mitä tahansa (>0). Suoritusajaksi valitaan lopuksi iteraatioiden mediaani. Luotettavan suoritusajan saamiseksi jokainen skenaario pitäisi suorittaa ainakin muutaman kerran, mutta etenkin isommilla kartoilla >=10 iteraatiolla testistä tulee erittäin hidas, voi noin 30 min. 1024x1024-kartoissa tehokkaallakin tietokoneella.

```
java -jar path-SNAPSHOT.jar bfs_path kartta.map
```
Testi vertaa leveyshaun löytämien polkujen pituuksia diagonaalisesti kulkevan algoritmin reittien pituuksiin. Tähän on käytetty JPS:ää sen nopeuden vuoksi, mutta yhtä hyvin se voisi olla Dijkstra tai A*. Testi on hyvin nopea, sillä jokainen skenaariotiedoston skenaario täytyy suorittaa vain kerran vertailukelpoisten tulosten saamiseksi.

```
java -jar path-SNAPSHOT.jar bfs_runtime kartta.map 10
```
Nopeusvertailu ensimmäisen testin tapaan, mutta leveyshaku mukana. Ero on oikeastaan graafissa, sillä leveyshaku ei löydä yhtä lyhyttä reittiä kuin muut algoritmit. Sen vuoksi tässä testissä näytetään vain x-akselilla testinumero polun pituuden sijaan. Myös tämä testi on hidas, joten iteraatioiden määrä kannattaa pitää pienenä.

.jarin, kartta- ja skenaariotiedostojen täytyy kaikkien sijaita samassa hakemistossa suorituskykytestien ajamista varten.

### Tuloksia
Suorituskykytestaukseni tapahtui kartoilla [Cauldron.map](https://github.com/ktatu/Path/blob/master/documentation/kuvat/Cauldron.png), [maze-512-32-7.map](https://github.com/ktatu/Path/blob/master/documentation/kuvat/maze512-32-7.png) ja [berlin_1_1024.map](https://github.com/ktatu/Path/blob/master/documentation/kuvat/Berlin_1_1024.png). Maze on kooltaan 512x512, Cauldron ja Berlin ovat 1024x1024.

Kyseiset kartat edustavat kolmea hyvin erilaista reitinhakualgoritmien sovelluskohdetta (sokkelot, strategiapelit, kartta- ja reitinhakusovellukset), joten ne tuntuivat luontevalta monipuolisen vertailun aikaansaamiseksi.

#### Suorituskyky - Dijkstra, A* ja JPS
Cauldron.map          |  Berlin_1_1024.map          |  maze512-32-7.map

<img src="https://github.com/ktatu/Path/blob/master/documentation/kuvat/no_bfs_runtime_cauldron.png" height="200">

