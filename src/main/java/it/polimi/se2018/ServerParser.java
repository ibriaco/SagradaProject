package it.polimi.se2018;

import it.polimi.se2018.org.json.simple.JSONObject;
import it.polimi.se2018.org.json.simple.parser.JSONParser;
import it.polimi.se2018.org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URLDecoder;

import static it.polimi.se2018.ServerConfig.*;

public class ServerParser {

    public Object createObj(){
        JSONParser jsonParser = new JSONParser();
        Object object = null;
        try{
            try {
                object = jsonParser.parse(new FileReader("./src/main/resources/GameResources/ServerConfiguration.json"));
            }catch (FileNotFoundException e){
                String decodedPath = URLDecoder.decode("./ServerConfiguration.json", "UTF-8");
                object = jsonParser.parse(new FileReader(decodedPath));

            }
        }catch (Exception e){
            try {
                object = jsonParser.parse(new InputStreamReader(getClass().getResourceAsStream("./ServerConfiguration.json")));
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
        return object;
    }


    public void reader(Object object) {

            JSONObject jsonObject = (JSONObject)object;

            LOCAL_HOST = (String)jsonObject.get("LOCAL_HOST");
            ZERO_VALUE = Integer.parseInt((String)jsonObject.get("ZERO_VALUE"));
            ONE_VALUE = Integer.parseInt((String)jsonObject.get("ONE_VALUE"));
            TWO_VALUE = Integer.parseInt((String)jsonObject.get("TWO_VALUE"));
            THREE_VALUE = Integer.parseInt((String)jsonObject.get("THREE_VALUE"));
            FOUR_VALUE = Integer.parseInt((String)jsonObject.get("FOUR_VALUE"));
            FIVE_VALUE = Integer.parseInt((String)jsonObject.get("FIVE_VALUE"));
            SIX_VALUE = Integer.parseInt((String)jsonObject.get("SIX_VALUE"));
            RED_AMOUNT = Integer.parseInt((String)jsonObject.get("RED_AMOUNT"));
            GREEN_AMOUNT = Integer.parseInt((String)jsonObject.get("GREEN_AMOUNT"));
            YELLOW_AMOUNT = Integer.parseInt((String)jsonObject.get("YELLOW_AMOUNT"));
            PURPLE_AMOUNT = Integer.parseInt((String)jsonObject.get("PURPLE_AMOUNT"));
            BLUE_AMOUNT = Integer.parseInt((String)jsonObject.get("BLUE_AMOUNT"));
            BOOL_TRUE = Boolean.parseBoolean((String)jsonObject.get("BOOL_TRUE"));
            BOOL_FALSE = Boolean.parseBoolean((String)jsonObject.get("BOOL_FALSE"));
            CLI_UI = (String)jsonObject.get("CLI_UI");
            GUI_UI = (String)jsonObject.get("GUI_UI");
            ROWS = Integer.parseInt((String)jsonObject.get("ROWS"));
            COLS = Integer.parseInt((String)jsonObject.get("COLS"));
            SOCKET_PORT = Integer.parseInt((String)jsonObject.get("SOCKET_PORT"));
            RMI_PORT = Integer.parseInt((String)jsonObject.get("RMI_PORT"));
            TURN_TIMER = Integer.parseInt((String)jsonObject.get("TURN_TIMER"));
            LOBBY_TIMER = Integer.parseInt((String)jsonObject.get("LOBBY_TIMER"));
            LOGIN_SUCCESSFULLY = (String)jsonObject.get("LOGIN_SUCCESSFULLY");
            USER = (String)jsonObject.get("USER");
            ONLINE_PLAYERS = (String)jsonObject.get("ONLINE_PLAYERS");
            TIMER_EXPIRED = (String)jsonObject.get("TIMER_EXPIRED");
            USERNAME_ALREADY_USED = (String)jsonObject.get("USERNAME_ALREADY_USED");
            FULL_LOBBY = (String)jsonObject.get("FULL_LOBBY");
            INVALID_USER = (String)jsonObject.get("INVALID_USER");
            SLEEP_TIME = Integer.parseInt((String)jsonObject.get("SLEEP_TIME"));
            AFTER_DRAFTING = (String)jsonObject.get("AFTER_DRAFTING");
            ON_WINDOW = (String)jsonObject.get("ON_WINDOW");
            ON_DRAFT = (String)jsonObject.get("ON_DRAFT");
            SPECIAL = (String)jsonObject.get("SPECIAL");
            STOP_MESSAGE = (String)jsonObject.get("STOP_MESSAGE");
            RUNNING_PLIERS = (String)jsonObject.get("RUNNING_PLIERS");
            TAP_WHEEL = (String)jsonObject.get("TAP_WHEEL");
            GROZING_PLIERS = (String)jsonObject.get("GROZING_PLIERS");
            CORK_BACKED_STRAIGHTEDGE = (String)jsonObject.get("CORK_BACKED_STRAIGHTEDGE");
            IMPOSSIBLE_REGISTRATION_OBJECT = (String)jsonObject.get("IMPOSSIBLE_REGISTRATION_OBJECT");
            REGISTRY = (String)jsonObject.get("REGISTRY");
            TO_ALL = (String)jsonObject.get("TO_ALL");
            CONNECTION_ERROR = (String)jsonObject.get("CONNECTION_ERROR");


    }
}
