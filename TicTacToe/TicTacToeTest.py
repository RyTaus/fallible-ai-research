from TicTacToe import GameBoard

gb = GameBoard()

print(gb.is_valid_move([1, 3]))

gb.place_piece([1,1])

gb.place_piece([1,2])

print(gb.turnNumber)

gb.print_board()
