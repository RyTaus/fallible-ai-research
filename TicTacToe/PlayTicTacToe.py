from Board import GameBoard

def play_tic_tac_toe():

    gb = GameBoard()
    coord = [3,3]

    while not gb.has_won(gb.pieces[gb.turnNumber]):
        gb.switch_turn()
        gb.print_board()

        while not gb.is_valid_move(coord):
            xCoord = input("%ss please input an x coordinate " %gb.pieces[gb.turnNumber])
            yCoord = input("%ss please input an y coordinate " %gb.pieces[gb.turnNumber])
            xCoord = int(xCoord)
            yCoord = int(yCoord)
            coord = [xCoord, yCoord]
            if not gb.is_valid_move(coord):
                print("%ss did not input a valid move, please input another coordinate..." %gb.pieces[gb.turnNumber])

        gb.place_piece(coord)

    gb.print_board()
    print("%ss Won the game!" %gb.pieces[gb.turnNumber])

play_tic_tac_toe()
