class GameBoard:

    def __init__(self):
        self.board = [[None for i in range(3)] for j in range(3)]
        self.pieces = ["O", "X"]
        self.turnNumber = 0

    def increment_board(self):
        self.turnNumber = (self.turnNumber + 1) % 2

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
            self.set_coord(coord, self.pieces[self.turnNumber])
            self.increment_board()

gb = GameBoard()

print(gb.is_valid_move([1, 3]))

gb.place_piece([1,1])

gb.place_piece([1,2])

print(gb.turnNumber)

gb.print_board()
