package dbp.proyecto.story;

import dbp.proyecto.content.Content;
import dbp.proyecto.user.domain.User;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Story extends Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp startdate;

    private Timestamp enddate;

    private String videourl;

    private String text;

    @ManyToOne
    private User user;

    public Story(Long id, Timestamp startdate, String videourl, String text) {
        this.id = id;
        this.startdate = startdate;
        this.enddate = new Timestamp(startdate.getTime() + 86400000);
        this.videourl = videourl;
        this.text = text;
    }

    public Story() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getStartdate() {
        return startdate;
    }

    public void setStartdate(Timestamp startdate) {
        this.startdate = startdate;
    }

    public Timestamp getEnddate() {
        return enddate;
    }

    public void setEnddate(Timestamp enddate) {
        this.enddate = enddate;
    }

    public String getVideourl() {
        return videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}