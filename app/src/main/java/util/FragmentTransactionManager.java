package util;

import android.support.annotation.AnimRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public final class FragmentTransactionManager {

    private FragmentTransactionManager() {
        /*FragmentTransactionManager*/
    }

    private static final String LOG_TAG = FragmentTransactionManager.class.getSimpleName();

    public static void displayFragment(FragmentManager fragmentManager, Fragment fragment, int view, boolean mustAddToBackStack) {
        if (mustAddToBackStack) {
            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(view, fragment, fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        } else {
            fragmentManager.beginTransaction()
                    .replace(view, fragment, fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    public static void displayFragmentWithTransactionAnim(FragmentManager fragmentManager, Fragment fragment,
                                                          int view, @AnimRes int start, @AnimRes int exit, boolean mustAddToBackStack) {
        if (mustAddToBackStack) {
            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .setCustomAnimations(start, exit)
                    .replace(view, fragment, fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        } else {
            fragmentManager.beginTransaction()
                    .setCustomAnimations(start, exit)
                    .replace(view, fragment, fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    public static void addFragment(FragmentManager fragmentManager, Fragment fragment, int view, boolean mustAddToBackStack) {
        if (mustAddToBackStack) {
            fragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .add(view, fragment, fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        } else {
            fragmentManager.beginTransaction()
                    .add(view, fragment, fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }
}
