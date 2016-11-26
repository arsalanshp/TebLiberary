package library.tebyan.com.teblibrary.classes.Sms;

/**
 * The SmsStorage has the responsibility to store the last sms intercepted by the library.
 *
 * @author Pedro Vicente GÃ³mez SÃ¡nchez <pgomez@tuenti.com>
 * @author Manuel Peinado <mpeinado@tuenti.com>
 */
interface SmsStorage {


    void updateLastSmsIntercepted(int smsId);

    int getLastSmsIntercepted();

    boolean isFirstSmsIntercepted();
}
