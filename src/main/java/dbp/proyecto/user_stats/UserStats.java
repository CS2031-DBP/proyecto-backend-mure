package dbp.proyecto.user_stats;

import dbp.proyecto.user.User;
import jakarta.persistence.*;

@Entity
public class UserStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id")
    private User user_id;

}
