package ducpt.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletContext;

public class Logger {
    private static final String LOG_DIR = "logs";
    private static final String LOG_FILE = "system_logs.txt";
    private static ServletContext context;
    
    public static void setServletContext(ServletContext servletContext) {
        context = servletContext;
    }
    
    private static void ensureLogDirectory() {
        if (context == null) {
            System.err.println("ServletContext not set - cannot create log directory");
            return;
        }
        
        try {
            String realPath = context.getRealPath("/");
            File logDir = new File(realPath, LOG_DIR);
            System.out.println("Creating log directory at: " + logDir.getAbsolutePath());
            if (!logDir.exists()) {
                boolean created = logDir.mkdirs();
                System.out.println("Log directory created: " + created);
            }
        } catch (Exception e) {
            System.err.println("Error creating log directory: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void logAction(String action, String username, String details) {
        if (context == null) {
            System.err.println("ServletContext not set - cannot log action");
            return;
        }
        
        ensureLogDirectory();
        String realPath = context.getRealPath("/");
        File logFile = new File(new File(realPath, LOG_DIR), LOG_FILE);
        System.out.println("Attempting to log action: " + action);
        System.out.println("Log file path: " + logFile.getAbsolutePath());
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(logFile, true))) {
            LocalDateTime now = LocalDateTime.now();
            String timestamp = now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            
            String logEntry = String.format("[%s] User: %s, Action: %s, Details: %s",
                    timestamp, username, action, details);
            
            writer.println(logEntry);
            System.out.println("Successfully wrote log entry: " + logEntry);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void info(String message) {
        System.out.println("INFO: " + message);
    }
    
    public static void error(String message, Exception e) {
        System.err.println("ERROR: " + message);
        e.printStackTrace();
    }
} 