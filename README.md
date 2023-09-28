# ShelterBooks_CapstoneProject
Back-end progetto finale Capstone del percorso con EPICODE School 2023 Full-Stack Course 🚀

Link al readme front-end : https://github.com/EazyM93/ShelterBooks_Frontend/blob/main/README.md

Ho scelto di creare un sito e-commerce di libri che permettesse all'utente di tenere traccia dei suoi acquisti sottoforma di libreria virtuale 📚

Quella che andrò a descrivere è la componente Back-end dell'applicativo sviluppata utilizzando Java, Springboot e PostgresSql 💻

Una funzionalità che si esegue in fase di avvio dell'applicativo back end è un controllo sui ruoli della lista utenti.
Se nell'elenco esiste un "ADMIN" il programma viene inizailizzato normalmente, altrimenti genera un admin con informazioni di default e una password salvata nel file locale env.properties.

Le entità presenti sono:

- <a href="#-user-">User 👤</a>
- <a href="#-book-">Book 📙</a>
- <a href="#-cart-">Cart 🛒</a>
- <a href="#-order-">Order 📦</a>
- Address 📫

👤 **USER** 👤
---------------------------------------------------------------

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

🔐 ✍🏻 **CREAZIONE e LOGIN** -------------------------------------------------------

L' utente sarà gestito con authorization all'endpoint http://localhost:3001/auth :

- 🟧 "/register" (POST)
  L'utente verrà registrato tramite il service dello User.Qui verranno creati carrello e indirizzo.

- 🟧 "/login" (POST)
  L'utente eseguirà il login con generazione del bearer token tramite autenticazione JWT.

- 🟧 "/logout" (POST)
  L'utente effettua il logout con conseguente rimozione del token

⚙️ 👤 **FUNZIONI USER (*permesse a tutti*)** -------------------------------------------------------

L'endpoint base degli utenti è http://localhost:3001/users
Le funzionalità dello user saranno raggiungibili dai seguenti endpoint:

- 🟩 "/getCurrent" (GET)
  Restituisce l'utente correntemente loggato

- 🟧 "/addWishlist/{idBook}" (POST)
  Aggiunge il libro con l’id ricevuto alla lista dei preferiti dell’ utente corrente

- 🟧 "/removeWishlist/{idBook}" (POST)
  Rimuove il libro con l’id ricevuto dalla lista dei preferiti dell’ utente corrente

- 🟦 "/updateCurrent" (PUT)
  Invia un json body con gli update che l’utente vuole fare dei suoi dati

- 🟥 "/deleteCurrent" (DELETE)
  Rimuove l’utente corrente dal database

⚙️ 🔑 **FUNZIONI ADMIN** -------------------------------------------------------

L'endpoint base dell'admin è lo stesso degli utenti http://localhost:3001/users
I seguenti endpoint saranno accesibili solo agli Admin e verrà effettuato un controllo prima di potervi accedere

- 🟩 " " (GET)
  Restituisce la lista di tutti gli utenti senza admin

- 🟩 "/idUser/{idUser}" (GET)
  Restituisce l'utente con l'id inviato

- 🟩 "/email/{email}" (GET)
  Restituisce l'utente con la mail inviata

- 🟥 "/{idUser}" (DELETE)
  Elimina l'utente con l'id inviato dal Database

<a href="#">TORNA SU</a>

📙 **BOOK** 📙
--------------------------------------------------------------

I libri saranno il prodotto centrale in vendita grazie all'applicazione e sarà così definito nei suoi attributi:

- idBook ( autogenerato come UUID )
- isbn
- title
- description
- coverLink
- author
- publisher
- pages
- price
- publicationYear
- genre
- insertionDate
- availableEbook
- ebookSize
- ebookPrice
- allSelledCopies

Quando viene creato un libro sarà possibile inserire un'eventuale versione ebook.
La insertion date sarà utilizzata per visualizzare le ultime novità aggiunte al sito nel front-end.
AllSelledCopies sarà utilizzato per visualizzare i bestseller del sito nel front-end.

⚙️ 📙 **FUNZIONI BOOK (*permesse a tutti*)** -------------------------------------------------------

L'endpoint base per i libri è http://localhost:3001/books
Le funzionalità pubbliche dei books saranno raggiungibili dai seguenti endpoint:

- 🟩 " " (GET)
  Restituisce la lista di tutti i libri del DB in forma di Pageable con la possibilità di specificare l’ordinamento

- 🟩 "/getAllBooks" (GET)
  Restituisce la lista di tutti i libri del database

- 🟩 "/idBook/{idBook} (GET)
  Restituisce un libro con l'id inviato

- 🟩 "/filter" (GET)
  Restituisce una lista di tutti i libri filtrati in modo specifico e in formato Pageable. Si può filtrare per isbn, titolo, autore, editor, prezzo minimo/massimo, genere
  
⚙️ 📙 🔑 **FUNZIONI BOOK (*permesse solo all'admin*)** -------------------------------------------------------

- 🟧 " " (POST)
  Crea un libro con id univoco passando un payload

- 🟧 "/updateCopies" (POST)
  Aggiungi copie di un libro esistente al magazzino passando il payload

- 🟦 "/{idBook}" (PUT)
  Modifica le informazioni di un libro tramite ID specifico, passando il payload

- 🟥 "/{idBook}" (DELETE)
  Cancella un libro dal Database tramite il suo ID

<a href="#">TORNA SU</a>

🛒 **CART** 🛒
---------------------------------------------------------------

Ogni utente avrà il suo personale carrello con ID univoco, questo permette di mantenere in memoria gli elementi contenuti nel carrello.

- idCart ( autogenerato come UUID )
- user
- booksWithQuantity

La collezione dei libri nel carrello è sottoforma di MAP, dove l'oggetto Book funge da chiave per un numero intero che funge da quantità di copie nel carrello. Questo permette di non avere errori nell'associare il libro corretto alla quantità che si vuole acquistare, il tutto senza creare un proprità in più all'interno della classe Book.

Le funzioni del CART saranno tutte accessibili con il seguente endpoint http://localhost:3001/carts

- 🟩 "/currentCart" (GET)
  Restituisce il carrello dello user corrente

- 🟧 "/addBook/{idBook}" (POST)
  Aggiunge un libro con l'id inviato al carrello dello user corrente

- 🟧 "/removeBook/{idBook}" (POST)
  Rimuove un libro con l'id inviato dal carrello dello user corrente

- 🟧 "/clearCart" (POST)
  Svuota il carrello da tutti i libri, li aggiunge alla lista dei libri comprati dall’utente e crea l'ordine appena pagato

<a href="#">TORNA SU</a>

📦 **ORDER** 📦
--------------------------------------------------------------

Gli ordini verranno generati all'acquisto dei prodotti, al fine di essere visualizzati dall'admin nella parte gestionale del front-end.

- idOrder ( autogenerato come UUID )
- user
- submitted
- shipped
- shppedBooksWithQuantity
  
⚙️ 📦 **FUNZIONI ORDER (*permesse a tutti*)** -------------------------------------------------------
 
L'endpoint degli ordini sarà http://localhost:3001/orders

- 🟩 "/createOrder" (GET)
  Creazione di un ordine manuale. Il programma è impostato per creare in autonomia l’ordine durante il “clear” del carrello contestualmente all’acquisto dei libri e la finalizzazione del pagamento

- 🟩 "/idOrder/{idOrder}" (GET)
  Restituisce un ordine tramite id

⚙️ 📦 🔑 **FUNZIONI ORDER (*permesse solo all'admin*)** -------------------------------------------------------

- 🟩 " " (GET)
  Restituisce una lista di tutti gli ordini in formato Pageable

- 🟧 "/shipOrder/{idOrder}" (POST)
  Inizializza la data di spedizione al momento dell'acquisto

- 🟥 "/{idOrder}" (DELETE)
  Cancella un ordine passato con id dal Database

<a href="#">TORNA SU</a>
