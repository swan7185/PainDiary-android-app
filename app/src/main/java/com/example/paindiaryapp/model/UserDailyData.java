package com.example.paindiaryapp.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.paindiaryapp.util.DateConverter;

import java.util.Date;



@Entity(primaryKeys = {"userEmail", "date"}, tableName = "UserDailyData")
@TypeConverters(DateConverter.class)
public class UserDailyData {

    @NonNull
    @ColumnInfo(name="painLevel")
    public int painLevel;

    @NonNull
    @ColumnInfo(name="painPosition")
    public String painPosition;

    @NonNull
    @ColumnInfo(name="mood")
    public String mood;

    @NonNull
    @ColumnInfo(name="goal")
    public int goal;

    @NonNull
    @ColumnInfo(name="cur")
    public int cur;

    public void setDate(@NonNull final Date date) {
        this.date = date;
    }

    @NonNull
    @ColumnInfo(name="date")
    public Date date;

    @NonNull
    @ColumnInfo(name="humidity")
    public float humidity;

    @NonNull
    @ColumnInfo(name="pressure")
    public float pressure;

    @NonNull
    @ColumnInfo(name="temperature")
    public float temperature;

    @NonNull
    @ColumnInfo(name="userEmail")
    public String userEmail;

    public String getDescription()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("painLevel: " + String.valueOf(painLevel) + "\n");
        builder.append("painPosition: " + painPosition + "\n");
        builder.append("mood: " + mood + "\n");
        builder.append("goal: " + goal + "\n");
        builder.append("cur: " + cur + "\n");

        builder.append("temperature: " + temperature + "\n");
        builder.append("humidity: " + humidity + "\n");
        builder.append("pressure: " + pressure);
        return builder.toString();
    }

    public UserDailyData(@NonNull String userEmail, @NonNull Date date,
                         int painLevel, @NonNull String painPosition, @NonNull String mood,
                         int goal, int cur,
                         float temperature, float humidity, float pressure) {
        this.painLevel = painLevel;
        this.painPosition = painPosition;
        this.mood = mood;
        this.goal = goal;
        this.cur = cur;
        this.date = date;
        this.humidity = humidity;
        this.pressure = pressure;
        this.temperature = temperature;
        this.userEmail = userEmail;
    }

    public int getPainLevel() {
        return this.painLevel;
    }

    @NonNull
    public String getPainPosition() {
        return this.painPosition;
    }

    @NonNull
    public String getMood() {
        return this.mood;
    }

    public int getGoal() {
        return this.goal;
    }

    public int getCur() {
        return this.cur;
    }

    @NonNull
    public Date getDate() {
        return this.date;
    }

    public float getHumidity() {
        return this.humidity;
    }

    public float getPressure() {
        return this.pressure;
    }

    public float getTemperature() {
        return this.temperature;
    }

    @NonNull
    public String getUserEmail() {
        return this.userEmail;
    }
}
