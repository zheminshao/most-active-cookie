package cookies;

// import org.apache.commons.cli.*;

import java.io.File;
import java.io.FileNotFoundException;
// import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class most_active_cookie {
    // private static final Option ARG_DATE = new Option("d", "date", true, "Target date in UTC");

    // private static void printHelp(Options options) {
    //     HelpFormatter formatter = new HelpFormatter();
    //     PrintWriter pw = new PrintWriter(System.out);
    //     pw.println("MostActiveCookie\n");
    //     formatter.printUsage(pw, 100, "java -jar most_active_cookie.jar logFile [options]");
    //     formatter.printOptions(pw, 100, options, 2, 5);
    //     pw.close();
    // }

    /*
    Given a log file and a target date, returns an ArrayList of cookies logged on that date (with repetitions)
    */
    public static ArrayList<String> findCookiesOnDate(String logFile, String date) throws FileNotFoundException {
        ArrayList<String> cookieList = new ArrayList<String>();
        
        try {
            Scanner sc = new Scanner(new File(logFile));
            sc.useDelimiter(",|\\n"); // Two delimiters for csv files: comma and new line
            String currentCookie;
            boolean foundDate = false;
            while (sc.hasNext()) {
                currentCookie = sc.next();
                if (sc.hasNext() && sc.next().startsWith(date)) { // Target date found!
                    cookieList.add(currentCookie);
                    foundDate = true;
                } else if (foundDate) {
                    // Reached when target date has been found before, but is no longer found
                    // Since cookies are sorted by timestamp, signifies end of search
                    break;
                }
            }
        } catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        return cookieList;
    }

    /*
    Given an ArrayList of cookies, returns an ArrayList of cookie(s) appearing with the highest frequency
    */
    public static ArrayList<String> findMostActiveCookies(ArrayList<String> cookieList) {
        ArrayList<String> activeList = new ArrayList<String>();
        int highestActivity = 0;

        HashMap<String, Integer> cookieMap = new HashMap<String, Integer>();
        int size = cookieList.size();
        for (int i = 0; i < size; i++) {
            String cookie = cookieList.get(i);
            int cookieValue;
            if (cookieMap.containsKey(cookie)) {
                cookieValue = cookieMap.get(cookie) + 1;
                cookieMap.replace(cookie, cookieValue);
            } else {
                cookieValue = 1;
                cookieMap.put(cookie, cookieValue);
            }
            if (cookieValue == highestActivity) { // Cookie tied for most appearances
                activeList.add(cookie);
            } else if (cookieValue > highestActivity) { // Cookie appears with the highest frequency thus far
                activeList.clear();
                activeList.add(cookie);
                highestActivity = cookieValue;
            }
        }

        return activeList;
    }

    /*
    Prints an ArrayList of cookies, one per line
    */
    public static void printList(ArrayList<String> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            System.out.println(list.get(i));
        }
    }

    public static void main (String[] args) throws FileNotFoundException {
        // CommandLineParser clp = new DefaultParser();
        // Options options = new Options();
        // options.addOption(ARG_DATE);
        
        // String logFile = "";
        // String targetDate = "";
        
        // try {
        //     CommandLine cl = clp.parse(options, args);

        //     if (cl.getArgList().size() < 1) {
        //         printHelp(options);
        //         System.exit(-1);
        //     }

        //     logFile = cl.getArgList().get(0);

        //     if (cl.hasOption(ARG_DATE.getLongOpt())) {
        //         targetDate = cl.getOptionValue("d");
        //     } else {
        //         printHelp(options);
        //     }
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        
        // ArrayList<String> cookiesOnDate = findCookiesOnDate(logFile, targetDate);
        ArrayList<String> cookiesOnDate = findCookiesOnDate(args[0], args[2]);
        ArrayList<String> mostActiveCookies = findMostActiveCookies(cookiesOnDate);
        printList(mostActiveCookies);
    }
}