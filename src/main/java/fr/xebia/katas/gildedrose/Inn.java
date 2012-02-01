package fr.xebia.katas.gildedrose;

import static com.google.common.collect.ImmutableList.*;
import static com.google.common.collect.Lists.*;
import java.util.ArrayList;
import java.util.List;

public class Inn {
	static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
	static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
	static final String AGED_BRIE = "Aged Brie";
	static final String CONJURED = "Conjured";
	private final List<Item> items;

	public List<Item> getItems() {
		return copyOf(items);
	}

	public Inn(List<Item> items) {
		this.items = items;
	}

	public void updateQuality() {
		for (int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			if (!newArrayList(AGED_BRIE, BACKSTAGE_PASSES).contains(item.getName())) {
				decreaseQuality(item);
			} else {

				increaseQuality(item);

				if (item.getName().equals(BACKSTAGE_PASSES)) {
					if (item.getSellIn() < 11) {
						increaseQuality(item);
					}

					if (item.getSellIn() < 6) {
						increaseQuality(item);
					}
				}
			}

			if (!item.getName().equals(SULFURAS)) {
				item.setSellIn(item.getSellIn() - 1);
			}

			if (item.getSellIn() < 0) {
				if (!item.getName().equals(AGED_BRIE)) {
					if (!item.getName().equals(BACKSTAGE_PASSES)) {
						decreaseQuality(item);
					} else {
						item.setQuality(0);
					}
				} else {
					increaseQuality(item);
				}
			}
		}

	}

	private void decreaseQuality(Item item) {
		if (!item.getName().equals(SULFURAS)) {
			item.setQuality(item.getQuality() - 1);
		}
		if (item.getName().equals(CONJURED)) {
			item.setQuality(item.getQuality() - 1);
		}

		if (item.getQuality() < 0) {
			item.setQuality(0);
		}
	}

	private void increaseQuality(Item item) {
		if (item.getQuality() < 50) {
			item.setQuality(item.getQuality() + 1);
		}
	}

	public static void main(String[] args) {
		System.out.println("OMGHAI!");
		Inn inn = initInn();
		inn.updateQuality();
	}

	public static Inn initInn() {
		List<Item> items = new ArrayList<Item>();
		items.add(new Item("+5 Dexterity Vest", 10, 20));
		items.add(new Item(AGED_BRIE, 2, 0));
		items.add(new Item("Elixir of the Mongoose", 5, 7));
		items.add(new Item(SULFURAS, 0, 80));
		items.add(new Item(BACKSTAGE_PASSES, 15, 20));
		items.add(new Item("Conjured Mana Cake", 3, 6));
		Inn inn = new Inn(items);
		return inn;
	}

}
