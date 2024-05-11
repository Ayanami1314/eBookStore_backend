package com.example.ebookstorebackend.utils;

public class Hash {
    // HINT: use this method to generate a hash code for an ID and a name
    // HINT: mainly for escape endless recur in bi-directional relationship
    public static int IDHashCode(Long id, String name) {
        int hash = 0;
        for (int i = 0; i < name.length(); i++) {
            hash = 31 * hash + name.charAt(i);
        }
        return hash + id.intValue();
    }
}
