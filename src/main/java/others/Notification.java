package others;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class Notification {

    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime time;


    Notification(String message, LocalDate date){
        this.message = message;
        this.date = date;
    }
    Notification(){
        this.message = "";
        this.date =null;
    }

    private String getMessage(){
        return message;
    }
    private LocalDate getDate(){
        return date;
    }
}
