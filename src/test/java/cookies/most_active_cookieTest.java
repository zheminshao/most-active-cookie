package cookies;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Test;

/**
 * Unit test for most_active_cookie
 */
public class most_active_cookieTest 
{
/**
     * Tests for findCookiesOnDate
     */
    @Test
    public void testCookiesOnDate() throws FileNotFoundException {
        final ClassLoader classLoader = getClass().getClassLoader();

        final String testName = "test.csv";
        final File testFile = new File(classLoader.getResource(testName).getFile());
        final String testString = testFile.getAbsolutePath();

        ArrayList<String> cookieList = most_active_cookie.findCookiesOnDate(testString, "5");
        assertEquals(2, cookieList.size());
 
        cookieList = most_active_cookie.findCookiesOnDate(testString, "4");
        assertEquals(5, cookieList.size());

        assertEquals("B", cookieList.get(0));
        assertEquals("A", cookieList.get(1));
        assertEquals("B", cookieList.get(2));
        assertEquals("B", cookieList.get(3));
        assertEquals("C", cookieList.get(4));

        cookieList = most_active_cookie.findCookiesOnDate(testString, "3");
        assertEquals(2, cookieList.size());

        cookieList = most_active_cookie.findCookiesOnDate(testString, "2");
        assertEquals(2, cookieList.size());

        cookieList = most_active_cookie.findCookiesOnDate(testString, "1");
        assertEquals(1, cookieList.size());
    }

    /**
     * Tests for findMostActiveCookies
     */
    @Test
    public void testMostActiveCookies() {
        ArrayList<String> cookieList = new ArrayList<String>();
        ArrayList<String> activeCookies = most_active_cookie.findMostActiveCookies(cookieList);
        assertEquals(0, activeCookies.size());

        cookieList.add("Hi");
        activeCookies = most_active_cookie.findMostActiveCookies(cookieList);
        assertEquals(1, activeCookies.size());
        assertEquals("Hi", activeCookies.get(0));
        
        cookieList.add("Hello");
        activeCookies = most_active_cookie.findMostActiveCookies(cookieList);
        assertEquals(2, activeCookies.size());
        assertEquals("Hi", activeCookies.get(0));
        assertEquals("Hello", activeCookies.get(1));

        cookieList.add("Hi");
        cookieList.add("Hi");
        cookieList.add("Howdy");
        cookieList.add("Hi");
        cookieList.add("Hey");
        cookieList.add("Hey");
        activeCookies = most_active_cookie.findMostActiveCookies(cookieList);
        assertEquals(1, activeCookies.size());
        assertEquals("Hi", activeCookies.get(0));

        cookieList.add("Howdy");
        cookieList.add("Howdy");
        cookieList.add("Howdy");
        cookieList.add("Hey");
        cookieList.add("Hey");
        activeCookies = most_active_cookie.findMostActiveCookies(cookieList);
        assertEquals(3, activeCookies.size());
        assertEquals("Hi", activeCookies.get(0));
        assertEquals("Howdy", activeCookies.get(1));
        assertEquals("Hey", activeCookies.get(2));
    }

    /**
     * General tests
     */
    @Test
    public void testGeneral() throws FileNotFoundException {
        final ClassLoader classLoader = getClass().getClassLoader();

        final String testName = "test.csv";
        final File testFile = new File(classLoader.getResource(testName).getFile());
        final String testString = testFile.getAbsolutePath();

        final String cookieLogName = "cookie_log.csv";
        final File cookieLogFile = new File(classLoader.getResource(cookieLogName).getFile());
        final String cookieLogString = cookieLogFile.getAbsolutePath();

        final String blankName = "blank.csv";
        final File blankFile = new File(classLoader.getResource(blankName).getFile());
        final String blankString = blankFile.getAbsolutePath();

        final String specialName = "special.csv";
        final File specialFile = new File(classLoader.getResource(specialName).getFile());
        final String specialString = specialFile.getAbsolutePath();
        
        ArrayList<String> mostActiveCookies = most_active_cookie.findMostActiveCookies(most_active_cookie.findCookiesOnDate(cookieLogString, "2018-12-09"));
        assertEquals(1, mostActiveCookies.size());
        assertEquals("AtY0laUfhglK3lC7", mostActiveCookies.get(0));

        mostActiveCookies = most_active_cookie.findMostActiveCookies(most_active_cookie.findCookiesOnDate(cookieLogString, "2018-12-08"));
        assertEquals(3, mostActiveCookies.size());
        assertEquals("SAZuXPGUrfbcn5UA", mostActiveCookies.get(0));
        assertEquals("4sMM2LxV07bPJzwf", mostActiveCookies.get(1));
        assertEquals("fbcn5UAVanZf6UtG", mostActiveCookies.get(2));

        mostActiveCookies = most_active_cookie.findMostActiveCookies(most_active_cookie.findCookiesOnDate(cookieLogString, "2018-12-10"));
        assertEquals(0, mostActiveCookies.size());
        
        mostActiveCookies = most_active_cookie.findMostActiveCookies(most_active_cookie.findCookiesOnDate(cookieLogString, "2018-12-06"));
        assertEquals(0, mostActiveCookies.size());

        mostActiveCookies = most_active_cookie.findMostActiveCookies(most_active_cookie.findCookiesOnDate(testString, "4"));
        assertEquals(1, mostActiveCookies.size());
        assertEquals("B", mostActiveCookies.get(0));

        mostActiveCookies = most_active_cookie.findMostActiveCookies(most_active_cookie.findCookiesOnDate(blankString, "2018-12-08"));
        assertEquals(0, mostActiveCookies.size());

        mostActiveCookies = most_active_cookie.findMostActiveCookies(most_active_cookie.findCookiesOnDate(specialString, "2022-02-01"));
        assertEquals(2, mostActiveCookies.size());
        assertEquals("2022-02-02T14:19:00+00:00", mostActiveCookies.get(0));
        assertEquals("4sMM2LxV07bPJzwf", mostActiveCookies.get(1));
    }
}
