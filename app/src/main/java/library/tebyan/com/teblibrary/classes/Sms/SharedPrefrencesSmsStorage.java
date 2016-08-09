package library.tebyan.com.teblibrary.classes.Sms;


import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * SmsStorage implementation based on shared preferences.
 *
 * @author Pedro Vicente GÃ³mez SÃ¡nchez <pgomez@tuenti.com>
 * @author Manuel Peinado <mpeinado@tuenti.com>
 */
class SharedPreferencesSmsStorage implements SmsStorage {

    private static final String LAST_SMS_PARSED = "last_sms_parsed";
    private static final int DEFAULT_SMS_PARSED_VALUE = -1;

    private SharedPreferences preferences;

    SharedPreferencesSmsStorage(SharedPreferences preferences) {
        if (preferences == null) {
            throw new IllegalArgumentException("SharedPreferences param can't be null");
        }
        this.preferences = preferences;
    }

    @Override
    public void updateLastSmsIntercepted(int smsId) {
        Editor editor = preferences.edit();
        editor.putInt(LAST_SMS_PARSED, smsId);
        editor.commit();
    }

    @Override
    public int getLastSmsIntercepted() {
        return preferences.getInt(LAST_SMS_PARSED, DEFAULT_SMS_PARSED_VALUE);
    }

    @Override
    public boolean isFirstSmsIntercepted() {
        return getLastSmsIntercepted() == DEFAULT_SMS_PARSED_VALUE;
    }
}
