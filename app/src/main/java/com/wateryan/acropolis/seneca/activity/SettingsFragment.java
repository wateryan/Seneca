package com.wateryan.acropolis.seneca.activity;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.wateryan.acropolis.seneca.R;

/**
 * Created on 7/30/2015.
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences_fragment);
    }
}
