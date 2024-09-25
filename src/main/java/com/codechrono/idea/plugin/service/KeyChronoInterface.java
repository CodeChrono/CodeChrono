package com.codechrono.idea.plugin.service;

import com.codechrono.idea.plugin.entity.KeyChrono;

public interface KeyChronoInterface extends CommonInterface<KeyChrono> {
    KeyChrono findByKeyCode(String keyCode);
}
