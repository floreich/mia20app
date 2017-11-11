import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.sql.Array;

/**
 * Created by flo on 08.11.17.
 */

public class CustomArrayAdapter extends ArrayAdapter {

    public CustomArrayAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }
}
