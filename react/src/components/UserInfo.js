import React, { useState, useEffect } from 'react';
import { Card, Row, Col } from 'flwww';
import axios from 'axios';

const UserInfo = props => {
  const [user, setUser] = useState({});

  const getUserInfo = id => {
    axios
      .get(`/playerInfo?playerId=${id}`)
      .then(res => {
        setUser(res.data);
      })
      .catch(console.log);
  };

  useEffect(() => {
    getUserInfo(props.id);
  }, []);

  return (
    <div>
      {user !== {} && (
        <Card>
          <Col grid='offset-2 8'>
            <Row style={{ justifyContent: 'center' }}>
              <h1>{user.username}</h1>
            </Row>
          </Col>

          <Row className='row-space-around'>
            <Col grid='5'>
              <h3>High Score: {user.highscore}</h3>
            </Col>
            <Col grid='4'>
              <h3>Ranking: #1</h3>
            </Col>
          </Row>
        </Card>
      )}
    </div>
  );
};

export default UserInfo;
