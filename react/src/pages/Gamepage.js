import React from 'react';
import { Container, Card, Row, Col } from 'flwww';

const Gamepage = () => {
  const cards = [[], [], [], [], []]; // user defined length

  for (var i = 0; i < 5; i++) {
    for (var j = 0; j < 5; j++) {
      cards[i].push(j);
    }
  }

  const rows = cards.map((item, i) => {
    const entry = item.map((element, j) => {
      return (
        <Col grid='2' key={j}>
          <Card
            style={{ height: '100px' }}
            onClick={() => console.log('A card was pressed')}
          >
            Hello World
          </Card>
        </Col>
      );
    });
    return (
      <Row key={i} className='gameRow'>
        {entry}
      </Row>
    );
  });

  return (
    <Container>
      <Row className='row-style'>
        <Col grid='4'>
          <h1>Player 1: Cruz</h1>
        </Col>
        <Col grid='4'>
          <h1>Score: 123948012</h1>
        </Col>
      </Row>
      <div className='gameContainer'>{rows}</div>
      <Row className='row-style'>
        <Col grid='4'>
          <h1>Player 1: Cruz</h1>
        </Col>
        <Col grid='4'>
          <h1>Score: 123948012</h1>
        </Col>
      </Row>
    </Container>
  );
};

export default Gamepage;
