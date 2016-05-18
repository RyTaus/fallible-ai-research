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

    def check_for_win(self, piece):
        for i in range(3):
            if (self.board[i][0] == piece and self.board[i][1] == piece and self.board[i][2] == piece):
                return True
            if (self.board[0][i] == piece and self.board[1][i] == piece and self.board[2][i] == piece):
                return True
        if (self.board[0][0] == piece and self.board[1][1] == piece and self.board[2][2] == piece):
            return True
        if (self.board[2][0] == piece and self.board[1][1] == piece and self.board[0][2] == piece):
            return True

        return False
