package dbp.proyecto.user_stats;

import dbp.proyecto.content.content;
import dbp.proyecto.user.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class UserStats {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id")
    private User user_id;

}
