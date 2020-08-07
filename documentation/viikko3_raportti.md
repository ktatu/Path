## Mitä olen tehnyt
Tein Validointi-luokan, joka tarkastaa että algoritmit oikeasti voivat 
suorittaa käyttäjän syöttämän skenaarion. Käytännössä tämä tarkoittaa, 
että syötetyt koordinaatit ovat kartan rajojen sisällä ja esteetön 
reitti alku- ja loppuruutujen välillä on olemassa.

Tein myös Dijkstran ja A*:n ja ne toimivat kuten pitäisikin. 
Luokkarakenteesta tuli kuitenkin melko sekava, Dijkstra perii abstraktin 
luokan Algoritmi ja A* perii Dijkstran. Teen ensi viikolla Jump Point 
Searchin, jos sekin käyttää lähes samoja metodeja niin teen ehkä 
erillisen luokan Dijkstra-variaatioiden yhteisille metodeille.

## Ensi viikolla
Teen Jump Point Searchin ja alustavan käyttöliittymän, jossa voi syöttää 
kartan ja skenaarion. Tätä varten pitää myös alkaa rakentaa 
sovelluslogiikkaa. Aloitan myös omia tietorakenteita koska näin 
aikataulussa vaaditaan, mutta luultavasti en ehdi niissä kovinkaan 
pitkälle.
