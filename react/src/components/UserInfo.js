import React from 'react';
import { Card, Row, Col } from 'flwww';
// import axios from 'axios';

const UserInfo = () => {
  // const [user, setUser] = useState({});
  // const [id, setId] = useState('');

  // const getPlayerInfo = () => {
  //   axios
  //     .post(`/playerinfo?playerId='${id}`, id)
  //     .then()
  //     .catch();
  // };

  //load everything
  // useEffect();

  return (
    <div>
      <Card>
        <Col grid='offset-2 8'>
          <Row style={{ justifyContent: 'center' }}>
            <h1>EpicGamer6969</h1>
          </Row>
        </Col>

        <Row className='row-space-around'>
          <Col grid='5'>
            <h3>High Score: 15000</h3>
          </Col>
          <Col grid='4'>
            <h3>Ranking: #1</h3>
          </Col>
        </Row>
      </Card>
    </div>
  );
};

export default UserInfo;
