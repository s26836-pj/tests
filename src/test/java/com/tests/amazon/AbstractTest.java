package com.tests.amazon;

import com.zebrunner.carina.utils.R;

public class AbstractTest {
    public boolean isMobile() {
        return R.CONFIG.getBoolean("mobile_view");
    }

}
