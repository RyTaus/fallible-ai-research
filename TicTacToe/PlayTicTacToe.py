from Board import GameBoard
from Persona import Persona
from Player import Player

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
    activePlayer[1] = Persona("X")
else:
    activePlayer[1] = Player("X")

def play_tic_tac_toe():

    gb = GameBoard()

    while (not gb.has_won(gb.pieces[gb.turnNumber]) and not gb.is_tie()):
        gb.switch_turn()
        # gb.print_board()
        # print("\n")

        if(player[gb.turnNumber] == "persona"):
            coord = activePlayer[gb.turnNumber].make_move()
            activePlayer[gb.turnNumber].update(gb, None)
        else:
            coord = activePlayer[gb.turnNumber].make_move(gb)

        gb.place_piece(coord)

    # gb.print_board()

    if gb.is_tie():
        # print("Xs and Os Tied!")
        activePlayer[1].end_game(gb, "-")
    else:
        # print("%ss Won the game!" %gb.pieces[gb.turnNumber])
        activePlayer[1].end_game(gb, gb.pieces[gb.turnNumber])

for i in range(2000):
    play_tic_tac_toe()
