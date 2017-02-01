package com.ionhaccp.model;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by mehtermu on 30-01-2017.
 */

public class SelectLanguageModel {
    private String strLanguageName, strLanguageCode;

    public SelectLanguageModel(String strLanguageName, String strLanguageCode) {
        this.strLanguageName = strLanguageName;
        this.strLanguageCode = strLanguageCode;
    }

    public String getStrLanguageName() {
        return strLanguageName;
    }

    public void setStrLanguageName(String strLanguageName) {
        this.strLanguageName = strLanguageName;
    }

    public String getStrLanguageCode() {
        return strLanguageCode;
    }

    public void setStrLanguageCode(String strLanguageCode) {
        this.strLanguageCode = strLanguageCode;
    }
}
