from random import randint
from random import random
from Board import GameBoard
import math

class Persona:
    def copy_board(self, board):
        tempBoard = GameBoard()
        for i in range(3):
            for j in range(3):
                tempBoard.board[i][j] = board.board[i][j]
        return tempBoard.board

    def rev_board(self, board):
        d = {"-": "-", "O": "X", "X": "O"}
        s = ""
        for c in board:
            s = s + d[c]
        return s

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
                # print(split)
                mem[split[0]] = (float(split[1]), float(split[2]))
        file.close()
        # print(mem)
        return mem


    def write_history(self, fileName):
        file = open(fileName, "w")
        for brd in self.memory:
            file.write(brd)
            file.write(" ")
            file.write(str(self.memory[brd][0])[:7])
            file.write(" ")
            file.write(str(self.memory[brd][1]))
            file.write("\n")
        file.close()

    def add_board_to_memory(self, board, w):
        if board in self.memory:
            self.memory[board] = ((self.memory[board][0] * self.memory[board][1] + w) / (self.memory[board][1] + 1), self.memory[board][1] + 1)
        else:
            self.memory[board] = (w, 1)
        
        rBoard = self.rev_board(board)

        d = {0: 1, 1: 0, self.TIEWORTH: self.TIEWORTH}

        if rBoard in self.memory:
            # self.memory[rBoard] = ((self.memory[rBoard][0] * self.memory[rBoard][1] + d[w]) / (self.memory[rBoard][1] + 1), self.memory[rBoard][1] + 1)
            t = True
        else:
            self.memory[rBoard] = (d[w], 1)

    # this is where in game memory is
    def add_board(self, brdStrng):
        if brdStrng not in self.boardStates:
            self.boardStates.append(brdStrng)


    def __init__(self, t):
        self.memory = self.read_history("history.txt")
        self.currBoard = GameBoard()
        self.boardStates = [GameBoard().to_string_one_line()]
        self.piece = t
        self.UNRESEARCHEDBOARDVALUE = .5
        self.TIEWORTH = .7

    # default operations of a "player"
    def Q(self, board):
        if board in self.memory:
            prevQ = self.memory[board][0]
        else:
            prevQ = self.UNRESEARCHEDBOARDVALUE
        return prevQ


    def evaluate_option(self, board, temp):
        top = math.e ** (self.Q(board) / temp)
        return top

    def evaluate_options(self):
        Temperature = 5
        currWinner = None
        editBoard = GameBoard()
        editBoard.board = self.copy_board(self.currBoard)
        score = 0
        strng = ""

        prob = list()

        for i in range(3):
            for j in range(3):
                if (self.currBoard.is_valid_move((i, j))):
                    editBoard.board[i][j] = self.piece
                    strng = editBoard.to_string_one_line()
                    score = 0
                    
                    prob.append(((i, j), self.evaluate_option(strng, Temperature)))


                    editBoard.board = self.copy_board(self.currBoard)
        bot = 0
        for dub in prob:
            bot += dub[1]
        for dub in range(len(prob)):
            # print(prob[dub])
            if dub == 0:
                prob[dub] = (prob[dub][0], prob[dub][1] / bot)
            else :
                prob[dub] = (prob[dub][0], prob[dub][1] / bot + prob[dub - 1][1])

        rand = random()
        for dub in prob:
            if (rand < dub[1]):
                return dub[0]
        print("FUCK")
        return (0, 0)

    def make_move(self):
        return self.evaluate_options()

    def update(self, board, pos):
        self.add_board(board.to_string_one_line())
        if (board.to_string_one_line() != self.currBoard.to_string_one_line()):
            self.currBoard.board = self.copy_board(board)

    def end_game(self, board, w):
        d = {"-": self.TIEWORTH, "O": 1 if self.piece == w else 0, "X": 1 if self.piece == w else 0}

        self.add_board(board.to_string_one_line())
        [self.add_board_to_memory(b, d[w]) for b in self.boardStates]
        self.write_history("history.txt")
