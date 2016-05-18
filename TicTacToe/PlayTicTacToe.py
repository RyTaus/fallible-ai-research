from Board import GameBoard

def play_tic_tac_toe():

    gb = GameBoard()

    while not gb.has_won(gb.pieces[gb.turnNumber]):
        gb.switch_turn()
        gb.print_board()
        xCoord = input("%ss please input an x coordinate " %gb.pieces[gb.turnNumber])
        yCoord = input("%ss please input an y coordinate " %gb.pieces[gb.turnNumber])
        xCoord = int(xCoord)
        yCoord = int(yCoord)
        coord = [xCoord, yCoord]
        gb.place_piece(coord)

    gb.print_board()
    print("%ss Won the game!" %gb.pieces[gb.turnNumber])
    
play_tic_tac_toe()
