package fr.xebia.katas.gildedrose;

import static fr.xebia.katas.gildedrose.Inn.*;
import static org.fest.assertions.Assertions.*;
import org.junit.Test;
import com.google.common.collect.Lists;

public class InnTest {
	@Test
	public void shouldDecreaseItemQualityAndSellIn() {
		Item item = new Item("item1", 2, 2);

		new Inn(Lists.newArrayList(item)).updateQuality();

		assertThat(item.getQuality()).isEqualTo(1);
		assertThat(item.getSellIn()).isEqualTo(1);
	}

	@Test
	public void shouldDecreaseItemQualityBy2AfterSellOut() {
		Item item = new Item("item1", 0, 10);

		new Inn(Lists.newArrayList(item)).updateQuality();

		assertThat(item.getQuality()).isEqualTo(8);
		assertThat(item.getSellIn()).isEqualTo(-1);
	}

	@Test
	public void shouldNeverHaveNegativeQuality() {
		Item item = new Item("item1", 2, 0);

		new Inn(Lists.newArrayList(item)).updateQuality();

		assertThat(item.getQuality()).isEqualTo(0);
		assertThat(item.getSellIn()).isEqualTo(1);
	}

	@Test
	public void shouldNotBeGreaterThan50() {
		Item item = new Item(AGED_BRIE, 5, 50);

		new Inn(Lists.newArrayList(item)).updateQuality();

		assertThat(item.getQuality()).isEqualTo(50);
		assertThat(item.getSellIn()).isEqualTo(4);
	}

	@Test
	public void shouldIncreaseBrieQuality() {
		Item item = new Item(AGED_BRIE, 2, 0);

		new Inn(Lists.newArrayList(item)).updateQuality();

		assertThat(item.getQuality()).isGreaterThan(0);
	}

	@Test
	public void shouldNotDecreaseSulfurasQualityNorSellIn() {
		Item item = new Item(SULFURAS, 2, 3);

		new Inn(Lists.newArrayList(item)).updateQuality();

		assertThat(item.getQuality()).isEqualTo(3);
		assertThat(item.getSellIn()).isEqualTo(2);
	}

	@Test
	public void shouldIncreaseBackstagePassQuality() {
		Item item = new Item(BACKSTAGE_PASSES, 11, 5);

		new Inn(Lists.newArrayList(item)).updateQuality();

		assertThat(item.getQuality()).isEqualTo(6);
		assertThat(item.getSellIn()).isEqualTo(10);
	}

	@Test
	public void shouldIncreaseBackstagePassQualityAfter10DaysOrLess() {
		Item item = new Item(BACKSTAGE_PASSES, 8, 5);

		new Inn(Lists.newArrayList(item)).updateQuality();

		assertThat(item.getQuality()).isEqualTo(7);
		assertThat(item.getSellIn()).isEqualTo(7);
	}

	@Test
	public void shouldIncreaseBackstagePassQualityAfter5DaysOrLess() {
		Item item = new Item(BACKSTAGE_PASSES, 3, 10);

		new Inn(Lists.newArrayList(item)).updateQuality();

		assertThat(item.getQuality()).isEqualTo(13);
		assertThat(item.getSellIn()).isEqualTo(2);
	}

	@Test
	public void shouldIncreaseBackstagePassQualityAfterConcert() {
		Item item = new Item(BACKSTAGE_PASSES, 0, 10);

		new Inn(Lists.newArrayList(item)).updateQuality();

		assertThat(item.getQuality()).isEqualTo(0);
		assertThat(item.getSellIn()).isEqualTo(-1);
	}

	@Test
	public void shouldDecreaseConjuredItemsTwiceAsFast() {
		Item item = new Item(CONJURED, 2, 5);

		new Inn(Lists.newArrayList(item)).updateQuality();

		assertThat(item.getQuality()).isEqualTo(3);
		assertThat(item.getSellIn()).isEqualTo(1);
	}

	@Test
	public void shouldDecreaseConjuredItemsTwiceAsFastAfterSellOut() {
		Item item = new Item(CONJURED, 0, 5);

		new Inn(Lists.newArrayList(item)).updateQuality();

		assertThat(item.getQuality()).isEqualTo(1);
		assertThat(item.getSellIn()).isEqualTo(-1);
	}

	@Test
	public void shouldDecreaseConjuredItemsTwiceAsFastAfterSellOutAndQualityStillPositive() {
		Item item = new Item(CONJURED, 0, 3);

		new Inn(Lists.newArrayList(item)).updateQuality();

		assertThat(item.getQuality()).isEqualTo(0);
		assertThat(item.getSellIn()).isEqualTo(-1);
	}

}
