# DARTS
## Introduzione
Sul progetto principale __StefanoLambiase/DARTS__ sono state fatte un totale di quattro Forks, ovvero: fasanosalvatore/DARTS, GabrielePisapia/DARTS,
gilbertrec/DARTS e imgios/DART. Nello specifico, la prima prevede il passaggio a un progetto Gradle e l'aggiunta di tre test smells al progetto padre,
ovvero Mistery Guest (MG), Hard Coded Test Data (HCTD) e Test Code Duplication (TCD). Nella seconda non viene presentata alcuna nuova documentazione ma,
analizzando il codice, vi sono alcuni contributi al progetto principale. Da una analisi più approfondita, il progetto sembra essere stato abbandonato,
in quanto le implementazioni sono state solo iniziate, ma non concluse. La terza prevede il passaggio a un progetto Gradle e l'aggiunta di un sottosistema in grado
di condurre alcune statistiche di utilizzo del tool, con un resoconto di varie misure (ad esempio: il numero dei click, il numero degli smells identificati,
il tempo di esecuzione ...) che sia esportabile. Inoltre, tali statistiche dovranno essere inviate ad un server remoto, denominato \textit{DARTStats}. Per quanto 
l'ultima fork, è auspicabile che sia stata fatta una fork mirata a salvare lo stato attuale del progetto, in quanto non vi sono cambiamenti al progetto padre.

## Panoramica sulle modifiche effettuate
Considerando esclusivamente le forks che hanno dato un contributo significativo alla versione originale del Tool IntelliJ, da una prima analisi comparativa emerge
il passaggio ad un progetto che, rispetto al progetto padre, segue un nuovo standard basato su __Gradle__. 
La prima fork candidata a prendere parte al processo
di integrazione è stata la fork gilbertrec/DARTS, alla quale è seguta la fork di fasanosalvatore/DARTS.
Ognuno dei processi di integrazione è una Change Request (CR). Lo svolgimento di queste ultime è avvuta tramite un processo sequenziale,
il che significa che solo una volta completata l’integrazione
della prima CR, si è susseguita la seconda. Terminate queste due fasi si è passati ad una terza CR
che consiste nell’allineamento del sistema, dunque sono state apportate le modifiche necessarie al fine di
ottenere un’ultima versione che risulti essere perfettamente funzionante e consta di tutte le funzionalità
aggiuntive date dalle due forks.

