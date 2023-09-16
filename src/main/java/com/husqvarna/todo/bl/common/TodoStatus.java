package com.husqvarna.todo.bl.common;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public enum TodoStatus {
    PENDING ("PENDING"),
    IN_PROGRESS ("IN_PROGRESS"),
    COMPLETED ("COMPLETED");

    private String status;

    private static final Map<String, TodoStatus> stringToEnumMap = new HashMap<>();

    static {
        for (TodoStatus statusEnum: TodoStatus.values()) {
            stringToEnumMap.put(statusEnum.status, statusEnum);
        }
    }

    public static TodoStatus fromValue(String status) {
        return stringToEnumMap.get(status);
    }
}
