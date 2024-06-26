package cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.S05T02N0Ortega.daniel.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameDTO {
    private long gameID;
    private int die1;
    private int die2;
    private boolean won;

}