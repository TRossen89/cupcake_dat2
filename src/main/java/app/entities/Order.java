package app.entities;
import java.util.Date;

public record Order(int id, Date date, String status, String userName) {
    
}
