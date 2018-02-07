package app.we.go.emojidraw.widget;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


// Toast with centered text
public class CenteredToast {


    public static Toast makeText(Context context, @StringRes int message, int duration) {
        final Toast toast = Toast.makeText(context, message, duration);
        LinearLayout layout = (LinearLayout) toast.getView();
        if (layout.getChildCount() > 0) {
            TextView tv = (TextView) layout.getChildAt(0);
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
        }
        return toast;
    }
}
