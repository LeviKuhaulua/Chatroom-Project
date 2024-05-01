import java.util.logging.Logger; 
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.ConsoleHandler; 
import java.util.logging.SimpleFormatter; 
import java.io.IOException;
import java.net.InetAddress; 
import java.net.ServerSocket;
import java.net.Socket; 
import java.net.UnknownHostException; 

/**
 * Handles the logging of information for the {@link serverP} and {@link clientp}. This will keep records on information
 * that is important for developers or admins to know. 
 * @author 
 *  <a href="mailto:levi.kuhaulua@outlook.com">Levi Kuhaulua</a>
 * @version 
 *  21.0.2
 */
public class ChatLogger {
    private Logger LOGGER; 
    private FileHandler FILE; 
    private final ConsoleHandler CONSOLE = new ConsoleHandler(); 

    /**
     * Create a Logger of the instance. 
     * @param name
     *  Name to associate the logger with. 
     */
    public ChatLogger(String name) {
        try {
            LogManager.getLogManager().reset(); 
            // Create a logger and store the log files in the resource directory. 
            LOGGER = Logger.getLogger(name); 
            FILE = new FileHandler(".\\resources\\" + name + ".log", true); // allows for files to be appended to. 
            FILE.setFormatter(new SimpleFormatter()); // Logs the files in more readable format. 
            LOGGER.addHandler(FILE); 
            LOGGER.info("Logger " + name + " initialized");
        } catch (IOException e) {
            System.err.println("Unable to create logger for: " + name);
            System.err.println(e.getMessage());
            System.exit(-1); 
        }
         
    }

    /**
     * Creates a Logger instance with the option of logging to the Console as well. 
     * @param name
     *  Name to associate the Logger with. 
     * @param addConsole
     *  {@code true/false} value that represents if user wishes to establish Console logs as well. 
     */
    public ChatLogger(String name, boolean addConsole) {
        try {
            LogManager.getLogManager().reset(); 
            // Create log files and store them in the resources directory
            LOGGER = Logger.getLogger(name); 
            FILE = new FileHandler(".\\resources\\" + name + ".log", true); 
            FILE.setFormatter(new SimpleFormatter()); // Logs the files in more readable format
            LOGGER.addHandler(FILE); 
            if (addConsole) {
                LOGGER.addHandler(CONSOLE); 
                LOGGER.info("Logger " + name + " initialized with Console"); 
            } else {
                LOGGER.info("Logger " + name + " initialized without Console"); 
            }
             
        } catch (IOException e) {
            System.err.println("Unable to create logger for: " + name);
            System.err.println(e.getMessage());
            System.exit(-1); 
        }
    }

    
    /**
     * Gets the Client Socket's information then logs it to the file. 
     * @param socket
     *  The {@code Socket} that connected to the server
     * @param username
     *  Username that is tied to the socket. 
     */
    public void getSocketInformation(Socket socket, String username) {
        LOGGER.info(username + " has connected on Port: " + socket.getLocalPort() + 
                    " with IP of : " + socket.getInetAddress().getHostAddress());
    }

    /**
     * Get the IP Address and Port Number of the Server then log it to the file
     * @param server
     *  The {@code ServerSocket} used to listen for connections
     * @throws 
     *  UnknownHostException should there be an issue getting the IP Address of the Server. 
     *  <p> 
     *      <b>NOTE:</b> This may happen if the ServerSocket is not initialized or if it is blocked for security reasons. 
     *  </p> 
     */
    public void getSocketInformation(ServerSocket server) {
        try {
            LOGGER.info("Server has started at " + InetAddress.getLocalHost().getHostAddress() + " on Port " + server.getLocalPort()
            + " and is listening for connections.");
        } catch (UnknownHostException e) {
            LOGGER.warning("Unable to get server information"); 
        }
    }

    /**
     * Log messages to the log file. 
     * @param message
     *  Message from client to be logged
     */
    public void logMessage(String message) {
        LOGGER.info(message); 
    }

    /**
     * Logs out any exception messages to the log file. 
     * @param message
     *  Exception / Error Message to be logged. 
     */
    public void messageException(String message) {
        LOGGER.warning(message); 
    }


    
}