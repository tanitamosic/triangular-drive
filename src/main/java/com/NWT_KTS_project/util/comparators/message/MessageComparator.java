package com.NWT_KTS_project.util.comparators.message;

import com.NWT_KTS_project.model.Message;

import java.util.Comparator;

public class MessageComparator implements Comparator<Message> {
    @Override
    public int compare(Message o1, Message o2) {
        return o1.getTime().compareTo(o2.getTime());
    }
}
