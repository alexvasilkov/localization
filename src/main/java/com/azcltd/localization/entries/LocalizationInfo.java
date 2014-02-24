package com.azcltd.localization.entries;

public class LocalizationInfo {

    public final LocalizationStructure structure;
    public final LocalizationTable localization;

    public LocalizationInfo(LocalizationStructure structure, LocalizationTable localization) {
        this.structure = structure;
        this.localization = localization;
    }

}
