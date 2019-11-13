package DAO;

import DTO.BoardDto;
import DTO.CardDto;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BoardDao {
    public static BoardDto generateNewBoard() {
        // Create a new array with 12 pairs of matching card
        CardDto cardArray[] = new CardDto[25];
        for (int i = 0; i < 12; i = i + 2) {
            cardArray[i] = new CardDto(i);
            cardArray[i + 1] = new CardDto(i);
        }

        // Add action card to cards array
        cardArray[24] = new CardDto(99);

        // Shuffle the cards array
        List<CardDto> cardList = Arrays.asList(cardArray);
        Collections.shuffle(cardList);
        cardList.toArray(cardArray);

        return new BoardDto(cardArray);
    }
}
