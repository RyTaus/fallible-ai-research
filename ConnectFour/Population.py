import random
from Chromosome import Chromosome
from Board import GameBoard
import Players
from operator import itemgetter, attrgetter, methodcaller

class Population:

    def __init__(self):
        self.populationSize = 10
        self.chromosomes = [Chromosome() for i in range(self.populationSize)]
        self.best_chromosome_seen = [None]
        self.time_best_unchanged = 0
        self.time_stayed_on_top = 0
        self.killThreshold = .4
        self.mateThreshold = .3
        self.mutateThreshold = .4
        self.mateAmount = .4


    def assess(self, other):
        for c1 in self.chromosomes:
            for c2 in other.chromosomes:
                # print("new battle")
                for games in range(10):
                    p1 = Players.Chromo("O", c1)
                    p2 = Players.Chromo("X", c2)
                    players = [p1, p2]
                    gb = GameBoard()
                    while True:
                        active, nextP = players[gb.turnState], players[(gb.turnState + 1) % 2]
                        move = active.make_move(gb)
                        # print(move)
                        # gb.print_board()
                        gb.place_piece(move)
                        gb.increment_turn()
                        r = gb.get_results()

                        if r == "O":
                            # gb.print_board()
                            c1.update(1)
                            c2.update(-1)
                            break
                        if r == "X":
                            # gb.print_board()
                            c1.update(-1)
                            c2.update(1)
                            break
                        if r == "-":
                            c1.update(.2)
                            c2.update(.2)
                            break
                        gb.switch_turn()

    def sort(self):
        self.chromosomes = sorted(self.chromosomes, key = attrgetter('fitness'))


    def kill(self):
        end = int(len(self.chromosomes) * self.killThreshold)
        for i in range(end):
            self.chromosomes.remove(self.chromosomes[i])


    def mate(self):
        temp = list()
        start = int(len(self.chromosomes) * (1 - self.mateThreshold))
        for i in range(int(self.mateAmount * len(self.chromosomes))):
            l = [self.chromosomes[i] for i in range(start, len(self.chromosomes))]
            mom = random.choice(l)
            l.remove(mom)
            dad = random.choice(l)
            temp.append(mom.mate(dad))
        for c in temp:
            self.chromosomes.append(c)

    def refresh(self):
        while len(self.chromosomes) < self.populationSize:
            self.chromosomes.append(Chromosome())


    def mutate(self):
        # end = int(len(self.chromosomes) * (1 - self.mutateThreshold))
        # l = [self.chromosomes[i] for i in range(0, end)]
        for i in range(int(self.mutateThreshold * len(self.chromosomes))):
            random.choice(self.chromosomes).mutate()

    def __str__(self):
        s = ""
        for i in self.chromosomes:
            s = s + i.__str__() + "\n"
        return s

    def __repr__(self):
        return self.__str__()

    def reset_scores(self):
        for chromo in self.chromosomes:
            chromo.fitness = 0
            
def fill_coords(XCoords, OCoords):
    testBoard = GameBoard()
    for x in range(len(XCoords)):
        testBoard.board[XCoords[x][0]][XCoords[x][1]] = 'X'
    for o in range(len(OCoords)):
        testBoard.board[OCoords[o][0]][OCoords[o][1]] = 'O'
    return testBoard

def made_good_choice(board, player, cols):
    return player.make_move(board) in cols

def test_population(player):
    # Check to see if player O plays at col 2 or 5
    XCoords = [[3,0],[4,0]]
    OCoords = [[3,1]]
    testBoard = fill_coords(XCoords, OCoords)
    cols = [2,5]
    testBoard.switch_turn()
    made_good_choice(testBoard, player, cols)

    # Check to see if player X plays at col 2 or 5
    OCoords = [[3,1],[4,1]]
    testBoard = fill_coords(XCoords, OCoords)
    made_good_choice(testBoard, player, cols)

    # Check to see if player O plays at col 1 or 4
    XCoords = [[2,0],[3,0]]
    OCoords = [[2,1],[3,1]]
    testBoard = fill_coords(XCoords, OCoords)
    cols = [1,4]
    testBoard.switch_turn()
    made_good_choice(testBoard, player, cols)

    # Check to see if player O plays at col 3 and player X plays at 0
    XCoords = [[0,0],[0,1],[0,2]]
    OCoords = [[3,0],[3,1],[3,2]]
    testBoard = fill_coords(XCoords, OCoords)
    cols = [1,4]
    testBoard.switch_turn()
    made_good_choice(testBoard, player, cols)

    # Check to see if player O plays at col 0
    XCoords = [[1,0],[2,1],[3,0],[3,1],[3,2],[4,0]]
    OCoords = [[1,1],[2,0],[2,2],[3,3],[5,0]]
    testBoard = fill_coords(XCoords, OCoords)
    cols = [1,4]
    testBoard.switch_turn()
    made_good_choice(testBoard, player, cols)

    # Check to see if player O plays at col 0
    XCoords = [[0,0],[0,1],[0,2]]
    OCoords = [[2,0],[2,1],[4,0]]
    testBoard = fill_coords(XCoords, OCoords)
    cols = [1,4]
    testBoard.switch_turn()
    made_good_choice(testBoard, player, cols)

player = Players.Player('O')
test_population(player)

p1 = Population()
p2 = Population()
print(p1)
print(p2)

pop1 = [Players.Chromo("O", i) for i in p1.chromosomes]

pop1 = [Players.Chromo("X", i) for i in p2.chromosomes]

# assess should take in a collection of players
for i in range(30):
    p1.assess(p2)
    p1.sort()
    p1.kill()
    p1.mate()
    p1.refresh()
    p1.sort()
    p1.mutate()
    p2.sort()
    p2.kill()
    p2.mate()
    p2.refresh()
    p2.sort()
    p2.mutate()
    print(p1)
    print(p2)
    p1.reset_scores()
    p2.reset_scores()
    print()
