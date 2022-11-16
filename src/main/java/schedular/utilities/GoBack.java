/**
 * Better interface layout needs to be implemented
 * @author Syed Khurshid
 * Still do not see the use of lambda but will check
 */
package schedular.utilities;


import java.io.IOException;

/**
 * This is an interface for lambda expression
 */
public interface GoBack {
    /**
     * This is to switchScreens between screens
     * @param s this is the path in string format
     * @throws IOException if an error occurs between switching screens
     */
    void switchScreens(String s) throws IOException;
}
