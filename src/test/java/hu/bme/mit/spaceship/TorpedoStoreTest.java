package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class TorpedoStoreTest {
    @Test
    void initialCountAndNonEmptyState() {
        TorpedoStore store = new TorpedoStore(3, 0.0);
        assertEquals(3, store.getTorpedoCount());
        assertFalse(store.isEmpty());
    }

    @Test
    void initiallyEmptyStore() {
        TorpedoStore store = new TorpedoStore(0, 0.0);
        assertEquals(0, store.getTorpedoCount());
        assertTrue(store.isEmpty());
    }

    @Test
    void successfulShotConsumesOneTorpedo() {
        TorpedoStore store = new TorpedoStore(3, 0.0);
        assertTrue(store.fire(1));
        assertEquals(2, store.getTorpedoCount());
        assertFalse(store.isEmpty());
    }

    @Test
    void successfulVolleyConsumesRequestedAmount() {
        TorpedoStore store = new TorpedoStore(5, 0.0);
        assertTrue(store.fire(3));
        assertEquals(2, store.getTorpedoCount());
    }

    @Test
    void firingAllRemainingTorpedoesEmptiesStore() {
        TorpedoStore store = new TorpedoStore(2, 0.0);
        assertTrue(store.fire(2));
        assertEquals(0, store.getTorpedoCount());
        assertTrue(store.isEmpty());
        assertThrows(IllegalArgumentException.class, () -> store.fire(1));
    }

    @Test
    void failedShotPreservesAmmunition() {
        TorpedoStore store = new TorpedoStore(3, 1.0);
        assertFalse(store.fire(1));
        assertEquals(3, store.getTorpedoCount());
        assertFalse(store.isEmpty());
    }

    @Test
    void failedVolleyPreservesAmmunition() {
        TorpedoStore store = new TorpedoStore(3, 1.0);
        assertFalse(store.fire(3));
        assertEquals(3, store.getTorpedoCount());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, 4})
    void invalidFiringAmountDoesNotChangeState(int amount) {
        TorpedoStore store = new TorpedoStore(3, 0.0);
        assertThrows(IllegalArgumentException.class, () -> store.fire(amount));
        assertEquals(3, store.getTorpedoCount());
    }

    @Test
    void cannotFireFromInitiallyEmptyStore() {
        TorpedoStore store = new TorpedoStore(0, 0.0);
        assertThrows(IllegalArgumentException.class, () -> store.fire(1));
        assertEquals(0, store.getTorpedoCount());
        assertTrue(store.isEmpty());
    }
}
