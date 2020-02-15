package com.illuzionzstudios.rainbowbot.controller;

import com.illuzionzstudios.rainbowbot.RainbowBot;
import lombok.Setter;
import net.dv8tion.jda.api.entities.Role;

import java.awt.*;
import java.util.ConcurrentModificationException;

/**
 * Copyright © 2020 Property of Illuzionz Studios, LLC
 * All rights reserved. No part of this publication may be reproduced, distributed, or
 * transmitted in any form or by any means, including photocopying, recording, or other
 * electronic or mechanical methods, without the prior written permission of the publisher,
 * except in the case of brief quotations embodied in critical reviews and certain other
 * noncommercial uses permitted by copyright law. Any licensing of this software overrides
 * this statement.
 */

/**
 * Thread for running rainbow stuff on
 */
public class RainbowThread implements Runnable {

    /**
     * Force stop the loop
     */
    @Setter
    public boolean stopped = false;

    @Override
    public void run() {
        while (!stopped) {
            // Wake up the thread because apparently it
            // doesn't think it exists
            try {
                Thread.currentThread().notify();
            } catch (IllegalMonitorStateException ignored) {
            }

            try {
                // Only run if enabled
                if (RainbowController.INSTANCE.isEnabled()) {
                    if (RainbowController.INSTANCE.getRoles() == null || RainbowController.INSTANCE.getRoles().isEmpty())
                        continue;

                    for (Color color : RainbowController.INSTANCE.getRainbowColours()) {
                        for (Role role : RainbowController.INSTANCE.getRoles().values()) {
                            // Set the colour
                            try {
                                role.getManager().setColor(color).complete();
                            } catch (Exception ignored) {
                                RainbowBot.getInstance().forceBuild();
                            }
                        }
                    }

                }

            } catch (ConcurrentModificationException ignored) {
            }
        }
    }
}
