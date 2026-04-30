# PA_A4_Sichim_Petru-Calin

Compulsory - Am facut o interfata grafica cu Java Swing. MainFrame foloseste un BorderLayout care aranjeaza restul componentelor, contine ConfigPanel, acesta preia dinamic prin JSpinner dimensiunea labirintului. ControlPanel are butoanele actionate prin Listeners pentru a functiona cu click. Ma folosesc de un Cell Data-Structure care retine coord pentru peretii din directiile sus stanga dreapta jos.

Homework - Generarea Labirintului: Ca sa vedem ca labirntul e rezolvabil, folosesc un BFS printr-un queue cu pozitia de inceput in stanga sus si de final in dreapta jos. Am suprascris paintComponent(Graphics g) ca sa fac labirintul folosind Graphics2D, si prin BufferedImage salvez rezultatul ca si fisier png cu ImageIO.write(). Starea o salvez printr-un flux incarcat in fisiere bin dat cu ObjectInputStream si ObjectOutputStream (Prin interfata marcator Serializable)

Advanced - TBD
