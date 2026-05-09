# PA_A4_Sichim_Petru-Calin

Am avut o greseala la upload, este pus corect pe branchul Lab8Corect, nu am schimbat acest branch pentru a se vedea data la care a fost pus

Compulsory - Am facut o interfata grafica cu Java Swing. MainFrame foloseste un BorderLayout care aranjeaza restul componentelor, contine ConfigPanel, acesta preia dinamic prin JSpinner dimensiunea labirintului. ControlPanel are butoanele actionate prin Listeners pentru a functiona cu click. Ma folosesc de un Cell Data-Structure care retine coord pentru peretii din directiile sus stanga dreapta jos.

Homework - Generarea Labirintului: Ca sa vedem ca labirntul e rezolvabil, folosesc un BFS printr-un queue cu pozitia de inceput in stanga sus si de final in dreapta jos. Am suprascris paintComponent(Graphics g) ca sa fac labirintul folosind Graphics2D, si prin BufferedImage salvez rezultatul ca si fisier png cu ImageIO.write(). Starea o salvez printr-un flux incarcat in fisiere bin dat cu ObjectInputStream si ObjectOutputStream (Prin interfata marcator Serializable)

Advanced - Am implementat prin Reverse DFS un algoritm care sa imi faca maze-ul in clasa PerfectMazeBuilder. Pentru generarea vizuala folosesc SwingUtilities.invokelater() si ruleaza pe un thread separat deoarece aveam probleme cu freeze-uri, si da repaint() la fiecare pas facut. Algoritmul de verificare foloseste BFS-ul prin intermediul unor linked lists, care sigur functioneaza pe mazeul generat. Nu raman pathuri blocate.
