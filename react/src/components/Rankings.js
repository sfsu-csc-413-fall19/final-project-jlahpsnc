import React, { useState, useEffect } from 'react';
import { Card, Table } from 'flwww';
import axios from 'axios';

const Rankings = () => {
  const columns = ['Username', 'Score'];
  const [rankings, setRankings] = useState([]);

  const getAllRankings = () => {
    axios
      .get(`/rankings`)
      .then(res => {
        const players = JSON.parse(res.data.responseBody);
        setRankings(
          players.map(player => {
            return {
              Username: player.username,
              Score: player.highScore
            };
          })
        );
      })
      .catch(console.log);
  };

  useEffect(() => {
    getAllRankings();
  }, []);

  return (
    <div>
      <Card title='Rankings' padding='0'>
        <Table columns={columns} rows={rankings} className='rankings' />
      </Card>
    </div>
  );
};

export default Rankings;
