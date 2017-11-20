package bankzworld.com.db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jaycee on 8/11/2017.
 */

public class PasswordContract {

    public static final class PasswordEntry implements BaseColumns {

        public static final String _ID = "id";

        public static final String ACCOUNT_TABLE = "my_table";

        public static final String ACCOUNT_TYPE = "my_type";

        public static final String ACCOUNT_USERNAME = "my_username";

        public static final String ACCOUNT_PASSWORD = "my_password";

    }
}
