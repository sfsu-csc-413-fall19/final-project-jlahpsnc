import React from 'react';
import ReactCardFlip from 'react-card-flip';
import { Button } from 'flwww';

const Card = ({ info, ws, gameId, currentPlayersTurn }) => {
  const [isFlipped, setIsFlipped] = React.useState(false);

  const handleClick = e => {
    const x = info.x.toString();
    const y = info.y.toString();
    const userId = localStorage.getItem('id');
    const responseBody = gameId + ',' + userId + ',' + x + ',' + y;
    const flipCardInfo = {
      responseType: 'Flip Card',
      responseBody: responseBody
    };

    ws.current.send(JSON.stringify(flipCardInfo));
    setIsFlipped(!isFlipped);
    console.log('this is the card info: ');
    console.log(info);
  };

  return (
    <ReactCardFlip
      isFlipped={info.isRevealed}
      flipDirection='horizontal'
      style={{ height: '100px' }}
    >
      <Button
        onClick={handleClick}
        disabled={currentPlayersTurn !== localStorage.getItem('id')}
      >
        This is the Front of the card.
      </Button>

      <Button
        onClick={handleClick}
        disabled={currentPlayersTurn !== localStorage.getItem('id')}
      >
        {info.cardId.toString()}
      </Button>
    </ReactCardFlip>
  );
};

export default Card;
