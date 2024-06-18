package com.codechrono.idea.plugin.test;


import com.intellij.openapi.diagnostic.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListenerExample implements NativeKeyListener {

    private static final Logger LOG = Logger.getInstance(GlobalKeyListenerExample.class);

    public GlobalKeyListenerExample() throws NativeHookException {
        System.out.println("测试*******.GlobalKeyListenerExample" );
        GlobalScreen.registerNativeHook();
        System.out.println("测试*******.GlobalKeyListenerExample" );
    }

    public static void main1() {
        System.out.println("测试*******.");
        LOG.debug("测试*******3");
        LOG.info("测试*******2");
        int a = 2 + 3;
        try {
            if (a < 8) {
                LOG.debug("测试*******2");
                System.out.println("测试*******." + a);
            } else {
                LOG.debug("测试*******");
                System.out.println("测试*******." + a);
            }
        } catch (Exception ex)
        {
            System.err.println(ex.getMessage());
        }

/*        try {
            // 注册全局键盘监听器
            GlobalScreen.registerNativeHook();

        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }*/

        //  GlobalScreen.addNativeKeyListener(new GlobalKeyListenerExample());
        LOG.debug("测试*****最后**");
        System.out.println("Global keyboard listener registered.");
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
        System.out.println("Key Typed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }
}
