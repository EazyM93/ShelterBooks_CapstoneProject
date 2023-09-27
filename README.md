# ShelterBooks_CapstoneProject
Back-end progetto finale Capstone del percorso con EPICODE School 2023 Full-Stack Course 🚀

Ho scelto di creare un sito e-commerce di libri che permettesse all'utente di tenere traccia dei suoi acquisti sottoforma di libreria virtuale 📚

Quella che andrò a descrivere è la componente Back-end dell'applicativo sviluppata utilizzando Java, Springboot e PostgresSql 💻

Una funzionalità che si esegue in fase di avvio dell'applicativo back end è un controllo sui ruoli della lista utenti.
Se nell'elenco esiste un "ADMIN" il programma viene inizailizzato normalmente, altrimenti genera un admin con informazioni di default e una password salvata nel file locale env.properties.

Le entità presenti sono:

- User 👤
- Book 📙
- Cart 🛒
- Order 📦
- Address 📫

--------------------------------------------- 👤 USER 👤 ---------------------------------------------

L'utente è il protagonista dell'applicativo e sarà così definito nei suoi attributi:

- idUser ( autogenerato come UUID )
- name
- surname
- image
- email
- password
- role
- address
- purchasedBooks
- wishlist

Name, surname, image, email, password, address sono informazioni che non possono essere null e saranno ricevute come payload che conterrà tutti i dati dell'utente e dell'indirizzo, quest'ultimo verrà automaticamente generato prenedendo queste informazioni.
Contestualmente alla creazione dell'utente verrà generato un carrello ad esso correlato e che possiede un proprio ID univoco.

CREAZIONE e LOGIN utente sarà gestito con authorization all'endpoint http://localhost:3001/auth :

- "/register" (POST)
  L'utente verrà registrato tramite il service dello User.Qui verranno creati carrello e indirizzo.

- "/login" (POST)
  L'utente eseguirà il login con generazione del bearer token tramite autenticazione JWT.

- "/logout" (POST)
  L'utente effettua il logout con conseguente rimozione del token

FUNZIONI USER (*permesse a tutti*)
L'endpoint base degli utenti è http://localhost:3001/users
Le funzionalità dello user saranno raggiungibili dai seguenti endpoint:

- "/getCurrent" (GET)
  Restituisce l'utente correntemente loggato

- "/addWishlist/{idBook}" (POST)
  Aggiunge il libro con l’id ricevuto alla lista dei preferiti dell’ utente corrente

- "/removeWishlist/{idBook}" (POST)
  Rimuove il libro con l’id ricevuto dalla lista dei preferiti dell’ utente corrente

- "/updateCurrent" (PUT)
  Invia un json body con gli update che l’utente vuole fare dei suoi dati

- "/deleteCurrent" (DELETE)
  Rimuove l’utente corrente dal database

FUNZIONI ADMIN
I seguenti endpoint saranno accesibili solo agli Admin e verrà effettuato un controllo prima di potervi accedere

- " " (GET)
  Restituisce la lista di tutti gli utenti senza admin

- "/idUser/{idUser}" (GET)
  Restituisce l'utente con l'id inviato

- "/email/{email}" (GET)
  Restituisce l'utente con la mail inviata

- "/{idUser}" (DELETE)
  Elimina l'utente con l'id inviato dal Database
  
