package theseinitiatives.atma.client;

import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelStore;
import android.content.res.Resources;
import android.os.Handler;
import androidx.fragment.app.FragmentController;
import androidx.loader.app.LoaderManager;
import androidx.collection.SimpleArrayMap;
import androidx.collection.SparseArrayCompat;
import androidx.appcompat.app.AppCompatDelegate;
import android.widget.Button;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static android.test.MoreAsserts.assertNotContainsRegex;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoginActivityTest {
    @Mock
    EditText edtUsername;
    @Mock
    EditText edtPassword;
    @Mock
    Button btnLogin;
    @Mock
    AppCompatDelegate mDelegate;
    @Mock
    Resources mResources;
    @Mock
    Handler mHandler;
    @Mock
    FragmentController mFragments;
    @Mock
    LoaderManager mLoaderManager;
    @Mock
    ViewModelStore mViewModelStore;
    @Mock
    SparseArrayCompat<String> mPendingFragmentActivityResults;
    @Mock
    SimpleArrayMap<Class<? extends SupportActivity.ExtraData>, SupportActivity.ExtraData> mExtraDataMap;
    @Mock
    LifecycleRegistry mLifecycleRegistry;
    @InjectMocks
    LoginActivity loginActivity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testOnCreate() throws Exception {
//        loginActivity.onCreate(null);

    }

    @Test
    public void testvalidateLogin() throws Exception {

        assertThat(loginActivity.validateLogin("atma","satu234"), is(true));
    }


}
