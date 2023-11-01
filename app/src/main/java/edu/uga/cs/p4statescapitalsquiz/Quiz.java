package edu.uga.cs.p4statescapitalsquiz;

import java.sql.Time;
import java.util.Date;

public class Quiz {
    private long   id;
    private Date quizDate;
    private Time quizTime;
    private Integer quizResult; // int?

    public Quiz()
    {
        this.id = -1;
        this.quizDate = null;
        this.quizTime = null;
        this.quizResult = null;
    }

    public Quiz( Date quizDate, Time quizTime, Integer quizResult ) {
        this.id = -1;  // the primary key id will be set by a setter method
        this.quizDate = quizDate;
        this.quizTime = quizTime;
        this.quizResult = quizResult;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public Date getQuizDate()
    {
        return quizDate;
    }

    public void setQuizDate(Date quizDate)
    {
        this.quizDate = quizDate;
    }

    public Time getQuizTime()
    {
        return quizTime;
    }

    public void setQuizTime(Time quizTime)
    {
        this.quizTime = quizTime;
    }

    public Integer getResult()
    {
        return quizResult;
    }

    public void setResult(Integer quizResult)
    {
        this.quizResult = quizResult;
    }

  

    public String toString()
    {
        return id + ": " + quizDate + " " + quizTime + " " + quizResult;
    }
}
