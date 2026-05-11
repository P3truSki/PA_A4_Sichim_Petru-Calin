# PA_A4_Sichim_Petru-Calin

LAB10 
Compulsory - Am facut doua programe, conform cerintei, care folosesc protocolul TCP/IP si Sockets pentru comunnicare. Partea de server asteapta conexiuni prin .accept(), fiecare client avand propriul lui thread si deleaga procesele pe un fir separat. Daca primeste comanda stop din client se opreste si inchide si toate celelalte procese zombie de la alte threaduri posibile. Clientul comunica exclusiv prin java.IO. Clientul ruleasza intr-o bucla infinita pana se opreste cu exit sau se inchide serverul. Folosesc si PrintWriter cu optiunea de auto-flush pentru a trimite mesajele citite de la tastatura si un BufferedReader care ia octetii prin InputStreamReader pentru a citi si afisa raspunsul serverului.

Homework - Am extins functionalitatea pentru a suporta un joc tip trivia prin: o clasa GameManager care citeste si incarca intrebarile la rulare dintr-un fisier local questions.json, folosind biblioteca Gson. Am adaptat logica din ClientThread pt ca serverul sa proceseze cereri noi prin BufferedReader. Am pastrat sistemul de ecou implementat, astfel incat serverul trimite constant confirmari prin PrintWriter catre clienti.

Advanced - TBD


LAB11

Compulsory - Am trecut de la comunicarea prin sockets la comunicarea prin REST API web service. Am generat proiectul cu Spring Boot. Comunicatia nu se mai face prin streamuri, doar prin cereti HTTP. Datele sunt pastrate intr-un PostgreSQL server. Folosesc Spring Data JPA si am creat Player adnotat cu @Entity, prin ddl=auto-update Hibernate genereaza singur tabelele, fara sa mai scriu cod. Pentru a expune baza de date am folosit @RestController, folosind metodele @GetMapping @PostMapping etc pentru a apela metodele dintr-o interfață JpaRepository, salvand clientii direct din browser sau Postman, in format JSON.

Homework -

Advanced -
