package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();
        ArrayList<TrainingSession> correctResultMonday = new ArrayList<>();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
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
        Timetable timetable = new Timetable();
        ArrayList<TrainingSession> correctResultMonday = new ArrayList<>();
        ArrayList<TrainingSession> correctResultThursday = new ArrayList<>();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
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
        Timetable timetable = new Timetable();
        ArrayList<TrainingSession> correctResultMonday = new ArrayList<>();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
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
        Timetable timetable = new Timetable();
        ArrayList<TrainingSession> correctResultThursday = new ArrayList<>();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
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
        Timetable timetable = new Timetable();
        ArrayList<TrainingSession> correctResultThursday = new ArrayList<>();
        ArrayList<TrainingSession> correctResultMonday = new ArrayList<>();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
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
        Timetable timetable = new Timetable();
        ArrayList<TrainingSession> correctResultThursday = new ArrayList<>();
        ArrayList<TrainingSession> correctResultMonday = new ArrayList<>();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
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
}