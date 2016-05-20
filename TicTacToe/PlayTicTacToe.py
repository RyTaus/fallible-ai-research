from Board import GameBoard
from Players import Persona
from Players import Player
import random

gb = GameBoard()
player = ["", ""]
player[0] = input("Is O a person or a persona? ")
player[1] = input("Is X a person or a persona? ")

activePlayer = [None, None]

if player[0] == "persona":
    activePlayer[0] = Persona("O")
else:
    activePlayer[0] = Player("O")

if player[1] == "persona":
    activePlayer[1] = Persona("X", learning = False)
else:
    activePlayer[1] = Player("X")

def play_tic_tac_toe():

    gb = GameBoard()
    gb.turnNumber = random.choice([0, 1])
    # gb.print_board()

    while True:
        # print()
        currPlayer, otherPlayer = activePlayer[gb.turnNumber], activePlayer[(gb.turnNumber + 1) % 2]
        coord = currPlayer.make_move(gb)
        gb.place_piece(coord)

        results = gb.get_results()
        if results == currPlayer.piece:
            currPlayer.update(1, gb)
            otherPlayer.update(-1, gb)
            break
        if results == "-": # tie game
            currPlayer.update(0.5, gb)
            otherPlayer.update(0.5, gb)
            break
        otherPlayer.update(0, gb)



        if player[0] != "persona" or player[1] != "persona":
            gb.print_board()

        gb.switch_turn()
    # gb.print_board()




for i in range(30000):
    # print(i)
    play_tic_tac_toe()
activePlayer[1].write_history()
