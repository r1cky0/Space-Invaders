**ISTRUZIONI UTILIZZO SPACE INVADERS. PROGETTO C-19 UNIPV**

1- Clonare il progetto C19 da GitHub a IntelliJ

2- Per aggiungere libreria slick2d: 
File --> Project Structure --> Libraries --> Click su + --> Selezionare "java" --> selezionare slick2d --> ok

3- All' interno di src, nel package "main" fare click destro sulla classe "SpaceInvaders" --> selezionare "create SpaceInvaders.main()..."
Nella finestra incollare nella riga "VM OPTIONS" la stringa seguente:

Per Ubuntu:
-Djava.library.path=natives_linux
	
Per Windows:
-Djava.library.path=natives_windows

Sempre nella stessa finestra nella riga "Working directory" al termine del path già inserito aggiungere "/SpaceInvaders"

4- Eseguire il programma.
	
5- Per multiplayer: 
All' interno di src, nel package "network.server" fare click destro sulla classe "ServerLauncher" --> selezionare "create ServerLauncher.main()..." --> ok

6- Il server é settato per giocare in locale.
Nel file di configurazione "res/configuration.xml", puó essere cambiato nei pc client l'indirizzo IP del server per poter giocare con più giocatori.
