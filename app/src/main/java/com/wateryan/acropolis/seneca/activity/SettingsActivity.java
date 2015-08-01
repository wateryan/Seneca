package com.wateryan.acropolis.seneca.activity;

import android.preference.PreferenceActivity;

import com.wateryan.acropolis.seneca.R;

import java.util.List;

/**
 * Created on 7/31/2015.
 */
public class SettingsActivity extends PreferenceActivity {

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.preference_headers, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return SettingsFragment.class.getName().equals(fragmentName);
    }
}
