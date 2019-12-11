import React from 'react';
import { Container, Row, Button, Col } from 'flwww';
import { NavLink, useHistory } from 'react-router-dom';
import Card from '../components/Card';

const wsSession = new WebSocket('ws://localhost:1234/wsLoading');
const Loadingpage = () => {
  let history = useHistory();
  const [screenId, setScreenId] = React.useState('');
  const [cards, setCards] = React.useState([]);
  const [gameState, setGameState] = React.useState({});
  const ws = React.useRef(wsSession);

  const messageHandler = message => {
    // debugger;
    const jsonBody = JSON.parse(message.responseBody);
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
        }, 2000);
        break;
      case 'Update Game':
        console.log('THE GAME HAS BEEN UPDATED. NO PRINTING');
        setGameState(jsonBody);
        setCards(jsonBody.gameBoard.boardLayout);
        break;
      case 'Game Over':
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
    ws.current.onclose = function() {}; // disable onclose handler first
    console.log('connection closed');
    ws.close();
  };

  ws.current.onclose = () => {
    console.log('connection closed');
  };

  ws.current.onclose = () => {
    console.log('connection closed');
  };

  ws.current.onerror = () => {
    console.log('ws error');
  };

  const nextPath = path => {
    history.push(path);
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
      <Row key={i} className='gameRow'>
        {entry}
      </Row>
    );
  });

  const renderBoard = () => {
    return (
      <Container>
        <Container>
          <Row className='row-style'>
            <Col grid='4'>
              <h1>Player 2: {gameState.playerTwo.username}</h1>
            </Col>
            <Col grid='4'>
              <h1>Score: {gameState.playerTwoScore}</h1>
            </Col>
          </Row>
          <div className='gameContainer'>{rows}</div>
          <Row className='row-style'>
            <Col grid='4'>
              <h1>Player 1: {gameState.playerOne.username}</h1>
            </Col>
            <Col grid='4'>
              <h1>Score: {gameState.playerOneScore}</h1>
            </Col>
          </Row>
        </Container>
      </Container>
    );
  };

  const renderLoadingScreen = () => {
    return (
      <Container className='full-page'>
        <h1>Welcome to the Waiting Room</h1>
        <h1>Please stay as you will load into a game shortly</h1>
        <Row className='row-style'>
          <Button outlined round type='danger' onClick={() => nextPath('/')}>
            {/* <a href='/loading'>Go to loading page</a> */}
            go to start page
          </Button>
          <Button
            outlined
            round
            type='danger'
            onClick={() => nextPath('/home')}
          >
            {/* <NavLink to='/home'>go to home screen</NavLink> */}
            go to home screen
          </Button>
          <NavLink to='/'>Go to loading</NavLink>
        </Row>
      </Container>
    );
  };

  const renderScreen = screenId => {
    switch (screenId) {
      case 'Game Board':
        return renderBoard();
      default:
        return renderLoadingScreen();
    }
  };

  return renderScreen(screenId);
};

export default Loadingpage;
