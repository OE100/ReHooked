package com.oe.rehooked.item.hooks.impl;

import com.oe.rehooked.item.hooks.def.BaseHookItem;
import com.oe.rehooked.item.hooks.def.HookProperties;

public class WoodHookItem extends BaseHookItem {
    public static final String NAME = "wood_hook";
    
    public WoodHookItem() {
        super(new HookProperties() {
            @Override
            public String DisplayName() {
                return "Wood Hook";
            }

            @Override
            public int Count() {
                return 1;
            }

            @Override
            public double Range() {
                return 8;
            }

            @Override
            public double Speed() {
                return 8;
            }

            @Override
            public double PullSpeed() {
                return 4;
            }

            @Override
            public int Cooldown() {
                return 100;
            }
        });
    }
}