package app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.MouseEvent;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// import app.*;
// import config.*;
// import database.*;
// import features.*;
// import ui.*;

public class CityBuilderTest {

    private CityBuilder cityBuilder;

    @BeforeEach
    public void setUp() {

        cityBuilder = new CityBuilder();
    }

    @Test
    public void testInitialSetup() {
        assertNotNull(cityBuilder);
        assertEquals(1400, cityBuilder.getGameWidth());
        assertEquals(600, cityBuilder.getGameHeight());
        assertEquals(40, cityBuilder.getTileSize());
        assertNotNull(cityBuilder.getCityMap());
    }


    @Test
    public void testMouseListener() {
        MouseEvent clickEvent = mock(MouseEvent.class);
        when(clickEvent.getX()).thenReturn(50);
        when(clickEvent.getY()).thenReturn(50);

        cityBuilder.getMouseListeners()[0].mouseClicked(clickEvent);
    }
}
