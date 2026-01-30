package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable;

    public Timetable() {
        this.timetable = new HashMap<>();
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            timetable.put(dayOfWeek, new TreeMap<>());
        }
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        timetable.get(trainingSession.getDayOfWeek()).putIfAbsent(trainingSession.getTimeOfDay(), new ArrayList<>());
        timetable.get(trainingSession.getDayOfWeek()).get(trainingSession.getTimeOfDay()).add(trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        ArrayList<TrainingSession> trainingSessionsForDay = new ArrayList<>();

        for (ArrayList<TrainingSession> trainingSessions : timetable.get(dayOfWeek).values()) {
            trainingSessionsForDay.addAll(trainingSessions);
        }
        return trainingSessionsForDay;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        return timetable.get(dayOfWeek).getOrDefault(timeOfDay, new ArrayList<>());
    }

    // Нужна ли в этом методе внутри циклов проверка на null или пустоту на случай
    // если ни одной тренировки не добавлено? Написанный тест показывает, что и без этого метод корректно отрабатывает,
    // но всё же есть сомнения, так как в таком случае не инициализированы явно внутренние структуры

    // Также ещё один вопрос: почему для тестов для данного метода необходимо переопределить equals и hashCode
    // для CounterOfTrainings, иначе тесты будут падать, а для предыдущих функций для TrainingSession всё и так
    // работает?
    public List<CounterOfTrainings> getCountByCoaches() {
        ArrayList<CounterOfTrainings> sortedCountByCoaches = new ArrayList<>();
        Map<Coach, Integer> countByCoaches = new HashMap<>();

        for (TreeMap<TimeOfDay, ArrayList<TrainingSession>> dayOfWeekSessions : timetable.values()) {
            for (ArrayList<TrainingSession> timeOfDaySessions : dayOfWeekSessions.values()) {
                for (TrainingSession trainingSession : timeOfDaySessions) {
                    countByCoaches.put(trainingSession.getCoach(),
                            countByCoaches.getOrDefault(trainingSession.getCoach(), 0) + 1);
                }
            }
        }

        for (Coach coach : countByCoaches.keySet()) {
            sortedCountByCoaches.add(new CounterOfTrainings(coach, countByCoaches.get(coach)));
        }

        Collections.sort(sortedCountByCoaches);

        return sortedCountByCoaches;
    }
}