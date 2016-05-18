from random import randint
from Board import GameBoard

class Persona:


    # this is where end of game / memory file stuff is
    def read_history(self, fileName):
        mem = dict()
        with open(fileName, "a+") as ins:
            split = []
            for line in ins:
                split = line.split(" ")
                mem[split[0]] = (float(mem[1]), float(mem[2]))
        ins.close()
        return mem


    def write_history(self, fileName):
        file = open(fileName, "w")
        for brd in self.memory:
            print(brd)
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
            print(brdStrng)
            self.boardStates.append(brdStrng)


    def __init__(self):
        self.memory = self.read_history("history.txt")
        self.boardStates = [GameBoard().to_string_one_line()]


    def make_move(self):
        temp = (randint(0,2), randint(0,2))
        while not self.currBoard.is_valid_move(temp):
            temp = (randint(0,2), randint(0,2))
        return temp

    def update(self, board, pos):
        self.add_board(board.to_string_one_line())
        self.currBoard = board

    def end_game(self, board, w):
        print(self.boardStates)
        self.add_board(board.to_string_one_line())
        [self.add_board_to_memory(b, w) for b in self.boardStates]
        self.write_history("history.txt")
