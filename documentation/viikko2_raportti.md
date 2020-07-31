## Mitä olen tehnyt
Tein datankäsittelyluokat ja niille testit sekä javadocit. Joudun kuitenkin ehkä vielä 
muuttamaan niitä - nyt FileMapReader tekee tiedostosta löytyneestä kartasta
kokonaislukumatriisin. Tämä kuitenkin tekee reitin printtaamisesta kartassa hyvin 
epäkäytännöllistä, sillä kokonaislukumatriisin lisäksi pitäisi säilyttää (tai hakea uudestaan) alkuperäinen kartta. Muutan varmaan ensi viikolla ohjelman hyödyntämään 
kirjaimista koostuvia karttoja reitinhaussa.

Sain myös leveyshaun tehtyä, ja se näyttäisi hyvin nopeiden kokeilujen perusteella toimivan.

En lisännyt Algoritmi-yläluokkaan tai BFS:ään minkäänlaisia tarkistuksia reitin tai alku- ja maalikoordinaattien oikeellisuudesta. Ajattelin tehdä tätä varten erillisen luokan, joka myös tarkistaisi syvyyshaulla, että reitti oikeasti on olemassa. 
Validoinnin 
sijoittaminen algoritmien sekaan olisi tuntunut väärältä ja kömpelöltä, koska se täytyy suorittaa vain kerran, jolloin sille ei ole paikkaa Algoritmi-yläluokassa. Sen sijoittaminen BFS:ään kuitenkin tarkoittaisi, että BFS täytyy aina suorittaa ja ensimmäisenä. 

## Ensi viikolla
Reitin validointi-luokka ja testit sille ja leveyshaulle. Sen jälkeen Dijkstra. Dijkstran jälkeen sovelluslogiikkaa, mutta se todennäköisesti menee kolmannelle viikolle.

<b>Käytetty aika 17h</b>
