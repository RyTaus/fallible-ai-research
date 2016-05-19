from random import randint
from Board import GameBoard

class Persona:


    # this is where end of game / memory file stuff is
    def read_history(self, fileName):
        mem = dict()
        with open(fileName, "r") as file:
            split = []
            # print(file.read())
            for line in file.readlines():
                # print(line)
                split = line.split(" ")
                split[2] = split[2][:-1]
                print(split)
                mem[split[0]] = (float(split[1]), float(split[2]))
        file.close()
        print(mem)
        return mem


    def write_history(self, fileName):
        file = open(fileName, "w")
        for brd in self.memory:
            file.write(brd)
            file.write(" ")
            file.write(str(self.memory[brd][0]))
            file.write(" ")
            file.write(str(self.memory[brd][1]))
            file.write("\n")
        file.close()

    def add_board_to_memory(self, board, w):
        if board in self.memory:
            self.memory[board] = ((self.memory[board][0] * self.memory[board][1] + w) / (self.memory[board][1] + 1), self.memory[board][1] + 1)
        else:
            self.memory[board] = (w, 1)

    # this is where in game memory is
    def add_board(self, brdStrng):
        if brdStrng not in self.boardStates:
            self.boardStates.append(brdStrng)


    def __init__(self, t):
        self.memory = self.read_history("history.txt")
        self.currBoard = GameBoard()
        self.boardStates = [GameBoard().to_string_one_line()]
        self.piece = t

    # default operations of a "player"

    # def evaluate_options():
    #     UNRESEARCHEDBOARDVALUE = .5
    #     currWinner = None
    #     editBoard = GameBoard()
    #     editBoard.board = self.currBoard.board
    #     for i in range(3):
    #         for j in range(3):
    #             if (self.currBoard.is_valid_move((i, j))):
    #                 editBoard.make_move((i, j))




    def make_move(self):
        temp = (randint(0,2), randint(0,2))
        while not self.currBoard.is_valid_move(temp):
            temp = (randint(0,2), randint(0,2))
        return temp

    def update(self, board, pos):
        self.add_board(board.to_string_one_line())
        # if (board.to_string_one_line() != self.currBoard.to_string_one_line()):
        self.currBoard.board = board.board

    def end_game(self, board, w):
        TIEWORTH = .5
        d = {"-": TIEWORTH, "O": 1 if self.piece == w else 0, "X": 1 if self.piece == w else 0}

        self.add_board(board.to_string_one_line())
        [self.add_board_to_memory(b, d[w]) for b in self.boardStates]
        self.write_history("history.txt")
