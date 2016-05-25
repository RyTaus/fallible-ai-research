import random

class population:

    def __init__(self):
        self.chromosomes = [None]
        self.best_chromosome_seen = [None]
        self.time_best_unchanged = 0
        self.time_stayed_on_top = 0
        self.killThreshold = .2
        self.mateThreshold = .8
        self.mutateThreshold = .2

    # def assess(self, chromosomes)


    # def sort(self, chromosomes):


    def kill(self, chromosomes):
        surivied = self.chromosomes.length * self.killThreshold;
        for i in range(surivied):
            newPopulation[i] = self.chromosomes[i]

        return newPopulation

    def mate(self, chromosomes):
        potentialToMate = self.chromosomes.length * self.mateThreshold;
        potentialToMate = int(potentialToMate)
        if potentialToMate % 2 == 1:
            potentialToMate -=1
        strongestChromosomes = [chromosomes]
        mateList = [None] * potentialToMate
        for i in range(potentialToMate/2):

            print()

    def mutate(self, chromosomes):
        mutants = self.chromosomes.length * self.mutateThreshold;
        for i in range(mutants):
            print()
            # chromosomes[]
