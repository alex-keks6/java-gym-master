package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    Timetable timetable;
    Coach coach;
    Group groupChild, groupAdult;

    @BeforeEach
    void initTimetable() {
        timetable = new Timetable();
        coach = new Coach("Васильев", "Николай", "Сергеевич");
        groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
    }

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        ArrayList<TrainingSession> correctResultMonday = new ArrayList<>();

        TrainingSession singleTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        correctResultMonday.add(singleTrainingSession);

        // Для надежности проверка не просто на количество, а на реально добавленное занятие
        //Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(correctResultMonday, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY));
        //Проверить, что за вторник не вернулось занятий
        Assertions.assertEquals(new ArrayList<>(), timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        ArrayList<TrainingSession> correctResultMonday = new ArrayList<>();
        ArrayList<TrainingSession> correctResultThursday = new ArrayList<>();

        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        correctResultMonday.add(mondayChildTrainingSession);
        correctResultThursday.add(thursdayChildTrainingSession);
        correctResultThursday.add(thursdayAdultTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(correctResultMonday, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY));
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        Assertions.assertEquals(correctResultThursday, timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY));
        // Проверить, что за вторник не вернулось занятий
        Assertions.assertEquals(new ArrayList<>(), timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));
    }

    @Test
    void testGetTrainingSessionsForDayAndTimeSingleSession() {
        ArrayList<TrainingSession> correctResultMonday = new ArrayList<>();

        TrainingSession singleTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        correctResultMonday.add(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        Assertions.assertEquals(correctResultMonday, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)));
        //Проверить, что за понедельник в 14:00 не вернулось занятий
        Assertions.assertEquals(new ArrayList<>(), timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(14, 0)));
    }

    @Test
    void testGetTrainingSessionsForDayAndTimeMultipleSessionsAtOneTime() {
        ArrayList<TrainingSession> correctResultThursday = new ArrayList<>();

        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(thursdayChildTrainingSession);

        correctResultThursday.add(thursdayAdultTrainingSession);
        correctResultThursday.add(thursdayChildTrainingSession);

        //Проверить, что за четверг в 13:00 вернулось два занятия
        Assertions.assertEquals(correctResultThursday, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.THURSDAY,
                new TimeOfDay(13, 0)));
    }

    @Test
    void testGetTrainingSessionsForDayAndTimeMultipleSessionsAtDifferentDay() {
        ArrayList<TrainingSession> correctResultThursday = new ArrayList<>();
        ArrayList<TrainingSession> correctResultMonday = new ArrayList<>();

        TrainingSession thursdayTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(thursdayTrainingSession);

        TrainingSession mondayTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(mondayTrainingSession);

        correctResultThursday.add(thursdayTrainingSession);
        correctResultMonday.add(mondayTrainingSession);

        //Проверить, что за четверг в 13:00 вернулось одно занятие
        Assertions.assertEquals(correctResultThursday, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.THURSDAY,
                new TimeOfDay(13, 0)));
        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        Assertions.assertEquals(correctResultMonday, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)));
    }

    @Test
    void testGetTrainingSessionsForDayAndTimeMultipleSessionsAtDifferentDayAndTime() {
        ArrayList<TrainingSession> correctResultThursday = new ArrayList<>();
        ArrayList<TrainingSession> correctResultMonday = new ArrayList<>();

        TrainingSession thursdayTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(thursdayTrainingSession);

        TrainingSession mondayTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.MONDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(mondayTrainingSession);

        correctResultThursday.add(thursdayTrainingSession);
        correctResultMonday.add(mondayTrainingSession);

        //Проверить, что за четверг в 13:00 вернулось одно занятие
        Assertions.assertEquals(correctResultThursday, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.THURSDAY,
                new TimeOfDay(13, 0)));
        //Проверить, что за понедельник в 20:00 вернулось одно занятие
        Assertions.assertEquals(correctResultMonday, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(20, 0)));
    }

    @Test
    void testGetCountByCoachesWithEqualSessions() {
        List<Integer> correctResult = new ArrayList<>();
        List<Integer> actualResult = new ArrayList<>();
        List<CounterOfTrainings> methodResult;

        Coach coach2 = new Coach("Александров", "Александр", "Александрович");
        Coach coach3 = new Coach("Иванов", "Иван", "Иванович");

        TrainingSession trainingSession1 = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession trainingSession2 = new TrainingSession(groupAdult, coach,
                DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        TrainingSession trainingSession3 = new TrainingSession(groupAdult, coach2,
                DayOfWeek.FRIDAY, new TimeOfDay(13, 0));
        TrainingSession trainingSession4 = new TrainingSession(groupAdult, coach2,
                DayOfWeek.TUESDAY, new TimeOfDay(18, 0));
        TrainingSession trainingSession5 = new TrainingSession(groupAdult, coach3,
                DayOfWeek.THURSDAY, new TimeOfDay(11, 0));

        timetable.addNewTrainingSession(trainingSession1);
        timetable.addNewTrainingSession(trainingSession2);
        timetable.addNewTrainingSession(trainingSession3);
        timetable.addNewTrainingSession(trainingSession4);
        timetable.addNewTrainingSession(trainingSession5);

        correctResult.add(2);
        correctResult.add(2);
        correctResult.add(1);

        methodResult = timetable.getCountByCoaches();

        actualResult.add(methodResult.get(0).getCountOfTrainings());
        actualResult.add(methodResult.get(1).getCountOfTrainings());
        actualResult.add(methodResult.get(2).getCountOfTrainings());

        Assertions.assertEquals(correctResult, actualResult);
    }

    @Test
    void testGetCountByCoachesNoSessions() {
        //Проверить, что вернулся пустой массив, так как занятий не было
        Assertions.assertEquals(new ArrayList<>(), timetable.getCountByCoaches());
    }

    @Test
    void testGetCountByCoachesWithDifferentSessions() {
        List<CounterOfTrainings> correctResult = new ArrayList<>();

        Coach coach2 = new Coach("Александров", "Александр", "Александрович");
        Coach coach3 = new Coach("Иванов", "Иван", "Иванович");

        TrainingSession trainingSession1 = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession trainingSession2 = new TrainingSession(groupAdult, coach,
                DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        TrainingSession trainingSession3 = new TrainingSession(groupAdult, coach,
                DayOfWeek.FRIDAY, new TimeOfDay(13, 0));
        TrainingSession trainingSession4 = new TrainingSession(groupAdult, coach2,
                DayOfWeek.TUESDAY, new TimeOfDay(18, 0));
        TrainingSession trainingSession5 = new TrainingSession(groupAdult, coach2,
                DayOfWeek.THURSDAY, new TimeOfDay(11, 0));
        TrainingSession trainingSession6 = new TrainingSession(groupAdult, coach3,
                DayOfWeek.SATURDAY, new TimeOfDay(19, 0));

        timetable.addNewTrainingSession(trainingSession1);
        timetable.addNewTrainingSession(trainingSession2);
        timetable.addNewTrainingSession(trainingSession3);
        timetable.addNewTrainingSession(trainingSession4);
        timetable.addNewTrainingSession(trainingSession5);
        timetable.addNewTrainingSession(trainingSession6);

        correctResult.add(new CounterOfTrainings(coach, 3));
        correctResult.add(new CounterOfTrainings(coach2, 2));
        correctResult.add(new CounterOfTrainings(coach3, 1));

        Assertions.assertEquals(correctResult, timetable.getCountByCoaches());
    }
}