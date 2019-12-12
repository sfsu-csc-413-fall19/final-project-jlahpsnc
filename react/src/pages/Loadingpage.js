import React from 'react';
import { Container, Row, Button, Col, Icon } from 'flwww';
import { useHistory } from 'react-router-dom';
import Card from '../components/Card';

const wsSession = new WebSocket('ws://localhost:1234/wsLoading');
const Loadingpage = () => {
  let history = useHistory();
  const [screenId, setScreenId] = React.useState('');
  const [cards, setCards] = React.useState([]);
  const [gameState, setGameState] = React.useState({});
  const ws = React.useRef(wsSession);

  const messageHandler = message => {
    const jsonBody = JSON.parse(message.responseBody);
    console.log(jsonBody);
    console.log(message.responseType);
    switch (message.responseType) {
      case 'New Game':
        setGameState(jsonBody);
        setCards(jsonBody.gameBoard.boardLayout);
        setScreenId('Game Board');
        break;
      case 'Paused Game':
        console.log('PAUSED GAME: ');
        setGameState(jsonBody);
        setCards(jsonBody.gameBoard.boardLayout);
        setTimeout(() => {
          ws.current.send(messageBuilder('Get Game', jsonBody.gameId));
        }, 1000);
        break;
      case 'Update Game':
        console.log('THE GAME HAS BEEN UPDATED. NO PRINTING');
        //MAYBE WAIT
        setGameState(jsonBody);
        setCards(jsonBody.gameBoard.boardLayout);
        break;
      case 'Game Over':
        //RENDER GAME OVER SCREEN/FILTER
        console.log('THE GAME SHOULD HAVE ENDED HERE');
        setGameState(jsonBody);
        setCards(jsonBody.gameBoard.boardLayout);
        setScreenId('Game Over');
        break;
      default:
        break;
    }
  };

  const messageBuilder = (type, body) => {
    const response = {
      responseType: type,
      responseBody: body
    };
    return JSON.stringify(response);
  };

  ws.current.onopen = () => {
    console.log('Connection open!');
    ws.current.send(messageBuilder('Play Game', localStorage.getItem('id')));
  };

  ws.current.onmessage = message => {
    messageHandler(JSON.parse(message.data));
  };

  window.onbeforeunload = function() {
    ws.current.onclose = function() {
      // ws.current.send(
      //   messageBuilder('Disconnected', localStorage.getItem('id'))
      // );
      // localStorage.removeItem('id');
    }; // disable onclose handler first
    ws.current.send(messageBuilder('Disconnected', localStorage.getItem('id')));
    console.log('connection closed');
    ws.close();
  };

  ws.current.onclose = () => {
    console.log('THIS USED THE REGULAR ON CLOSE METHOD');
  };

  ws.current.onerror = () => {
    console.log('ws error');
  };

  const nextPath = path => {
    history.push(path);
    ws.current.close();
  };

  const rows = cards.map((item, i) => {
    const entry = item.map((cardInfo, j) => {
      return (
        <Col grid='2' key={j}>
          {!cardInfo.isOffBoard && (
            <Card
              info={cardInfo}
              ws={ws}
              gameId={gameState.gameId}
              currentPlayersTurn={gameState.currentPlayersTurn}
              cardId={gameState.cardId}
            >
              Hello world
            </Card>
          )}
        </Col>
      );
    });
    return (
      <Row key={i} className='row-space-between'>
        {entry}
      </Row>
    );
  });

  const renderBoard = () => {
    return (
      <Container full>
        <Row>
          <Col grid='6'>
            <div className='game-container'>{rows}</div>
          </Col>
          <Col grid='6'>
            <div className='game-info'>
              <Row className='row-style'>
                {gameState.playerTwo._id === gameState.currentPlayersTurn && (
                  <Col grid='4'>
                    <h2 style={{ color: '#ad62aa' }}>
                      Current Turn <Icon type='arrowRight' />
                    </h2>
                  </Col>
                )}
                <Col grid='4'>
                  <h1
                    className='game-username'
                    style={
                      gameState.playerTwo._id === gameState.currentPlayersTurn
                        ? { color: '#ad62aa' }
                        : null
                    }
                  >
                    {gameState.playerTwo.username}
                  </h1>
                </Col>
                <Col grid='4'>
                  <h1>Score: {gameState.playerTwoScore}</h1>
                </Col>
              </Row>
              <Row className='row-style'>
                {gameState.playerOne._id === gameState.currentPlayersTurn && (
                  <Col grid='4'>
                    <h2 style={{ color: '#ad62aa' }}>
                      Current Turn <Icon type='arrowRight' />
                    </h2>
                  </Col>
                )}
                <Col grid='4'>
                  <h1
                    style={
                      gameState.playerOne._id === gameState.currentPlayersTurn
                        ? { color: '#ad62aa' }
                        : null
                    }
                  >
                    {gameState.playerOne.username}
                  </h1>
                </Col>
                <Col grid='4'>
                  <h1>Score: {gameState.playerOneScore}</h1>
                </Col>
              </Row>
            </div>
          </Col>
        </Row>
      </Container>
    );
  };

  const renderLoadingScreen = () => {
    return (
      <Container className='full-page'>
        <Row className='row-style'>
          <h1>Welcome to the Waiting Room</h1>
        </Row>
        <Row className='row-style'>
          <h1>Please stay as you will load into a game shortly</h1>
        </Row>

        <Row className='row-style'>
          <Button round onClick={() => nextPath('/home')}>
            Go Back Home
          </Button>
        </Row>
      </Container>
    );
  };

  const renderGameOver = () => {
    return (
      <Container>
        <Row className='row-style'>
          <h1>GAME OVER</h1>
        </Row>
        <Row className='row-style'>
          <h3>
            {gameState.playerOne.username}'s Score: {gameState.playerOneScore}
          </h3>
        </Row>
        <Row className='row-style'>
          <h3>
            {gameState.playerTwo.username}'s Score: {gameState.playerTwoScore}
          </h3>
        </Row>
        <Row className='row-style'>
          <Button
            round
            onClick={() => {
              setScreenId('');
              ws.current.send(
                messageBuilder('Play Game', localStorage.getItem('id'))
              );
            }}
          >
            Go back into queue
          </Button>
        </Row>
        <Row className='row-style'>
          <Button
            round
            onClick={() => {
              nextPath('/home');
            }}
          >
            Go home
          </Button>
        </Row>
      </Container>
    );
  };

  const renderScreen = screenId => {
    switch (screenId) {
      case 'Game Board':
        return renderBoard();
      case 'Game Over':
        return renderGameOver();
      default:
        return renderLoadingScreen();
    }
  };

  return renderScreen(screenId);
};

export default Loadingpage;
