# PA_A4_Sichim_Petru-Calin

Am avut o problema la upload la fel ca la lab 8, am pus totul pe Lab9Corect

Compulsory - Am implementat unsistem de threaduri care ruleaza simultan prin volatile care asigura ca toate entitatile afla cand se termina jocul cu synchronized. Clasele au fost facute printr-o interfata, totul este afisat tip text cu o pentru casuta pereti si exit. Nu se misca inca cu vreun sens. Robotii totusi au un shared memory care momentan este nefunctional. Viteza lor poate fi modificata datorita sistemului de threaduri pentru odata la cate ms se misca fiecare clasa si odata la cat timp se afiseaza labirintul. Maze-ul este facut printr-un dfs care sparge peretii din matricea plina, mazeul nu este complet randomizat dar 100% este rezolvabil.

Homework - Robotii acum folosesc un stack ca sa exploreze sistematic printr-un peek(), daca sunt blocati dau cu spatele, nu intra in pereti, printr-un DFS. DaemonManager ul este un thread care ruleaza pe fundal care afiseaza textul si se asigura ca ?codul ruleaza in timpul limita. Tot in consola prin intermediul Main-ului se pot citi comenzi de la tastatura pentru stop, schimbat viteza si resume prin JavaStreams, utilizand scanner.nextLine. Functia de schimbat viteza functioneaza prin applyDelay si e folosit si la stop, tine entitatea intr-o bucla pana isi ia resume.

Advanced - TBD
