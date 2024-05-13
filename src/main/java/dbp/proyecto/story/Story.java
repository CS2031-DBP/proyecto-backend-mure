package dbp.proyecto.story;

import dbp.proyecto.content.Content;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
public class Story extends Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp startDate;

    private Timestamp endDate;

    private String videourl;

    private String text;

    public Story() {}

    public Story(Long id, Timestamp startdate, String videourl, String text) {
        this.id = id;
        this.startDate = startdate;
        this.endDate = new Timestamp(startdate.getTime() + 86400000);
        this.videourl = videourl;
        this.text = text;
    }
}
