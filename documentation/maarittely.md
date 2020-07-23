# Projektin määrittely
Java-ohjelma tulee vertailemaan eri reitinhakualgoritmien tehokkuutta. Projektin pohjana toimii Nathan 
R. Sturtevantin Moving Ai Lab-projekti [Benchmarks for Grid-based Pathfinding (2012)](http://web.cs.du.edu/~sturtevant/papers/benchmarks.pdf), [Moving Ai](https://movingai.com).

## Miten ohjelma toimii
Käyttäjä syöttää käyttöliittymässä ohjelmalle kaksi tiedostoa: kartan ja siihen liittyvän skenaario-tiedoston. Skenaarioiden suorituksesta eri algoritmeilla kerääntyy vertailudataa, josta lopuksi kerätään kooste käyttöliittymään.

## Vertailuympäristö ja syötedata
Algoritmit etsivät lyhimpiä reittejä kahden ruudun välillä kaksiulotteisissa ruudukkokartoissa. 

Valmiita karttoja ja niihen liittyviä skenaarioita löytyy [Moving Ai:n sivuilta](https://movingai.com/benchmarks.grids.html). Karttoja ja skenaarioita voi myös tehdä itse, mutta niiden tulee noudattaa [Moving Ai:n määrittelemiä 
formaatteja](https://movingai.com/benchmarks/formats.html).

### Kartat
Kartat muodostuvat eri ominaispiirteitä omaavista maastokappaleista:
```
. G S   - Passable terrain
@ O T W - Out of bounds
```
"Passable terrain" -maastossa voi siis liikkua ja "Out of bounds" -maastossa ei. Ylläoleva määrittely perustuu Sturtevantin [paperiin](http://web.cs.edu/~sturtevant/papers/benchmarks.pdf). Moving Ai:n [formaattisivulla](https://movingai.com/benchmarks/formats.html) on 
erilaiset määritelmät, mutta julkaisussaan Sturtevant tarkentaa näiden olevan vain semanttista erottelua. 

Kartoissa voi liikkua viereisille, sallituille ruuduille (Passable terrain) vertikaalisilla, horisontaalisilla ja diagonaalisilla liikkeillä. Horisontaalisten ja vertikaalisten liikkeiden kustannus on 1, diagonaalisten liikkeiden \sqrt{2}. Diagonaaliselle, 
sallitulle ruudulle voi liikkua vain jos sekä aloitusruutua että diagonaalista kohderuutua vierustavat kaksi ruutua ovat myös vapaita.
```
X on alkuruutu josta halutaan siirtyä Y:n
                       X.  |  .Y                             XT  |  @Y
Sallittuja liikkeitä:  .Y  |  X.     Kiellettyjä liikkeitä:  .Y  |  XG
```
### Skenaariot
Yksittäinen skenaario koostuu oleellisimpana aloituskoordinaateista, joista algoritmit käynnistyvät ja loppukoordinaateista, joihin halutaan löytää lyhyin reitti. Jokaiseen skenaarioon sisältyy myös optimiaika (kustannus) ja ne ovat lajiteltu tämän mukaan eri 
"ämpäreihin" tiedostoissa. Ohjelma tulee mahdollisesti hyödyntämään tätä loppuvaiheessa.

## Algoritmit ja tietorakenteet
Valitsin alla olevat algoritmit koska ne ovat tunnetuimpia reitinhakualgoritmeja, joten niiden keskenään vertailu tuntuu luontevalta. Etuna on myös tietenkin, että niistä löytyy paljon materiaalia. 

### Toteutettavat algoritmit
```
V = solmu (vertex), tämän ohjelman tapauksessa yksi ruutu
È = kaari (edge), ohjelmassa tämä vastaa reunaa tai kulmaa kahden ruudun välillä
```

#### BFS (leveyshaku)
- Aikavaativuus O(E+V): pahimmassa tapauksessa joudutaan käymään kartan jokaisessa ruudussa ja tutkimaan jokaisen ruudun viereiset ruudut
- Tilavaativuus O(V): algoritmiin tullaan tarvitsemaan jono ja pari läpikäytävän kartan kokoista aputaulukkoa

#### Dijkstra
- Aikavaativuus O(E+V*log(V)): algoritmi voi joutua käymään läpi kaikkien ruutujen kaikki naapurit sekä lisäämään ja poistamaan kunkin niistä prioriteettijonosta
- Tilavaativuus O(V): riippuen käytettävästä Dijkstran versiosta kaikki solmut voivat olla aluksi prioriteettijonossa [Jyrki Kivisen materiaalin mukaisesti](https://cs.helsinki.fi/u/jkivinen/opetus/tira/k16/luku8b.pdf) tai sitten kaikki läpikäytävät 
solmut voivat lopulta päätyä listaan [(Uniform Cost Search)](http://www.bgu.ac.il/~felner/2011/dikstra.pdf), jolloin tilavaativuus on myös O(V). [Tirakirjan](https://cs.helsinki.fi/u/ahslaaks/tirakirja) (kpl. 11) mukainen vieruslistan 
hyödyntäminen veisi enemmän tilaa, mutta todennäköisesti en aio käyttää sitä

#### A*
- Aikavaativuus O(E+V*log(V)): hyvän heuristisen funktion avulla A*:n pitäisi olla huomattavasti Dijkstraa [nopeampi](https://youtube.com/watch?v=g024lzsknDo), mutta se myös hankaloittaa merkittävästi aikavaativuuden arviointia. Pohjimmiltaan A* on 
kuitenkin Dijkstra lisäominaisuudella. [Video josta tämän voi helposti havaita](https://www.youtube.com/watch?v=6TsL96NAZCo&t). Epäilen, että esimerkiksi hyvin sokkeloisissa kartoissa kyseisten algoritmien nopeudet eivät välttämättä eroa merkittävästi.  
- Tilavaativuus O(V): kuten Dijkstran tietorakenteet ja algoritmit-kurssilla opitussa versiossa, [A*:n täytyy säilyttää jokaista solmua tietorakenteissaan](https://en.wikipedia.org/wiki/A*_search_algorithm)

### Tarvittavat tietorakenteet
- Matriiseja tullaan tarvitsemaan tiedostosta luetun kartan säilyttämiseen ja aputietorakenteena leveyshaussa, mahdollisesti muissakin algoritmeissa
- ArrayList skenaarioiden talletusta varten, todennäköisesti myös vertailudatalle
- Dijkstra ja A* hyödyntävät PriorityQueuea
- Ehkä HashSet jos toteutan Dijkstrasta Uniform Cost Search-version

## Kaikki käytetyt lähteet on linkitetty tekstissä

