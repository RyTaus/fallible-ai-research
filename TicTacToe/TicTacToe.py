class GameBoard:

    def __init__(self):
        self.board = [[None for i in range(3)] for j in range(3)]
        self.pieces = ["O", "X"]
        self.turnNumber = 0

    def increment_board(self):
        self.turnNumber += 1

    def print_board(self):
        for i in range(3):
            print(self.board[i])

    def set_coord(self, coord, piece):
        self.board[coord[0]][coord[1]] = piece

    def is_valid_move(self, coord):
        if (coord[0] and coord[1]  in [0, 1, 2]):
            if (self.board[coord[0]][coord[1]] == None):
                return True

        return False

    def place_piece(self, coord):
        if (self.is_valid_move(coord)):
            if (self.turnNumber % 2 == 0):
                self.set_coord(coord, self.pieces[0])
                self.increment_board()
            else:
                self.set_coord(coord, self.pieces[1])
                self.increment_board()    
        return None

gb = GameBoard()

print(gb.is_valid_move([1, 3]))

gb.place_piece([1,1])

gb.place_piece([1,2])

print(gb.turnNumber)

gb.print_board()
