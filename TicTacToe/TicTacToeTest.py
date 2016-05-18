from TicTacToe import GameBoard

def test_is_valid_move():
    print("Testing is_valid_move...")
    gb1 = GameBoard()
    gb1.place_piece([1,1])
    if (not(gb1.is_valid_move([1,1]))):
        print(" Success")
    if (gb1.is_valid_move([1, 2])):
        print(" Success")
    if (not(gb1.is_valid_move([1,3]))):
        print(" Success")

def test_check_for_win():
    print("\nTesting check_for_win...")
    gb2 = GameBoard()
    gb2.place_piece([0,0])
    gb2.place_piece([1,0])
    gb2.place_piece([1,1])
    gb2.place_piece([2,0])
    gb2.place_piece([2,2])
    gb2.place_piece([2,1])
    gb2.print_board()

    if (gb2.check_for_win(gb2.pieces[gb2.turnNumber])):
        print("Success")

test_is_valid_move()
test_check_for_win()
