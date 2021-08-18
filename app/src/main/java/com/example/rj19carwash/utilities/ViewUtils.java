package com.example.rj19carwash.utilities;

import android.view.View;
import android.view.ViewGroup;

public class ViewUtils {

    public static String phonePattern = "[7-9][0-9]{9}";

    public static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public static void setViewGroupEnabled(ViewGroup view, boolean enabled)
    {
        int childern = view.getChildCount();

        for (int i = 0; i< childern ; i++)
        {
            View child = view.getChildAt(i);
            if (child instanceof ViewGroup)
            {
                setViewGroupEnabled((ViewGroup) child, enabled);
            }
            child.setEnabled(enabled);
        }
        view.setEnabled(enabled);
    }
}
