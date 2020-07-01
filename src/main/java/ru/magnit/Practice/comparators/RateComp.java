package ru.magnit.Practice.comparators;

import ru.magnit.Practice.models.Idea;

import java.util.Comparator;

public class RateComp implements Comparator<Idea> {
    @Override
    public int compare(Idea o1, Idea o2) {
        if (o1.getRate() > o2.getRate()) return -1;
        if (o1.getRate() == o2.getRate()) return 0;
        if (o1.getRate() < o2.getRate()) return 1;
        return 0;
    }
}
