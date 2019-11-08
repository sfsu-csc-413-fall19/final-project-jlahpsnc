# OP Memory

## Opening projects
- Open this react directory with vs code.
- Open a terminal in vs code an run `npm i`
- Run `npm start`
- Open the spark directory with intelij
- Start the spark app

## Todos
- Demo making a client request from the FE to the BE using axios
- Add get parameters to send data to a get endpoint
- Add post parameters to send more complicated data to the back end
- Use a dto to send some structured data from server to client
- Use a stateful variable on the client to display that data

## FE 
- Create a component and import to use it another file
- Do a conditional render
- Use a function to calculate what to render

## UI Mock-up
### Landing Screen
![Image of screen 1](https://github.com/sfsu-csc-413-fall19/final-project-jlahpsnc/blob/master/wireframes/screen_1.png)
### Home Screen
![Image of screen 2](https://github.com/sfsu-csc-413-fall19/final-project-jlahpsnc/blob/master/wireframes/screen_2.png)
### Loading Screen
![Image of screen 3](https://github.com/sfsu-csc-413-fall19/final-project-jlahpsnc/blob/master/wireframes/screen_3.png)
### Game Screen A
![Image of screen 4a](https://github.com/sfsu-csc-413-fall19/final-project-jlahpsnc/blob/master/wireframes/screen_4a.png)
### Game Screen B
![Image of screen 4b](https://github.com/sfsu-csc-413-fall19/final-project-jlahpsnc/blob/master/wireframes/screen_4b.png)

## Description
- Our 2D web game will be a memory based card game. The cards will be laid faced down in a 5x5 grid, and players will take turns finding pairs. In order to find a pair, a player must first pick one card. That card will flip over and be revealed. The player must then attempt to pick the other matching card. If a player picks a matching pair, they go again. Once a player fails to pick a matching pair, their turn is over, and the other player's turn begins.

## Extra features planned (If Time)
- Each player starts the game with 3 "action" cards. These cards will have various effects (such as: suffle the board, reveal 2 cards, etc). At the begginning of a player's turn, they may choose to play an action card. This card's action will take place, then the player will resume their turn normally.

## Team-member tasks
- Pedro: Backend - Matching algorithm, player authentication, database
- Jorge: Backend - Point system, matchmaking, database
- Ashley: Front end - Pixel art for cards along with other game visuals
- Niko: Front end - Card and gameboard layout, animations
