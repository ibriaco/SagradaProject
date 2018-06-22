package it.polimi.se2018.view.ui;


import it.polimi.se2018.model.*;
import it.polimi.se2018.model.event.*;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.network.client.NetworkHandler;
import it.polimi.se2018.view.viewevents.*;
import it.polimi.se2018.view.viewevents.RollDiceEvent;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.se2018.view.ui.CLIUtils.*;

/**
 * Command line interface
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class CLIView implements ViewInterface {

    static int counter = 0;

    private NetworkHandler nh;
    private VCEvent vcEvent;
    private int choice;
    private String user;
    private ArrayList<MyObserver> observersCollection = new ArrayList<>();
    private List<WindowCard> myCardList;
    private static final Logger LOGGER = Logger.getLogger( CLIView.class.getName() );

    /**
     * Updates a window card
     */
    @Override
    public void updateWindowCard() {

    }

    /**
     * Shows the user interface
     * @throws RemoteException thrown exception
     * @throws InvalidConnectionException thrown exception
     * @throws InvalidViewException thrown exception
     */
    @Override
    public void showUI() throws IOException, InvalidConnectionException, InvalidViewException, ParseException {

        boolean validInput = false;
        while(!validInput) {
            choice = printConnectionChoice();
            if (choice == 1 || choice == 2) {
                createNH(choice);
                validInput = true;
                loginScreen();
            } else
                consoleErrorWriter.println("Invalid input, please try again!");
        }
    }

    /**
     * Shows the login interface to the user
     * @throws RemoteException thrown exception
     * @throws InvalidConnectionException thrown exception
     * @throws InvalidViewException thrown exception
     */
     public void loginScreen() throws IOException, InvalidConnectionException, InvalidViewException, ParseException {
        printOnConsole("~~~~~~~~~~ Login page ~~~~~~~~~~");
        printOnConsole("Insert your username here: ");
        setUsername(consoleScanner.next());
        createLoginEvent();
    }


    @Override
    public void receiveEvent(VCEvent event) {

    }

    private int printConnectionChoice() {
        printOnConsole("Select the Connection type you want to use:");
        printOnConsole("1) " + rmi);
        printOnConsole("2) " + socket);
        return consoleScanner.nextInt();
    }

    @Override
    public void registerObserver(MyObserver observer) {
        observersCollection.add(observer);
    }

    @Override
    public void unregisterObserver(MyObserver observer) {
        observersCollection.remove(observer);
    }

    @Override
    public void notifyObservers() throws IOException, InvalidConnectionException, InvalidViewException, ParseException {
        for (MyObserver o : observersCollection) {
            o.update(this, vcEvent);
        }
    }

    @Override
    public void update(MyObservable o, VCEvent arg) throws RemoteException, InvalidConnectionException, InvalidViewException {

    }

    @Override
    public void update(MyObservable o, MVEvent arg) throws RemoteException, InvalidConnectionException, InvalidViewException {

        arg.accept(this);
    }


    //METODI PER CREARE EVENTI VC
    @Override
    public void createLoginEvent() throws InvalidConnectionException, IOException, InvalidViewException, ParseException {
        vcEvent = new LoginEvent(user);
        notifyObservers();
    }

    public void createPlaceDieEvent() throws InvalidConnectionException, IOException, InvalidViewException, ParseException {
        int pos = getNumber();
        int coordX = getNumber();
        int coordY = getNumber();
        vcEvent = new PlaceDieEvent(user, pos, coordX, coordY);
        notifyObservers();
    }

    public void createSkipTurnEvent() throws InvalidConnectionException, IOException, InvalidViewException, ParseException {
        vcEvent = new SkipTurnEvent(user);
        notifyObservers();
    }

    public void createUseToolEvent() throws InvalidConnectionException, IOException, InvalidViewException, ParseException {
        int pos = getNumber()-1;
        vcEvent = new UseToolEvent(user, pos);
        notifyObservers();
    }

    public void createRollDiceEvent() throws InvalidConnectionException, IOException, InvalidViewException, ParseException {
        vcEvent = new RollDiceEvent(user);
        notifyObservers();
    }

    private void createChooseCardEvent(WindowCard windowCard) throws InvalidConnectionException, IOException, InvalidViewException, ParseException {
        vcEvent = new ChooseCardEvent(user, windowCard);
        notifyObservers();
    }

    public String getString() {

        return consoleScanner.nextLine();
    }

    public int getNumber() {

        return consoleScanner.nextInt();
    }

    //METODI PER GESTIRE MVEVENT
    @Override
    public void handleMVEvent(LoggedUserEvent event) {
        event.printState();
    }

    @Override
    public void handleMVEvent (PrivateCardEvent event){
         event.printPrivateName();
    }


    @Override
    public void handleMVEvent(WindowCardEvent event) throws RemoteException, InvalidConnectionException, InvalidViewException {
        Color color;
        int value;
        String windowName = "";
        boolean correctName = false;
        consoleWriter.println("[WINDOW CARD]\n");
        myCardList = event.getWindowCards();

        for (WindowCard w: event.getWindowCards()) {
            consoleWriter.println(w.getWindowName() + " " + w.getDifficulty());
            for(int i=0; i<4; i++){
                for(int j=0; j<5; j++){
                    color = w.getGridCell(i, j).getColor();
                    value = w.getGridCell(i, j).getShade();
                    printCell(color, value);
                }
                consoleWriter.println("");
            }
        }


        launchThread();

        //ReaderThread r = new ReaderThread();
        //r.go();

        /*
        try {
            System.out.println(testL());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        */
        /*BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
        //Scanner scanner = new Scanner(System.in);
        String scelta = null;
        try {
            scelta = scanner.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(scelta);

        /*consoleWriter.println("INSERT THE NAME OF WINDOW CARD YOU WANT CHOOSE");
        while (!correctName) {
            windowName = consoleScanner.nextLine();
            for (WindowCard w: event.getWindowCards()) {
                if (w.getWindowName().equals(windowName)) {
                    correctName = true;
                    createChooseCardEvent(w);
                }
            }
            consoleWriter.println("NOT-EXISTENT WINDOW CARD");
        }
*/
    }


    @Override
    public void handleMVEvent(NewGameEvent event) {
        // consoleWriter.println(event.getWindowCardList().size());
        Color color;
        int value;
        int user=0;

        for (WindowCard w: event.getWindowCardList()) {
            consoleWriter.println(event.getUser().get(user) + "\t" + w.getWindowName());
            for(int i=0; i<4; i++){
                for(int j=0; j<5; j++){
                    color = w.getGridCell(i, j).getColor();
                    value = w.getGridCell(i, j).getShade();
                    printCell(color, value);
                }
                consoleWriter.println("");

            }user++;
        }
    }


    private void printCell(Color color, int value){
        String toPrint;
        String ok;
        if (value == 0) {
            toPrint = "\u25FC";
            if(color == null) {
                consoleWriter.print(toPrint);
            }
            else{
                switch (color) {
                    case BLUE:
                        consoleWriter.print(ANSI_BLUE + toPrint + ANSI_RESET);
                        break;
                    case RED:
                        consoleWriter.print(ANSI_RED + toPrint + ANSI_RESET);
                        break;
                    case GREEN:
                        consoleWriter.print(ANSI_GREEN + toPrint + ANSI_RESET);
                        break;
                    case YELLOW:
                        consoleWriter.print(ANSI_YELLOW + toPrint + ANSI_RESET);
                        break;
                    case PURPLE:
                        consoleWriter.print(ANSI_PURPLE + toPrint + ANSI_RESET);
                        break;
                    case WHITE:
                        consoleWriter.print(ANSI_WHITE + toPrint + ANSI_RESET);
                        break;
                    default:
                        break;
                }
            }
        } else {

            switch(value){
                case 1:
                    consoleWriter.print("\u2680");
                    break;
                case 2:
                    consoleWriter.print("\u2681");
                    break;
                case 3:
                    consoleWriter.print("\u2682");
                    break;
                case 4:
                    consoleWriter.print("\u2683");
                    break;
                case 5:
                    consoleWriter.print("\u2684");
                    break;
                case 6:
                    consoleWriter.print("\u2685");
                    break;
            }
        }
    }

    private void launchThread() {
         new Thread(() -> {
/*
             final int[] t = {10};
             new Thread(() ->{
                 for (; t[0] >= 0 ; t[0]--) {
                     try {
                         Thread.sleep(1000);
                         //System.out.println(t[0]);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                 }

             }).start();

*/
             TimerTask timerTask = new TimerTask() {

                 @Override
                 public void run() {
                     //System.out.println("TimerTask executing counter is: " + counter);
                     counter++;//increments the counter
                 }
             };

             Timer timer = new Timer("MyTimer");//create a new Timer
             timer.scheduleAtFixedRate(timerTask, 0, 1000);//this line starts the timer at the same time its executed

             while(true) {
                 consoleWriter.println("inserisci carta:");
                 Scanner scanner = new Scanner(System.in);
                 String fromThread = null;

                 fromThread = scanner.nextLine();

                 consoleWriter.println(fromThread);
                 WindowCard selectedW = findInCards(fromThread);
                 if(selectedW != null) {
                     try {
                         createChooseCardEvent(findInCards(fromThread));
                     } catch (InvalidConnectionException | IOException | InvalidViewException e) {
                         LOGGER.log(Level.SEVERE, e.toString(), e);
                     } catch (ParseException e) {
                         LOGGER.log(Level.SEVERE, e.toString(), e);
                     }
                     break;
                 }

             }
         }).start();
    }

    @Override
    public void handleMVEvent(PublicCardEvent event) {
        event.printPublicName();
    }

    @Override
    public void handleMVEvent(ToolCardEvent event) {
        event.printToolCards();
    }

    @Override
    public void createNH(int choice) throws RemoteException {
        nh = new NetworkHandler(choice);
        registerObserver(nh);
        nh.registerObserver(this);
    }

    @Override
    public void setUsername(String u) {
        user = u;
    }

    private WindowCard findInCards(String n) {
        return myCardList.stream().filter(w -> w.getWindowName().equalsIgnoreCase(n)).findFirst().orElse(null);
    }

    private String testL() throws InterruptedException, ExecutionException
    {
        ExecutorService executor = Executors.newCachedThreadPool();
        Callable<String> callable = () -> {
            consoleWriter.println("inserisci");
            try (Scanner scanner = new Scanner(System.in)) {
                return scanner.nextLine();
            }
        };
        Future<String> future = executor.submit(callable);
        executor.shutdown();
        return future.get();
    }
}

