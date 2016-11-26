package library.tebyan.com.teblibrary.classes;


import java.util.Date;

/**
 * Class created to work as time provider. This entity returns the current date. Avoid the client code to use System
 * .currentTimeMillis or create a new Date object directly.
 *
 * @author Pedro Vicente GÃ³mez SÃ¡nchez <pgomez@tuenti.com>
 * @author Manuel Peinado <mpeinado@tuenti.com>
 */
public class TimeProvider {

    public Date getDate() {
        return new Date();
    }

}

