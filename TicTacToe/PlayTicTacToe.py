from Board import GameBoard
from Persona import Persona
from Player import Player

def play_tic_tac_toe():

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


    while (not gb.has_won(gb.pieces[gb.turnNumber]) and not gb.is_tie()):
        gb.switch_turn()
        gb.print_board()
        print("\n")

        if(player[gb.turnNumber] == "persona"):
            coord = activePlayer[gb.turnNumber].make_move()
        else:
            coord = activePlayer[gb.turnNumber].make_move(gb)

        gb.place_piece(coord)

    gb.print_board()

    if gb.is_tie():
        print("Xs and Os Tied!")
    else:
        print("%ss Won the game!" %gb.pieces[gb.turnNumber])

play_tic_tac_toe()
