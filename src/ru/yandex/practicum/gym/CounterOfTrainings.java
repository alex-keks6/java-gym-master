package ru.yandex.practicum.gym;

import java.util.Objects;

public class CounterOfTrainings {
    private final Coach coach;
    private int countOfTrainings;

    public CounterOfTrainings(Coach coach, int countOfTrainings) {
        this.coach = coach;
        this.countOfTrainings = countOfTrainings;
    }

    @Override
    public String toString() {
        return "CounterOfTrainings{" +
                "coach=" + coach +
                ", countOfTrainings=" + countOfTrainings +
                '}';
    }

    public Coach getCoach() {
        return coach;
    }

    public int getCountOfTrainings() {
        return countOfTrainings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CounterOfTrainings that = (CounterOfTrainings) o;
        return countOfTrainings == that.countOfTrainings && Objects.equals(coach, that.coach);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coach, countOfTrainings);
    }
}