from Board import GameBoard
from Player import Player
from Players import Persona
import random
import pygame

pygame.init()

SCREEN_HEIGHT = 600
SCREEN_WIDTH = 600

SQUARE_SIZE = 200

screen = pygame.display.set_mode((SCREEN_WIDTH, SCREEN_HEIGHT))

BLACK = (0, 0, 0)
RED = (250, 0, 0)
BLUE = (0, 0, 250)
WHITE = (250, 250, 250)

done = False
clock = pygame.time.Clock()

cellStates = ["-", "O", "X"]
colors = [WHITE, RED, BLUE]


gb = GameBoard()
p1 = Player("O")
p2 = Persona("X", learning = False)
players = [p1, p2]



def get_cell(msx, msy):
    return (msx // 200, msy // 200)


count = 0
# -------- Game Loop -----------
while not done:
    # --- Main event loop
    for event in pygame.event.get(): # User did something
        if event.type == pygame.QUIT:
            done = True

    
    # --- Game logic

    mx = pygame.mouse.get_pos()[0]
    my = pygame.mouse.get_pos()[1]
    mbd = pygame.mouse.get_pressed()[0]
    # print(get_cell(mx, my))
    loc = get_cell(mx, my)
    p1.update(gb, loc)
    turn = gb.turnNumber



    # print(gb.turnNumber)
    if mbd and not mbdPrev or False:
        if gb.is_valid_move(players[turn].make_move(gb)):
            gb.place_piece(players[turn].make_move(gb))

            results = gb.get_results()
            if results != None:
                print(results)
                # p2.end_game(gb, results)
                # p1.end_game(gb, results)
                gb = GameBoard()
                gb.turnNumber = random.choice([0, 1])
                if count == 1000:
                    done = True
            else:
                gb.switch_turn()


 
    # --- Drawing code
 

    screen.fill(WHITE)

    for i in range(3):
        for j in range(3):
                pygame.draw.rect(screen, colors[cellStates.index(gb.board[i][j])], [i * SQUARE_SIZE, j * SQUARE_SIZE, 200, 200], 0)

    
    mbdPrev = mbd
    # pygame.draw.rect(screen, RED, [i * SQUARE_SIZE, j * SQUARE_SIZE, 200, 200], 0)
 
 
    # --- Update Screen
    pygame.display.update()
 
    # --- Frames Per Second
    clock.tick(600)
