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
        <Row>
          <Col grid='10'>
            <Row>
              <h3>Replace with user.username</h3>
            </Row>
            <Row>
              <h3>Replace with user.highscore</h3>
            </Row>
          </Col>
          <Col grid='2'>
            <p>Replace with user ranking</p>
          </Col>
        </Row>
      </Card>
    </div>
  );
};

export default UserInfo;
