import Players
from Board import GameBoard
import random
import math

def play(numberOfGames):
    p1 = Players.Player("O")
    p2 = Players.Player("X")
    players = [p1, p2]

    for i in range(numberOfGames):
        gb = GameBoard()
        while True:
            print(gb.valid_actions())
            gb.print_board()
            move = players[gb.turnNumber].make_move(gb)
            gb.place_piece(move)
            if (gb.get_results() != None):
                break
            gb.switch_turn()

def train(numberOfGames):
    gb = GameBoard()
    p1 = Players.Merlin("O")
    p2 = Players.Merlin("X")
    players = [p1, p2]

    for i in range(numberOfGames):
        if i % 100 == 0:
            print(i)
        gb = GameBoard()
        gb.turnNumber = random.choice([0, 1])
        while True:
            # gb.print_board()
            active, nextP = players[gb.turnNumber], players[(gb.turnNumber + 1) % 2]
            move = active.make_move(gb)
            gb.place_piece(move)

            r = gb.get_results()
            # print(r)

            if r == "O" or r == "X":
                active.update(1, gb)
                nextP.update(-1, gb)
                break
            if r == "-":
                active.update(0, gb)
                nextP.update(0, gb)
                break
            active.update(0, gb)
            nextP.update(0, gb)
            gb.switch_turn()
    p2.write_history()


def play_vs_merlin(numberOfGames):
    p1 = Players.Player("O")
    p2 = Players.Merlin("X", learning = False)
    players = [p1, p2]

    for i in range(numberOfGames):
        gb = GameBoard()
        gb.turnNumber = random.choice([0, 1])
        while True:
            gb.print_board()
            move = players[gb.turnNumber].make_move(gb)
            gb.place_piece(move)
            gb.switch_turn()
            if (gb.get_results() != None):
                break

def main():
    mode = int(input(" 1: Player vs Player \n 2: Player vs Merlin \n 3: Training\n" ))
    numGames = int(input("input the number of requested games\n"))

    if mode == 1:
        play(numGames)
    elif mode == 2:
        play_vs_merlin(numGames)
    elif mode == 3:
        train(numGames)

main()
