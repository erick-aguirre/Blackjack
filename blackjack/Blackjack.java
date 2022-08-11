package blackjack;

import java.util.*;

public class Blackjack implements BlackjackEngine {

	/*
	 * creates the instance variables of the class. This includes the account a
	 * player has, how much is being bet, the number of decks there is the status of
	 * the game, and the Random variable which will be used when shuffling the
	 * decks. There is also three ArrayList, one representing the deck of the game,
	 * the deck of the dealer, and the deck of the player.
	 */

	private int account;
	private int bet;
	private int numberDecks;
	private int gameStatus;
	private Random randomNumbers;
	ArrayList<Card> deckArrayList;
	ArrayList<Card> playerCards;
	ArrayList<Card> dealerCards;

	/**
	 * Constructor you must provide. Initializes the player's account to 200 and the
	 * initial bet to 5. Feel free to initialize any other fields. Keep in mind that
	 * the constructor does not define the deck(s) of cards.
	 * 
	 * @param randomGenerator
	 * @param numberOfDecks
	 */

	/*
	 * like mentioned above, this is the constructor for the class, which
	 * initializes certain instance variables according to parameters, and other
	 * instance variables according to the logic of the game, such as creating new
	 * ArrayList<Card> objects to represent the game deck, player deck, and dealer
	 * deck.
	 */

	public Blackjack(Random randomGenerator, int numberOfDecks) {

		account = 100;
		bet = 5;
		numberDecks = numberOfDecks;
		randomNumbers = randomGenerator;
		deckArrayList = new ArrayList<>();
		playerCards = new ArrayList<>();
		dealerCards = new ArrayList<>();
		gameStatus = 8;

	}

	// returns the number of decks in the gamedeck.

	public int getNumberOfDecks() {

		return numberDecks;

	}

	/*
	 * loads a deck of 52 cards into the game deck arrayList however many times
	 * according to the integer numberOfdecks, which is declared by a user in the
	 * constructor. The ArrayList is then shuffled according to the Random number
	 * variable which was also declared in the constructor.
	 */

	public void createAndShuffleGameDeck() {

		for (int i = 1; i <= numberDecks; i++) {

			for (CardSuit suit : CardSuit.values()) {

				for (CardValue value : CardValue.values()) {

					deckArrayList.add(new Card(value, suit));
				}

			}
		}

		Collections.shuffle(deckArrayList, randomNumbers);

	}

	// returns the game deck arrayList elements in a Array of Card objects

	public Card[] getGameDeck() {

		Card[] cardArray = new Card[deckArrayList.size()];

		for (int i = 0; i < cardArray.length; i++) {

			cardArray[i] = deckArrayList.get(i);
		}

		return cardArray;

	}

	/*
	 * starts a new game when called. Old decks including the player deck, game deck
	 * and dealer deck are assigned to a new and shuffled deck. The new list are
	 * first loaded shuffled, and 4 cards in total are dealt to the new player and
	 * dealer decks from the game deck, and lastly the new decks are assigned to the
	 * instance variables.
	 */

	public void deal() {

		ArrayList<Card> newArrayList = new ArrayList<>();
		ArrayList<Card> newPlayerList = new ArrayList<>();
		ArrayList<Card> newDealerList = new ArrayList<>();

		for (int i = 1; i <= numberDecks; i++) {

			for (CardSuit suit : CardSuit.values()) {

				for (CardValue value : CardValue.values()) {

					newArrayList.add(new Card(value, suit));
				}

			}
		}

		Collections.shuffle(newArrayList, randomNumbers);

		for (int i = 0; i < 4; i++) {

			if (i == 0) {

				newPlayerList.add(newArrayList.get(0));
				newPlayerList.get(i).setFaceUp();
				newArrayList.remove(0);

			} else if (i == 1) {

				newDealerList.add(newArrayList.get(0));
				newDealerList.get(newDealerList.size() - 1).setFaceDown();
				newArrayList.remove(0);

			} else if (i == 2) {

				newPlayerList.add(newArrayList.get(0));
				newPlayerList.get(newPlayerList.size() - 1).setFaceUp();
				newArrayList.remove(0);

			} else if (i == 3) {

				newDealerList.add(newArrayList.get(0));
				newDealerList.get(newDealerList.size() - 1).setFaceUp();
				newArrayList.remove(0);

			}
		}

		dealerCards = newDealerList;
		deckArrayList = newArrayList;
		playerCards = newPlayerList;
		gameStatus = 8;
		account -= bet;

	}

	// returns an array of card objects that represents the elements in the dealer
	// Deck.

	public Card[] getDealerCards() {

		Card[] cardArray = new Card[dealerCards.size()];

		for (int i = 0; i < cardArray.length; i++) {

			cardArray[i] = dealerCards.get(i);
		}

		return cardArray;

	}

	/*
	 * returns in array of integers represnting the possible sums of the card
	 * objects the dealer currently has in their deck. This is done by checking if
	 * an ace is present in deck, and if there is, a process is done to calculute a
	 * sum with an ace being 11 and one with an ace being 1 and the sums which are
	 * 21 or under are added in the array. If no ace is present, a normal process is
	 * conducted to find the sum of the cards, if its 21 or under, they will be
	 * added to the array. Theres no possible combination that is 21 or under, then
	 * null is returned.
	 */

	public int[] getDealerCardsTotal() {

		int[] possibilities;
		int firstSum = 0;
		int secondSum = 0;

		boolean checker = false;

		for (int i = 0; i < dealerCards.size(); i++) {

			if (dealerCards.get(i).getValue() == CardValue.Ace) {
				checker = true;

			}
		}

		if (checker == true) {

			for (int i = 0; i < dealerCards.size(); i++) {

				if (dealerCards.get(i).getValue().getIntValue() == 1) {

					firstSum += 11;
				} else {

					firstSum += dealerCards.get(i).getValue().getIntValue();
				}
			}

			if (firstSum > 21) {

				for (int i = 0; i < dealerCards.size(); i++) {

					secondSum += dealerCards.get(i).getValue().getIntValue();
				}
				if (secondSum > 21) {

					return null;
				} else {

					possibilities = new int[1];
					possibilities[0] = secondSum;
					return possibilities;
				}

			} else {

				possibilities = new int[2];
				secondSum = 0;
				firstSum = 0;

				for (int i = 0; i < dealerCards.size(); i++) {

					secondSum += dealerCards.get(i).getValue().getIntValue();
				}

				possibilities[0] = secondSum;

				for (int i = 0; i < dealerCards.size(); i++) {

					if (dealerCards.get(i).getValue().getIntValue() == 1) {
						firstSum += 11;

					} else {

						firstSum += dealerCards.get(i).getValue().getIntValue();
					}
				}

				possibilities[1] = firstSum;
				return possibilities;
			}

		} else {

			secondSum = 0;

			for (int i = 0; i < dealerCards.size(); i++) {

				secondSum += dealerCards.get(i).getValue().getIntValue();
			}
			if (secondSum <= 21) {

				possibilities = new int[1];
				possibilities[0] = secondSum;
				return possibilities;

			}

		}

		return null;

	}

	/*
	 * evaluates the dealer deck. if null, then the dealer has busted. if under 21,
	 * the dealer has the condition of has less than 21 which is associated with 2,
	 * if equal to 21, then it will be checked weather if it has blackjack and
	 * returned its respective number, otherwise then it will be has 21.
	 */

	public int getDealerCardsEvaluation() {

		int sum;

		if (getDealerCardsTotal() == null) {

			gameStatus = 7;
			for (int i = 0; i < dealerCards.size(); i++) {

				if (dealerCards.get(i).isFacedUp() == false) {

					dealerCards.get(i).setFaceUp();
				}
			}

			return 3;
		} else {

			sum = getDealerCardsTotal()[getDealerCardsTotal().length - 1];
		}

		if (sum < 21) {

			return 2;

		} else if (sum > 21) {

			gameStatus = 7;

			for (int i = 0; i < dealerCards.size(); i++) {

				if (dealerCards.get(i).isFacedUp() == false) {

					dealerCards.get(i).setFaceUp();
				}

			}
			return 3;
		} else if (sum == 21) {

			if (dealerCards.size() == 2) {

				if (dealerCards.get(0).getValue().getIntValue() == 1
						&& dealerCards.get(1).getValue().getIntValue() == 10) {

					return 4;
				} else if (dealerCards.get(0).getValue().getIntValue() == 10
						&& dealerCards.get(1).getValue().getIntValue() == 1) {

					return 4;
				}
			} else {

				return 5;

			}
		} else if (sum == 21 && dealerCards.size() > 2) {

			return 5;

		}

		return 0;

	}

	// returns a card array which contains the elements of the player deck.

	public Card[] getPlayerCards() {

		Card[] cardArray = new Card[playerCards.size()];

		for (int i = 0; i < cardArray.length; i++) {

			cardArray[i] = playerCards.get(i);
		}

		return cardArray;

	}

	/*
	 * returns in array of integers representing the possible sums of the card
	 * objects the player currently has in their deck. This is done by checking if
	 * an ace is present in deck, and if there is, a process is done to calculate a
	 * sum with an ace being 11 and one with an ace being 1 and the sums which are
	 * 21 or under are added in the array. If no ace is present, a normal process is
	 * conducted to find the sum of the cards, if its 21 or under, they will be
	 * added to the array. Theres no possible combination that is 21 or under, then
	 * null is returned.
	 */

	public int[] getPlayerCardsTotal() {

		int[] possibilities;
		int firstSum = 0;
		int secondSum = 0;

		boolean checker = false;

		for (int i = 0; i < playerCards.size(); i++) {

			if (playerCards.get(i).getValue() == CardValue.Ace) {

				checker = true;
			}
		}

		if (checker == true) {

			for (int i = 0; i < playerCards.size(); i++) {

				if (playerCards.get(i).getValue().getIntValue() == 1) {

					firstSum += 11;
				} else {

					firstSum += playerCards.get(i).getValue().getIntValue();

				}
			}

			if (firstSum > 21) {

				for (int i = 0; i < playerCards.size(); i++) {

					secondSum += playerCards.get(i).getValue().getIntValue();
				}
				if (secondSum > 21) {

					return null;
				} else {

					possibilities = new int[1];
					possibilities[0] = secondSum;
					return possibilities;
				}

			} else {

				possibilities = new int[2];
				secondSum = 0;
				firstSum = 0;

				for (int i = 0; i < playerCards.size(); i++) {

					secondSum += playerCards.get(i).getValue().getIntValue();
				}
				possibilities[0] = secondSum;
				for (int i = 0; i < playerCards.size(); i++) {
					if (playerCards.get(i).getValue().getIntValue() == 1) {
						firstSum += 11;
					} else {
						firstSum += playerCards.get(i).getValue().getIntValue();
					}
				}
				possibilities[1] = firstSum;
				return possibilities;
			}

		} else {
			secondSum = 0;
			for (int i = 0; i < playerCards.size(); i++) {

				secondSum += playerCards.get(i).getValue().getIntValue();
			}
			if (secondSum <= 21) {
				possibilities = new int[1];
				possibilities[0] = secondSum;
				return possibilities;
			}

		}

		return null;

	}

	/*
	 * evaluates the player deck. if null, then the player has busted. if under 21,
	 * the player has the condition of has less than 21 which is associated with 2,
	 * if equal to 21, then it will be checked weather if it has blackjack and
	 * returned its respective number, otherwise then it will be has 21.
	 */

	public int getPlayerCardsEvaluation() {

		int sum;

		if (getPlayerCardsTotal() == null) {

			gameStatus = 6;

			for (int i = 0; i < dealerCards.size(); i++) {

				if (dealerCards.get(i).isFacedUp() == false) {

					dealerCards.get(i).setFaceUp();

				}
			}
			return 3;
		} else {

			sum = getPlayerCardsTotal()[getPlayerCardsTotal().length - 1];
		}

		if (sum < 21) {

			return 2;

		} else if (sum > 21) {

			gameStatus = 6;

			for (int i = 0; i < dealerCards.size(); i++) {

				if (dealerCards.get(i).isFacedUp() == false) {

					dealerCards.get(i).setFaceUp();
				}
			}

			return 3;
		} else if (sum == 21) {

			if (playerCards.size() == 2) {

				if (playerCards.get(0).getValue().getIntValue() == 1
						&& playerCards.get(1).getValue().getIntValue() == 10) {

					return 4;
				} else if (playerCards.get(0).getValue().getIntValue() == 10
						&& playerCards.get(1).getValue().getIntValue() == 1) {

					return 4;
				} else {

					return 5;

				}
			}
		}

		if (sum == 21 && playerCards.size() > 2) {

			return 5;

		}

		return 0;

	}

	/*
	 * takes a card from the game deck, deletes it from the game deck, and adds it
	 * to the player deck, and checks if the player has busted
	 */

	public void playerHit() {

		playerCards.add(deckArrayList.get(0));

		deckArrayList.remove(0);

		if (getPlayerCardsEvaluation() != 3) {

			gameStatus = 8;
		} else {

			gameStatus = 6;
		}

	}

	/*
	 * process for when a player decides to stand. Here, a dealers cards will be
	 * checked to see if their null, if so, he busts. If dealer combination is less
	 * than 16, dealer will continue to add until he busts, or is in between 16 and
	 * 21 with 16 being inclusive. game status is updated accordingly.
	 */

	public void playerStand() {

		for (int i = 0; i < dealerCards.size(); i++) {

			if (dealerCards.get(i).isFacedUp() == false) {

				dealerCards.get(i).setFaceUp();

			}

		}

		if (getDealerCardsTotal() == null) {

			gameStatus = 7;
			account += (bet * 2);

		}

		if (getDealerCardsTotal().length > 1 && getDealerCardsTotal() != null) {

			while (getDealerCardsTotal()[1] < 16 && getDealerCardsTotal() != null) {

				int i = 0;
				dealerCards.add(deckArrayList.get(i));
				deckArrayList.remove(i);
				i++;

			}

			if (getDealerCardsTotal() == null) {

				gameStatus = 7;
				account += (bet * 2);
			} else if (getDealerCardsTotal()[getDealerCardsTotal().length
					- 1] == getPlayerCardsTotal()[getPlayerCardsTotal().length - 1]) {

				gameStatus = 1;
				account += bet;

			} else if (getDealerCardsTotal()[getDealerCardsTotal().length
					- 1] > getPlayerCardsTotal()[getPlayerCardsTotal().length - 1]) {

				gameStatus = 6;

			} else if ((getDealerCardsTotal()[getDealerCardsTotal().length
					- 1] < getPlayerCardsTotal()[getPlayerCardsTotal().length - 1])) {

				gameStatus = 7;
				account += (bet * 2);

			}
		} else {

			while (getDealerCardsTotal() != null && getDealerCardsTotal()[0] < 16) {

				int i = 0;
				dealerCards.add(deckArrayList.get(i));
				deckArrayList.remove(i);
				i++;

			}

			if (getDealerCardsTotal() == null) {

				gameStatus = 7;
				account += (bet * 2);

			} else if (getDealerCardsTotal()[getDealerCardsTotal().length
					- 1] == getPlayerCardsTotal()[getPlayerCardsTotal().length - 1]) {

				gameStatus = 1;
				account += bet;

			} else if (getDealerCardsTotal()[getDealerCardsTotal().length
					- 1] > getPlayerCardsTotal()[getPlayerCardsTotal().length - 1]) {

				gameStatus = 6;

			} else if ((getDealerCardsTotal()[getDealerCardsTotal().length
					- 1] < getPlayerCardsTotal()[getPlayerCardsTotal().length - 1])) {

				gameStatus = 7;
				account += (bet * 2);

			}

		}

	}

	// returns game status instance variable

	public int getGameStatus() {

		return gameStatus;

	}

	// sets bet amount according to parameter.

	public void setBetAmount(int amount) {

		bet = amount;

	}

	// returns bet amount

	public int getBetAmount() {

		return bet;

	}

	// sets account amount according to parameter

	public void setAccountAmount(int amount) {

		account = amount;
	}

	// returns account instance variable.

	public int getAccountAmount() {

		return account;
	}

}