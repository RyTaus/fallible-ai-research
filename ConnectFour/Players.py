from Board import GameBoard
import random
import math

class Player:
    def __init__(self, piece):
        self.species = "human"
        self.piece = piece

    def make_move(self, board):
        col = -1
        while not board.is_valid_action(col):
            col = int(input("%ss Col: " %board.pieces[board.turnNumber])) - 1
            
            if not board.is_valid_action(col):
                print("%ss invalid move" %board.pieces[board.turnNumber])

        return col

    def update(self, reward, board):
        print()

class RandomPlayer(Player):
    def __init__(self, piece):
        self.species = "random"
        self.piece = piece

    def make_move(self, board):
        return random.choice(board.valid_actions())

    def update(self, reward, board):
        pass

class Merlin(Player):
    def __init__(self, piece, fileName = "history.txt", e = .2, a = .3, g = .9, t = .5, learning = True):
        self.fileName = fileName
        self.species = "Merlin"
        self.piece = piece
        self.memory = self.read_history()
        self.learning = learning

        self.epsilon = e
        self.alpha = a
        self.gamma = g
        self.temperature = t

        self.prevMove = None
        self.prevBoard = GameBoard()


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
        for sa in self.memory:
            file.write("{}  {}  {}\n".format(sa, str(self.memory[sa][0]), str(self.memory[sa][1])))
        file.close()

    def make_move(self, board): # Temperature based
        if self.learning:
            actionList = board.valid_actions()
            actionValues = [self.get_Q(board, i) for i in actionList]
            buff = abs(min(actionValues))
            actionValues = [100 * math.e ** ((actionValues[i] + buff) / self.temperature) for i in actionValues]

            hat = list()
            for i in range(len(actionList)):
                for j in range(int(actionValues[i])):
                    hat.append(actionList[i])

            action = random.choice(hat)

        else:
            actionList = board.valid_actions()
            actionValues = [self.get_Q(board, i) for i in actionList]
            maxQ = max(actionValues)
            if (actionValues.count(maxQ) >= 2):
                options = [i for i in range(len(actionList)) if actionValues[i] == maxQ]
                index = random.choice(options)
            else:
                index = actionValues.index(maxQ)
            action = actionList[index]

        self.prevMove = action
        self.prevBoard = board.copy()
        return action

    def update_Q(self, state, action, reward, nextState):
        Q = self.get_Q(state, action)

        moves = nextState.poss_moves()
        maxNewQ = max([self.get_Q(nextState, a) for a in moves]) if len(moves) != 0 else 1

        newQ  = Q + self.alpha*(reward + self.get_gamma(state, action) * maxNewQ - Q)

        newTimes = 1
        if (repr((state.to_string_one_line(), action)) in self.memory):
            newTimes = self.memory[repr((state.to_string_one_line(), action))][1] + 1

        self.memory[repr((state.to_string_one_line(), action))] = (newQ, newTimes)

    def get_gamma(self, state, action):
        return self.gamma

    def get_Q(self, state, action):
        if repr((state.to_string_one_line(), action)) in self.memory:
            return self.memory[repr((state.to_string_one_line(), action))][0]
        else:
            self.memory[repr((state.to_string_one_line(), action))] = (1, 1)
            return 1

    def update(self, reward, board):
        if self.prevMove != None:
            self.update_Q(self.prevBoard, self.prevMove, reward, board)
