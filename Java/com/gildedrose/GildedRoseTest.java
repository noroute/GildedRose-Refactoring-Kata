package com.gildedrose;

import static org.junit.Assert.*;

import org.junit.Test;

public class GildedRoseTest {

    @Test
    public void updateQualityWorksWithoutItems() {
        // Arrange
        GildedRose app = new GildedRose(withNoItems());

        // Act
        app.updateQuality();

        // Assert
        assertTrue(true);
    }

    @Test
    public void updateQualityDecreasesSellIn() {
        // Arrange
        GildedRose app = new GildedRose(withDefaultItem(1, 0));

        // Act
        app.updateQuality();

        // Assert
        assertEquals(0, app.items[0].sellIn);
    }

    @Test
    public void updateQualityDecreasesQuality() {
        // Arrange
        GildedRose app = new GildedRose(withDefaultItem(0, 1));

        // Act
        app.updateQuality();

        // Assert
        assertEquals(0, app.items[0].quality);
    }

    @Test
    public void updateQualityDecreasesQualityByTwoIfSellInIsZero() {
        // Arrange
        GildedRose app = new GildedRose(withDefaultItem(0, 8));

        // Act
        app.updateQuality();

        // Assert
        assertEquals(6, app.items[0].quality);
    }

    @Test
    public void updateQualityNeverDecreasesQualityBelowZero() {
        // Arrange
        GildedRose app = new GildedRose(withDefaultItem(0, 0));

        // Act
        app.updateQuality();

        // Assert
        assertEquals(0, app.items[0].quality);
    }

    @Test
    public void agedBrieGainsQualityByAge() {
        // Arrange
        GildedRose app = new GildedRose(withAgedBrieItem(1, 0));

        // Act
        app.updateQuality();

        // Assert
        assertEquals(1, app.items[0].quality);
    }

    @Test
    public void agedBrieGainsTwoQualityAfterSellIn() {
        // Arrange
        GildedRose app = new GildedRose(withAgedBrieItem(-1, 48));

        // Act
        app.updateQuality();

        // Assert
        assertEquals(50, app.items[0].quality);
    }

    @Test
    public void anAgedBriesQualityIsNeverMoreThanFifty() {
        // Arrange
        GildedRose app = new GildedRose(withAgedBrieItem(1, 50));

        // Act
        app.updateQuality();

        // Assert
        assertEquals(50, app.items[0].quality);
    }

    @Test
    public void anAgedBriesQualityInitializedToMoreThanFiftyKeepsTheQuality() {
        // Arrange
        GildedRose app = new GildedRose(withAgedBrieItem(1, 51));

        // Act
        app.updateQuality();

        // Assert
        assertEquals(51, app.items[0].quality);
    }

    @Test
    public void aDefaultItemsInitalizedWithQualityOverFiftyDecreasesNormally() {
        // Arrange
        GildedRose app = new GildedRose(withDefaultItem(1, 52));

        // Act
        app.updateQuality();

        // Assert
        assertEquals(51, app.items[0].quality);
    }

    @Test
    public void sulfurasQualityAndSellInNeverChange() {
        // Arrange
        GildedRose app = new GildedRose(withSulfurasItem(1, 2));

        // Act
        app.updateQuality();

        // Assert
        assertSellInAndQuality(app.items[0], 1, 2);
    }

    @Test
    public void sulfurasKeepsNegativeProperties() {
        // Arrange
        GildedRose app = new GildedRose(withSulfurasItem(-1, -2));

        // Act
        app.updateQuality();

        // Assert
        assertSellInAndQuality(app.items[0], -1, -2);
    }

    @Test
    public void backStagePassesIncreaseInValue() {
        // Arrange
        GildedRose app = new GildedRose(withBackstageItem(20, 5));

        // Act
        app.updateQuality();

        // Assert
        assertEquals(6, app.items[0].quality);
    }

    @Test
    public void backStagePassesIncreaseInValueByTwoIf10DaysOrLessLeft() {
        // Arrange
        GildedRose app = new GildedRose(withBackstageItem(10, 5));

        // Act
        app.updateQuality();

        // Assert
        assertEquals(7, app.items[0].quality);
    }

    @Test
    public void backStagePassesIncreaseInValueByThreeIf5DaysOrLessLeft() {
        // Arrange
        GildedRose app = new GildedRose(withBackstageItem(5, 5));

        // Act
        app.updateQuality();

        // Assert
        assertEquals(8, app.items[0].quality);
    }

    @Test
    public void backStagePassesDropsToZeroAfterTheConcert() {
        // Arrange
        GildedRose app = new GildedRose(withBackstageItem(0, 5));

        // Act
        app.updateQuality();

        // Assert
        assertEquals(0, app.items[0].quality);
    }

    private Item[] withNoItems() {

        return new Item[] {};
    }

    private Item[] withDefaultItem(int sellIn, int quality)    {
            return withItem("foo", sellIn, quality);
    }

    private Item[] withAgedBrieItem(int sellIn, int quality)    {
            return withItem("Aged Brie", sellIn, quality);
    }

    private Item[] withBackstageItem(int sellIn, int quality)    {
            return withItem("Backstage passes to a TAFKAL80ETC concert", sellIn, quality);
    }

    private Item[] withSulfurasItem(int sellIn, int quality)    {
            return withItem("Sulfuras, Hand of Ragnaros", sellIn, quality);
    }

    private Item[] withItem(String name, int sellIn, int quality)    {
            return new Item[] {new Item(name, sellIn, quality)};
    }

    private void assertSellInAndQuality(final Item item, final int expectedSellIn, final int expectedQuality) {

        assertEquals(expectedSellIn, item.sellIn);
        assertEquals(expectedQuality, item.quality);
    }
}
