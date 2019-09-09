This was a project done for a class in December 2018.





=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: _______
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. A standard 2048 game features a 4x4 board. To represent the board, 
  I will use a 2D array of integers. 0 will represent a square without a number in it, 
  while 2, 4, 8,..., 2^n will represent the numbers that are on the board. 
  At the beginning of the game, the board will begin with two numbers randomly assigned to two of the sixteen locations: 
  it will either be a 2 or a 4, with a 90% that it is a 2.

  2. The Collections will be a Linked List of 2D arrays, and it’ll store each of the moves a player makes. This will allow 
  the player to undo their moves. The 2D arrays are stored in a linked list because order is important when undoing a 
  player’s moves. When a player undo their move, it will revert to the last added entry to the list.

  3. My 2048 implementation will allow players to save their game for later and come back to it. The state of the 2D array 
  will be stored, and players can load the saved game by letting the game read the saved file. 

  4. I will create methods that takes in integers as parameters and creates a 2D array of the game. I will use this to 
  test when the game ends (when the player can no longer make any more moves), along with edge cases such as the player 
  trying to make a move that is impossible to make. In addition, I can make sure my linked list of 2D arrays is being 
  updated correctly and each move actually works.


=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  I have a single class in my game, and it generates the gameboard. Initially, I created a Tile class to represent each
  Tile--however, I realized that they served little purpose and it was much more efficient to just have a 2D array of integers.


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  The most significant stumbling block was figuring out how to merge the tiles properly. For example, when I shift the blocks up,
  the uppermost blocks are merged initially, and then the lower blocks are merged. If I had a row that was 4, 2, 2, 2 and I shift 
  the blocks right, then the row should be 0, 4, 2, 4 rather than 0, 4, 4, 2. I later on saw a tutorial that described an interesting
  approach that allowed me to split the move into three steps: the tutorial is cited in the external resources.


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
  There is a good separation of functionality and the private state is well encapsulated.



========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
  https://steven.codes/blog/cs10/2048-merge/ 
