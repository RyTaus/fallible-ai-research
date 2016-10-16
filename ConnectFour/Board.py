class GameBoard:

    def __init__(self):
        self.height = 6
        self.width = 7

        self.board = [["-" for i in range(self.height)] for j in range(self.width)]
        self.pieces = ["X", "O"]
        self.turnState = 0
        self.lastPosition = [None, None]
        self.turnNumber = 0


    def increment_turn(self):
        self.turnNumber += 1

    def switch_turn(self):
        self.turnState = (self.turnState + 1) % 2

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
        self.set_coord(col, self.pieces[self.turnState])

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
        copy.turnNumber = self.turnNumber
        copy.pieces = self.pieces
        copy.turnState = self.turnState
        copy.lastPosition = self.lastPosition
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
        if boardCopy.is_valid_action(col):
            boardCopy.place_piece(col)
            boardCopy.board[boardCopy.lastPosition[0]][boardCopy.lastPosition[1]] = piece
        return boardCopy.has_won(piece)

    def check_for_trap(self, piece):
        otherPiece = {"X" : "O", "O" : "X"}
        opponentWins = [False for i in range(7)]
        for i in range(7):
            copy = self.copy()
            if copy.is_valid_action(i):
                copy.place_piece(i)
            opponentWins[i] = copy.will_win_on_col(i, otherPiece[copy.pieces[copy.turnState]])
        return opponentWins

    def in_bounds(self, loc):
        return loc[0] < 0 or loc[0] >= self.width or loc[1] < 0 or loc[1] >= self.height

    def get_value(self, d, init, piece):
        dic = {"X" : "O", "O" : "X"}
        amount = 0
        types = [(), (2, -1, -2), (3, 2, 1, -1), ()]
        for i in range(1, 4):
            x = init[0] + (i * d[0])
            y = init[1] + (i * d[1])
            if self.in_bounds((x, y)):
                break
            p = self.board[x][y]
            # print(p)
            if  p == piece:
                amount += 1
            elif p != "-":
                break
        matters = False

        l = types[amount]
        for v in l:
            x = init[0] + (v * d[0])
            y = init[1] + (v * d[1])
            if (x < 0 or x >= self.width) or (y < 0 or y >= self.height):
                pass
            elif (self.board[x][y] != dic[piece]):
                matters = True
            else:
                pass
        if (amount == 2 and matters):
            check = [-1, 1, 2]
            if self.in_bounds((init[0] + (-1 * d[0]), init[1] + (-1 * d[1]))) and self.in_bounds((init[0] + (2 * d[0]), init[1] + (2 * d[1]))):
                if sum([1 if board[init[0] + (v * d[0])][init[1] + (v * d[1])] == piece else 0 for v in check]) == 3:
                    return 3
        if (amount == 1 and matters):
            check = [-2, -1, 1]
            if self.in_bounds((init[0] + (-2 * d[0]), init[1] + (-2 * d[1]))) and self.in_bounds((init[0] + (1 * d[0]), init[1] + (1 * d[1]))):
                if sum([1 if board[init[0] + (v * d[0])][init[1] + (v * d[1])] == piece else 0 for v in check]) == 3:
                    return 3

        if amount == 3:
            return 3

        if matters:
            return amount
        return 0

    def rate_move(self, col, piece): # maybe add in a weight of time till reachable
        d = {"X" : "O", "O" : "X"}
        values = [0 for i in range(7)]
        values[0] = (5 - abs(3 - col)) / 3
        init = (col, self.height - self.board[col].count("-"))

        l = [(1, 0), (1, 1), (0, 1), (-1, 1), (-1, 0), (-1, -1), (0, -1), (1, -1)]
        for i in range(len(l)): 
            v = self.get_value(l[i], init, d[piece])
            if v:
                values[v] += 1        # find blocks
            v = self.get_value(l[i], init, piece)
            # print("block going :" + str(l[i]) + " = " + str(v))
            if v:
                values[v + 3] += 1           # find connections
        # print(values)
        return values

# b = GameBoard()
# b.place_piece(2)
# b.place_piece(2)
# b.place_piece(2)
# b.switch_turn()
# b.place_piece(4)
# b.place_piece(4)
# b.place_piece(4)
# b.print_board()

# b.rate_move(2, "O")
# b.place_piece(2)
# b.print_board()

# print(b.lastPosition)
