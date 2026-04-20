#### Branch. Lag alltid en ny branch hver gang du jobber med noe nytt.
-	git branch // Viser de tilgjengelige branchene
-	git checkout -b <navn-på-branch> // lager ny branch
-   git branch -d <branch_name> // sletter branch

#### Navn på branch
-   Kun små bokstaver og "-" indikerer mellomrom
-   initialler/navn-på-branch
-   Eksempel: hnl/oppdatere-readme

#### Hvordan pulle(hente) endringer. Gjøres hver gang man skal jobbe.
-	git status
-	git switch main
-	git pull
-	git switch <ønsket branch>
-   git   main //sørge for at din branch er oppdatert med main

#### Hvordan pushe endringer.
-	git add .   // legger til endringer i staging area
-	git commit -m “Hva du har gjort” // lagrer add endringene i den lokale git historien din.
-	git push -u origin <branch_navn> //sender de lokale endringene i git historien til de eksterne repositoriet. Nå kan andre hente endringene. Husk å lage en pull request. Bare git push hvis man har pushet branchen før.
-	Lag en pull request på github hvor du spør en annen om å godkjenne hva du har gjort
