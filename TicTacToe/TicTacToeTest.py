from TicTacToe import GameBoard

def test_is_valid_move():
    print("Testing is_valid_move...")
    gb = GameBoard()
    gb.place_piece([1,1])
    if (not(gb.is_valid_move([1,1]))):
        print(" Success")
    if (gb.is_valid_move([1, 2])):
        print(" Success")
    if (not(gb.is_valid_move([1,3]))):
        print(" Success")

def test_has_won():
    print("\nTesting has_won...")
    gb = GameBoard()
    gb.place_piece([0,0])
    gb.place_piece([1,0])
    gb.place_piece([1,1])
    gb.place_piece([2,0])
    gb.place_piece([2,2])
    gb.place_piece([2,1])
    if (gb.has_won(gb.pieces[gb.turnNumber])):
        print("Success")

test_is_valid_move()
test_has_won()
