package ie.ucd.setup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import ie.ucd.gameEntities.Card;
import ie.ucd.gameEntities.Player;
import ie.ucd.gameEntities.Room;
import ie.ucd.gameEntities.RoomCard;
import ie.ucd.gameEntities.Suspect;
import ie.ucd.gameEntities.SuspectCard;
import ie.ucd.gameEntities.Weapon;
import ie.ucd.gameEntities.WeaponCard;

public class CardsSetup extends Setup{

	ArrayList<Card> cardDeck;
	ArrayList<Card> murdererCards;
	
	public CardsSetup() {
		setupCardDeck();
		murdererCards = new ArrayList<Card>();
	}

	
	public ArrayList<Card> setupCards(){
		setScene();
		dealCards();
		return murdererCards;
	}
	
	private void dealCards() {
		
		// shuffle cards
		Collections.shuffle(cardDeck);
	
		// remove a card and give it to next player until all out
		for(int numCards = cardDeck.size(), i=1; numCards>0; numCards--, i++) { 
			
			Player currentPlayer = playerCollection.get(i%(numPlayers));
			
			// remove a card from the deck
			Card dealtCard = cardDeck.remove(0);
			
			// give the card to the current player and add it to their notebook
			currentPlayer.giveCard(dealtCard);
			currentPlayer.getNotebook().addEvent("You have card: " + dealtCard.getName().toString());
		}
	}
	/*
	 * Adds an instance of every card to the cardDeck array
	 */
	private void setupCardDeck(){
		cardDeck = new ArrayList<Card>();
		for (Suspect sus : Suspect.values())
		{
			cardDeck.add(new SuspectCard(sus));
		}
		for (Room ro : Room.values())
		{
			cardDeck.add(new RoomCard(ro));
		}
		for (Weapon wep : Weapon.values())
		{
			cardDeck.add(new WeaponCard(wep));
		}	
	}
	/*
	 * Randomly selects murder cards from cardDeck
	 */
	private void setScene() {
		// first pick the murderer, murder weapon and murder room
		Random rand = new Random();
		// remove random suspect card
		murdererCards.add(cardDeck.remove(rand.nextInt(Suspect.values().length-1)));
		// remove random room card
		murdererCards.add(cardDeck.remove(Suspect.values().length-1+rand.nextInt(Room.values().length-1)));
		// remove random weapon card
		murdererCards.add(cardDeck.remove(Suspect.values().length-1+Room.values().length-1+rand.nextInt(Weapon.values().length-1)));
	}
	
}
