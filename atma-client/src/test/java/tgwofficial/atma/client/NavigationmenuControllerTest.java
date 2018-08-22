package tgwofficial.atma.client;

import android.app.Activity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class NavigationmenuControllerTest {
    @Mock
    Activity activity;
    @InjectMocks
    NavigationmenuController navigationmenuController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testStartIdentitasIbu() throws Exception {
        navigationmenuController.startIdentitasIbu();
    }

    @Test
    public void testStartTransportasi() throws Exception {
        navigationmenuController.startTransportasi();
    }
}
