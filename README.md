# Sudoku Game

## Welcome! 

This is a sudoku game built with Java.

## Gameplay

### What Randomized New Game Looks Like
![newgame](images/newGame.PNG)

### Game Win + New Game GIF
![gameplay](images/gamePlay.gif)

## How to Play

The rules are the same as any good ol' sudoku rules.\
You can read them [here](https://sudoku.com/how-to-play/sudoku-rules-for-complete-beginners/)!

It's a fun brain-teaser for when you want to challenge yourself in logical thinking :)


## How to Launch

Get a copy of the repo, then add javafx jar files in /lib folder to your run configurations.

Detailed tutorial in step 3 of [this site](https://javabook.bloomu.edu/setupjavafx.html).

## General Project Structure

VIEW: sudoku.userinterface \
except for its logic subfolder, which is CONTROLLER.

MODEL: sudoku.problemdomain \
It holds data of the game as well as the game grid and game state.

CONTROLLER: sudoku.userinterface.logic.ControlLogic \
ControlLogic uses methods from sudoku.computationlogic to generate new game boards and check if game is complete or not.

The application entry point is the Main class, using main function from SudokuApplication class.

## Sudoku Algorithms

The "Simple Solving Algorithm" is the algorithm used in this project to check if a given partially-emptied 
sudoku board is solvable or not.

For further information and learning material in different sudoku solving algorithms, look [here](https://pi.math.cornell.edu/~mec/Summer2009/meerkamp/Site/Solving_any_Sudoku_I.html)!

## Enjoy!

![github logo](/images/githublogo.jpg)