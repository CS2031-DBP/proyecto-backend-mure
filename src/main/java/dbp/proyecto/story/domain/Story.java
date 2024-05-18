package dbp.proyecto.story.domain;

import dbp.proyecto.content.Content;
import dbp.proyecto.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@Entity
public class Story extends Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Timestamp startDate;

    @Column(nullable = false)
    private Timestamp endDate;

    @Column(nullable = false)
    private String videoUrl;

    @Size(max = 200)
    private String text;

    @ManyToOne
    private User user;

    public Story(Long id, Timestamp startdate, String videourl, String text) {
        this.id = id;
        this.startDate = startdate;
        this.endDate = new Timestamp(startdate.getTime() + 86400000);
        this.videoUrl = videourl;
        this.text = text;
    }

    public Story() {
    }
}