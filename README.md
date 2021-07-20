## Getting Started

- This is final project of network application programming at NSYSU 1092 semester
- The team consist of two student, B073040030 B073040031

## Folder Structure
```
.
├── Behaviour.java
├── Client.java
├── GroundController.java
├── GroundView.fxml
├── img
│   ├── monkey.png
│   ├── swords.png
│   └── tetris.png
├── javafx-sdk-16
│   └── lib
│   └── lib
├── LoginController.java
├── LoginView.fxml
├── Pattern.java
├── pattern.py
├── pattern.txt
├── runServer.sh
├── run.sh
└── Server.java
```
## How to execute:

If your java version is relatively new, you should make sure you have downloaded `javafx-sdk-16` dependencies
Then `chmod 777 run.sh && chmod 777 runServer.sh`

`./runServer.sh` is to run server of the game, and then we need two terminal to run client of two players
`./run.sh`
`./run.sh`

After the two command line, it will jump 2 client ui to login, our default name set to a and b,
 which means you have to enter a in a window, and enter b in another window.
All done, the game will start. 

## Usage

- As classic Tetris, keyboard up down left right to control, then make them fill the horizontal line to earn line
You can just play the game to make more score, we are just not implement attck characteristic for the project 
, so now cannot beat the opponent yet.
- Our project can move the rectangle slowly, and you can also see enemy's move.
Down the rectangle can earn score, while remove a filled line can earn line.

- How to game over? Any side of player fail to remove line and pile up to the ceiling, will cause game over.
So if you don't want to lose, keep the line being remove.
Having fun, please gently treat the game, it's sort of fragile.

## Design

- Our origin thought is revive our memories of childhood, which is Tetris Battle on Facebook where are many inspiration come from
- But in practice, we encounter very difficulties. so we did some compromise.

### How to generate pattern(rectangle) of the game?
    We generate a pattern pool in advanced by `pattern.py` to `pattern.txt`
    The file will be exist in client, and server is responsible generate the seed
    to tell client which the pattern should choose this game.
### How to manage the game?
    There is a thread concurrently in server, to monitor how many players is logged in
    If there are two players connected, server will initialize the game tell both players
    of enemy name, gaming seed, and stand by receiveing the client move
### Why need receive client move?
    Because the client play the game local, but need to know the enemy's situation.
    So we put the enemy playground right of the window, while mine is at right.
    The gaming seed will make two players have the same pattern order,
    But as they play, they won't always in the same situation.
    Client will maintain two serial number to maintain the progress, 
    make you can clear see two players' playing situation.

```
client A            server           client B

start UI                             start UI
enter name  ------>       <-------  enter name
                thread A
                    thread B
                
                Game is ready
name of B   <------       ------->  name of A
game seed                           game seed

print ok                            print ok
open game field                     open game field

play move   ------>
                          -------> receive to display on right
                          <------- play move
enemy move  <------
-------------------Gameing process--------------------
                    until who die
If any side of player has no space to 
```
