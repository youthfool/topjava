package ru.javawebinar.topjava;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MealTestDurationRule extends Stopwatch {
    private List<String> results = new ArrayList<>();

    @Override
    protected void finished(long nanos, Description description) {
        String result = String.format("%s spent %d mcs", description.getMethodName(), TimeUnit.NANOSECONDS.toMicros(nanos));
        results.add(result);
    }

    protected List<String> getResults() {
        return results;
    }
}
