package com.fantasycard.app;

import java.util.ArrayList;
import java.util.Random;

import com.fantasycard.cardinfo.CardInfo;

public class DeckManager {
	private ArrayList<CardInfo> mDeck = new ArrayList<CardInfo>();
	
	public void init() {
		initTestLib();
		mDeck = suffleDeck(mDeck);
	}
	
	public void initTestLib() {
		for(int i=0;i<30;i++){
			if(i < 5){
				CardInfo card = new CardInfo();
				card.card_id = 1;
				card.card_category = AppValues.CARD_MANA;
				card.mana_cost = 0;
				card.attackpoint = 0;
				card.defensepoint = 0;
				card.lifepoint = 0;
				card.material = 0;
				mDeck.add(card);
			}
			else if(i >= 5 && i < 10){
				CardInfo card = new CardInfo();
				card.card_id = 2;
				card.card_category = AppValues.CARD_MANA;
				card.mana_cost = 0;
				card.attackpoint = 0;
				card.defensepoint = 0;
				card.lifepoint = 0;
				card.material = 1;
				mDeck.add(card);
			}
			else if(i >= 10 && i < 15){
				CardInfo card = new CardInfo();
				card.card_id = 3;
				card.card_category = AppValues.CARD_MANA;
				card.mana_cost = 0;
				card.attackpoint = 0;
				card.defensepoint = 0;
				card.lifepoint = 0;
				card.material = 2;
				mDeck.add(card);
			}
			else if(i >= 15 && i < 17){
				CardInfo card = new CardInfo();
				card.card_id = 4;
				card.card_category = AppValues.CARD_CREATURE;
				card.mana_cost = 1;
				card.attackpoint = 1;
				card.defensepoint = 0;
				card.lifepoint = 1;
				card.material = 0;
				mDeck.add(card);
			}
			else if(i >= 17 && i < 19){
				CardInfo card = new CardInfo();
				card.card_id = 5;
				card.card_category = AppValues.CARD_CREATURE;
				card.mana_cost = 1;
				card.attackpoint = 0;
				card.defensepoint = 1;
				card.lifepoint = 1;
				card.material = 1;
				mDeck.add(card);
			}
			else if(i >= 19 && i < 21){
				CardInfo card = new CardInfo();
				card.card_id = 6;
				card.card_category = AppValues.CARD_CREATURE;
				card.mana_cost = 2;
				card.attackpoint = 1;
				card.defensepoint = 1;
				card.lifepoint = 1;
				card.material = 2;
				mDeck.add(card);
			}
			else if(i >= 21 && i < 23){
				CardInfo card = new CardInfo();
				card.card_id = 7;
				card.card_category = AppValues.CARD_CREATURE;
				card.mana_cost = 2;
				card.attackpoint = 1;
				card.defensepoint = 0;
				card.lifepoint = 2;
				card.material = 1;
				mDeck.add(card);
			}
			else if(i >= 23 && i < 25){
				CardInfo card = new CardInfo();
				card.card_id = 8;
				card.card_category = AppValues.CARD_CREATURE;
				card.mana_cost = 2;
				card.attackpoint = 2;
				card.defensepoint = 0;
				card.lifepoint = 1;
				card.material = 0;
				mDeck.add(card);
			}
			else if(i >= 25 && i < 27){
				CardInfo card = new CardInfo();
				card.card_id = 9;
				card.card_category = AppValues.CARD_CREATURE;
				card.mana_cost = 3;
				card.attackpoint = 3;
				card.defensepoint = 0;
				card.lifepoint = 2;
				card.material = 1;
				mDeck.add(card);
			}
			else if(i >= 27 && i < 30){
				CardInfo card = new CardInfo();
				card.card_id = 10;
				card.card_category = AppValues.CARD_CREATURE;
				card.mana_cost = 4;
				card.attackpoint = 3;
				card.defensepoint = 1;
				card.lifepoint = 3;
				card.material = 2;
				mDeck.add(card);
			}
		}
	}
	
	public CardInfo getCardFromDeck() {
		return mDeck.remove(mDeck.size() - 1);
	}
	
	public void putCardToDeck(CardInfo cardinfo) {
		mDeck.add(cardinfo);
	}
	
	public void suffleDeck() {
		mDeck = suffleDeck(mDeck);
	}
	
	public ArrayList<CardInfo> suffleDeck(ArrayList<CardInfo> library) {
		ArrayList<CardInfo> tmpList = new ArrayList();
		Random random = new Random();
		
		while( library.size() > 0) {
			int r = random.nextInt( library.size() );
			tmpList.add( library.remove(r) );
		}
		
		for (CardInfo card : tmpList) {
			library.add(card);
		}
		
		return library;
	}
	
	public int getDeckSize() {
		return mDeck.size();
	}
}