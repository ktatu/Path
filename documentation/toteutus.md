# Toteutusdokumentti

## Ohjelman rakenne
Kuva ohjelman pakkausrakenteesta [täällä](https://github.com/ktatu/Path/blob/master/documentation/kuvat/path_package.png). 

Pakkausrakenne on mielestäni selkeä, mutta luokkarakenne pakkauksessa algorithms voi vaikuttaa sekavalta. BFS ja Dijkstra perivät abstraktin luokan Algorithm, joka sisältää kaikkien algoritmien hyödyntämiä rakenteita. AStar ja JPS puolestaan perivät Dijkstran, sillä Dijkstra-variaatioina ne hyödyntävät samoja metodeja etenkin ruutujen tarkistuksissa. En halunnut sijoittaa yhteisiä metodeja Algorithm-luokkaan, sillä BFS olisi perinyt paljon metodeja joita se ei itse käyttäisi ollenkaan.

## Aika- ja tilavaativuudet
Lopullisen ohjelman aika- ja tilavaativuudet eivät iso-o-notaatioltaan poikkea [määrittelydokumentin](https://github.com/ktatu/Path/blob/master/documentation/maarittely.md) arvioista. Ohjelman tilankäyttö ei kuitenkaan ole kovin tehokasta, kukin algoritmi hyödyntää kolmea kartan ruutujen määrän kokoista listaa edeltäjien, etäisyyksien ja läpikäytyjen ruutujen talletukseen. Lisäksi BFS käyttää fifo-jonoa ja muut prioriteettijonoa käsiteltävien ruutujen tallettamiseen ja vielä lopullista polkua varten on lista. Toisaalta alkeistyyppien käyttö (float, int) säästää jonkin verran muistissa.

## Puutteita, parannettavaa
- Ohjelmassa joutuu itse keksimään tai katsomaan skenaariotiedostoista suoritettavia koordinaatteja, jotka syöttää käyttöliittymässä. Olisi käyttäjälle kätevämpää, jos kartan voisi saada näkyviin ja sitten klikata kartasta alku- ja loppupisteet
- Algoritmi-luokissa kokonaislukujen käyttö eri metodeissa kuvaamaan ruutuja ei ollut mielestäni hyvä ratkaisu. Toisaalta se toimii, mutta koordinaatteja joutuu jatkuvasti metodista toiseen muuttamaan kokonaisluvusta koordinaateiksi tai päinvastoin. Tämä ratkaisu saattoi ehkä säästää jonkin verran muistia (talletetaan kokonaislukuja Grid-olioiden sijaan jne.), mutta ohjelmassa ei ole puute muistista ja hintana on koodin paikoittainen epäselvyys.

## Lähteet
- A*: https://en.wikipedia.org/wiki/A*_search_algorithm
- Dijkstra: https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm, Tietorakenteet ja Algoritmit (Antti Laaksonen 2020) s. 125-127
- Leveyshaku (BFS): https://en.wikipedia.org/wiki/Breadth-first_search
- Jump Point Search (JPS): Online Graph Pruning for Pathfinding on Grid Maps (Daniel Harabor ja Alban Grastien), [latauslinkki](https://www.aaai.org/ocs/index.php/AAAI/AAAI11/paper/download/3761/4007)
