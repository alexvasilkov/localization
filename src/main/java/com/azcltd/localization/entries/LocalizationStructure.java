package com.azcltd.localization.entries;

import java.util.ArrayList;
import java.util.List;

public class LocalizationStructure {

    private final List<Record> records;

    public LocalizationStructure() {
        records = new ArrayList<Record>();
    }

    public void addSection(String value) {
        add(Type.SECTION, value, null);
    }

    public void addKey(String value, String description) {
        add(Type.KEY, value, description);
    }

    private void add(Type type, String value, String description) {
        records.add(new Record(type, value, description));
    }

    public List<Record> getRecords() {
        return records;
    }

    public static class Record {
        public final Type type;
        public final String value;
        public final String description;

        private Record(Type type, String value, String description) {
            this.type = type;
            this.value = value;
            this.description = description;
        }
    }

    public static enum Type {
        SECTION, KEY
    }

}
