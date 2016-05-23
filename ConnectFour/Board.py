class GameBoard:

    def __init__(self):
        self.height = 6
        self.width = 7

        self.board = [["-" for i in range(self.height)] for j in range(self.width)]
        self.pieces = ["X", "O"]
        self.turnNumber = 0
        self.lastPosition = [None, None]


    def switch_turn(self):
        self.turnNumber = (self.turnNumber + 1) % 2

    def print_board(self):
        board = ""
        for i in range(self.height):
            for j in range(self.width):
                board += self.board[j][self.height - 1 - i]
            board +="\n"

        print(board)

    def to_string_one_line(self):
        s = ""
        for i in range(self.height):
            for j in range(self.width):
                s = s + self.board[j][i]
        return s

    def set_coord(self, col, piece):
        rowCoord = self.height - self.board[col].count("-")
        self.lastPosition = (col, rowCoord)
        self.board[col][rowCoord] = piece

    def place_piece(self, col):
        self.set_coord(col, self.pieces[self.turnNumber])

    def valid_actions(self):
        validCols = []
        for col in range(7):
            if self.board[col].count("-") > 0:
                validCols.append(col)
        return validCols

    def is_valid_action(self, col):
        return col in self.valid_actions()

    def is_tie(self):
        for col in self.board:
            if col.count("-") > 0:
                return False
        return True

    def has_won_vertically(self, piece):
        col = self.lastPosition[0]
        row = self.lastPosition[1]

        rows = [row - 1, row - 2, row - 3]
        if any([r < 0 for r in rows]):
            return False
        return all([self.board[col][r] == self.board[col][row] for r in rows])

    def has_won_horizontally(self, piece):
        col = self.lastPosition[0]
        row = self.lastPosition[1]

        cols = [[col - 3, col - 2, col - 1], [col - 2, col - 1, col + 1], [col - 1, col + 1, col + 2], [col + 1, col + 2, col + 3]]

        for cl in cols:
            if any([c < 0 or c >= self.width for c in cl]):
                pass
            else:
                if all([self.board[c][row] == self.board[col][row] for c in cl]):
                    return True
        return False

    def has_won_diagonallyBLTR(self, piece):
        col = self.lastPosition[0]
        row = self.lastPosition[1]
        p = self.board[col][row]

        c = col
        r = row

        while c > 0 and r > 0 and self.board[c - 1][r - 1] == p:
            c -= 1
            r -= 1

        if (c + 4) >= self.width or (r + 4) >= self.width:
            return False

        return (p == self.board[c][r] == self.board[c + 1][r + 1] == self.board[c + 2][r + 2] == self.board[c + 3][r + 3])


    def has_won_diagonallyTLBR(self, piece):
        col = self.lastPosition[0]
        row = self.lastPosition[1]
        p = self.board[col][row]

        c = col
        r = row

        while c > 0 and r < (self.height - 1) and self.board[c - 1][r + 1] == p:
            c -= 1
            r += 1

        if (c + 4 >= self.width) or (r - 4 < 0):
            return False

        return (p == self.board[c][r] == self.board[c + 1][r - 1] == self.board[c + 2][r - 2] == self.board[c + 3][r - 3])

    def has_won_diagonally(self, piece):
        return self.has_won_diagonallyTLBR(piece) or self.has_won_diagonallyBLTR(piece)


    def has_won(self, piece):
        return self.has_won_vertically(piece) or self.has_won_horizontally(piece) or self.has_won_diagonally(piece)

    def copy(self):
        copy = GameBoard()
        for i in range(self.height):
            for j in range(self.width):
                copy.board[j][i] = self.board[j][i]
        return copy

    def get_results(self):
        if self.has_won("ok"):
            return self.board[self.lastPosition[0]][self.lastPosition[1]]
        if self.is_tie():
            return "-"

        return None

    def will_win_on_col(self, col, piece):
        boardCopy = self.copy()
        boardCopy.place_piece(col)
        return boardCopy.has_won(piece)

    def cocks_to_block(self, piece):
        cocksToBlock = [False] * 7
        for i in range(7):
            if self.is_valid_action(i):
                cocksToBlock[i] = (self.will_win_on_col(i, piece))
        return cocksToBlock
