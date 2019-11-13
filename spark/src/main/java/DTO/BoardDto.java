package DTO;

public class BoardDto {
    public final CardDto[][] boardLayout;

    public BoardDto(CardDto[] cardList) {
        CardDto[][] tempBoardLayout = new CardDto[5][5];
        int currentCard = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                tempBoardLayout[i][j] = cardList[currentCard];
                currentCard++;
            }
        }
        boardLayout =  tempBoardLayout;
    }
}
