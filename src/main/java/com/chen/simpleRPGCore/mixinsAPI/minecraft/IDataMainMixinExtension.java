package com.chen.simpleRPGCore.mixinsAPI.minecraft;

import java.util.concurrent.atomic.AtomicBoolean;

public interface IDataMainMixinExtension {
    AtomicBoolean isRunData = new AtomicBoolean(false);
}
