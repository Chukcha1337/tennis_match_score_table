import com.chuckcha.entity.Player;
import com.chuckcha.repository.PlayerRepository;
import com.chuckcha.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

public class RunnerTest {

    @Test
    void checkH2() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
            Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Player player = Player.builder().name("faf").build();
            PlayerRepository playerRepository = new PlayerRepository(session);
            playerRepository.update(player);

            System.out.println(playerRepository.findById(player.getId()));

            session.getTransaction().commit();

            System.out.println();
        }
    }
}
