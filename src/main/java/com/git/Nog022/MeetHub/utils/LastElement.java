package com.git.Nog022.MeetHub.utils;

import java.util.List;

public class LastElement {
    public static <T> T getLastElement(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("A lista está vazia ou é nula.");
        }
        return list.get(list.size() - 1);
    }
}
