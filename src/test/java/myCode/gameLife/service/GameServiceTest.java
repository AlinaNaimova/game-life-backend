package myCode.gameLife.service;

import myCode.gameLife.entity.Cell;
import myCode.gameLife.entity.Game;
import myCode.gameLife.repository.GameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameService gameService;

    @Test
    void testNewGeneration() {
        Game game = new Game();
        game.setId(1L);
        game.setPlayingFieldSize(10);

        Cell cell1 = new Cell();
        cell1.setX(5);
        cell1.setY(5);
        cell1.setLife(true);
        Cell cell2 = new Cell();
        cell2.setX(6);
        cell2.setY(5);
        cell2.setLife(true);
        Cell cell3 = new Cell();
        cell3.setX(5);
        cell3.setY(6);
        cell3.setLife(true);
        Set<Cell> cells = new HashSet<>();
        cells.add(cell1);
        cells.add(cell2);
        cells.add(cell3);

        game.setCells(cells);

        Mockito.when(gameRepository.save(game)).thenReturn(game);

        Game newGame = gameService.newGeneration(game);

        // Проверяем, что новое поколение клеток было создано
        assertEquals(4, newGame.getCells().size());
    }

    @Test
    void testCheckCell() {
        Game game = new Game();
        game.setPlayingFieldSize(10);

        Cell cell1 = new Cell();
        cell1.setX(5);
        cell1.setY(5);
        cell1.setLife(true);
        Set<Cell> cells = new HashSet<>();
        cells.add(cell1);
        game.setCells(cells);

        // Проверяем, что клетка не должна остаться живой в новом поколении
        assertFalse(gameService.checkCell(cell1, game));
    }

    @Test
    void testCheckBorder() {
        Game game = new Game();
        game.setPlayingFieldSize(10);

        Cell cell1 = new Cell();
        cell1.setX(0);
        cell1.setY(0);
        cell1.setLife(false);

        // Проверяем, что клетка не выходит за границы поля
        assertTrue(gameService.checkBorder(cell1, game));
    }
}
