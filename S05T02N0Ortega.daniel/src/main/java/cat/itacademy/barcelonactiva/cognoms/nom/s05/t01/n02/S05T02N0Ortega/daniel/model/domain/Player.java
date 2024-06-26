package cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "Player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long playerID;

    @Column(name = "Player_Name")
    private String playerName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private final Date registerDate = new Date();

    @Column(name = "Win_rate")
    private double winRate;

    @OneToMany(mappedBy = "player")
    private List<Game> gamesList;

    @Column(name = "user_id")
    private String userId;

    @Transient
    private User user;


    public void addingGame(Game game) {
        if (gamesList == null) {
            gamesList = new ArrayList<>();
        }
        gamesList.add(game);
    }

    public double calculateSuccessRate() {

        if (gamesList.isEmpty()) {
            return 0;
        }

        long totalGames = gamesList.size();
        long wonGames = gamesList.stream().filter(Game::hasWon).count();
        winRate = ((double) wonGames / totalGames) * 100;

        return winRate;

    }
}