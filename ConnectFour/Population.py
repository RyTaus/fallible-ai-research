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
