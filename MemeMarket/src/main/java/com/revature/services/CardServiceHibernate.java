package com.revature.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.beans.Card;
import com.revature.beans.PackTier;
import com.revature.beans.Rarity;
import com.revature.beans.Tag;
import com.revature.beans.User;
import com.revature.data.CardDao;
import com.revature.data.PackTierDao;
import com.revature.data.TagDao;

@Service
public class CardServiceHibernate implements CardService {
	@Autowired
	private OwnedCardService ocs;
	@Autowired
	private CardDao cd;
	@Autowired
	private PackTierDao ptd;
	@Autowired
	private TagDao td;

	@Override
	public int addCard(Card c) {
		for (Tag t : c.getTag()) {
			Tag test = td.getTagByName(t.getName());
			if (test == null) {
				// Tag doesn't exist yet, add it
				td.addTag(t);
			} else {
				// Tag already exists, set id
				t.setId(test.getId());
			}
		}
		return cd.addCard(c);
	}

	@Override
	public Card getCard(int id) {
		Card c = cd.getCard(id);
		String s[] = c.getMemeText().split("<br>");
		if(s != null && s.length > 0) {
			String memeText = s[0];
			for(int i=1;i<s.length;i++) {
				memeText = memeText + "\n";
				memeText = memeText + s[i];
			}
			c.setMemeText(memeText);
		}
		return c;
	}

	@Override
	public List<Card> genCardPack(int packTierId, User u){
		PackTier pt = ptd.getPackTier(packTierId);
		return genCardPack(pt, u);
	}
	
	@Override
	public List<Card> genCardPack(PackTier pt, User u){
		if(u.getPatron().getStonks() < pt.getTierPrice()) {
			return null;
		}
		u.getPatron().setStonks(u.getPatron().getStonks() - pt.getTierPrice());
		List<Card> cardPack = new ArrayList<>();
		List<Rarity> rl = new ArrayList<>(pt.getPackRarities());
		int totalWeight = 0;
		int randWeightNumber;
		Map<Rarity, List<Card>> crm = new HashMap<>();
		
		for(int i=0;i<rl.size();i++) {
			totalWeight += rl.get(i).getWeight();
			List<Card> cs = cd.getCardsByRarity(rl.get(i));
			crm.put(rl.get(i), new ArrayList<Card>(cs));
		}
		Random rand = new Random();
		for(int i=0;i<pt.getNumOfCards();i++) {
			randWeightNumber = rand.nextInt(totalWeight);
			int subTotalWeight = 0;
			for(int j=0;i<rl.size();j++) {
				if(randWeightNumber >= subTotalWeight && randWeightNumber < subTotalWeight + rl.get(j).getWeight()) {
					List<Card> cl = crm.get(rl.get(j));
					Card c = cl.get(rand.nextInt(cl.size()));
					cardPack.add(c);
					ocs.addOwnedCard(c, u);
					break;
				}
				subTotalWeight += rl.get(j).getWeight();
			}
			
		}
		return cardPack;
	}

	@Override
	public boolean updateCard(Card c) {
		return cd.updateCard(c);
	}

	@Override
	public boolean deleteCard(Card c) {
		return cd.deleteCard(c);
	}

	@Override
	public Set<Card> getCards() {
		return cd.getCards();
	}

}
