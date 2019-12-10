package DTO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BoardDto {
    public CardDto[][] boardLayout;

    public BoardDto(CardDto[] cardList) {
        CardDto[][] tempBoardLayout = new CardDto[5][5];
        int currentCard = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                CardDto tempCard = cardList[currentCard];
                tempCard.x = i;
                tempCard.y = j;
                tempBoardLayout[i][j] = tempCard;
                currentCard++;
            }
        }
        boardLayout =  tempBoardLayout;
    }

    public CardDto getCard(int x, int y) {
        if (x >= 0 && x <= 4 && y >= 0 && y <= 4) {
            return boardLayout[x][y];
        }
        return null;
    }

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
