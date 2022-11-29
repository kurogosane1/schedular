/**
 * Better interface layout needs to be implemented
 * Still do not see the use of lambda but will check
 */
package schedular.utilities;

/**
 * @author Syed Khurshid
 */

import java.io.IOException;

/**
 * This is an interface for lambda expression
 */
public interface GoBack {
    /**
     * This is to switchScreens between screens
     * @param s this is the path in string format
     * @param pageDescription is what title in string format should the window being display have
     * @throws IOException if an error occurs between switching screens
     */
    void switchScreens(String s, String pageDescription) throws IOException;
}
