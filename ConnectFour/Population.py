import random
from operator import itemgetter, attrgetter, methodcaller

class population:

    def __init__(self):
        self.chromosomes = [None]
        self.best_chromosome_seen = [None]
        self.time_best_unchanged = 0
        self.time_stayed_on_top = 0
        self.killThreshold = .2
        self.mateThreshold = .2
        self.mutateThreshold = .2

        self.populationSize = 20

    # def assess(self, chromosomes)


    def sort(self):
        return sorted(self.chromosomes, key = attrgetter('fitness'))


    def kill(self, chromosomes):
        end = int(len(self.chromosomes) * self.killThreshold)
        for i in range(surivied):
            self.chromosomes.remove(self.chromosomes[i])

        return newPopulation

    def mate(self, chromosomes):
        temp = list()
        start = int(len(self.chromosomes) * (1 - self.mateThreshold))
        while len(start) + len(self.chromosomes) < self.populationSize
            l = [self.chromosomes[i] for i in range(start, len(self.chromosomes))]
            mom = random.choice(l)
            l.remove(mom)
            dad = random.choice(l)
            temp.append(mom.mate(dad))
        for c in temp:
            self.chromosomes.append(c)


    def mutate(self, chromosomes):
        start = int(len(self.chromosomes) * (1 - self.mateThreshold))
        exempt = [self.chromosomes[i] for i in range(start, len(self.chromosomes))]
        l = [self.chromosomes[i] for i in range(start, len(self.chromosomes))]
        for i in range(self.mutateThreshold * len(self.chromosomes)):
            random.choice(l).mutate()
