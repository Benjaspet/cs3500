## Changes to Models & View

### Model
- Instead of having cards control visibility, the model now handles that using a
  stack of booleans equivalent to the positions of said cards.
- In the KlondikeCard class, I am now using enums to ensure valid card suits and
  values are being passed into the card constructor. I am also using enum ordinals
  to determine the value of the card.
- I created an abstract class AKlondike and overrode necessary methods in each of 
  my Klondike variants, using abstraction.
- I moved my code that generates cascade piles and the draw pile to a helper method.
- I moved some of my exception checks to helper methods for abstraction purposes.