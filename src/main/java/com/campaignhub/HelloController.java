
package com.campaignhub;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String accueil() {
        return "Bienvenue sur CampaignHub !";
    }
}
