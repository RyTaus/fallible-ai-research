class GameBoard:

    def __init__(self):
        self.board = [["-" for i in range(3)] for j in range(3)]
        self.pieces = ["O", "X"]
        self.turnNumber = 0

    def switch_turn(self):
        self.turnNumber = (self.turnNumber + 1) % 2

    def print_board(self):
        for i in range(3):
            print(self.board[i])

    def to_string_one_line(self):
        s = ""
        for i in range(3):
            for j in range(3):
                s = s + self.board[i][j]
        return s

    def set_coord(self, coord, piece):
        self.board[coord[0]][coord[1]] = piece

    def is_valid_move(self, coord):
        if (coord[0] in [0, 1, 2] and coord[1] in [0, 1, 2]):
            if (self.board[coord[0]][coord[1]] == "-"):
                return True

        return False

    def place_piece(self, coord):
        self.set_coord(coord, self.pieces[self.turnNumber])

    def has_won(self, piece):
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

    def poss_moves(self):
        valid_moves = []
        for i in range(3):
            for j in range(3):
                if self.board[i][j] == "-":
                    valid_moves.append([i, j])
        return valid_moves;

    def is_tie(self):
        for i in range(3):
            for j in range(3):
                if self.board[i][j] == "-":
                    return False

        return True

    def get_results(self):
        if self.is_tie():
            return "-"
        if self.has_won("O"):
            return "O"
        if self.has_won("X"):
            return "X"
        return None
