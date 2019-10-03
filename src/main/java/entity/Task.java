package entity;
import lombok.Data;
import java.time.LocalDateTime;

@Data

public class Task {

    private String taskDescription;
    private LocalDateTime finishTime;
    private Prioryty prioryty;

}
