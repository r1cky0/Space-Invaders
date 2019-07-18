**ISTRUZIONI UTILIZZO SPACE INVADERS. PROGETTO C-19 UNIPV**

1- Aprire la cartella "SpaceInvaders" da Intellij
2- File --> Project Structure --> Libraries --> Click su + --> Selezionare "java" nel menu a tendina --> selezionare slick2d
3- Run --> Edit configuration --> Aggiungere Main di SpaceInvaders --> In VM Options scrivere: 
	
	Per Ubuntu:
	-Djava.library.path=natives_linux

	Per Windows:
	-Djava.library.path=natives_windows
4- Per multiplayer: 
Run --> Edit configuration --> Aggiungere Main ServerLauncher

5- Il server é settato per giocare solo in locale.
Nel file di configurazione "configuration.xml", l'indirizzo IP del server puó essere cambiato nei pc client per poter giocare in modalitá multiplayer, inserendo l'ip del server.
