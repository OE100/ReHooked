package com.oe.rehooked.item.hooks.impl;

import com.oe.rehooked.item.hooks.def.BaseHookItem;
import com.oe.rehooked.item.hooks.def.HookProperties;

public class IronHookItem extends BaseHookItem {
    public static final String NAME = "iron_hook";
    
    public IronHookItem() {
        super(new HookProperties() {
            @Override
            public String DisplayName() {
                return "Iron Hook";
            }

            @Override
            public int Count() {
                return 2;
            }

            @Override
            public double Range() {
                return 16;
            }

            @Override
            public double Speed() {
                return 16;
            }

            @Override
            public double PullSpeed() {
                return 8;
            }

            @Override
            public int Cooldown() {
                return 50;
            }
        });
    }
}
