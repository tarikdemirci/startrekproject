package io.tarik.startrekproject.runner;

import io.tarik.startrekproject.klingon.service.KlingonService;
import io.tarik.startrekproject.stapi.service.StapiService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CliRunner implements CommandLineRunner{

    private final StapiService stapiService;
    private final KlingonService klingonService;

    @Autowired
    public CliRunner(StapiService stapiService, KlingonService klingonService) {
        this.stapiService = stapiService;
        this.klingonService = klingonService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length > 0) {
            String name = args[0];
            klingonService.translateToKlingon(name)
                    .ifPresent(klingonChars -> System.out.println(StringUtils.join(klingonChars, " ")));
            stapiService.getSpeciesOfName(args[0])
                    .ifPresent(species -> System.out.println(StringUtils.join(species, " ")));
        } else {
            System.out.println("Please specify <name>");
        }

    }
}
