import random
from operator import itemgetter, attrgetter, methodcaller

class Chromosome:

    def __init__(self):
        # weights = position, block1, block2, block3, connect1, connect2, connect3
        self.weights = self.randomize()
        self.fitness = 0

    def randomize(self):
        w = [0 for i in range(7)]
        w[0] = random.uniform(0, 100)
        for i in range(1, 7):
            if i % 3 == 1:
                w[i] = random.uniform(0, 100)
            else:
                w[i] = random.uniform(w[i-1], 100)
        return w

    def mate(self, other):
        ow = other.weights
        c = Chromosome()
        c.weights = [(random.uniform(self.weights[i], ow[i]) + random.uniform(self.weights[i], ow[i])) / 2 for i in range(len(self.weights))]
        return c

    def mutate(self):
        self.weights = self.randomize()

    def __str__(self):
        s = ""
        for i in self.weights:
            s = s + str(i)[:4] + " | "
        return s + " " + str(self.fitness)

    def __repr__(self):
        return self.__str__()


def sort(chromos):
    return sorted(chromos, key = attrgetter('fitness'))

header = " pos | blk1 | blk2 | blk3 | con1 | con2 | con3 | fit"
c = Chromosome()
c2 = Chromosome()

print(header)
print(c)
print(c2)

c3 = c.mate(c2)

print()
print(c3)
print()
print()

c.fitness = 3
c2.fitness = 18.3
c3.fitness = 5.8

l = [c, c2, c3]

print(l)
print(sort(l))
