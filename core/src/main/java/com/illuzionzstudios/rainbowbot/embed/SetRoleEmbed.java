package com.illuzionzstudios.rainbowbot.embed;

import com.illuzionzstudios.discordlibrary.embed.Embed;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;

/**
 * Copyright © 2020 Property of Illuzionz Studios, LLC
 * All rights reserved. No part of this publication may be reproduced, distributed, or
 * transmitted in any form or by any means, including photocopying, recording, or other
 * electronic or mechanical methods, without the prior written permission of the publisher,
 * except in the case of brief quotations embodied in critical reviews and certain other
 * noncommercial uses permitted by copyright law. Any licensing of this software overrides
 * this statement.
 */

public class SetRoleEmbed extends Embed {

    @Override
    public MessageEmbed createEmbed() {
        return null;
    }

    public MessageEmbed createEmbed(Role role) {
        embed.setTitle("Rainbow Bot");

        embed.addField("SUCCESS", "Set rainbow role to " + role.getAsMention(), false);

        embed.setFooter("Illuzionz Studios");

        return embed.build();
    }
}
