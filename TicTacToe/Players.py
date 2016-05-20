import random
from Board import GameBoard

class Player:
    def __init__(self, piece):
        self.species = "human"
        self.piece = piece

    def make_move(self, board):
        poss = board.poss_moves()
        coord = [5, 5]
        while not board.is_valid_move(coord):
            col = int(input("%ss row: " %board.pieces[board.turnNumber]))
            row = int(input("%ss col: " %board.pieces[board.turnNumber]))
            
            coord = [col, row]
            if not board.is_valid_move(coord):
                print("%ss invalid move" %board.pieces[board.turnNumber])

        return coord

    def update(self, reward, board):
        print()
        # reward = d[w]
        # print(self.species, " rewarded: ", reward)



class Persona(Player):
    def read_history(self):
        mem = dict()
        with open(self.fileName, "r") as file:
            split = []
            for line in file.readlines():
                split = line.split("_")
                split[2] = split[2][:-1]
                mem[split[0]] = (float(split[1]), float(split[2]))
        file.close()
        return mem

    def write_history(self):
        file = open(self.fileName, "w")
        for brd in self.memory:
            file.write("{}_{}_{}\n".format(brd, str(self.memory[brd][0])[:7], str(self.memory[brd][1])))
        file.close()

    def __init__(self, piece, fileName = "history.txt", e = .2, a = .3, g = .9, learning = True):
        self.fileName = fileName
        self.species = "Persona"
        self.memory = self.read_history()
        self.epsilon = e
        self.alpha = a
        self.gamma = g
        self.piece = piece
        self.prevMove = None
        self.prevBoard = GameBoard()
        self.learning = learning
        self.TIEWORTH = .5

    def update_Q(self, state, action, reward, nextState):
        Q = self.get_Q(state, action)

        moves = nextState.poss_moves()
        if (len(moves) == 0):
            maxNewQ = self.TIEWORTH
        else:
            maxNewQ = max([self.get_Q(nextState, a) for a in moves])

        newQ  = Q + self.alpha*(reward + self.gamma * maxNewQ - Q)

        newTimes = 1
        if (repr((state.to_string_one_line(), action)) in self.memory):
            newTimes = self.memory[repr((state.to_string_one_line(), action))][1] + 1

        self.memory[repr((state.to_string_one_line(), action))] = (newQ, newTimes)


    def get_Q(self, state, action):
        if repr((state.to_string_one_line(), action)) in self.memory:
            return self.memory[repr((state.to_string_one_line(), action))][0] # I CANT BELIEVE THIS WAS A 1
        else:
            self.memory[repr((state.to_string_one_line(), action))] = (1, 1)
            return 1

    def make_move(self, board):
        if (self.epsilon > random.random() and self.learning): # e-greedy exploration
            move = random.choice(board.poss_moves())
        else:
            moves = board.poss_moves()
            qs = [self.get_Q(board, move) for move in moves]

            maxQ = max(qs)

            if (qs.count(maxQ) >= 2):
                options = [i for i in range(len(moves)) if qs[i] == maxQ]
                index = random.choice(options)
            else:
                index = qs.index(maxQ)
            move = moves[index]

        self.prevBoard = board.copy_board()
        self.prevMove = move

        return move

    def update(self, reward, board):
        if self.prevMove != None:
            i = self.get_Q(self.prevBoard, self.prevMove)
            self.update_Q(self.prevBoard, self.prevMove, reward, board)
            # print(i, " -> ", self.get_Q(self.prevBoard, self.prevMove))



