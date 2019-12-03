package DTO;

import javax.smartcardio.Card;

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
}
