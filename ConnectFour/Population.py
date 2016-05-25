import random

class population:

    def __init__(self):
        self.chromosomes = [None]
        self.best_chromosome_seen = [None]
        self.time_best_unchanged = 0
        self.time_stayed_on_top = 0
        self.killThreshold = .2
        self.mateThreshold = .2
        self.mutateThreshold = .2

    # def assess(self, chromosomes)


    # def sort(self, chromosomes):


    def kill(self, chromosomes):
        surivied = self.chromosomes.length * self.killThreshold;
        for i in range(surivied):
            newPopulation[i] = self.chromosomes[i]

        return newPopulation
        
    def mate(self, chromosomes):
        potentialToMate = self.chromosomes.length * (1 - self.mateThreshold);
        potentialToMate = int(potentialToMate)
        if potentialToMate % 2 == 1:
            potentialToMate -=1
        strongestChromosomes = [chromosomes[i] for i in range(potentialToMate, len(self.chromosomes))]
        mateList = [None] * potentialToMate

        for i in range(potentialToMate):
            mateList[i] = random.choice(strongestChromosomes)
            strongestChromosomes.remove(mateList[i])

        for i in range(potentialToMate/2):
            self.chromosomes.extend(mateList[i].mate(mateList[i+1]))
            i += 1

    def mutate(self, chromosomes):
        mutants = self.chromosomes.length * self.mutateThreshold;
        for i in range(mutants):
            print()
            # chromosomes[]
