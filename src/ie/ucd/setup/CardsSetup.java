package ie.ucd.setup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import ie.ucd.items.Card;
import ie.ucd.items.Player;
import ie.ucd.items.Room;
import ie.ucd.items.RoomCard;
import ie.ucd.items.Suspect;
import ie.ucd.items.SuspectCard;
import ie.ucd.items.Weapon;
import ie.ucd.items.WeaponCard;

public class CardsSetup extends Setup{


	public ArrayList<Card> dealCards() {
		ArrayList<Card> cardDeck = this.setupCardDeck();
		int numPlayers;
		ArrayList<Card> murdererCards = new ArrayList<Card>();
		 
		// first pick the murderer, murder weapon and murder room
		Random rand = new Random();
		// remove random suspect card
		murdererCards.add(cardDeck.remove(rand.nextInt(Suspect.values().length-1)));
		// remove random room card
		murdererCards.add(cardDeck.remove(Suspect.values().length-1+rand.nextInt(Room.values().length-1)));
		// remove random weapon card
		murdererCards.add(cardDeck.remove(Suspect.values().length-1+Room.values().length-1+rand.nextInt(Weapon.values().length-1)));
		
		// deal remaining cards randomly to players
		Collections.shuffle(cardDeck);
		
		// find out the number of active players
		numPlayers = 0;
		for(Player p : playerCollection) {
			if(p.isActive())
				numPlayers++;
		}
		// remove a card and give it to next player until all out
		for(int numCards = cardDeck.size(), i=1; numCards>0; numCards--, i++) { 
			Player currentPlayer = playerCollection.get(i%(numPlayers));
			// remove a card from the deck
			Card dealtCard = cardDeck.remove(0);
			// give the card to the current player and add it to their notebook
			currentPlayer.giveCard(dealtCard);
			currentPlayer.getNotebook().addEvent("You have card: " + dealtCard.getName().toString());
		}
		// return the cards that were removed from the deck at the beginning
		return murdererCards;
	}
	
	private ArrayList<Card> setupCardDeck(){
		ArrayList<Card> cardDeck = new ArrayList<Card>();
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

		return cardDeck;	
	}
	
}
