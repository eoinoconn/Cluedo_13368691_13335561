package ie.ucd.gameEntities;

import java.util.ArrayList;

public class Hand {

	private ArrayList<Card> hand;
	
	public Hand() {
		this.hand = new ArrayList<Card>();
	}
	
	/**
	 * Add a card to this hand
	 * @param card
	 */
	public void addCard(Card card) {
		hand.add(card);
	}
	
	/**
	 * Check the hand for any instances of cards for this room, suspect and weapon
	 * @param room
	 * @param suspect
	 * @param weapon
	 * @return any card in the hand corresponding to one of these
	 */
	public Card checkCards(Room room, Suspect suspect, Weapon weapon) {
		// iterate through each card in hand
		for(int i = 0; i < hand.size(); i++) {
			Card currentCard = hand.get(i);
			
			// Check if and of the hypothesis statement match players cards
			if((currentCard instanceof RoomCard && ((RoomCard) currentCard).getName() == room) ||
				(currentCard instanceof WeaponCard && ((WeaponCard) currentCard).getName() == weapon) ||
				(currentCard instanceof SuspectCard && ((SuspectCard) currentCard).getName() == suspect)) {
				return currentCard;	// Found card, refuting hypothesis
			}
		}
		return null;	// Did not find card, do not refute hypothesis
	}
	
	/**
	 * Converts the collection of cards in the hand to a string listing them
	 */
	public String toString() {
		String str = "";
		for(Card card : hand) {
			str += card.toString() + "\n";
		}
		return str;
	}
}
