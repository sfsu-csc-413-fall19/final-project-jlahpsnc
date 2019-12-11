package DataObjects;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameBoard {
    public Card[][] boardLayout;

    public GameBoard(Card[] cardList) {
        Card[][] tempBoardLayout = new Card[5][5];
        int currentCard = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Card tempCard = cardList[currentCard];
                tempCard.x = i;
                tempCard.y = j;
                tempBoardLayout[i][j] = tempCard;
                currentCard++;
            }
        }
        boardLayout =  tempBoardLayout;
    }

    public Card getCard(int x, int y) {
        if (x >= 0 && x <= 4 && y >= 0 && y <= 4) {
            return boardLayout[x][y];
        }
        return null;
    }

    public static GameBoard generateNewBoard() {
        // Create a new array with 12 pairs of matching card
        Card cardArray[] = new Card[25];
        for (int i = 0; i < 24; i = i + 2) {
            cardArray[i] = new Card(i);
            cardArray[i + 1] = new Card(i);
        }

        // Add action card to cards array
        cardArray[24] = new Card(99);

        // Shuffle the cards array
        List<Card> cardList = Arrays.asList(cardArray);
        Collections.shuffle(cardList);
        cardList.toArray(cardArray);

        return new GameBoard(cardArray);
    }
}
