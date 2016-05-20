class Player:
	def __init__(self, t):
		self.x = 0
		self.y = 0
		self.piece = t

	def make_move(self, board):
		return (self.x, self.y)

	def update(self, board, pos):
		self.x = pos[0]
		self.y = pos[1]

	def end_game(self, b, r):
		return None
