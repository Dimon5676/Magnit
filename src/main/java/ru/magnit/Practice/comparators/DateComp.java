package ru.magnit.Practice.comparators;

import ru.magnit.Practice.models.Idea;

import java.util.Comparator;

public class DateComp implements Comparator<Idea> {
    @Override
    public int compare(Idea o1, Idea o2) {
        return o1.getDate().compareTo(o2.getDate()) * -1;
    }
}
