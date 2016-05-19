from Board import GameBoard

class Player:

    def __init__(self, t):
        self.piece = t

    def make_move(self, gameBoard):
        coord = [3,3]
        gb = gameBoard
        
        while not gb.is_valid_move(coord):
            xCoord = input("%ss please input an x coordinate " %gb.pieces[gb.turnNumber])
            yCoord = input("%ss please input an y coordinate " %gb.pieces[gb.turnNumber])
            xCoord = int(xCoord)
            yCoord = int(yCoord)
            coord = [xCoord, yCoord]
            if not gb.is_valid_move(coord):
                print("%ss did not input a valid move, please input another coordinate..." %gb.pieces[gb.turnNumber])

        return coord
